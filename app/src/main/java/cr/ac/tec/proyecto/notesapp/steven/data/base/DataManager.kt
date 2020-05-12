package cr.ac.tec.proyecto.notesapp.steven.data.base

import cr.ac.tec.proyecto.notesapp.steven.data.model.Note

interface DataManager {

    fun listNotes(event: DataEvent<List<Note>>)

    fun getNode(id: Int, event: DataEvent<Note>)

    fun addNote(note: Note, event: DataEvent<Note>)

    fun editNote(note: Note, event: DataEvent<Note>)

    fun deleteNote(note: Note, event: DataEvent<Note>)

    fun deleteNotes(notes: List<Note>, event: DataEvent<List<Note>>)

}