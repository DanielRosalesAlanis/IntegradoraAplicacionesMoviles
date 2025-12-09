package mx.edu.utez.integradoraaplicacionesmoviles.sensor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GestureDetector {

    private var lastNear = false
    private var nearStartTime = 0L
    private var lastGestureTime = 0L
    private val minInterval = 2000L
    private val maxNearDuration = 500L

    private val _gesture = MutableStateFlow(Gesture.None)
    val gesture: StateFlow<Gesture> = _gesture

    fun onProximityChanged(isNear: Boolean) {
        val now = System.currentTimeMillis()
        
        if (isNear && !lastNear) {
            nearStartTime = now
        }
        
        if (!isNear && lastNear) {
            val nearDuration = now - nearStartTime
            val timeSinceLastGesture = now - lastGestureTime
            
            if (nearDuration < maxNearDuration && timeSinceLastGesture > minInterval) {
                android.util.Log.d("GestureDetector", "Quick swipe detected! Duration: ${nearDuration}ms")
                _gesture.value = Gesture.Tap
                lastGestureTime = now
                
                CoroutineScope(Dispatchers.Main).launch {
                    delay(100)
                    _gesture.value = Gesture.None
                }
            } else {
                android.util.Log.d("GestureDetector", "Ignored - Duration: ${nearDuration}ms, Interval: ${timeSinceLastGesture}ms")
            }
        }
        
        lastNear = isNear
    }
}

enum class Gesture {
    None,
    Tap
}
