package com.developance.imaginary.utils


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.developance.imaginary.photos.data.remote.Location

//Condition is not always true! false compile warning
fun Location.asString(): String? =
    if (city != null && country != null) "$city , $country" else null

fun buildStringFromTextAndLink(prefix: String, name: String, url: String) =


    buildAnnotatedString {

        val str = "$prefix $name "
        val nameStartIndex = str.indexOf(name)
        val nameEndIndex = nameStartIndex + name.length
        append(str)

        addStyle(
            style = SpanStyle(
                color = Color(0xff64B5F6),
                fontSize = 16.sp,
                textDecoration = TextDecoration.Underline,
            ), start = nameStartIndex, end = nameEndIndex
        )

        // attach a string annotation that stores a URL to the text "link"
        addStringAnnotation(
            tag = "URL",
            annotation = url,
            start = nameStartIndex,
            end = nameEndIndex
        )

    }
