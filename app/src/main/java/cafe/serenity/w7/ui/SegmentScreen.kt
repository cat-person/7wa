package cafe.serenity.w7.ui

import android.content.ClipData
import android.graphics.PointF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.Draggable2DState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.toAndroidDragEvent
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
import androidx.compose.ui.input.pointer.pointerInput
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

var globalOffset by mutableStateOf(Offset(100f, 0f))

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Segment(modifier: Modifier,
            externalRadius: Dp,
            segmentWidth: Dp,
            startAngleDegrees: Float,
            sweepAngleDegrees: Float) {
    Canvas(modifier
            .width(200.dp)
            .height(200.dp)
//            .pointerInput(Unit) {
//                detectDragGestures { change, dragAmount ->
//                    change.consume()
//                    globalOffset.x += dragAmount.x
//                    globalOffset.y += dragAmount.y
//                }
//            }
            .draggable2D(
                state = rememberDraggable2DState { delta ->
                    globalOffset += delta
                }
            )
            .graphicsLayer {
                translationX = globalOffset.x
                translationY = globalOffset.y
            }
            .background(Color.Magenta)) {

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SegmentScreen() {

    SegmentView(Modifier,
//        .dragAndDropTarget({throw RuntimeException("AAAAA")}, dndTarget)
        100.dp, 40.dp, -150f, 120f)
}

@Composable
fun SegmentView(modifier: Modifier, externalRadius: Dp, segmentWidth: Dp, startAngleDegrees: Float, sweepAngleDegrees: Float) {
    Box(modifier = modifier.background(Color.LightGray)) {
        Segment(modifier, externalRadius, segmentWidth, startAngleDegrees, sweepAngleDegrees)
    }
}