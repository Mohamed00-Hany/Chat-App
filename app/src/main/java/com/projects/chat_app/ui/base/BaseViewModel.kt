package com.projects.chat_app.ui.base

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.projects.chat_app.database.FireStoreUtils

open class BaseViewModel<Nav: BaseNavigator>:ViewModel() {
    var navigator: Nav?=null
    val auth= FirebaseAuth.getInstance()
    val dataBase=FireStoreUtils.getInstance()

    open fun backToPreviousScreen()
    {
        navigator?.backToPreviousScreen()
    }

}