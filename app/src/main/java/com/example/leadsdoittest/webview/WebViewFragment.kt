package com.example.leadsdoittest.webview

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.leadsdoittest.MainActivity
import com.example.leadsdoittest.R
import kotlinx.android.synthetic.main.webview_fragment.*


class WebViewFragment : Fragment() {
    companion object {
        const val LAST_URL = "lastUrl"
        const val BASE_URL = "https://pin-up.games/"
    }

    private val prefs: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            "PREFS",
            MODE_PRIVATE
        )
    }

    private val lastOrMainUrl: String by lazy { prefs.getString(LAST_URL, BASE_URL).toString() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.webview_fragment, container, false)

        val webView = rootView.findViewById<WebView>(R.id.webview)
        webView.webViewClient = WebViewClient()
        CookieManager.getInstance().setAcceptCookie(true)
        webView.loadUrl(lastOrMainUrl)
        webView.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack()
            }
            true
        }


        return rootView
    }

    override fun onPause() {
        super.onPause()
        prefs.edit()
            ?.putString(LAST_URL, webview.url)
            ?.apply()
    }
}


