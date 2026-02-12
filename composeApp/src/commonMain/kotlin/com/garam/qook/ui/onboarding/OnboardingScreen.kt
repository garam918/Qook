package com.garam.qook.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.mainColor
import com.garam.qook.ui.login.LoginScreen
import com.garam.qook.ui.navigation.Route
import org.jetbrains.compose.resources.painterResource
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.onboarding_img


@Composable
fun OnBoardingScreen(onNavigateToHome : (Route.RouteHome) -> Unit) {

    var showBottomSheet by remember { mutableStateOf(false) }


    Column(modifier = Modifier.background(color = Color(0xFFF5F8F5)).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(Res.drawable.onboarding_img), contentDescription = "",
            contentScale = ContentScale.FillWidth)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Grocery List Ready with Just a Video Link!", fontFamily = fontFamily(), fontWeight = FontWeight.ExtraBold,
            color = Color.Black, fontSize = 24.sp, textAlign = TextAlign.Center,modifier = Modifier.padding(horizontal = 20.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Paste a cooking video link.\nAI automatically generates your grocery list and recipe summary",
            color = Color.Gray, fontFamily = fontFamily(), fontWeight = FontWeight.Normal, fontSize = 12.sp, textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(onClick = {
            showBottomSheet = true
        },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = mainColor)
            ) {

            Text(text = "Get Started", color = Color.Black, fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp)
//            Icon()
        }
    }

    if(showBottomSheet) LoginScreen(onDismiss = {
        showBottomSheet = false
    }, onNavigateToHome = {

        onNavigateToHome(it)

    })

}