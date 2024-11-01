package cafe.serenity.w7.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.roundToInt

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun DragScreen(navController: NavController) {
//
//    // [START android_compose_drag_and_drop_1]
//    Modifier.dragAndDropSource {
//        detectTapGestures(onLongPress = {
//            // Transfer data here.
//            Log.e("AAA", "onLongPress")
//        })
//    }
//    // [END android_compose_drag_and_drop_1]
//
//    // [START android_compose_drag_and_drop_2]
//    Modifier.dragAndDropSource {
//        detectTapGestures(onLongPress = {
//            startTransfer(
//                DragAndDropTransferData(
//                    ClipData.newPlainText(
//                        "image Url", "AAAAAAAA"
//                    )
//                )
//            )
//        })
//    }
//    // [END android_compose_drag_and_drop_2]
//
//    // [START android_compose_drag_and_drop_3]
//    Modifier.dragAndDropSource {
//        detectTapGestures(onLongPress = {
//            startTransfer(
//                DragAndDropTransferData(
//                    ClipData.newPlainText(
//                        "image Url", "AAAAAAAAAA"
//                    ),
//                    flags = View.DRAG_FLAG_GLOBAL
//                )
//            )
//        })
//    }
//    // [END android_compose_drag_and_drop_3]
//
//    // [START android_compose_drag_and_drop_4]
//    val callback = remember {
//        object : DragAndDropTarget {
//            override fun onDrop(event: DragAndDropEvent): Boolean {
//                // Parse received data
//                return true
//            }
//        }
//    }
//    // [END android_compose_drag_and_drop_4]
//
//    // [START android_compose_drag_and_drop_5]
//    Modifier.dragAndDropTarget(
//        shouldStartDragAndDrop = { event ->
//            event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
//        }, target = callback
//    )
//    // [END android_compose_drag_and_drop_5]
//
//    // [START android_compose_drag_and_drop_6]
//    object : DragAndDropTarget {
//        override fun onStarted(event: DragAndDropEvent) {
//            // When the drag event starts
//        }
//
//        override fun onEntered(event: DragAndDropEvent) {
//            // When the dragged object enters the target surface
//        }
//
//        override fun onEnded(event: DragAndDropEvent) {
//            // When the drag event stops
//        }
//
//        override fun onExited(event: DragAndDropEvent) {
//            // When the dragged object exits the target surface
//        }
//
//        override fun onDrop(event: DragAndDropEvent): Boolean = true
//    }
//    // [END android_compose_drag_and_drop_6]
//}


@Composable
fun DragScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().padding(48.dp)) {
        var offsetX by remember {
            mutableStateOf(0f)
        }
        var offsetY by remember {
            mutableStateOf(0f)
        }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
//                .pointerInput {
//                    detectDragGestures { change, dragAmount ->

//                    }
//                }
        )
    }
}