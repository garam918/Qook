package com.garam.qook.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.garam.qook.data.RecipeAnalysis
import com.garam.qook.data.local.LocalUserData
import com.garam.qook.resources.blackTextColor
import com.garam.qook.resources.cardBorderColor
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.linkTextFieldBorderColor
import com.garam.qook.resources.mainBackgroundColor
import com.garam.qook.resources.mainColor
import com.garam.qook.revenueCatTest.RevenueCatRepository
import com.garam.qook.ui.dialog.AnalysisLimitDialogScreen
import com.garam.qook.ui.dialog.LogOutDialog
import com.garam.qook.util.getRelativeTimeTextEn
import com.garam.qook.util.isYouTubeUrl
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.logout
import qook.composeapp.generated.resources.menu_24px
import qook.composeapp.generated.resources.next_icon
import qook.composeapp.generated.resources.privacy_policy_string
import qook.composeapp.generated.resources.shopping_cart
import qook.composeapp.generated.resources.terms_of_use_string
import kotlin.math.absoluteValue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalMaterial3Api::class, ExperimentalUuidApi::class, ExperimentalTime::class)
@Composable
fun HomeScreen(
    onNavigationToResult: (RecipeAnalysis) -> Unit,
    onNavigationToPaywall: () -> Unit,
    onNavigationToOnboarding: () -> Unit,
    onNavigationToGrocery: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    var videoLinkText by rememberSaveable { mutableStateOf("") }
    val list by viewModel.analysisList.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val sendUrlScope = rememberCoroutineScope()

    var isLoadingShow by remember { mutableStateOf(false) }

    var isUrlError by remember { mutableStateOf(false) }

    var isAnalysisFail by remember { mutableStateOf(false) }

    val currentUser by viewModel.currentUser.collectAsState()

    var isShowLogoutDialog by remember { mutableStateOf(false) }

    var isShowUpgradeDialog by remember { mutableStateOf(false) }

    val groceryList by viewModel.groceryList.collectAsState()


    if(isShowUpgradeDialog) AnalysisLimitDialogScreen(
        onDismiss = {
            isShowUpgradeDialog = false
        },
        onUpgradePremium = {
            onNavigationToPaywall()
        }
    )


    if (isShowLogoutDialog) LogOutDialog("Logout", onDismiss = {

        isShowLogoutDialog = false

    }, logOut = {

        isShowLogoutDialog = false

        viewModel.signOut().invokeOnCompletion {
            onNavigationToOnboarding()

        }

    })

    LaunchedEffect(Unit) {

        viewModel.getCurrentUser()

        println("Home Screen User : $currentUser")

    }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // 사이드 메뉴 내부 디자인 (ModalDrawerSheet 사용)
            ModalDrawerSheet(modifier = Modifier.width(250.dp)) {
                DrawerMenuContent(
                    onLogout = { isShowLogoutDialog = true },
                    onDeleteAccount = { /* 탈퇴 로직 */ },
                    onNavigationToPaywall = {
                        onNavigationToPaywall()
                    },
                    currentUser = currentUser
                )
            }
        }
    ) {
        Scaffold(
            contentColor = mainBackgroundColor,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Qook",
                            fontFamily = fontFamily(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = mainBackgroundColor
                    ),
                    navigationIcon = {


                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.menu_24px),
                                contentDescription = ""
                            )
                        }

                    },
                    actions = {

                        BadgedBox(
                            badge = {
                                Badge(
                                    contentColor = Color.White,
                                    containerColor = Color.Red
                                ) {

                                    Text(text = "${groceryList.size}", textAlign = TextAlign.Center)

                                }
                            }
                        ) {
                            IconButton(onClick = {

                                onNavigationToGrocery()

                            }) {
                                Icon(
                                    painter = painterResource(Res.drawable.shopping_cart),
                                    contentDescription = ""
                                )

                            }
                        }


                    }
                )
            },
            modifier = Modifier.padding(horizontal = 10.dp)
        ) { innerPadding ->

            LazyColumn(

                modifier = Modifier.fillMaxWidth().fillMaxHeight()
                    .background(color = mainBackgroundColor).padding(innerPadding)
            ) {


                item {
//                    Spacer(modifier = Modifier.height(20.dp))

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
                                        color = if (isUrlError || isAnalysisFail) Color.Red else linkTextFieldBorderColor
                                    ), shape = RoundedCornerShape(10.dp)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent, // 밑줄 제거
                                    unfocusedIndicatorColor = Color.Transparent,
                                    unfocusedContainerColor = Color.White,
                                    disabledContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    cursorColor = Color.Black,
                                    errorContainerColor = Color.White,
                                    errorIndicatorColor = Color.Transparent
                                ),
                                placeholder = {
                                    Text(text = "Paste YouTube Link", fontFamily = fontFamily(), fontWeight = FontWeight.Normal)
                                },
                                isError = isUrlError || isAnalysisFail
                            )

                            if (!isUrlError) Spacer(modifier = Modifier.height(10.dp))

                            if (isLoadingShow) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                    CircularProgressIndicator(
                                        color = mainColor,
                                        strokeWidth = 2.dp,
                                        gapSize = 2.dp
                                    )
                                }
                            }

                            if (isUrlError || isAnalysisFail) Text(
                                text = if(isUrlError) "Invalid URL. Please enter a valid YouTube link" else "Analysis failed. Please try again later",
                                color = Color.Red,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 10.dp)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            TextButton(
                                enabled = !isLoadingShow,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = mainColor,
                                    disabledContainerColor = Color.LightGray,
                                    contentColor = mainColor,
                                    disabledContentColor = Color.LightGray
                                ),
                                contentPadding = PaddingValues(vertical = 5.dp),
                                shape = RoundedCornerShape(10.dp),
                                onClick = {

                                    val lastUseDate = LocalDate.parse(currentUser?.lastUseDate.toString())

                                    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

                                    println(lastUseDate.daysUntil(today))

                                    println(currentUser)

                                    if(lastUseDate.daysUntil(today).absoluteValue >= 1) {

                                        currentUser?.let {
                                            viewModel.updateUser(it.copy(lastUseDate = today.toString(), usageCount = 3))

                                        }
                                    }

                                    if (isYouTubeUrl(videoLinkText)) {

                                        val isPaid = currentUser?.paid
                                        val usageCount = currentUser?.usageCount

                                        if(isPaid != true && usageCount == 0) {
                                            isShowUpgradeDialog = true
                                        }
                                        else {

                                            isLoadingShow = true
                                            isUrlError = false
                                            isAnalysisFail = false

                                            sendUrlScope.launch {


                                                val result = viewModel.sendUrl(videoLinkText).await()


                                                result.onSuccess {
                                                    isLoadingShow = false
                                                }.onFailure {
                                                    isLoadingShow = false
                                                }

                                                println("home screen ${result.getOrNull()}")

                                                val data = RecipeAnalysis(
                                                    id = Uuid.random().toString(),
                                                    dish = result.getOrNull()?.dish ?: "Unknown",
                                                    source = result.getOrNull()?.source ?: "",
                                                    createdTime = Clock.System.now().epochSeconds,
                                                    ingredients = result.getOrNull()?.ingredients ?: listOf(),
                                                    recipe = result.getOrNull()?.recipe ?: listOf()
                                                )

                                                if(data.dish == "Unknown") {
                                                    isAnalysisFail = true
                                                }
                                                else {

                                                    if (isPaid != true) currentUser?.let {
                                                        viewModel.updateUser(
                                                            it.copy(
                                                                lastUseDate = today.toString(),
                                                                usageCount = it.usageCount - 1
                                                            )
                                                        )
                                                    }

                                                    viewModel.saveAnalysis(data)

                                                    onNavigationToResult(data)
                                                }

                                            }
                                        }
                                    } else {

                                        isUrlError = true

                                    }

                                },
                            ) {

                                Text(
                                    text = "Analyze Recipe", color = blackTextColor,
                                    fontFamily = fontFamily(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }

                        }

                    }
                }

                item {

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            text = "History",
                            fontFamily = fontFamily(),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Start
                        )

                    }
                }

                items(items = list, key = { item -> item.id }) { item ->

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(3.dp)
//                        border = BorderStroke(0.5.dp, Color.LightGray)

                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        ) {

                            Column(modifier = Modifier.weight(1f).padding(vertical = 20.dp)) {

                                Text(
                                    text = item.dish,
                                    fontFamily = fontFamily(),
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black,
                                    fontSize = 17.sp,
                                    textAlign = TextAlign.Start,
                                    maxLines = 1
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                Text(
                                    text = getRelativeTimeTextEn(item.createdTime),
                                    fontFamily = fontFamily(),
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Gray,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Start
                                )

                            }

                            IconButton(
                                onClick = {

                                    onNavigationToResult(item)

                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.next_icon),
                                    contentDescription = ""
                                )

                            }

                            Spacer(modifier = Modifier.width(10.dp))

                        }


                    }


                }

            }
        }
    }
}

@Composable
fun DrawerMenuContent(
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    onNavigationToPaywall: () -> Unit,
//    revenueCatRepo: RevenueCatRepository,
    currentUser: LocalUserData?
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val uriHandler = LocalUriHandler.current

        val termsOfUseUrl = stringResource(Res.string.terms_of_use_string)
        val privacyPolicyUrl = stringResource(Res.string.privacy_policy_string)

        // 프로필 영역
        Spacer(modifier = Modifier.height(24.dp))
        Surface(shape = CircleShape, color = Color(0xFFC8E6C9), modifier = Modifier.size(64.dp)) {
//            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(16.dp))
        }
        Text(
            text = currentUser?.email.toString(),
            fontFamily = fontFamily(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp)
        )


        HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))

        // 메뉴 아이템들
//        NavigationDrawerItem(
//            label = { Text("Manage Subscription") },
//            selected = false,
//            onClick = {
//                onNavigationToPaywall()
//            },
//            icon = { Icon(painter = painterResource(Res.drawable.credit_card), contentDescription = null) }
//        )

        Spacer(modifier = Modifier.weight(1f)) // 아래로 밀어내기

        // 하단 영역 (Delete Account 및 정책)
//        TextButton(onClick = onDeleteAccount) {
////            Icon(Icons.Default.DeleteForever, contentDescription = null, tint = Color.Red)
//            Spacer(Modifier.width(8.dp))
//            Text("Delete Account", color = Color.Red)
//        }

        NavigationDrawerItem(
            label = { Text("Logout") },
            selected = false,
            onClick = onLogout,
            icon = { Icon(painter = painterResource(Res.drawable.logout), contentDescription = null) }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        TextButton(onClick = {
            uriHandler.openUri(termsOfUseUrl)
        }) {
            Text(
                "Terms of Service",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }

        TextButton(onClick = {
            uriHandler.openUri(privacyPolicyUrl)

        }) {
            Text("Privacy Policy", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        }


    }
}