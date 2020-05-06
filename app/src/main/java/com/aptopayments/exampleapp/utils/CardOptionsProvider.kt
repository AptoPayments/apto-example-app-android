package com.aptopayments.exampleapp.utils

import com.aptopayments.core.features.managecard.CardOptions
import com.aptopayments.exampleapp.fonts.FontParser

/**
 * CardOptionsProvider
 * Configure here the features that you want to enable/disable
 */
class CardOptionsProvider(private val fontParser: FontParser) {

    fun provide() = CardOptions(
        showStatsButton = true,
        showNotificationPreferences = true,
        showDetailedCardActivityOption = true,
        hideFundingSourcesReconnectButton = true,
        authenticateOnStartup = false,
        authenticateWithPINOnPCI = false,
        fontOptions = fontParser.getFontOptions()
    )
}
