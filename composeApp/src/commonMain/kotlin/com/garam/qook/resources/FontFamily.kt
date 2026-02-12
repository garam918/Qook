package com.garam.qook.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.noto_sans_bold
import qook.composeapp.generated.resources.noto_sans_extra_bold
import qook.composeapp.generated.resources.noto_sans_regular
import qook.composeapp.generated.resources.noto_sans_semi_bold
import qook.composeapp.generated.resources.noto_sans_thin


@Composable
fun fontFamily() : FontFamily = FontFamily(
    Font(
        resource = Res.font.noto_sans_thin,
        weight = FontWeight.Thin
    ),
    Font(
        resource = Res.font.noto_sans_regular,
        weight = FontWeight.Medium
    ),
    Font(
        resource = Res.font.noto_sans_semi_bold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resource = Res.font.noto_sans_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resource = Res.font.noto_sans_extra_bold,
        weight = FontWeight.ExtraBold
    )
)