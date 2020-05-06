package com.aptopayments.exampleapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.aptopayments.core.platform.AptoPlatformDelegate
import com.aptopayments.exampleapp.fonts.FontParser
import com.aptopayments.exampleapp.fonts.FontType
import com.aptopayments.exampleapp.utils.CardOptionsProvider
import com.aptopayments.sdk.core.extension.hide
import com.aptopayments.sdk.core.extension.show
import com.aptopayments.sdk.core.platform.AptoUiSdk
import com.aptopayments.sdk.utils.MessageBanner
import com.aptopayments.sdk.utils.ViewUtils.hideKeyboard
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_rl_loading.*

class MainActivity : AppCompatActivity(), AptoPlatformDelegate {

    private val fontParser: FontParser by lazy { FontParser(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setupUI()
        AptoUiSdk.setDelegate(this)
        loginOrContinueIfHasToken()
    }

    private fun loginOrContinueIfHasToken() {
        if (AptoUiSdk.userTokenPresent()) {
            launchCardFlowWithExistingToken()
        } else {
            showMainLayout()
        }
    }

    private fun showMainLayout() {
        splash_group.hide()
        main_group.show()
    }

    private fun setupUI() {
        setContentView(R.layout.activity_main)
        setFonts()
        setAppVersion()
    }

    override fun onStart() {
        super.onStart()
        continue_button.setOnClickListener { launchButtonTapped() }
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard(this)
    }

    override fun onStop() {
        showMainLayout()
        super.onStop()
    }

    private fun launchCardFlowWithExistingToken() {
        AptoUiSdk.startCardFlow(this, getCardOptions(),
            onSuccess = { },
            onError = {
                showMainLayout()
                notify(it.errorMessage())
            }
        )
    }

    private fun launchButtonTapped() {
        showLoading()
        AptoUiSdk.startCardFlow(this, getCardOptions(),
            onSuccess = { hideLoading() },
            onError = {
                hideLoading()
                notify(it.errorMessage())
            }
        )
    }

    private fun setFonts() {
        continue_button.typeface = fontParser.getFont(FontType.MEDIUM)
        tv_instructions.typeface = fontParser.getFont(FontType.REGULAR)
    }

    @Suppress("DEPRECATION")
    private fun showLoading() {
        pb_progress.indeterminateDrawable.setColorFilter(
            getColor(R.color.colorPrimary),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        rl_loading_view.show()
        rl_loading_view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
    }

    private fun hideLoading() {
        rl_loading_view.hide()
        rl_loading_view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
    }

    private fun notify(message: String) = MessageBanner().showBanner(this, message, MessageBanner.MessageType.ERROR)

    override fun sdkDeprecated() {
        startActivity(ForceUpdateActivity.callingIntent(this))
    }

    private fun getCardOptions() = CardOptionsProvider(fontParser).provide()

    private fun setAppVersion() {
        val version = getString(R.string.version_ids, AptoUiSdk.getAppVersion(this))
        tv_main_version.text = version
        tv_splash_version.text = version
    }

    companion object {
        fun callingIntent(from: Context, action: String?, data: Uri?) =
            Intent(action, data, from, MainActivity::class.java)
    }
}
