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
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Segment(modifier: Modifier, externalRadius: Dp, segmentWidth: Dp, startAngleDegrees: Float, sweepAngleDegrees: Float) {
    Canvas(modifier = modifier
        ) {

        val width = externalRadius * 2f
        val height = externalRadius * 2f

        val internalRadius = externalRadius - segmentWidth

        // Corners
        val cornerRadius = segmentWidth.toPx() / 4f

        val endAngleDegrees = startAngleDegrees + sweepAngleDegrees

        val startRotationVector = PointF(cos(startAngleDegrees * 3.14f / 180f), sin(startAngleDegrees * 3.14f / 180f))
        val endRotationVector = PointF(cos(endAngleDegrees * 3.14f / 180f), sin(endAngleDegrees * 3.14f / 180f))

        drawPath(Path().apply {
            arcTo(Rect(0f, 0f, width.toPx(), height.toPx()), startAngleDegrees, sweepAngleDegrees, false)

            arcTo(Rect(
                Offset((externalRadius.toPx() - cornerRadius + (externalRadius.toPx() - cornerRadius) * endRotationVector.x),
                    externalRadius.toPx() - cornerRadius + (externalRadius.toPx() - cornerRadius) * endRotationVector.y),
                Size(2f * cornerRadius, 2f * cornerRadius)), endAngleDegrees, 90f, false)

            arcTo(Rect(
                Offset(externalRadius.toPx() - cornerRadius + (internalRadius.toPx() + cornerRadius) * endRotationVector.x,
                    externalRadius.toPx() - cornerRadius + (internalRadius.toPx() + cornerRadius) * endRotationVector.y),
                Size(2f * cornerRadius, 2f * cornerRadius)), endAngleDegrees + 90f, 90f, false)

            arcTo(
                Rect(segmentWidth.toPx(),
                    segmentWidth.toPx(),
                    (2f * externalRadius - segmentWidth).toPx(),
                    (2f * externalRadius - segmentWidth).toPx()),
                startAngleDegrees + sweepAngleDegrees,
                -sweepAngleDegrees,
                false)

            arcTo(Rect(
                Offset(externalRadius.toPx() - cornerRadius + (internalRadius.toPx() + cornerRadius) * startRotationVector.x,
                    externalRadius.toPx() - cornerRadius + (internalRadius.toPx() + cornerRadius) * startRotationVector.y),
                Size(2f * cornerRadius, 2f * cornerRadius)), 180f + startAngleDegrees, 90f, false)

            arcTo(Rect(
                Offset((externalRadius.toPx() - cornerRadius + (externalRadius.toPx() - cornerRadius) * startRotationVector.x),
                    externalRadius.toPx() - cornerRadius + (externalRadius.toPx() - cornerRadius) * startRotationVector.y),
                Size(2f * cornerRadius, 2f * cornerRadius)), startAngleDegrees - 90f, 90f, false)

        }, Color.Red)

//        drawCircle(Color.Green,
//            cornerRadius,
//            Offset((externalRadius.toPx() + (externalRadius.toPx() - cornerRadius) * startRotationVector.x), externalRadius.toPx()  + (externalRadius.toPx() - cornerRadius) * startRotationVector.y))
//
//        drawCircle(Color.Black,
//            cornerRadius,
//            Offset(externalRadius.toPx() + (internalRadius.toPx() + cornerRadius) * startRotationVector.x, externalRadius.toPx() + (internalRadius.toPx() + cornerRadius) * startRotationVector.y))
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

