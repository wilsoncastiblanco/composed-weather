package com.example.androiddevchallenge.ui.model

data class PathScale(
    val x: Float = 0f,
    val y: Float = 0f,
    val px: Float = 0f,
    val py: Float = 0f
)

const val LARGE_SCALE = 8f
const val SMALL_SCALE = 4f
const val LARGE_PADDING = -15f
const val SMALL_PADDING = -5f

sealed class PathScaleScreen(val pathConfig: PathScale) {
    object Large : PathScaleScreen(
        PathScale(
            x = LARGE_SCALE,
            y = LARGE_SCALE,
            px = LARGE_PADDING
        )
    )

    object Small : PathScaleScreen(
        PathScale(
            x = SMALL_SCALE,
            y = SMALL_SCALE,
            px = SMALL_PADDING
        )
    )
}