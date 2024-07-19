package com.example.mobilepay_flutter_stripe

import android.app.Application
import com.stripe.android.PaymentConfiguration

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51Ln67yIdrrbatSEwJuiWwsYvg52b3j163WnQ7LslO0ChhMPXh7n5JLGbh8qmOjau77EbCl5o6pIs8jkqCtlESEgo00maqvnrGi"
        )
    }
}
