package com.aelshal.daftar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection

/** A piece of interface text in both languages. Every label in the app is one of these. */
data class Bi(val en: String, val ar: String)

/**
 * Shows English on the left and Arabic on the right of one row,
 * the pattern used on every screen of the design.
 */
@Composable
fun BilingualRow(
    text: Bi,
    style: TextStyle,
    modifier: Modifier = Modifier,
    arabicStyle: TextStyle = style,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicText(text = text.en, style = style)
        BasicText(
            text = text.ar,
            style = arabicStyle.copy(textDirection = TextDirection.Rtl, textAlign = TextAlign.End),
        )
    }
}
