package com.aptopayments.exampleapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aptopayments.exampleapp.fonts.FontParser
import com.aptopayments.exampleapp.fonts.FontType
import com.aptopayments.mobile.data.config.UIStatusBarStyle
import com.aptopayments.sdk.core.ui.StatusBarUtil
import com.aptopayments.sdk.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_force_update.*

class ForceUpdateActivity : AppCompatActivity() {

    private val fontParser = FontParser(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_force_update)
        setupUI()
    }

    private fun setupUI() {
        setupStatusBar()
        setupFonts()
        setupListeners()
    }

    private fun setupStatusBar() {
        StatusBarUtil.setStatusBarColor(window, Color.WHITE, UIStatusBarStyle.AUTO)
    }

    private fun setupFonts() {
        tv_title.typeface = fontParser.getFont(FontType.BOLD)
        tv_description.typeface = fontParser.getFont(FontType.REGULAR)
        continue_button.typeface = fontParser.getFont(FontType.MEDIUM)
    }

    private fun setupListeners() {
        continue_button.setOnClickListener { continueButtonTapped() }
    }

    override fun onResume() {
        super.onResume()
        ViewUtils.hideKeyboard(this)
    }

    private fun continueButtonTapped() {
        val appPackageName = packageName
        val uri = try {
            Uri.parse("market://details?id=$appPackageName")
        } catch (e: android.content.ActivityNotFoundException) {
            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
        }
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    override fun onBackPressed() {}

    companion object {
        fun callingIntent(from: Context) = Intent(from, ForceUpdateActivity::class.java)
    }
}
