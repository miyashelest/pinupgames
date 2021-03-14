package com.example.leadsdoittest

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
// Get user consent
        FacebookSdk.setAutoInitEnabled(true)
        FacebookSdk.fullyInitialize()
        AppLinkData.fetchDeferredAppLinkData(
            this
        ) {
            // Process app link data
        }
        
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(
                this
            ) { pendingDynamicLinkData ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

            }
            .addOnFailureListener(
                this
            ) { e -> Log.w("TAG", "getDynamicLink:onFailure", e) }
    }
}