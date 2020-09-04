package com.aptopayments.exampleapp

import android.app.Application
import com.aptopayments.mobile.platform.AptoSdkEnvironment

import com.aptopayments.sdk.core.platform.AptoUiSdk

class CardApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeAptoSdk()
    }

    private fun initializeAptoSdk() {
        val apiKey = getString(R.string.apto_api_key)
        val env = getString(R.string.apto_environment)
        AptoUiSdk.initializeWithApiKey(this, apiKey, AptoSdkEnvironment.valueOf(env))
    }
}
