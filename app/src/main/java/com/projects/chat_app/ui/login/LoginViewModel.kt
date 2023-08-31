package com.projects.chat_app.ui.login

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositories.user.UserRepositoryImpl
import com.projects.chat_app.repositories.user.UserDataSourceImpl
import com.projects.chat_app.repositoriesContract.user.OnUserTaskCompleted
import com.projects.chat_app.repositoriesContract.user.UserDataSource
import com.projects.chat_app.repositoriesContract.user.UserRepository
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<Navigator>() {
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

    private val userDataSource: UserDataSource = UserDataSourceImpl(dataBase)
    private val userRepo: UserRepository = UserRepositoryImpl(userDataSource)

    private suspend fun getUserFromDatabase(userId:String) {
        userRepo.getUser(userId,object: OnUserTaskCompleted {
            override fun onComplete() {
                navigator?.hideLoading()
            }

            override fun onSuccess(user: User?) {
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

            override fun onFail(error: String?) {
                navigator?.showMessage(error?:"Error, user not found")
            }
        })
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