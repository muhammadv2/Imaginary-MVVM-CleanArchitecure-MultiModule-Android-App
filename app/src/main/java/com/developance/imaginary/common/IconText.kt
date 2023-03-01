package com.developance.imaginary.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.developance.imaginary.ui.theme.mediumPadding
import com.developance.imaginary.ui.theme.smallPadding
import timber.log.Timber

@Composable
fun IconText( @DrawableRes iconId: Int, text: String?) {
    if (text == null || text.isBlank()) return
    Timber.d("string $text")
    Row(
        horizontalArrangement = Arrangement.spacedBy(smallPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = iconId), contentDescription = text)
        Text(text = text, style = MaterialTheme.typography.labelLarge)
    }
}