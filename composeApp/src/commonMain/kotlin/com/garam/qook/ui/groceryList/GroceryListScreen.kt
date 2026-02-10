package com.garam.qook.ui.groceryList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.garam.qook.resources.fontFamily
import com.garam.qook.resources.mainBackgroundColor
import com.garam.qook.util.shareText
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import qook.composeapp.generated.resources.Res
import qook.composeapp.generated.resources.back_icon
import qook.composeapp.generated.resources.delete
import qook.composeapp.generated.resources.ios_share

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceryListScreen(groceryViewModel: GroceryViewModel = koinViewModel(), onBackBtn: () -> Unit) {


    val list by groceryViewModel.groceryList.collectAsState()



    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Grocery List", fontFamily = fontFamily(), fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackBtn()
                    }) {
                        Icon(painter = painterResource(Res.drawable.back_icon), contentDescription = "")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = mainBackgroundColor,
                ),
                actions = {

                    IconButton(onClick = {

                        var shareText = "Grocery List\n"

                        list.forEach {
                            shareText += "\n${it.ingredientName}"
                        }

                        shareText(shareText)

                    }) {

                        Icon(painter = painterResource(Res.drawable.ios_share), contentDescription = "")


                    }


                }
            )
        },


    ) { innerPadding ->

        LazyColumn(modifier = Modifier.fillMaxSize()
            .background(color = mainBackgroundColor).padding(innerPadding)
            .padding(10.dp)) {


            items(list, key = { it.id } ) { item ->

                Spacer(modifier = Modifier.height(5.dp))

                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp), verticalAlignment = Alignment.CenterVertically) {

                    Text(text = item.ingredientName, fontFamily = fontFamily(), fontWeight = FontWeight.SemiBold, color = Color.Black,
                        fontSize = 18.sp, modifier = Modifier.weight(1f))


                    IconButton(onClick = {

                        groceryViewModel.deleteGrocery(item)

                    }) {

                        Icon(painter = painterResource(Res.drawable.delete), contentDescription = "")

                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

            }


        }
    }


}