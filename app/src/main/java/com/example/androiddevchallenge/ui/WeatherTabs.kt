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

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat

@Composable
fun WeatherTabs(
    viewState: WeatherViewModel.WeatherScreenState,
    viewModel: WeatherViewModel
) {
    TabRow(
        selectedTabIndex = viewState.selectedScreen.position,
        backgroundColor = MaterialTheme.colors.background
    ) {
        viewState.tabs.forEachIndexed { index, tab ->
            val selected = index == viewState.selectedScreen.position
            Tab(
                selected = selected,
                modifier = Modifier.padding(16.dp),
                onClick = { viewModel.onWeatherScreenSelected(tab) }
            ) {
                Text(
                    color = Color.Black,
                    text = tab.title.toUpperCase(
                        ConfigurationCompat.getLocales(LocalConfiguration.current)[0]
                    ),
                    modifier = Modifier.semantics {
                        contentDescription =
                            "Select Tab to know weather of ${tab.title} "
                    }
                )
            }
        }
    }
}
