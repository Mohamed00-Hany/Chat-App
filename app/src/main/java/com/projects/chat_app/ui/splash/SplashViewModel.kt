package com.projects.chat_app.ui.splash

import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositories.user.UserRepositoryImpl
import com.projects.chat_app.repositories.user.UserDataSourceImpl
import com.projects.chat_app.repositoriesContract.TaskStates
import com.projects.chat_app.repositoriesContract.user.UserDataSource
import com.projects.chat_app.repositoriesContract.user.UserRepository
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.chat_app.ui.UserProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel<Navigator>() {

    private val userDataSource: UserDataSource = UserDataSourceImpl(dataBase)
    private val userRepo: UserRepository = UserRepositoryImpl(userDataSource)

    fun navigate() {
        if (auth.currentUser == null) {
            navigator?.goToLogin()
            return
        }
        viewModelScope.launch {
            userRepo.getUser(auth.currentUser?.uid!!).collect {
                when (it) {
                    is TaskStates.TaskSucceed<*> -> {
                        val user = it.data as User
                        UserProvider.user = user
                        if (UserProvider.user!=null)
                        {
                            navigator?.goToHome()
                        }
                        else
                        {
                            navigator?.goToLogin()
                        }
                    }
                    else -> {
                        navigator?.goToLogin()
                    }
                }
            }
        }
    }
}