package edu.ucne.jose_estrella_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.jose_estrella_ap2_p2.presentation.Gasto.GastoScreen
import edu.ucne.jose_estrella_ap2_p2.presentation.Gasto.GastoViewModel
import edu.ucne.jose_estrella_ap2_p2.ui.theme.Jose_Estrella_AP2_P2Theme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Jose_Estrella_AP2_P2Theme {
                val viewModel: GastoViewModel = viewModel()

                GastoScreen(viewModel = viewModel)
            }
        }
    }
}

