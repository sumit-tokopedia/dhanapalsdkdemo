package com.example.dhanapalademo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.sdkdhanapala.model.DhanapalaBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var id : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       fetchGoogleAdvertisementID()
        Staging.text = "Production"
        Staging.setOnClickListener {
            id?.let {
                DhanapalaBuilder().apply {
                    setContext(context = this@MainActivity)
                    setTitle("Dhanapala Demo")
                    setDeviceId(id!!)
                    setSDKKey("12345")
                    build().start()
                }
            }
        }
    }
fun fetchGoogleAdvertisementID() {
    Thread {
        var adInfo: com.google.android.gms.ads.identifier.AdvertisingIdClient.Info?
        try {
            adInfo =
                com.google.android.gms.ads.identifier.AdvertisingIdClient.getAdvertisingIdInfo(this)
        } catch (e: IOException) {
            Log.e("dhanapala ga-id:", "IOException")
            adInfo = null
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.e("dhanapala ga-id:", "GooglePlayServicesNotAvailableException")
            adInfo = null
            e.printStackTrace()
        } catch (e: GooglePlayServicesRepairableException) {
            Log.e("dhanapala ga-id:", "GooglePlayServicesRepairableException")
            adInfo = null
            e.printStackTrace()
        }

        if (adInfo != null) {
            id = adInfo.id
        }
    }.start()
        }
}