package com.projects.chat_app.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.projects.chat_app.R
import com.projects.chat_app.ui.base.BaseActivity
import com.projects.chat_app.databinding.ActivityRegisterBinding
import com.projects.chat_app.ui.home.HomeActivity
import com.projects.chat_app.ui.login.LoginActivity
import com.projects.chat_app.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        val viewModel: RegisterViewModel by viewModels()
        return viewModel
    }

    override fun goToHome() {
        val intent= Intent(this@RegisterActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun backToPreviousScreen() {
        val intent= Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}