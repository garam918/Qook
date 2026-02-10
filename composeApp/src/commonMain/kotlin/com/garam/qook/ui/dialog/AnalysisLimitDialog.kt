package com.garam.qook.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.mainColor

@Composable
fun AnalysisLimitDialogScreen(onDismiss: () -> Unit, onUpgradePremium: () -> Unit) {

    Dialog(
        onDismissRequest = {
            onDismiss()
        },

        ) {

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.wrapContentHeight()
        ) {

            Column(modifier = Modifier.padding(20.dp).wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                    text = "Free Limit Reached!", fontFamily = fontFamily(),
                    fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Free users can analyze up to 3 videos per day. You can wait until tomorrow or upgrade for unlimited access.", fontFamily = fontFamily(),
                    fontWeight = FontWeight.Normal, fontSize = 14.sp, color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                TextButton(
                    modifier = Modifier.fillMaxWidth(), onClick = {

                        onUpgradePremium()

                    }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = mainColor,
                        disabledContainerColor = mainColor,
                        contentColor = mainColor
                    )
                ) {
                    Text(
                        text = "Upgrade to Premium", fontFamily = fontFamily(),
                        fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )

                }

                Spacer(modifier = Modifier.height(15.dp))

                TextButton(onClick = {

                    onDismiss()

                }) {

                    Text(
                        text = "Maybe Later", fontFamily = fontFamily(),
                        fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.LightGray
                    )

                }

            }
        }
    }
}