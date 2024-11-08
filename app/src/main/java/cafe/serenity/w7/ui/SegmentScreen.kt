package cafe.serenity.w7.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun Segment(modifier: Modifier) {
    Canvas(modifier = modifier
        ) {
//        drawRect(
//            color = Color.Magenta,
//            size = size / 2f
//        )


//        drawCircle(
//            color = Color.Magenta,
//            radius = 48.dp.toPx()
//        )

//        drawArc(
//            color = Color.DarkGray,
//            startAngle = -90f,
//            sweepAngle = 40f,
//            useCenter = true
//        )
//
//        drawArc(
//            color = Color.Red,
//            startAngle = -90f,
//            sweepAngle = 40f,
//            useCenter = true,
//            size = Size(20f, 20f))
    }
}

@Composable
fun SegmentScreen() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Segment(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .graphicsLayer {
                    clip = true


                    shape = GenericShape { size: Size, _ ->
                        val width = size.width
                        val height = size.height

                        val cornerRadiusPx = 50f
                        val gapCornerRadiusPx = 50f
                        val gapSizePx = 50f
                        val concavityFactor = 1.1f

                        moveTo(0f, cornerRadiusPx)
                        quadraticTo(0f, 0f, cornerRadiusPx, 0f)

                        lineTo(width - gapSizePx - gapCornerRadiusPx, 0f)
                        quadraticTo(width - gapSizePx, 0f, width - gapSizePx, gapCornerRadiusPx)

                        quadraticTo(width - concavityFactor*gapSizePx, concavityFactor*gapSizePx, width - gapCornerRadiusPx, gapSizePx)

                        quadraticTo(width, gapSizePx, width, gapSizePx + gapCornerRadiusPx)

                        lineTo(width, height - cornerRadiusPx)
                        quadraticTo(width, height, width - cornerRadiusPx, height)

                        lineTo(cornerRadiusPx, height)
                        quadraticTo(0f, height, 0f, height - cornerRadiusPx)

                        lineTo(0f, cornerRadiusPx)
                    }
                }
                .background(Color.LightGray),
        )
    }
}

