/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.model

data class PathScale(
    val x: Float = 0f,
    val y: Float = 0f,
    val px: Float = 0f,
    val py: Float = 0f
)

const val LargeScale = 8f
const val SmallScale = 6f
const val LargePadding = -15f
const val SmallPadding = -12f

sealed class PathScaleScreen(val pathConfig: PathScale) {
    object Large : PathScaleScreen(
        PathScale(
            x = LargeScale,
            y = LargeScale,
            px = LargePadding
        )
    )

    object Small : PathScaleScreen(
        PathScale(
            x = SmallScale,
            y = SmallScale,
            px = SmallPadding
        )
    )
}
