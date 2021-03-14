package com.example.leadsdoittest.start

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leadsdoittest.database.UsersDatabaseDao

class StartFragmentViewModelFactory(
    private val dataSource: UsersDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartFragmentViewModel::class.java)) {
            return StartFragmentViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}