package mx.edu.utez.integradoraaplicacionesmoviles.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProximityManager(context: Context) : SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val proximitySensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    private val _isNear = MutableStateFlow(false)
    val isNear: StateFlow<Boolean> = _isNear

    fun start() {
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val value = event?.values?.getOrNull(0) ?: return
        _isNear.value = value < (proximitySensor?.maximumRange ?: 0f)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
