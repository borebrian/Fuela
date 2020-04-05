package com.example.fuela

import android.app.AlertDialog
import android.app.DownloadManager
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

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.ProgressBar

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import android.provider.Settings.ACTION_WIRELESS_SETTINGS
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.audiofx.BassBoost
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.webkit.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.*


@Suppress("DEPRECATION")
class Dashboard :  AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    internal var url = "http://fuela3-001-site1.itempurl.com/Agents/Log_in.aspx"
    private val PERMISSION_REQUEST_CODE = 1
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showToast(isConnected)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        when (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
            Configuration.SCREENLAYOUT_SIZE_LARGE -> this.window.setLayout(900, 755)
            Configuration.SCREENLAYOUT_SIZE_XLARGE -> this.window.setLayout(1080, 1000) //width x height
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)



        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )








//TEST IF INTERNET IS UP AND WORKING
        if(internetConnectionAvailable(500)) {


            loading.visibility = View.GONE
            webview.visibility = View.GONE
            noInteret.visibility = View.VISIBLE


            webview.webViewClient = myWebClient()
            webview.settings.setAppCacheEnabled(true)
            webview.settings.cacheMode = WebSettings.LOAD_DEFAULT
            webview.settings.setAppCachePath(cacheDir.path)
            webview.settings.javaScriptEnabled = true
            webview.settings.builtInZoomControls = false
            webview.settings.displayZoomControls = false


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

            webview.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
                val request = DownloadManager.Request(Uri.parse(url))
                request.setMimeType(mimeType)
                request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
                request.addRequestHeader("User-Agent", userAgent)
                request.setDescription("Downloading file...")
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalFilesDir(this@Dashboard, Environment.DIRECTORY_DOWNLOADS, ".pdf")
                val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
            }
        }
else{
            loading.visibility=View.GONE
            webview.visibility=View.GONE
            noInteret.visibility=View.VISIBLE
        }






        var wv: WebView? = null

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(); // Code for permission
            }





        }
        else
        {

            // Code for Below 23 API Oriented Device
            // Do next code
        }

//SETTING TO LOAD FROM CACHE


        wifi.setOnClickListener(){
            startActivity(Intent(ACTION_WIRELESS_SETTINGS))

        }
        data.setOnClickListener(){
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))

        }
        retry.setOnClickListener() {
            if (internetConnectionAvailable(500)) {

unhide()
                webview.reload()
            } else{
                hide()
            }
        }
    }
    fun unhide() {
        // Page loading started
        loading.visibility=View.GONE
        webview.visibility=View.VISIBLE
        noInteret.visibility=View.GONE
    }
    fun hide() {
        // Page loading started
        loading.visibility=View.GONE
        webview.visibility=View.GONE
        noInteret.visibility=View.VISIBLE
    }
    
    inner class myWebClient : WebViewClient() {

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

            if (internetConnectionAvailable(500)) {

            unhide()
                // Page loading started
            loading.visibility=View.VISIBLE
            webview.visibility=View.GONE
            noInteret.visibility=View.GONE
            }
                else{
                    hide()


                }
            }



        override fun shouldOverrideUrlLoading(view:WebView, url:String):Boolean {
            Log.d("webview_override", url)
            if (url.contains("/downloadstatement.htm"))
            {
                val request = DownloadManager.Request(Uri.parse(url))
                val extension = MimeTypeMap.getFileExtensionFromUrl(url)
                val mime = MimeTypeMap.getSingleton()
                val mimeType = mime.getMimeTypeFromExtension(extension)
                request.setMimeType(mimeType)
                //------------------------COOKIE!!------------------------
                val cookies = CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("cookie", cookies)
                request.setDescription("Downloading file...")
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "bs.pdf")
                val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
                return false
            }
            view.loadUrl(url)
            return true
        }
        override fun onReceivedError(view:WebView, request: WebResourceRequest, error: WebResourceError) {
            //Your code to do
            loading.visibility=View.GONE
            webview.visibility=View.GONE
            noInteret.visibility=View.VISIBLE
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
        else{

            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle(R.string.alertTitle)
            //set message for alert dialog
            builder.setMessage(R.string.message)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Exit"){dialogInterface, which ->
               finish()
            }

            //performing negative action
            builder.setNegativeButton("Stay   "){dialogInterface, which ->

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)


        return super.onKeyDown(keyCode, event)
    }


    fun loadURL() {

        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.loadUrl("http://fuela2-001-site1.atempurl.com/Agents/Log_in.aspx")
    }
    private fun checkPermission():Boolean {
        val result = ContextCompat.checkSelfPermission(this@Dashboard, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this@Dashboard, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            Toast.makeText(this@Dashboard, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show()
        }
        else
        {
            ActivityCompat.requestPermissions(this@Dashboard, arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
    }
    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<String>, grantResults:IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                toasting("Permission granted")
            }
            else
            {
                toasting("Permission denied")

            }
        }
    }

    fun toasting(message:String){
        Toast.makeText(this@Dashboard, message, Toast.LENGTH_LONG).show()
    }
     fun internetConnectionAvailable(timeOut:Int):Boolean {
        var inetAddress: InetAddress? = null
        try
        {
            val future = Executors.newSingleThreadExecutor().submit(Callable<InetAddress> {
                try {
                    InetAddress.getByName("google.com")
                } catch (e: UnknownHostException) {
                    null
                }
            })
            inetAddress = future.get(timeOut.toLong(), TimeUnit.MILLISECONDS)
            future.cancel(true)
        }
        catch (e:InterruptedException) {}
        catch (e: ExecutionException) {}
        catch (e: TimeoutException) {}
        return inetAddress != null && !inetAddress.equals("")
    }
}

