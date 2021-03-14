package com.example.leadsdoittest.start

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.leadsdoittest.R
import com.example.leadsdoittest.database.UsersDatabase
import com.example.leadsdoittest.databinding.StartFragmentBinding

class StartFragment : Fragment() {

    private val application by lazy { requireNotNull(this.activity).application }
    private val dataSource by lazy { UsersDatabase.getInstance(application).usersDatabaseDao }
    private val viewModelFactory by lazy { StartFragmentViewModelFactory(dataSource, application) }
    private val startFragmentViewModel by lazy {
        ViewModelProvider(
            this, viewModelFactory
        ).get(StartFragmentViewModel::class.java)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: StartFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.start_fragment, container, false
        )


        binding.startFragmentViewModel = startFragmentViewModel
        binding.lifecycleOwner = this

        createChannel(
            getString(R.string.notification_channel_id),
            getString(R.string.notification_channel_name)
        )
        binding.inputPhoneEd.setAutofillHints(View.AUTOFILL_HINT_PHONE)
        binding.inputPhoneEd.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_YES
        startFragmentViewModel.navigateToWebView.observe(viewLifecycleOwner, { user ->
            if (startFragmentViewModel.isFormValid()) {
                user?.let {
                    this.findNavController().navigate(
                        StartFragmentDirections.actionStartFragmentToWebViewFragment()
                    )
                    startFragmentViewModel.doneNavigation()
               }
            }
        })


        return binding.root
    }



    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(true)
                }

            notificationChannel.description = getString(R.string.notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }

    companion object {
        fun newInstance() = StartFragment()
    }



}