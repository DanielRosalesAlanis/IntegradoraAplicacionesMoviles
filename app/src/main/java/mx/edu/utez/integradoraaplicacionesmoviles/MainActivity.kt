package mx.edu.utez.integradoraaplicacionesmoviles
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import mx.edu.utez.integradoraaplicacionesmoviles.ui.navigation.AppNavigation
import mx.edu.utez.integradoraaplicacionesmoviles.ui.theme.IntegradoraAplicacionesMovilesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntegradoraAplicacionesMovilesTheme {
                AppNavigation()
            }
        }
    }
}