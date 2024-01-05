package be.mauricecantaert.mobileappdevandroid.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import be.mauricecantaert.mobileappdevandroid.network.utils.ConnectionState
import be.mauricecantaert.mobileappdevandroid.network.utils.currentConnectivityState
import be.mauricecantaert.mobileappdevandroid.network.utils.observeConnectivityAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Observe the connectivity state as a Composable State.
 * Emits [ConnectionState] updates as a State<ConnectionState> when the network state changes.
 *
 * @return A State<ConnectionState> representing the network connectivity state.
 */
@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}
