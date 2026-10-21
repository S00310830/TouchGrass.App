package com.example.spike_localdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spike_localdb.data.DatabaseHelper
import com.example.spike_localdb.ui.theme.SpikeLocalDBTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseHelper = DatabaseHelper(this)

        setContent {
            SpikeLocalDBTheme {
                //put the db helper in the UI so the UI can call DB functions
                NotesScreen(databaseHelper)
            }
        }
    }
}

@Composable
fun NotesScreen(databaseHelper: DatabaseHelper) {

    var noteText by remember {
        mutableStateOf("")
    }

    var notes by remember {
        mutableStateOf(databaseHelper.getAllNotes())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Local Database Demo",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Enter a note") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {

                if (noteText.isNotBlank()) {

                    databaseHelper.insertNote(noteText)

                    notes = databaseHelper.getAllNotes()

                    noteText = ""
                }
            }
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            //Create a card for every note in notes
            items(notes) { note ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Text(
                        text = note,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}