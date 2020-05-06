package com.aptopayments.exampleapp.fonts

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface
import com.aptopayments.core.features.managecard.FontOptions

class FontParser(context: Context) {

    private val fontsMap = mutableMapOf<FontType, Typeface>()

    init {
        FontType.values().iterator().forEach { parseFont(context.assets, it) }
    }

    fun getFont(type: FontType): Typeface? = fontsMap.getOrElse(type, { null })

    fun getFontOptions() =
        FontOptions(
            getFont(FontType.REGULAR),
            getFont(FontType.MEDIUM),
            getFont(FontType.SEMI_BOLD),
            getFont(FontType.BOLD)
        )

    private fun parseFont(assets: AssetManager, type: FontType) {
        getFontFromAssets(assets, type.filename)?.let {
            fontsMap.put(type, it)
        }
    }

    private fun getFontFromAssets(assets: AssetManager, type: String): Typeface? {
        return try {
            Typeface.createFromAsset(assets, type)
        } catch (exception: RuntimeException) {
            null
        }
    }
}
