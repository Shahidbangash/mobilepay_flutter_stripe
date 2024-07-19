package com.example.mobilepay_flutter_stripe

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.stripe.android.PaymentConfiguration

class MobilepayFlutterStripePlugin : FlutterPlugin, ActivityAware, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private var activity: ActivityPluginBinding? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "mobilepay_flutter_stripe")
        channel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "startCheckout") {
            val paymentIntentClientSecret = call.argument<String>("paymentIntentClientSecret")
            val publishableKey = call.argument<String>("publishableKey")
            try {
                PaymentConfiguration.init(activity!!.activity.applicationContext, publishableKey!!)
                startCheckout(paymentIntentClientSecret!!)
                result.success("Checkout Started")
            } catch (e: Exception) {
                result.error("ERROR", e.message, null)
            }
        } else {
            result.notImplemented()
        }
    }

    private fun startCheckout(paymentIntentClientSecret: String) {
        val intent = Intent(activity!!.activity, CheckoutActivity::class.java).apply {
            putExtra("paymentIntentClientSecret", paymentIntentClientSecret)
        }
        activity!!.activity.startActivity(intent)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }
}
