# Servidor Backend - Music Player App

## 🚀 Instalación

1. Instalar dependencias:
```bash
pip install -r requirements.txt
```

## ▶️ Ejecutar el servidor

```bash
python app.py
```

El servidor se iniciará en `http://localhost:5000`

## 🧪 Probar el servidor

En otra terminal, ejecuta:
```bash
python test_server.py
```

## 📡 Endpoints disponibles

### GET /songs
Obtiene todas las canciones
```
GET http://localhost:5000/songs
```

### POST /songs
Crea una nueva canción (multipart/form-data)
```
POST http://localhost:5000/songs
Content-Type: multipart/form-data

file: [archivo.mp3]
name: "Nombre de la canción"
artist: "Nombre del artista"
year: 2024
```

### PUT /songs/{id}
Actualiza los datos de una canción
```
PUT http://localhost:5000/songs/1
Content-Type: application/json

{
  "name": "Nuevo nombre",
  "artist": "Nuevo artista",
  "year": 2024
}
```

### DELETE /songs/{id}
Elimina una canción
```
DELETE http://localhost:5000/songs/1
```

### GET /uploads/{filename}
Descarga un archivo MP3
```
GET http://localhost:5000/uploads/cancion.mp3
```

## 📱 Conectar desde Android

Para conectar desde tu dispositivo Android:

1. **Emulador**: Usa `http://10.0.2.2:5000`
2. **Dispositivo físico**: Usa la IP de tu PC (ej: `http://192.168.1.100:5000`)

Para obtener tu IP en Windows:
```bash
ipconfig
```
Busca "Dirección IPv4" en tu adaptador de red activo.

## 📂 Estructura de archivos

- `app.py` - Servidor Flask principal
- `database.py` - Configuración de SQLite
- `requirements.txt` - Dependencias Python
- `uploads/` - Carpeta donde se guardan los archivos MP3
- `songs.db` - Base de datos SQLite (se crea automáticamente)
