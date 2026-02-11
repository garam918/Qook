package com.garam.qook.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
fun DeleteAccountDialog(
    onDismiss: () -> Unit,
    onDeleteAccount: () -> Unit
) {


    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.wrapContentHeight()
        ) {

            Column(
                modifier = Modifier.padding(20.dp).wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "We'll miss you!",
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Deleting your account is permanent.\nAll your saved grocery lists and recipes will be gone.",
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))


                TextButton(
                    onClick = {
                        onDismiss()

                    }, modifier = Modifier.fillMaxWidth(), colors = ButtonColors(
                        containerColor = mainColor, disabledContainerColor = mainColor,
                        contentColor = Color.White, disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Keep My Account", color = Color.Black, fontFamily = fontFamily(),
                        fontWeight = FontWeight.SemiBold, fontSize = 15.sp
                    )
                }

                TextButton(onClick = {
                    onDeleteAccount()
                }) {
                    Text(
                        text = "Yes, Delete Anyway", color = Color.LightGray, fontFamily = fontFamily(),
                        fontWeight = FontWeight.SemiBold, fontSize = 13.sp
                    )

                }


            }


        }

    }



}