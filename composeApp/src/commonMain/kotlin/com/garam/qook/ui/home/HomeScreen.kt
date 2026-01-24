package com.garam.qook.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.garam.qook.data.AnalysisData
import com.garam.qook.resources.blackTextColor
import com.garam.qook.resources.cardBorderColor
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.linkTextFieldBorderColor
import com.garam.qook.resources.mainBackgroundColor
import com.garam.qook.resources.mainColor
import com.garam.qook.ui.navigation.RouteResult
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.apple_login_icon
import qook.composeapp.generated.resources.menu_24px
import qook.composeapp.generated.resources.next_icon
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class)
@Composable
fun HomeScreen(onNavigationToResult : (RouteResult) -> Unit,
               viewModel: HomeViewModel = koinViewModel()
) {

    var videoLinkText by rememberSaveable { mutableStateOf("") }
    val list by viewModel.analysisList.collectAsState()


    Scaffold(
        contentColor = mainBackgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Qook", fontFamily = fontFamily(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = mainBackgroundColor
                ),
                navigationIcon = {

                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(painter = painterResource(Res.drawable.menu_24px), contentDescription = "")
                    }

                }
            )
        }
    ) { innerPadding ->

        LazyColumn(

            modifier = Modifier.fillMaxWidth().fillMaxHeight()
                .background(color = mainBackgroundColor).padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = CardDefaults.shape,
                    border = BorderStroke(2.dp, color = cardBorderColor),
                    colors = CardDefaults.cardColors(
                        contentColor = Color.White,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier.padding(10.dp)
                ) {

                    Column(
                        modifier = Modifier.background(color = Color.White)
                            .clip(shape = RoundedCornerShape(10.dp)).padding(15.dp)
                    ) {

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "New Recipe Analysis",
                            color = Color.Black,
                            fontFamily = fontFamily(),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        TextField(
                            value = videoLinkText,
                            onValueChange = { newText ->
                                videoLinkText = newText
                            },
                            modifier = Modifier.fillMaxWidth().border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = linkTextFieldBorderColor
                                ), shape = RoundedCornerShape(10.dp)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent, // 밑줄 제거
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                cursorColor = Color.Black
                            ),
                            placeholder = {
                                Text("Paste YouTube, Instagram, or TikTok")
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        TextButton(
                            onClick = {


                                viewModel.saveAnalysis(AnalysisData(
                                    id = Uuid.random().toString(),
                                    title = videoLinkText,
                                    videoUrl = "",
                                    cookingImgUrl = "",
                                    createdTime = Clock.System.now().epochSeconds,
                                    ingredientsList = listOf()
                                ))
                                
                                onNavigationToResult(RouteResult)


                            },
                            modifier = Modifier.background(
                                color = mainColor,
                                shape = RoundedCornerShape(10.dp)
                            ).fillMaxWidth()
                        ) {

                            Text(text = "Analyze Recipe", color = blackTextColor,
                                fontFamily = fontFamily(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }

                    }

                }
            }

            item {

                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                    Text(text = "History", fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold,
                        color = Color.Black, fontSize = 15.sp, textAlign = TextAlign.Start)

                }
            }

            items(items = list, key = { item -> item.id }) { item ->

                Card(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )

                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {


                        AsyncImage(
                            model = item.cookingImgUrl,
                            placeholder = painterResource(Res.drawable.apple_login_icon),
                            contentDescription = "",
                            modifier = Modifier.padding(10.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f).padding(vertical = 20.dp)){

                            Text(text = item.title, fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold,
                                color = Color.Black, fontSize = 18.sp, textAlign = TextAlign.Start)

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(text = "", fontFamily = fontFamily(), fontWeight = FontWeight.Normal,
                                color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Start)

                        }

                        IconButton(
                            onClick = {

                                onNavigationToResult(RouteResult)

                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(painter = painterResource(Res.drawable.next_icon), contentDescription = "")

                        }

                        Spacer(modifier = Modifier.width(10.dp))

                    }


                }


            }

        }
    }


}