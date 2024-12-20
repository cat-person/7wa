package cafe.serenity.w7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cafe.serenity.w7.ui.ScoreScreen
import cafe.serenity.w7.ui.WonderChoiceScreen
import cafe.serenity.w7.ui.WonderScreen
import cafe.serenity.w7.ui.WondersInGameScreen
import cafe.serenity.w7.ui.SegmentScreen
import cafe.serenity.w7.ui.theme.W7Theme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Host()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Host() {
    W7Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Graph data")
                    },
                )
            }
        ) { paddingValues->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                NavGraph(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Route.WonderChoiceRoute) {
        composable<Route.WonderChoiceRoute> {
            WonderChoiceScreen(navController)
        }
        composable<Route.WonderRoute> {
            val args = it.toRoute<Route.WonderRoute>()
            WonderScreen(navController, args.name)
        }
        composable<Route.ScoreRoute> {
            ScoreScreen(navController)
        }
        composable<Route.WondersInGameRoute> {
            WondersInGameScreen(navController)
        }
        composable<Route.DragRoute> {
            SegmentScreen()
//            CircularScreen()
        }
        composable<Route.SegmentRoute> {
            SegmentScreen()
        }
    }
}

sealed interface Route {
    @Serializable
    data object WonderChoiceRoute : Route
    @Serializable
    data class WonderRoute(val name: String) : Route
    @Serializable
    data object ScoreRoute : Route
    @Serializable
    data object WondersInGameRoute : Route
    @Serializable
    data object DragRoute : Route
    @Serializable
    data object SegmentRoute : Route
}


