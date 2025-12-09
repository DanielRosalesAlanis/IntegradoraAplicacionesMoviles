package mx.edu.utez.integradoraaplicacionesmoviles.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mx.edu.utez.integradoraaplicacionesmoviles.domain.model.Song

@Composable
fun SongItemCard(
    song: Song,
    onClick: () -> Unit,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = song.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "${song.artist} - ${song.year}", style = MaterialTheme.typography.bodyMedium)
            }
            
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}