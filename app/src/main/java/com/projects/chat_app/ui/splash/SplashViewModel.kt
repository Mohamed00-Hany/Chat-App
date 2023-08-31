package com.projects.chat_app.ui.splash

import androidx.lifecycle.viewModelScope
import com.projects.chat_app.database.models.User
import com.projects.chat_app.repositories.user.UserRepositoryImpl
import com.projects.chat_app.repositories.user.UserDataSourceImpl
import com.projects.chat_app.repositoriesContract.user.OnUserTaskCompleted
import com.projects.chat_app.repositoriesContract.user.UserDataSource
import com.projects.chat_app.repositoriesContract.user.UserRepository
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.chat_app.ui.UserProvider
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
            userRepo.getUser(auth.currentUser?.uid!!,object : OnUserTaskCompleted {
                override fun onComplete() {
                    return
                }

                override fun onSuccess(user: User?) {
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

                override fun onFail(error: String?) {
                    navigator?.goToLogin()
                }

            })
        }
    }
}