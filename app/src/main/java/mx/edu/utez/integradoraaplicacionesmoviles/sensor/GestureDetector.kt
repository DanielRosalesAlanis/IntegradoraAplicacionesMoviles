package mx.edu.utez.integradoraaplicacionesmoviles.sensor

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GestureDetector {

    private var lastTapTime = 0L
    private var tapCount = 0
    private var lastNear = false
    private var holdStart = 0L

    private val _gesture = MutableStateFlow(Gesture.None)
    val gesture: StateFlow<Gesture> = _gesture

    fun onProximityChanged(isNear: Boolean) {
        val now = System.currentTimeMillis()

        if (isNear && !lastNear) {
            holdStart = now
        }

        if (!isNear && lastNear) {
            val holdDuration = now - holdStart

            if (holdDuration > 1200) {
                _gesture.value = Gesture.Hold
            } else {
                if (now - lastTapTime < 350) {
                    tapCount++
                } else {
                    tapCount = 1
                }

                lastTapTime = now

                when (tapCount) {
                    1 -> _gesture.value = Gesture.Tap
                    2 -> _gesture.value = Gesture.DoubleTap
                    3 -> _gesture.value = Gesture.TripleTap
                }
            }
        }

        lastNear = isNear
    }
}

enum class Gesture {
    None,
    Tap,
    DoubleTap,
    TripleTap,
    Hold
}
