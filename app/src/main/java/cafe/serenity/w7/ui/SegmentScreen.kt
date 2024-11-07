package cafe.serenity.w7.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Segment(modifier: Modifier = Modifier) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            color = Color.Magenta,
            size = size / 2f
        )
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
                .background(Color.LightGray),
        )
    }
}