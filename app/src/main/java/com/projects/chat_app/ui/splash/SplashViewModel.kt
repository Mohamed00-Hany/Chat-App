package com.projects.chat_app.ui.splash

import androidx.lifecycle.viewModelScope
import com.projects.domain.models.User
import com.projects.data.repositories.user.UserRepositoryImpl
import com.projects.data.repositories.user.UserDataSourceImpl
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.user.UserDataSource
import com.projects.domain.repositoriesContract.user.UserRepository
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.chat_app.ui.UserProvider
import com.projects.domain.useCases.GetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getUser: GetUser): BaseViewModel<Navigator>() {

    fun navigate() {
        if (auth.currentUser == null) {
            navigator?.goToLogin()
            return
        }
        viewModelScope.launch {
            getUser(auth.currentUser?.uid!!).collect {
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