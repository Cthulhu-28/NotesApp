package cr.ac.tec.proyecto.notesapp.steven.ui.activities.main

import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.MvpView

interface MainView : MvpView {

    fun onNotesLoaded(notes: List<Note>)

    fun onNotesError(msg: String)

    fun onNotesDeleted(notes: List<Note>)

    fun onNotesDeletedError(msg: String)
}