package com.garam.qook.ui.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.garam.qook.resources.mainBackgroundColor
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AnalysisScreen(viewModel: AnalysisViewModel = koinViewModel()) {

    val categoryList = mutableStateListOf<List<String>>()

    LazyColumn(
        modifier = Modifier.background(color = mainBackgroundColor)
    ) {

        item {

            Card {

                Row {

//                    Image()

                    Column {

                        Text("")
                        Text("")
                        Text("")

                    }

                }

            }

        }

        items(categoryList) { category ->

            Column {

                Row {

                    Text("")
                    Text("")

                }

                category.forEach {

                    Row {

                        Checkbox(
                            checked = false,
                            onCheckedChange = {

                            }
                        )

                        Column {

                            Text(text = it)
                            Text("")

                        }


                    }


                }



            }
        }



    }



}