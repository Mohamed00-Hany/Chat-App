package com.projects.chat_app.ui.base

import android.content.DialogInterface

interface BaseNavigator {
    fun showLoading(message:String)
    fun hideLoading()
    fun showMessage(message:String,posActionTitle:String?="Ok",posAction:OnDialogClickListener?=null,
                    negActionTitle:String?=null,negAction:OnDialogClickListener?=null)
    fun backToPreviousScreen()
}