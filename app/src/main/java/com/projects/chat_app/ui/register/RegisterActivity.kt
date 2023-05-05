package com.projects.chat_app.ui.register

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.projects.chat_app.R
import com.projects.chat_app.ui.base.BaseActivity
import com.projects.chat_app.databinding.ActivityRegisterBinding
import com.projects.chat_app.ui.home.HomeActivity
import com.projects.chat_app.ui.login.LoginActivity

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator=this
        viewBinding.vm=viewModel
        viewBinding.activityToolBar.vm=viewModel
    }

    override fun getLayoutId():Int
    {
        return R.layout.activity_register
    }

    override fun generateViewModel(): RegisterViewModel {
        return ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun goToHome() {
        val intent= Intent(this@RegisterActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun backToPreviousScreen() {
        super.backToPreviousScreen()
        val intent= Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}