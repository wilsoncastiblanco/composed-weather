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
package com.example.androiddevchallenge.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun Wall(modifier: Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        (0..size.height.toInt()).filter { it % 42 == 0 }.forEach { height ->
            (0..size.width.toInt()).filter { it % 86 == 0 }.forEach { width ->
                val offsetWidth = if ((height / 42) % 2 == 0) width.toFloat() - 40f else width.toFloat()
                drawRect(
                    color = Color(0xFF805A46),
                    topLeft = Offset(x = offsetWidth, y = height.toFloat()),
                    size = Size(width = 80f, height = 40f),
                    style = Stroke()
                )
            }
        }
    }
}
