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
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.ObserverModifierNode
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.node.observeReads
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
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
                .segmentDimens(externalRadius, segmentWidth, startAngleDegrees, sweepAngleDegrees)
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

private class SegmentDimensElement(private val externalRadius: Dp, private val segmentWidth: Dp, private val startAngleDegrees: Float, private val sweepAngleDegrees: Float): ModifierNodeElement<SegmentDimensNode>() {
    override fun create(): SegmentDimensNode {
        return SegmentDimensNode(externalRadius, segmentWidth, startAngleDegrees, sweepAngleDegrees)
    }

    override fun update(node: SegmentDimensNode) {
        node.externalRadius = externalRadius
        node.segmentWidth = segmentWidth
        node.startAngleDegrees = startAngleDegrees
        node.sweepAngleDegrees = sweepAngleDegrees
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SegmentDimensElement

        if (externalRadius != other.externalRadius) return false
        if (segmentWidth != other.segmentWidth) return false
        if (startAngleDegrees != other.startAngleDegrees) return false
        if (sweepAngleDegrees != other.sweepAngleDegrees) return false

        return true
    }

    override fun hashCode(): Int {
        var result = externalRadius.hashCode()
        result = 31 * result + segmentWidth.hashCode()
        result = 31 * result + startAngleDegrees.hashCode()
        result = 31 * result + sweepAngleDegrees.hashCode()
        return result
    }
}

private class SegmentDimensNode(
    var externalRadius: Dp,
    var segmentWidth: Dp,
    var startAngleDegrees: Float,
    var sweepAngleDegrees: Float
) : Modifier.Node(), ObserverModifierNode {

    override fun onObservedReadsChanged() {

    }
}

@Stable
fun Modifier.segmentDimens(
    externalRadius: Dp,
    segmentWidth: Dp,
    startAngleDegrees: Float,
    sweepAngleDegrees: Float): Modifier {
    return this.then(
        SegmentDimensElement(
            externalRadius,
            segmentWidth,
            startAngleDegrees,
            sweepAngleDegrees)
    )
}

