from flask import Flask, request, jsonify, send_from_directory
from flask_cors import CORS
from werkzeug.utils import secure_filename
import os
from database import init_db, get_connection

app = Flask(__name__)
CORS(app)

UPLOAD_FOLDER = os.path.join(os.path.dirname(__file__), 'uploads')
ALLOWED_EXTENSIONS = {'mp3', 'wav', 'm4a'}

app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
app.config['MAX_CONTENT_LENGTH'] = 50 * 1024 * 1024  # 50MB max

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

# GET all songs
@app.route('/songs', methods=['GET'])
def get_songs():
    conn = get_connection()
    cursor = conn.cursor()
    cursor.execute('SELECT * FROM songs')
    songs = cursor.fetchall()
    conn.close()
    
    return jsonify([dict(song) for song in songs])

# POST new song
@app.route('/songs', methods=['POST'])
def create_song():
    print("📥 Recibiendo petición POST /songs")
    print("Files:", request.files)
    print("Form:", request.form)
    
    if 'file' not in request.files:
        print("❌ No file in request")
        return jsonify({'error': 'No file provided'}), 400
    
    file = request.files['file']
    name = request.form.get('name')
    artist = request.form.get('artist')
    year = request.form.get('year')
    
    print(f"📝 Datos: name={name}, artist={artist}, year={year}, file={file.filename}")
    
    if not all([name, artist, year]):
        print("❌ Missing fields")
        return jsonify({'error': 'Missing required fields'}), 400
    
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        file_path = os.path.join(app.config['UPLOAD_FOLDER'], filename)
        print(f"💾 Guardando archivo en: {file_path}")
        file.save(file_path)
        
        conn = get_connection()
        cursor = conn.cursor()
        cursor.execute(
            'INSERT INTO songs (name, artist, year, file_path) VALUES (?, ?, ?, ?)',
            (name, artist, int(year), filename)
        )
        conn.commit()
        song_id = cursor.lastrowid
        print(f"✅ Canción guardada con ID: {song_id}")
        
        cursor.execute('SELECT * FROM songs WHERE id = ?', (song_id,))
        song = cursor.fetchone()
        conn.close()
        
        return jsonify(dict(song)), 201
    
    print("❌ Invalid file")
    return jsonify({'error': 'Invalid file'}), 400

# PUT update song
@app.route('/songs/<int:id>', methods=['PUT'])
def update_song(id):
    data = request.get_json()
    name = data.get('name')
    artist = data.get('artist')
    year = data.get('year')
    
    if not all([name, artist, year]):
        return jsonify({'error': 'Missing required fields'}), 400
    
    conn = get_connection()
    cursor = conn.cursor()
    cursor.execute(
        'UPDATE songs SET name = ?, artist = ?, year = ? WHERE id = ?',
        (name, artist, int(year), id)
    )
    conn.commit()
    
    cursor.execute('SELECT * FROM songs WHERE id = ?', (id,))
    song = cursor.fetchone()
    conn.close()
    
    if song:
        return jsonify(dict(song))
    return jsonify({'error': 'Song not found'}), 404

# DELETE song
@app.route('/songs/<int:id>', methods=['DELETE'])
def delete_song(id):
    conn = get_connection()
    cursor = conn.cursor()
    
    cursor.execute('SELECT file_path FROM songs WHERE id = ?', (id,))
    song = cursor.fetchone()
    
    if song:
        file_path = os.path.join(app.config['UPLOAD_FOLDER'], song['file_path'])
        if os.path.exists(file_path):
            os.remove(file_path)
        
        cursor.execute('DELETE FROM songs WHERE id = ?', (id,))
        conn.commit()
        conn.close()
        return '', 204
    
    conn.close()
    return jsonify({'error': 'Song not found'}), 404

# GET song file
@app.route('/uploads/<filename>', methods=['GET'])
def get_file(filename):
    return send_from_directory(app.config['UPLOAD_FOLDER'], filename)

if __name__ == '__main__':
    init_db()
    print("🎵 Servidor iniciado en http://localhost:5000")
    print("📁 Carpeta de uploads:", UPLOAD_FOLDER)
    app.run(host='0.0.0.0', port=5000, debug=True)
