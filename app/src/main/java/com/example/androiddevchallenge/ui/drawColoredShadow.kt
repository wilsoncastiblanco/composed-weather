package com.example.androiddevchallenge.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 1f,
    shadowRadius: Dp = 30.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    path: Path,
    centerOffset: Offset = Offset(0f, 0f),
    rotationRadius: Float = 0f
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.strokeWidth = 50f
        frameworkPaint.style = android.graphics.Paint.Style.STROKE

        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        withTransform(
            {
                rotate(rotationRadius * 360, centerOffset)
            },
            {
                it.drawPath(
                    path = path,
                    paint = paint
                )
            }
        )

    }
}