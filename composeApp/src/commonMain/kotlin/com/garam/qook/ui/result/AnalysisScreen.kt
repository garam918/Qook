@file:OptIn(ExperimentalUuidApi::class)

package com.garam.qook.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.model.PlayerSpeed
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.model.VideoPlayerConfig
import chaintech.videoplayer.ui.video.VideoPlayerComposable
import com.garam.qook.data.RecipeAnalysis
import com.garam.qook.data.local.LocalGroceryData
import com.garam.qook.resources.brandGreen
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.lightGreen
import com.garam.qook.resources.mainBackgroundColor
import com.garam.qook.resources.mainColor
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.back_icon
import qook.composeapp.generated.resources.info
import kotlin.uuid.ExperimentalUuidApi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(viewModel: AnalysisViewModel = koinViewModel(), onBackBtn : () -> Unit, data: RecipeAnalysis, isPaid: Boolean) {

    val categoryList = data.ingredients
    val uriHandler = LocalUriHandler.current

    val snackbarScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val playerHost = remember { MediaPlayerHost(
        mediaUrl = data.source,
        autoPlay = false,
        isMuted = false,
        initialSpeed = PlayerSpeed.X1,
        initialVideoFitMode = ScreenResize.FIT,
        isLooping = false,
        startTimeInSeconds = 0f,
        isFullScreen = false
    ) }

    val groceryList = remember { mutableStateListOf<LocalGroceryData>() }


    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Recipe Analysis", fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold,
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
        },
        snackbarHost = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                )
            }


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

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.White,
                        contentColor = Color.White
                    )
                ) {


                    Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {

                        Text(
                            text = data.dish, color = Color.Black, fontFamily = fontFamily(),
                            fontWeight = FontWeight.SemiBold, fontSize = 18.sp
                        )

                        Text(
                            text = "${categoryList.sumOf { it.items.size }} ingredients",
                            fontFamily = fontFamily(),
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(Res.drawable.info), contentDescription = "", modifier = Modifier.size(16.dp))

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(text = "This content is AI-generated and may be inaccurate, so please use it for reference only.",
                        fontFamily = fontFamily(), fontWeight = FontWeight.Normal, fontSize = 11.sp, color = Color.Gray,
                        modifier = Modifier.weight(1f))
                }

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

                        val recipeList = data.recipe

                        recipeList.forEachIndexed { index, s ->

                            RecipeSummaryStep(index+1, s.step)

                            Spacer(modifier = Modifier.height(10.dp))

                        }

                    }

                }

            }

            items(categoryList, key = { it.category}) { category ->

                Column(modifier = Modifier.fillMaxWidth()) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(text = category.category, fontFamily = fontFamily(), fontWeight = FontWeight.Bold,
                            fontSize = 18.sp, color = Color.Black, modifier = Modifier.weight(1f))

                        Text(text = "${category.items.size} items", fontFamily = fontFamily(),
                            fontWeight = FontWeight.Normal, fontSize = 13.sp, color = Color.LightGray)

                    }

                    category.items.forEach { item ->

                        var checked by rememberSaveable { mutableStateOf(false) }


                        Spacer(modifier = Modifier.height(5.dp))

                        Row(modifier = Modifier.fillMaxWidth().clickable(true, onClick = {
                            val searchUrl = "https://www.amazon.com/s?k=${item.ingredient}"

                            // val searchUrl = "https://www.coupang.com/np/search?q=$ingredient"

                            // 3. 브라우저 열기
                            uriHandler.openUri(searchUrl)
                        })) {

                            Checkbox(
                                checked = checked,
                                onCheckedChange = {

                                    checked = it

                                    val groceryData = LocalGroceryData(item.ingredient, item.ingredient)

                                    if(checked) groceryList.add(groceryData)
                                    else groceryList.remove(groceryData)

                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = brandGreen,
                                    checkmarkColor = Color.White
                                )
                            )

                            Column {

                                Text(text = item.ingredient, fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp, color = Color.Black)
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(text = item.amount, fontFamily = fontFamily(), fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp, color = Color.Gray)

                            }


                        }

                        Spacer(modifier = Modifier.height(5.dp))

                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            item {

                TextButton(onClick = {

                    snackbarHostState.currentSnackbarData?.dismiss()

                    groceryList.forEach {
                        viewModel.saveGrocery(it)
                    }

                    snackbarScope.launch {

                        snackbarHostState.showSnackbar(
                            message = "Add to Grocery List Successfully",
                            duration = SnackbarDuration.Short
                        )



                    }


                },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mainColor,
                        contentColor = mainColor,
                        disabledContainerColor = mainColor,
                        disabledContentColor = mainColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                    ) {

                    Text(text = "Add to Grocery List", fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp, color = Color.Black)

                }

                Spacer(modifier = Modifier.height(10.dp))

            }


        }

    }
}

@Composable
fun RecipeSummaryStep(number: Int, text: String) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$number", fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold, fontSize = 14.sp,
            color = brandGreen)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = text, fontFamily = fontFamily(), fontWeight = FontWeight.Normal, fontSize = 13.sp,
            color = Color.Gray)
    }

}