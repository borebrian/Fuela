package com.example.fuela

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard :  AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {


    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showToast(isConnected)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        registerReceiver(ConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        loadURL()
    }
    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun showToast(isConnected: Boolean) {
        if (!isConnected) {
//            Toast.makeText(this, "You are offline now.!!!", Toast.LENGTH_LONG).show()
            loading.visibility=View.VISIBLE
            loading.visibility=View.GONE
            webview.visibility=View.GONE


        } else {
            loading.visibility=View.GONE
            loading.visibility=View.GONE
            webview.visibility=View.VISIBLE

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
    fun  loadURL(){
        val myWebView: WebView = findViewById(R.id.webview)
        myWebView.loadUrl("http://fuela2-001-site1.atempurl.com/Agents/Log_in.aspx")
    }
}
