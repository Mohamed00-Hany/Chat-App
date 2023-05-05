package com.projects.chat_app.ui.splash

import android.util.Log
import com.projects.chat_app.database.models.User
import com.projects.chat_app.ui.base.BaseViewModel
import com.projects.chat_app.ui.UserProvider

class SplashViewModel:BaseViewModel<Navigator>() {
    fun navigate() {
        if(auth.currentUser==null)
        {
            navigator?.goToLogin()
            return
        }
        dataBase.getUser(auth.currentUser?.uid!!).addOnCompleteListener{task->
            if(task.isSuccessful)
            {
                val user=task.result.toObject(User::class.java)
                UserProvider.user=user
                navigator?.goToHome()
            }
            else
            {
                navigator?.goToLogin()
            }
        }
    }
}