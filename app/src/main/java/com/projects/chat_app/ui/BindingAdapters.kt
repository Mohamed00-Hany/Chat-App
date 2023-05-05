package com.projects.chat_app.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("error")
fun showError(textInputLayout: TextInputLayout,errorMessage:String?)
{
    textInputLayout.error=errorMessage
}

@BindingAdapter("visibility")
fun showBackButton(imageView: ImageView,isVisible:Boolean)
{
    imageView.isVisible=isVisible
}

@BindingAdapter("textId")
fun bindTextById(textView: TextView,textId:Int)
{
    textView.setText(textId)
}

@BindingAdapter("imageId")
fun bindImageById(imageView: ImageView,imageId:Int)
{
    imageView.setImageResource(imageId)
}