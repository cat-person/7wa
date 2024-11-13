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
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

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
    SegmentView(100.dp, 20.dp, -150f, 120f)
}

@Composable
fun SegmentView(externalRadius: Dp, segmentWidth: Dp, startAngleDegrees: Float, sweepAngleDegrees: Float) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        val width = externalRadius * 2f
        val height = externalRadius * 2f
        Segment(
            modifier = Modifier
                .width(width)
                .height(height)
                .background(Color.Magenta)
                .graphicsLayer {
                    clip = true
                    shape = GenericShape { size: Size, _ ->
                        arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), startAngleDegrees, sweepAngleDegrees, false)
                        arcTo(
                            Rect(segmentWidth.toPx(),
                                segmentWidth.toPx(),
                                (2f * externalRadius - segmentWidth).toPx(),
                                (2f * externalRadius - segmentWidth).toPx()),
                            startAngleDegrees + sweepAngleDegrees,
                            -sweepAngleDegrees,
                            false)

//                        arcTo(Rect(0f, 200f, 900f, 800f), -150f, 180f, false)



//                        lineTo( 150f, 0f)
//                        quadraticTo(width - gapSizePx, 0f, width - gapSizePx, gapCornerRadiusPx)
//
//                        quadraticTo(width - concavityFactor*gapSizePx, concavityFactor*gapSizePx, width - gapCornerRadiusPx, gapSizePx)
//
//                        quadraticTo(width, gapSizePx, width, gapSizePx + gapCornerRadiusPx)

//                        lineTo(150f, 40f)
////                        quadraticTo(width, height, width - cornerRadiusPx, height)
//
//                        lineTo(-150f, 40f)
////                        quadraticTo(0f, height, 0f, height - cornerRadiusPx)
//
//                        lineTo(-150f, 0f)
                    }
                }
                .background(Color.LightGray),
        )
    }
}

