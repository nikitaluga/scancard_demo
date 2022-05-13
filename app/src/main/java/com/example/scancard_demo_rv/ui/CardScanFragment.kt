package com.example.scancard_demo_rv.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.scancard_demo_rv.R
import com.example.scancard_demo_rv.databinding.CardScanFragmentBinding
import com.getbouncer.cardscan.ui.CardScanActivity
import com.getbouncer.cardscan.ui.CardScanActivityResult

class CardScanFragment : Fragment(R.layout.card_scan_fragment) {

    private val binding: CardScanFragmentBinding by viewBinding(CardScanFragmentBinding::bind)

    private val TAG = "~~CardScanFragment"
    private val API_KEY = "qOJ_fF-WLDMbG05iBq5wvwiTNTmM2qIn"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.number2Til.setEndIconOnClickListener {
            onScanPress()
        }

        CardScanActivity.warmUp(
            context = requireContext(),
            apiKey = API_KEY,
            initializeNameAndExpiryExtraction = true
        )
    }

    private fun onScanPress() {
        val scanIntent = Intent(requireContext(), CardScanActivity::class.java)

        resultLauncher.launch(scanIntent)
    }

    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val scanResult: CardScanActivityResult? =
                    result.data?.getParcelableExtra(CardScanActivity.RESULT_SCANNED_CARD)

                val cardData = """
                    Card number: ${scanResult?.pan}
                    Card holder: ${scanResult?.cardholderName}
                    Card expiration date: ${scanResult?.expiryMonth}/${scanResult?.expiryYear}
                    """.trimIndent()
                Log.i(TAG, "Card info: $cardData")
                insertCardData(scanResult)
            }
            Activity.RESULT_CANCELED -> Log.i(TAG, "Scan canceled")
            else -> Log.i(TAG, "Scan failed")
        }
    }

    private fun insertCardData(card: CardScanActivityResult?) {
        val dateCard = "${card?.expiryMonth}/${card?.expiryYear}"

        binding.number2Et.setText(card?.pan)
        binding.name2Et.setText(card?.cardholderName)
        binding.date2Et.setText(dateCard)
    }
}