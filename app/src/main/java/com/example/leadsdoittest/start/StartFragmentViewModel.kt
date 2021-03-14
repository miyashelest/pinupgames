package com.example.leadsdoittest.start

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leadsdoittest.database.User
import com.example.leadsdoittest.database.UsersDatabaseDao
import kotlinx.coroutines.*

class StartFragmentViewModel(
    val database: UsersDatabaseDao,
    application: Application,
) : AndroidViewModel(application) {

    val name: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = MutableLiveData()
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _navigateToWebView = MutableLiveData<User>()
    val navigateToWebView: LiveData<User>
        get() = _navigateToWebView

    fun doneNavigation() {
        uiScope.launch {
            val user = User()
            user.userName = name.value.toString()
            user.userEmail = email.value.toString()
            user.userPhone = email.value.toString()
            _navigateToWebView.value = user
            insert(user)
            Log.d("DB", " $user")
        }
    }

    private suspend fun insert(user: User) {
        withContext(Dispatchers.IO) {
            database.insert(user)
        }
    }

    val errors = ObservableArrayList<Errors>()

    enum class Errors {
        INVALID_NAME,
        INVALID_EMAIL,
        INVALID_PHONE_NUMBER
    }

    fun isFormValid(): Boolean {
        errors.clear()
        if (name.value.isNullOrEmpty()) {
            errors.add(Errors.INVALID_NAME)
        }
        if (phone.value.isNullOrEmpty() || !isPhoneValid(phone.value.toString())) {
            errors.add(Errors.INVALID_PHONE_NUMBER)
        }
        if (email.value.isNullOrEmpty() || !isEmailValid(email.value.toString())) {
            errors.add(Errors.INVALID_EMAIL)
        }
        return errors.isEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPhoneValid(number: String): Boolean {
        return Patterns.PHONE.matcher(number).matches()
    }

}