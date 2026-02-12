package com.garam.qook.data

import com.garam.qook.data.local.LocalRecipeAnalysis
import com.garam.qook.data.remote.NetworkRecipeAnalysis
import kotlin.jvm.JvmName
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@OptIn(ExperimentalUuidApi::class)
fun NetworkRecipeAnalysis.toLocal(urlSource: String) = LocalRecipeAnalysis(
    id = Uuid.random().toString(),
    dish = dish,
    source = urlSource,
    createdTime = Clock.System.now().epochSeconds,
    ingredients = ingredients,
    recipe = recipe
)

fun LocalRecipeAnalysis.toUI() = RecipeAnalysis(
    id = id,
    dish = dish,
    source = source,
    createdTime = createdTime,
    ingredients = ingredients,
    recipe = recipe
)

fun RecipeAnalysis.toLocal(uid: String) = LocalRecipeAnalysis(
    id = id,
    uid = uid,
    dish = dish,
    source = source,
    createdTime = createdTime,
    ingredients = ingredients,
    recipe = recipe
)

@JvmName("LocalRecipeToUI")
fun List<LocalRecipeAnalysis>.toUI() = map(LocalRecipeAnalysis::toUI)