package com.projects.chat_app.ui.login

import androidx.databinding.ObservableField
import com.projects.chat_app.database.models.User
import com.projects.chat_app.ui.UserProvider
import com.projects.chat_app.ui.base.BaseViewModel

class LoginViewModel : BaseViewModel<Navigator>() {
    val email=ObservableField<String>()
    val password=ObservableField<String>()
    val emailError=ObservableField<String?>()
    val passwordError=ObservableField<String?>()

    fun login()
    {
        if(!validForm())
            return
        navigator?.showLoading("Logging In...")
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener {task->
            if(task.isSuccessful)
            {
                getUserFromDatabase(task.result.user?.uid!!)
            }
            else
            {
                navigator?.hideLoading()
                navigator?.showMessage(task.exception?.localizedMessage?:"")
            }
        }
    }

    private fun getUserFromDatabase(userId:String) {
        val doc=dataBase.getUser(userId)

        doc.addOnCompleteListener{task->
            navigator?.hideLoading()
            if(task.isSuccessful)
            {
                val user=task.result.toObject(User::class.java)
                UserProvider.user=user
                navigator?.goToHome()
            }
            else
            {
                navigator?.showMessage(task.exception?.localizedMessage?:"")
            }
        }
    }

    fun validForm():Boolean
    {
        var isValid=true

        if(email.get().isNullOrBlank())
        {
            isValid=false
            emailError.set("Please enter your email")
        }
        else
        {
            isValid=true
            emailError.set(null)
        }
        if(password.get().isNullOrBlank())
        {
            isValid=false
            passwordError.set("Please enter your password")
        }
        else
        {
            isValid=true
            passwordError.set(null)
        }

        return isValid
    }
    fun goToRegister()
    {
        navigator?.goToRegister()
    }

}