import requests
import os

BASE_URL = 'http://localhost:5000'

def test_get_songs():
    print("\n📋 Probando GET /songs")
    response = requests.get(f'{BASE_URL}/songs')
    print(f"Status: {response.status_code}")
    print(f"Canciones: {response.json()}")

def test_create_song():
    print("\n➕ Probando POST /songs")
    
    # Crear un archivo de prueba si no existe
    test_file = 'test_song.mp3'
    if not os.path.exists(test_file):
        with open(test_file, 'wb') as f:
            f.write(b'fake mp3 content for testing')
    
    with open(test_file, 'rb') as f:
        files = {'file': f}
        data = {
            'name': 'Canción de Prueba',
            'artist': 'Artista Test',
            'year': '2024'
        }
        response = requests.post(f'{BASE_URL}/songs', files=files, data=data)
        print(f"Status: {response.status_code}")
        print(f"Respuesta: {response.json()}")
        return response.json().get('id')

def test_update_song(song_id):
    print(f"\n✏️ Probando PUT /songs/{song_id}")
    data = {
        'name': 'Canción Actualizada',
        'artist': 'Artista Modificado',
        'year': 2023
    }
    response = requests.put(f'{BASE_URL}/songs/{song_id}', json=data)
    print(f"Status: {response.status_code}")
    print(f"Respuesta: {response.json()}")

def test_delete_song(song_id):
    print(f"\n🗑️ Probando DELETE /songs/{song_id}")
    response = requests.delete(f'{BASE_URL}/songs/{song_id}')
    print(f"Status: {response.status_code}")

if __name__ == '__main__':
    print("🧪 Iniciando pruebas del servidor...")
    print("⚠️ Asegúrate de que el servidor esté corriendo en http://localhost:5000")
    
    try:
        # Probar GET inicial
        test_get_songs()
        
        # Crear una canción
        song_id = test_create_song()
        
        # Ver todas las canciones
        test_get_songs()
        
        if song_id:
            # Actualizar la canción
            test_update_song(song_id)
            
            # Ver todas las canciones
            test_get_songs()
            
            # Eliminar la canción
            test_delete_song(song_id)
            
            # Ver todas las canciones
            test_get_songs()
        
        print("\n✅ Pruebas completadas!")
        
    except requests.exceptions.ConnectionError:
        print("\n❌ Error: No se pudo conectar al servidor.")
        print("Asegúrate de ejecutar 'python app.py' primero.")
    except Exception as e:
        print(f"\n❌ Error: {e}")
