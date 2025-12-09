package mx.edu.utez.integradoraaplicacionesmoviles.sensor

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GestureDetector {

    private var lastTriggerTime = 0L
    private val minInterval = 800L

    private val _gesture = MutableStateFlow(Gesture.None)
    val gesture: StateFlow<Gesture> = _gesture

    fun onProximityChanged(isNear: Boolean) {
        val now = System.currentTimeMillis()
        
        if (isNear && (now - lastTriggerTime > minInterval)) {
            android.util.Log.d("GestureDetector", "Proximity detected - Toggle!")
            _gesture.value = Gesture.Toggle
            lastTriggerTime = now
        }
    }
}

enum class Gesture {
    None,
    Toggle
}
