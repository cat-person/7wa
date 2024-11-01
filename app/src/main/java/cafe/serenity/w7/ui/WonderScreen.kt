package cafe.serenity.w7.ui

import androidx.activity.compose.BackHandler
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import cafe.serenity.w7.ui.data.Wonder

@Composable
fun WonderScreen(navController: NavController, name: String) {
    Text(name)
}