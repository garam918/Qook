package com.garam.qook.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.garam.qook.auth.rememberGoogleAuthHandler
import com.garam.qook.getPlatform
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.googleLoginBtnBorderColor
import com.garam.qook.resources.mainBackgroundColor
import com.garam.qook.resources.mainColor
import com.garam.qook.ui.navigation.RouteHome
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.apple_login_icon
import qook.composeapp.generated.resources.google_login_icon
import qook.composeapp.generated.resources.privacy_policy_string
import qook.composeapp.generated.resources.terms_of_use_string


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onDismiss : () -> Unit, onNavigateToHome : (RouteHome) -> Unit, viewModel: LoginViewModel = koinViewModel()) {

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(true) }

    val googleAuthHandler = rememberGoogleAuthHandler()
    val googleLoginScope = rememberCoroutineScope()
    val appleLoginScope = rememberCoroutineScope()


    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
            showSheet = false
        },
        sheetState = sheetState,
        containerColor = Color.White
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight().padding(16.dp,10.dp,16.dp,16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

//            Image()

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Welcome to Qook!", fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold, fontSize = 20.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Sign in to sync your recipes and grocery lists across devices automatically",
                textAlign = TextAlign.Center,fontFamily = fontFamily(), fontWeight = FontWeight.Medium, fontSize = 15.sp)

            Spacer(modifier = Modifier.height(30.dp))

            if(getPlatform().name == "Android") SocialLoginButton(
                text = "Continue with Google",
                backgroundColor = Color.White,
                borderColor = googleLoginBtnBorderColor,
                textColor = Color.Black,
                icon = painterResource(Res.drawable.google_login_icon),
                type = "google",
                onClick = {

                    googleLoginScope.launch {
                        val tokenList = googleAuthHandler.signIn()

                        if(tokenList.isNotEmpty()) {
                            val idToken = tokenList[0].toString()
                            val accessToken = tokenList[1].toString()

                            val user = viewModel.googleLogin(idToken, accessToken)

                            if(user != null) {

                                onNavigateToHome(RouteHome)

                            }


                        }
                    }


                }
            )
            else SocialLoginButton(
                text = "Continue with Apple",
                backgroundColor = Color.Black,
                borderColor = googleLoginBtnBorderColor,
                textColor = Color.White,
                icon = painterResource(Res.drawable.apple_login_icon),
                type = "",
                onClick = {
                    appleLoginScope.launch {


                        val user = viewModel.appleLogin()

                        if(user != null) {

                            onNavigateToHome(RouteHome)


                        }

                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            val annotatedString = clickText()
            val uriHandler = LocalUriHandler.current

            Text(text = annotatedString,
                modifier = Modifier.padding(horizontal = 20.dp).clickable {
                    annotatedString.getLinkAnnotations(start = 0, end = annotatedString.length)
                        .forEach { annotation ->

                            when (annotation.item) {
                                is LinkAnnotation.Url -> uriHandler.openUri((annotation.item as LinkAnnotation.Url).url)
                            }
                        }
                },
                textAlign = TextAlign.Center,
                fontFamily = fontFamily(),
                fontWeight = FontWeight.Normal, fontSize = 11.sp, color = Color.Gray)

        }


    }

}

@Composable
fun SocialLoginButton(
    text: String,
    backgroundColor: Color,
    borderColor: Color,
    textColor: Color,
    icon : Painter,
    type: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.background(color = backgroundColor, shape = RoundedCornerShape(24.dp))
            .fillMaxWidth().height(48.dp)

        , border = if(type == "google") BorderStroke(width = 1.dp, color = borderColor) else null
    ) {
        Icon(painter = icon, contentDescription = "",
            tint = Color.Unspecified)
        Spacer(modifier = Modifier.width(15.dp))
        Text(text = text, color = textColor,
            fontFamily = fontFamily(),
            fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun clickText() : AnnotatedString {

    val annotatedText = buildAnnotatedString {
        append("By Continuing, you agree to our ")

        withLink(
            LinkAnnotation.Url(
                url = stringResource(Res.string.terms_of_use_string),
                styles = TextLinkStyles(style = SpanStyle(color = mainColor, fontWeight = FontWeight.Bold))
            )
        ) {
            append("Terms of Service")
        }

        append(" and ")

        withLink(
            LinkAnnotation.Url(
                url = stringResource(Res.string.privacy_policy_string),
                styles = TextLinkStyles(style = SpanStyle(color = mainColor, fontWeight = FontWeight.Bold))
            )
        ) {
            append("Privacy Policy")
        }

    }

    return annotatedText
}