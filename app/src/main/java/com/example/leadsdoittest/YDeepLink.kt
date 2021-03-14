package com.example.leadsdoittest

import android.app.Application
import android.util.Log
import com.yandex.metrica.DeferredDeeplinkListener
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig.newConfigBuilder


class YDeepLink : Application() {
    override fun onCreate() {
        super.onCreate()
        val API_key = "f63f6f84-5260-4b8b-98df-1c5c3cc3dce1"
        val config = newConfigBuilder(API_key).build()
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)

        YandexMetrica.requestDeferredDeeplink(object : DeferredDeeplinkListener {
            override fun onDeeplinkLoaded(deeplink: String) {
                Log.i("Deeplink", "deeplink = $deeplink")
            }

            override fun onError(error: DeferredDeeplinkListener.Error, referrer: String?) {
                Log.i("Deeplink", "Error: ${error.description}, unparsed referrer: $referrer")
            }
        })
    }
}