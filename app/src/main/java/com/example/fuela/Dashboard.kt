package com.example.fuela

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener  {
    private var snackBar: Snackbar? = null
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


    }
    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }
    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            noNet.visibility=View.GONE;

        } else {
            noNet.visibility=View.VISIBLE;

//            Toast.makeText(this,"Internet not availlabel",Toast.LENGTH_LONG).show()

        }
    }

}
