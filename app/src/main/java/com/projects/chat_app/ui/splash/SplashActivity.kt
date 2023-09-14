package com.projects.chat_app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.projects.chat_app.R
import com.projects.chat_app.databinding.ActivitySplashBinding
import com.projects.chat_app.ui.base.BaseActivity
import com.projects.chat_app.ui.home.HomeActivity
import com.projects.chat_app.ui.login.LoginActivity
import com.projects.chat_app.ui.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding,SplashViewModel>(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator=this
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.navigate()
        },1000)
    }

    override fun goToHome() {
        val intent=Intent(this@SplashActivity,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goToLogin() {
        val intent=Intent(this@SplashActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun getLayoutId(): Int =R.layout.activity_splash

    override fun generateViewModel(): SplashViewModel {
        val viewModel: SplashViewModel by viewModels()
        return viewModel
    }
}