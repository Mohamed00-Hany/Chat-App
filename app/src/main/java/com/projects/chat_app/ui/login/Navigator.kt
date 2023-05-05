package com.projects.chat_app.ui.login

import com.projects.chat_app.ui.base.BaseNavigator

interface Navigator: BaseNavigator {
    fun goToRegister()
    fun goToHome()
}