package com.projects.chat_app.ui.register

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.Message
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositories.user.UserRepositoryImpl
import com.projects.chat_app.repositories.user.UserDataSourceImpl
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.user.UserDataSource
import com.projects.chat_app.repositoriesContract.user.UserRepository
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.chat_app.ui.isMatch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel<Navigator>() {
    val userName = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val passwordConfirmation = ObservableField<String>()
    val userNameError = ObservableField<String?>()
    val emailError = ObservableField<String?>()
    val passwordError = ObservableField<String?>()
    val passwordConfirmationError = ObservableField<String?>()

    fun register() {
        if (!validForm())
            return
        navigator?.showLoading("Loading...")
        auth.createUserWithEmailAndPassword(email.get()!!, password.get()!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        insertUserToDatabase(task.result.user?.uid!!)
                    }
                } else {
                    navigator?.hideLoading()
                    navigator?.showMessage(task.exception?.localizedMessage ?: "")
                }
            }

    }

    private val userDataSource: UserDataSource = UserDataSourceImpl(dataBase)
    private val userRepo: UserRepository = UserRepositoryImpl(userDataSource)

    private suspend fun insertUserToDatabase(userId: String) {
        val user = User(userId, userName.get(), email.get())
        userRepo.insertUser(user).collect {
            when (it) {
                is TaskStates.TaskCompleted<*> -> {
                    navigator?.hideLoading()
                }
                is TaskStates.TaskSucceed<*> -> {
                    val user = it.data as User
                    UserProvider.user = user
                    if (UserProvider.user != null) {
                        goToHome()
                    } else {
                        navigator?.showMessage("Error, user not found")
                    }
                }
                else -> {
                    val taskFailed = it as TaskStates.TaskFailed
                    navigator?.showMessage(taskFailed.error ?: "Error, can't add user")
                }
            }
        }
    }

    private fun goToHome() {
        navigator?.goToHome()
    }

    fun validForm(): Boolean {
        var isValid = true

        if (userName.get().isNullOrBlank()) {
            isValid = false
            userNameError.set("Please enter your name")
        } else {
            userNameError.set(null)
        }

        if (email.get().isNullOrBlank()) {
            isValid = false
            emailError.set("Please enter your email")
        } else if (email.get()?.isMatch() != true) {
            isValid = false
            emailError.set("The email address is badly formatted")
        } else {
            emailError.set(null)
        }

        if (password.get().isNullOrBlank()) {
            isValid = false
            passwordError.set("Please enter password")
        } else if ((password.get()?.length ?: 0) < 6) {
            isValid = false
            passwordError.set("The password should be at least 6 characters")
        } else {
            passwordError.set(null)
        }

        if (passwordConfirmation.get().isNullOrBlank()) {
            isValid = false
            passwordConfirmationError.set("Please re-enter password")
        } else if (!passwordConfirmation.get().equals(password.get())) {
            isValid = false
            passwordConfirmationError.set("Doesn't match")
        } else {
            passwordConfirmationError.set(null)
        }

        return isValid
    }
}