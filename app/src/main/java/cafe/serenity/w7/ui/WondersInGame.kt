package cafe.serenity.w7.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import cafe.serenity.w7.model.Wonder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


private val viewModel = WondersInGameViewModel()

@Composable
fun WondersInGameScreen(navController: NavController) { // Id list

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        this.items(state.wonders) {
            WonderItem(wonder = it)
        }
    }
}

@Composable
fun WonderItem(wonder: Wonder) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(8.dp)
        .background(Color.LightGray)) {
        Text(modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
            text = wonder.name,
            textAlign = TextAlign.Center)
    }
}

class WondersInGameViewModel() : ViewModel() {
    private val _stateFlow: StateFlow<State> = MutableStateFlow(State.default)
    val stateFlow: StateFlow<State> = _stateFlow

    data class State(val wonders: List<Wonder>) {
        companion object {
            val default = State(
                wonders = listOf(
                    Wonder(1, "A"),
                    Wonder(2, "B"),
                    Wonder(3, "C"),
                    Wonder(4, "D"),
                )
            )
        }
    }
}