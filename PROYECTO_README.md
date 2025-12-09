# 🎵 Music Player con Sensor de Proximidad

Aplicación Android con Kotlin que simula un reproductor de música tipo Spotify con control por sensor de proximidad.

## 📋 Características

- ✅ CRUD completo de canciones (Crear, Leer, Actualizar, Eliminar)
- ✅ Reproducción de archivos MP3
- ✅ Control por sensor de proximidad:
  - **1 tap**: Siguiente canción
  - **2 taps**: Pausar/Reanudar
  - **3 taps**: Canción anterior
  - **Hold (mantener)**: Detener
- ✅ Backend Python con Flask
- ✅ Interfaz moderna con Jetpack Compose

## 🏗️ Arquitectura

```
app/
├── data/                    # Capa de datos
│   ├── remote/
│   │   ├── api/            # Retrofit API
│   │   └── dto/            # Data Transfer Objects
│   └── repository/         # Repositorios
├── domain/                  # Capa de dominio
│   ├── model/              # Modelos de negocio
│   └── repository/         # Interfaces de repositorio
├── player/                  # Reproductor de música
├── sensor/                  # Manejo de sensores
├── ui/                      # Interfaz de usuario
│   ├── components/         # Componentes reutilizables
│   ├── navigation/         # Navegación
│   ├── screens/            # Pantallas
│   ├── theme/              # Tema de la app
│   └── viewmodel/          # ViewModels
└── util/                    # Utilidades
```

## 🚀 Configuración

### 1. Backend (Python)

```bash
cd server
pip install -r requirements.txt
python app.py
```

El servidor se iniciará en `http://localhost:5000`

### 2. Android App

1. Abre el proyecto en Android Studio
2. Actualiza la URL del servidor en `util/Constants.kt`:
   - **Emulador**: `http://10.0.2.2:5000/`
   - **Dispositivo físico**: `http://TU_IP:5000/`
3. Sincroniza Gradle
4. Ejecuta la app

## 📱 Uso

### Agregar canciones:
1. Ve a "Mis Canciones"
2. Presiona el botón "+"
3. Llena los datos (nombre, artista, año)
4. Selecciona un archivo MP3
5. Guarda

### Reproducir:
1. Selecciona una canción de la lista
2. Usa el sensor de proximidad para controlar la reproducción

### Gestos del sensor:
- Pasa la mano 1 vez: Siguiente
- Pasa la mano 2 veces rápido: Pausar/Reanudar
- Pasa la mano 3 veces rápido: Anterior
- Mantén la mano: Detener

## 🔧 Tecnologías

### Android:
- Kotlin
- Jetpack Compose
- Retrofit
- ExoPlayer (Media3)
- Coroutines & Flow
- Navigation Compose
- ViewModel

### Backend:
- Python 3
- Flask
- SQLite
- Flask-CORS

## 📦 Dependencias principales

```kotlin
// Networking
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")

// Media Player
implementation("androidx.media3:media3-exoplayer:1.4.1")

// Navigation
implementation("androidx.navigation:navigation-compose:2.7.7")

// Compose
implementation(platform("androidx.compose:compose-bom:2024.09.01"))
```

## 🔐 Permisos

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
```

## 📝 API Endpoints

- `GET /songs` - Obtener todas las canciones
- `POST /songs` - Crear nueva canción
- `PUT /songs/{id}` - Actualizar canción
- `DELETE /songs/{id}` - Eliminar canción
- `GET /uploads/{filename}` - Descargar archivo MP3

## 🐛 Troubleshooting

### No se conecta al servidor:
- Verifica que el servidor Python esté corriendo
- Revisa la URL en `Constants.kt`
- En dispositivo físico, asegúrate de estar en la misma red

### El sensor no funciona:
- Verifica que tu dispositivo tenga sensor de proximidad
- Prueba en un dispositivo físico (no todos los emuladores lo soportan)

### Error al reproducir:
- Verifica que el archivo MP3 sea válido
- Revisa los permisos de almacenamiento
- Comprueba la conexión al servidor

## 👥 Equipo

Proyecto final - Aplicaciones Móviles
UTEZ

## 📄 Licencia

Proyecto educativo - UTEZ 2024
