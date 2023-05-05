package com.projects.chat_app.ui.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.projects.chat_app.R

abstract class BaseActivity<VB:ViewDataBinding,VM:ViewModel> :AppCompatActivity(), BaseNavigator {
    lateinit var viewBinding: VB
    lateinit var viewModel: VM
    var progressDialog: ProgressDialog?=null
    var alertDialog: AlertDialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=DataBindingUtil.setContentView(this,getLayoutId())
        viewModel= generateViewModel()
    }

    abstract fun getLayoutId():Int
    abstract fun generateViewModel():VM

    override fun showLoading(message: String) {
        progressDialog= ProgressDialog(this, R.style.MytDialogStyle)
        progressDialog?.setMessage(message)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog=null
    }

    override fun showMessage(message:String,posActionTitle:String?,posAction: OnDialogClickListener?,
                             negActionTitle:String?,negAction:OnDialogClickListener?) {
        val builder= AlertDialog.Builder(this,R.style.MytDialogStyle).setMessage(message).setCancelable(false)
        if(posActionTitle!=null)
        {
            builder.setPositiveButton(posActionTitle){dialog, which ->
                dialog.dismiss()
                posAction?.onClick()
            }
        }
        if(negActionTitle!=null)
        {
            builder.setNegativeButton(negActionTitle){dialog, which ->
                dialog.dismiss()
                negAction?.onClick()
            }
        }
        alertDialog=builder.show()
    }

    override fun backToPreviousScreen() {
        return
    }
}
