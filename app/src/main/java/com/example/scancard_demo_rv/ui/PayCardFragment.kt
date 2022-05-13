package com.example.scancard_demo_rv.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import com.example.scancard_demo_rv.R
import com.example.scancard_demo_rv.databinding.PayCardFragmentBinding

class PayCardFragment : Fragment(R.layout.pay_card_fragment) {

    private val binding: PayCardFragmentBinding by viewBinding(PayCardFragmentBinding::bind)

    private val TAG = "~~PayCardFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberTil.setEndIconOnClickListener {
            scanCard()
        }
    }

    private fun scanCard() {
        val intent = ScanCardIntent.Builder(this.context).build()
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(StartActivityForResult()) { result ->
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

    private fun insertCardData(card: Card?) {
        binding.numberEt.setText(card?.cardNumber)
        binding.nameEt.setText(card?.cardHolderName)
        binding.dateEt.setText(card?.expirationDate)
    }
}