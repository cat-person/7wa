package cafe.serenity.w7.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cafe.serenity.w7.Route
import cafe.serenity.w7.ui.data.Side
import cafe.serenity.w7.ui.data.Wonder
import cafe.serenity.w7.ui.data.allWonders
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

private val wonderChoiceModel = WonderChoiceModel()

@Composable
fun WonderChoiceScreen(navController: NavController) {


    val state by wonderChoiceModel.stateFlow.collectAsState()
    // + empty
    LazyColumn {
        this.item {
            Text("Wonder list")
        }
        this.items(state.remainedWonders){
            Wonder(
                navController = navController,
                wonder = it)
        }
    }
}

@Composable
fun Wonder(navController: NavController, wonder: Wonder) {
    Button(onClick = {
//        navController.navigate(Route.WonderRoute(wonder.name))
//        navController.navigate(Route.WondersInGameRoute)
        navController.navigate(Route.DragRoute)
    }) {
        Text(wonder.name)
    }
}


class WonderChoiceModel() : ViewModel() {
    private val _remainedWondersFlow: StateFlow<List<Wonder>> = MutableStateFlow(allWonders)
    private val _chosenWondersFlow: StateFlow<List<Wonder>> = MutableStateFlow(listOf())

    val stateFlow: StateFlow<State> = combine(
        _remainedWondersFlow,
        _chosenWondersFlow
    ) { remainedWonders, chosenWonders ->
        State(
            remainedWonders = remainedWonders,
            chosenWonders = chosenWonders
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = State.default
    )

    data class State(val remainedWonders: List<Wonder>, val chosenWonders: List<Wonder>) {
        companion object {
            val default = State(
                remainedWonders = allWonders,
                chosenWonders = listOf()
            )
        }
    }
}
