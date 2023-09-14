package com.projects.chat_app.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.projects.domain.models.User
import com.projects.data.repositories.user.UserRepositoryImpl
import com.projects.data.repositories.user.UserDataSourceImpl
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.user.UserDataSource
import com.projects.domain.repositoriesContract.user.UserRepository
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.domain.useCases.GetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val getUser: GetUser) : BaseViewModel<Navigator>() {
    val email=ObservableField<String>()
    val password=ObservableField<String>()
    val emailError=ObservableField<String?>()
    val passwordError=ObservableField<String?>()

    fun login()
    {
        if(!validForm())
            return
        navigator?.showLoading("Logging In...")
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener {task->
            if(task.isSuccessful)
            {
                viewModelScope.launch {
                    getUserFromDatabase(task.result.user?.uid!!)
                }
            }
            else
            {
                navigator?.hideLoading()
                navigator?.showMessage(task.exception?.localizedMessage?:"")
            }
        }
    }

    private suspend fun getUserFromDatabase(userId:String) {
        getUser(userId).collect {
            when (it) {
                is TaskStates.TaskCompleted<*> -> {
                    navigator?.hideLoading()
                }
                is TaskStates.TaskSucceed<*> -> {
                    val user = it.data as User
                    UserProvider.user = user
                    if (UserProvider.user!=null)
                    {
                        navigator?.goToHome()
                    }
                    else
                    {
                        navigator?.showMessage("Error, user not found")
                    }
                }
                else -> {
                    val taskFailed = it as TaskStates.TaskFailed
                    navigator?.showMessage(taskFailed.error ?: "Error, user not found")
                }
            }
        }
    }


    fun validForm():Boolean
    {
        var isValid=true

        if(email.get().isNullOrBlank())
        {
            isValid=false
            emailError.set("Please enter your email")
        }
        else
        {
            isValid=true
            emailError.set(null)
        }
        if(password.get().isNullOrBlank())
        {
            isValid=false
            passwordError.set("Please enter your password")
        }
        else
        {
            isValid=true
            passwordError.set(null)
        }

        return isValid
    }
    fun goToRegister()
    {
        navigator?.goToRegister()
    }

}