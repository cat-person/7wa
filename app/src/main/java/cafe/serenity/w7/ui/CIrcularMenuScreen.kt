package cafe.serenity.w7.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MultiContentMeasurePolicy
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

@Composable
fun Circular(
    modifier: Modifier = Modifier,
    overrideRadius: (() -> Dp)? = null,
    startAngle: () -> Float = { 0.0f },
    center: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Layout(
        measurePolicy = circularMeasurePolicy(overrideRadius, startAngle),
        contents = listOf(center, content),
        modifier = modifier,
    )
}

private fun circularMeasurePolicy(
    overrideRadius: (() -> Dp)?,
    startAngle: () -> Float,
) =
    MultiContentMeasurePolicy { (centerMeasurables: List<Measurable>, contentMeasurables: List<Measurable>),
                                constraints: Constraints ->

        require(centerMeasurables.size == 1) { "Center composable can have only one child" }

        // Measure children
        val modifiedConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val centerPlaceable: Placeable = centerMeasurables.first().measure(modifiedConstraints)
        val contentPlaceables: List<Placeable> = contentMeasurables.map { it.measure(modifiedConstraints) }

        require(centerPlaceable.isCircle()) { "Center composable must be circle" }
        require(contentPlaceables.all { it.isCircle() }) { "Content composables must be circle" }

        // Calculate layout size
        val overallRadius = overrideRadius?.invoke()?.roundToPx() ?: (centerPlaceable.height / 2)
        val maxExtraRadius = 120.dp.roundToPx()
        val totalRadius = overallRadius + maxExtraRadius
        val biggestChildSize = contentPlaceables.maxOfOrNull { it.height } ?: 0
        val centerSize = centerPlaceable.height
        val layoutSize = max(centerSize, 2 * totalRadius + biggestChildSize)

        layout(layoutSize, layoutSize) {
            // Place children
            val angleBetweenChildren = 360.0f / contentPlaceables.size
            val middle = layoutSize / 2
            var angle = startAngle()
            contentPlaceables.forEach { placeable ->
                val finalAngle = angle //(placeable.parentData as? CircularParentData)?.exactAngle ?: angle
                val angleRadian = finalAngle * Math.PI / 180
                val radius = overallRadius + 60.dp.roundToPx()
                placeable.place(
                    x = (middle + radius * sin(angleRadian) - placeable.height / 2).toInt(),
                    y = (middle - radius * cos(angleRadian) - placeable.height / 2).toInt(),
                )
                angle += angleBetweenChildren
            }
            centerPlaceable.place(middle - centerSize / 2, middle - centerSize / 2)
        }
    }

private fun Placeable.isCircle() = height == width

@Composable
fun CircularScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Circular(
            overrideRadius = { 120.dp },
            center = {
                CenterAvatar ()
            }
        ) {
            for (i in 1..12) {
                ChildAvatar()
            }
        }
    }
}

@Composable
fun CenterAvatar(
) {
    Box(modifier = Modifier
        .width(60.dp)
        .height(60.dp)
        .background(Color.Red))
}

@Composable
fun ChildAvatar(
) {
    Box(modifier = Modifier
        .width(30.dp)
        .height(30.dp)
        .background(Color.Magenta))
}

//@LayoutScopeMarker
//@Immutable
//interface CircularScope {
//    @Stable
//    fun Modifier.extraRadius(radius: Dp): Modifier
//
//    @Stable
//    fun Modifier.exactAngle(angle: Float): Modifier
//}
