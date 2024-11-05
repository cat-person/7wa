package cafe.serenity.w7.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MultiContentMeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density

/**
 * A layout composable with [content].
 * A composable function that arranges its children in a circular layout.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param overrideRadius A lambda function to override the radius of the circular layout (from center of the layout to center of the children's center). If sets to null it will be the radius of center child. Defaults to null.
 * @param startAngle A lambda function to specify the starting angle for the first child. the starting point is 0'clock and clockwise. Defaults to 0.0f.
 * @param center A composable function representing the center element of the circular layout.
 * @param content A composable function defining the children to be placed in the circular layout.
 */
@Composable
fun Circular(
    modifier: Modifier = Modifier,
    overrideRadius: (() -> Dp)? = null,
    startAngle: () -> Float = { 0.0f },
    center: @Composable () -> Unit,
    content: @Composable CircularScope.() -> Unit,
) {
    Layout(
        measurePolicy = circularMeasurePolicy(overrideRadius, startAngle),
        contents = listOf(center, { CircularScopeInstance.content() }),
        modifier = modifier,
    )
}

@JvmInline
@Immutable
internal value class ExtraRadius(
    private val radius: Dp
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) =
        ((parentData as? CircularParentData) ?: CircularParentData()).apply {
            extraRadius = radius
        }
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
        val maxExtraRadius = contentPlaceables.mapNotNull { placeable ->
            (placeable.parentData as? CircularParentData)?.extraRadius?.roundToPx()
        }.maxOrNull() ?: 0
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
                val finalAngle = (placeable.parentData as? CircularParentData)?.exactAngle ?: angle
                val angleRadian = finalAngle * Math.PI / 180
                val radius =
                    overallRadius + ((placeable.parentData as? CircularParentData)?.extraRadius?.roundToPx()
                        ?: 0)
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
        var isExpanded by remember { mutableStateOf(false) }

        val radius by animateDpAsState(
            targetValue = if (isExpanded) 120.dp else 80.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow,
            ),
        )

        Circular(
            overrideRadius = { radius },
            center = {
                CenterAvatar { isExpanded = !isExpanded }
            }
        ) {
            for (i in 1..12) {
                ChildAvatar(
//                    imageId = imageIds[i]
                )
            }
        }
    }
}

@Composable
fun CenterAvatar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.width(30.dp)
        .height(30.dp)
        .background(Color.Red))
}

@Composable
fun ChildAvatar(
) {
//    AsyncImage(
//        modifier = modifier
//            .size(48.dp)
//            .clip(CircleShape),
//        contentScale = ContentScale.Crop,
////        model = "https://i.pravatar.cc/300?u=$imageId",
//        contentDescription = null
//    )
    Box(modifier = Modifier
        .width(30.dp)
        .height(30.dp)
        .background(Color.Magenta))
}

@LayoutScopeMarker
@Immutable
interface CircularScope {
    @Stable
    fun Modifier.extraRadius(radius: Dp): Modifier

    @Stable
    fun Modifier.exactAngle(angle: Float): Modifier
}

internal object CircularScopeInstance : CircularScope {
    @Stable
    override fun Modifier.extraRadius(radius: Dp): Modifier =
        then(ExtraRadius(radius))

    @Stable
    override fun Modifier.exactAngle(angle: Float): Modifier =
        then(ExactAngle(angle))
}

internal data class CircularParentData(
    var exactAngle: Float? = null,
    var extraRadius: Dp? = null,
)

@JvmInline
@Immutable
internal value class ExactAngle(
    private val angle: Float
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) =
        ((parentData as? CircularParentData) ?: CircularParentData()).apply {
            exactAngle = angle
        }
}
