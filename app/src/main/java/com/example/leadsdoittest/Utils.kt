package com.example.leadsdoittest

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

class Utils {
    companion object {
        @JvmStatic
        @BindingAdapter("app:errorText")
        fun setErrorMessage(view: TextInputLayout, errorMessage: String) {
            view.error = errorMessage
        }
    }
}