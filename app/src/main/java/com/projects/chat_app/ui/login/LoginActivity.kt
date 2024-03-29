package com.projects.chat_app.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.projects.chat_app.R
import com.projects.chat_app.ui.base.BaseActivity
import com.projects.chat_app.databinding.ActivityLoginBinding
import com.projects.chat_app.ui.home.HomeActivity
import com.projects.chat_app.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator=this
        viewBinding.vm=viewModel
        viewBinding.activityToolBar.vm=viewModel
    }

    override fun goToHome()
    {
        val intent= Intent(this@LoginActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getLayoutId():Int
    {
        return R.layout.activity_login
    }

    override fun generateViewModel(): LoginViewModel {
        val viewModel:LoginViewModel by viewModels()
        return viewModel
    }

    override fun goToRegister() {
        val intent= Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

}