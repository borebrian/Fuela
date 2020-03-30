package com.example.fuela

import android.content.Context
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View

import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dashboard.*
import android.webkit.WebViewClient

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.webkit.WebSettings


class Dashboard :  AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    internal var url = "http://fuela2-001-site1.atempurl.com/Agents/Log_in.aspx"
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showToast(isConnected)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )


        var wv: WebView? = null

//SETTING TO LOAD FROM CACHE
        webview.webViewClient = myWebClient()
        webview.settings.setAppCacheEnabled(true)
        webview.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webview.settings.setAppCachePath(cacheDir.path)
         webview.settings.javaScriptEnabled = true
        webview.settings.builtInZoomControls = false
        webview.settings.displayZoomControls = false

        // Enable zooming in web view

//        settings.setSupportZoom(true)
//        settings.builtInZoomControls = true
//        settings.displayZoomControls = true
        // Enable disable images in web view
//        settings.blockNetworkImage = false

//        // Whether the WebView should load image resources

//        settings.loadsImagesAutomatically = true

        // More web view settings
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            webview.settings.safeBrowsingEnabled = true  // api 26
        }

        //settings.pluginState = WebSettings.PluginState.ON
       webview.settings.useWideViewPort = true
        webview.settings.loadWithOverviewMode = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.settings.mediaPlaybackRequiresUserGesture = false
        webview.loadUrl(url)

    }
    inner class myWebClient : WebViewClient() {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            // Page loading started
            loading.visibility=View.VISIBLE
            webview.visibility=View.GONE
            noInteret.visibility=View.GONE
        }


        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {


            view.loadUrl(url)
            return true

        }

        override fun onPageFinished(view: WebView, url: String) {
//
            loading.visibility=View.GONE
            webview.visibility=View.VISIBLE
            noInteret.visibility=View.GONE
        }

    }


    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    private fun showToast(isConnected: Boolean) {
        if (!isConnected) {
//            Toast.makeText(this, "You are offline now.!!!", Toast.LENGTH_LONG).show()
            loading.visibility = View.GONE
            noInteret.visibility = View.VISIBLE
            webview.visibility = View.GONE


        } else {
            loading.visibility = View.GONE
            noInteret.visibility = View.GONE
            webview.visibility = View.VISIBLE

//            if (networkType()) {
//                Toast.makeText(this, "You are online now.!!!" + "\n Connected to Wifi Network", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "You are online now.!!!" + "\n Connected to Cellular Network", Toast.LENGTH_LONG).show()
//            }
        }
    }

    private fun networkType(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isWifi: Boolean = activeNetwork?.type == ConnectivityManager.TYPE_WIFI
        return isWifi
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val myWebView: WebView = findViewById(R.id.webview)

        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }


    fun loadURL() {

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.loadUrl("http://fuela2-001-site1.atempurl.com/Agents/Log_in.aspx")
    }
}

