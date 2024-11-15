package cafe.serenity.w7.ui

import android.graphics.PointF
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun Segment(modifier: Modifier, externalRadius: Dp, segmentWidth: Dp, startAngleDegrees: Float, sweepAngleDegrees: Float) {
    Canvas(modifier = modifier
        ) {

        val width = externalRadius * 2f
        val height = externalRadius * 2f

        drawRect(Color.Blue, Offset(0f, 0f), Size((segmentWidth / 10f).toPx(), segmentWidth.toPx() / 10f))

//        drawPath(Path().apply {
//            arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), startAngleDegrees, sweepAngleDegrees, false)
//
////                        arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), -90f, 90f, false)
////                        arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), 0f, 90f, false)
//
//            arcTo(
//                Rect(segmentWidth.toPx(),
//                    segmentWidth.toPx(),
//                    (2f * externalRadius - segmentWidth).toPx(),
//                    (2f * externalRadius - segmentWidth).toPx()),
//                startAngleDegrees + sweepAngleDegrees,
//                -sweepAngleDegrees,
//                false)
//        }, Color.Red)


    }
}

@Composable
fun SegmentScreen() {
    SegmentView(100.dp, 40.dp, -150f, 120f)
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
//                .graphicsLayer {
//                    clip = true
//                    shape = GenericShape { size: Size, _ ->
//                        arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), startAngleDegrees, sweepAngleDegrees, false)
//
////                        arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), -90f, 90f, false)
////                        arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), 0f, 90f, false)
//
//                        arcTo(
//                            Rect(segmentWidth.toPx(),
//                                segmentWidth.toPx(),
//                                (2f * externalRadius - segmentWidth).toPx(),
//                                (2f * externalRadius - segmentWidth).toPx()),
//                            startAngleDegrees + sweepAngleDegrees,
//                            -sweepAngleDegrees,
//                            false)
//                    }
//                }
                .background(Color.LightGray),
                    externalRadius, segmentWidth, startAngleDegrees, sweepAngleDegrees
        )
    }
}

