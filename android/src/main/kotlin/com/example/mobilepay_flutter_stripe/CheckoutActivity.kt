package com.example.mobilepay_flutter_stripe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.PaymentConfiguration
import com.stripe.android.payments.paymentlauncher.PaymentLauncher
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethodCreateParams
import com.stripe.android.payments.paymentlauncher.PaymentResult
import io.flutter.plugin.common.MethodChannel

class CheckoutActivity : AppCompatActivity() {
    private lateinit var paymentLauncher: PaymentLauncher
    private lateinit var channel: MethodChannel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val paymentIntentClientSecret = intent.getStringExtra("paymentIntentClientSecret")
        paymentLauncher = PaymentLauncher.create(
            this,
            PaymentConfiguration.getInstance(applicationContext).publishableKey,
            PaymentConfiguration.getInstance(applicationContext).stripeAccountId,
            ::onPaymentResult
        )
        startCheckout(paymentIntentClientSecret!!)
    }

    private fun startCheckout(paymentIntentClientSecret: String) {
        val confirmParams = ConfirmPaymentIntentParams
            .createWithPaymentMethodCreateParams(
                paymentMethodCreateParams = PaymentMethodCreateParams.createMobilePay(),
                clientSecret = paymentIntentClientSecret
            )
        paymentLauncher.confirm(confirmParams)
    }

    private fun onPaymentResult(paymentResult: PaymentResult) {
        val message = when (paymentResult) {
            is PaymentResult.Completed -> "Completed!"
            is PaymentResult.Canceled -> "Canceled!"
            is PaymentResult.Failed -> "Failed: " + paymentResult.throwable.message
        }
        // Return result to Flutter
        channel.invokeMethod("onPaymentResult", message)
    }
}
