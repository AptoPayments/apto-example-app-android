package com.aptopayments.exampleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aptopayments.sdk.utils.ViewUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewUtils.hideKeyboard(this)
        startActivity(MainActivity.callingIntent(this, intent.action, intent.data))
        finish()
    }
}
