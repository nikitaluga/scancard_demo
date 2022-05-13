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
import com.example.scancard_demo_rv.databinding.CardIoFragmentBinding
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard

class CardIoFragment : Fragment(R.layout.card_io_fragment) {

    private val binding: CardIoFragmentBinding by viewBinding(CardIoFragmentBinding::bind)

    private val TAG = "~~CardIoFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.number3Til.setEndIconOnClickListener {
            onScanPress()
        }
    }

    private fun onScanPress() {
        val scanIntent = Intent(requireContext(), CardIOActivity::class.java)

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, true)
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false)

        resultLauncher.launch(scanIntent)
    }

    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val scanResult: CreditCard? =
                    result.data?.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)

                val cardData = """
                    Card number: ${scanResult?.redactedCardNumber}
                    Card holder: ${scanResult?.cardholderName}
                    Card expiration date: ${scanResult?.expiryMonth}/${scanResult?.expiryYear}
                    """.trimIndent()
                Log.i(TAG, "Card info: $cardData")
                insertCardData(scanResult)
            }
            Activity.RESULT_CANCELED -> Log.i(TAG, "Scan canceled")
            else -> Log.i(TAG, "Scan failed result = $result")
        }
    }

    private fun insertCardData(card: CreditCard?) {
        val dateCard = "${card?.expiryMonth}/${card?.expiryYear}"

        binding.number3Et.setText(card?.redactedCardNumber)
        binding.name3Et.setText(card?.cardholderName)
        binding.date3Et.setText(dateCard)
    }
}