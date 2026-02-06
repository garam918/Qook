package com.garam.qook.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.model.PlayerSpeed
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.model.VideoPlayerConfig
import chaintech.videoplayer.ui.preview.VideoPreviewComposable
import chaintech.videoplayer.ui.video.VideoPlayerComposable
import com.garam.qook.data.RecipeAnalysis
import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.resources.brandGreen
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.lightGreen
import com.garam.qook.resources.mainBackgroundColor
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.back_icon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(viewModel: AnalysisViewModel = koinViewModel(), onBackBtn : () -> Unit, data: RecipeAnalysis, isPaid: Boolean) {

    val categoryList = data.ingredients
    val uriHandler = LocalUriHandler.current

    val playerHost = remember { MediaPlayerHost(
        mediaUrl = data.urlSource,
        autoPlay = false,
        isMuted = false,
        initialSpeed = PlayerSpeed.X1,
        initialVideoFitMode = ScreenResize.FIT,
        isLooping = false,
        startTimeInSeconds = 0f,
        isFullScreen = false
    ) }


    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Grocery List", fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp, color = Color.Black)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = mainBackgroundColor
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackBtn()
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.back_icon),
                            contentDescription = ""
                        )

                    }
                }
            )
        }


    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(color = mainBackgroundColor).padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            item {

                VideoPlayerComposable(
                    modifier = Modifier.background(
                        shape = RoundedCornerShape(10.dp), color = Color.Transparent
                    ).fillMaxWidth().height(200.dp),
                    playerHost = playerHost,
                    playerConfig = VideoPlayerConfig(
                        isSpeedControlEnabled = false,
                        isFullScreenEnabled = false,
                        showVideoQualityOptions = false,
                        showSubTitlesOptions = false,
                        showAudioTracksOptions = false
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column {

                    Text(text = data.dish, color = Color.Black, fontFamily = fontFamily(),
                        fontWeight = FontWeight.SemiBold, fontSize = 18.sp)

                    Text(text = "Home made Creamy Garlic Pasta")
                    Text(text = "${categoryList.sumOf { it.items.size }} ingredients", fontFamily = fontFamily(),
                        fontWeight = FontWeight.Normal, color = Color.Gray, fontSize = 14.sp)
                }


            }

            if(isPaid) item {

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.White,
                        contentColor = Color.White
                    )
                ) {

                    Column(modifier = Modifier.padding(10.dp)) {

                        Text(text = "Recipe Summary", color = Color.Black, fontWeight = FontWeight.SemiBold,
                            fontFamily = fontFamily(), fontSize = 18.sp)

                        Spacer(modifier = Modifier.height(10.dp))

                        val summaryList = listOf("Test1","Test2","Test3","Test4","Test5","Test6")

                        summaryList.forEachIndexed { index, s ->

                            RecipeSummaryStep(index+1, s)

                            Spacer(modifier = Modifier.height(10.dp))

                        }

                    }

                }

            }

            items(categoryList, key = { it.category}) { category ->

                Column {

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(text = category.category, fontFamily = fontFamily(), fontWeight = FontWeight.Bold,
                            fontSize = 18.sp, color = Color.Black, modifier = Modifier.weight(1f))

                        Text(text = "${category.items.size} items", fontFamily = fontFamily(),
                            fontWeight = FontWeight.Normal, fontSize = 13.sp, color = Color.LightGray)

                    }

                    category.items.forEach { item ->

                        Spacer(modifier = Modifier.height(5.dp))

                        Row(modifier = Modifier.fillMaxWidth().clickable(true, onClick = {
                            val searchUrl = "https://www.amazon.com/s?k=${item.ingredient}"

                            // val searchUrl = "https://www.coupang.com/np/search?q=$ingredient"

                            // 3. 브라우저 열기
                            uriHandler.openUri(searchUrl)
                        })) {

//                            Checkbox(
//                                checked = false,
//                                onCheckedChange = {
//
//                                }
//                            )

                            Column {

                                Text(text = item.ingredient)
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(text = item.amount)

                            }


                        }

                        Spacer(modifier = Modifier.height(5.dp))

                    }

                    Spacer(modifier = Modifier.height(20.dp))


                }
            }


        }

    }
}

@Composable
fun RecipeSummaryStep(number: Int, text: String) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "${number}", fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold, fontSize = 14.sp,
            color = brandGreen,
            modifier = Modifier.background(shape = CircleShape, color = lightGreen).padding(2.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, fontFamily = fontFamily(), fontWeight = FontWeight.Normal, fontSize = 13.sp,
            color = Color.Gray)
    }

}