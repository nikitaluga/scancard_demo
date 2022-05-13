package com.example.scancard_demo_rv

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                // There are no request codes
                val card: Card? = result.data?.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)
                val cardData = """
                    Card number: ${card?.cardNumberRedacted.toString()}
                    Card holder: ${card?.cardHolderName.toString()}
                    Card expiration date: ${card?.expirationDate}
                    """.trimIndent()
                Log.i(TAG, "Card info: $cardData")
                insertCardData(card)
            }
            Activity.RESULT_CANCELED -> Log.i(TAG, "Scan canceled")
            else -> Log.i(TAG, "Scan failed")
        }
    }

    private fun insertCardData(card: Card?) {}
}