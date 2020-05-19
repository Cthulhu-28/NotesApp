package cr.ac.tec.proyecto.notesapp.steven.ui.activities.addnote

import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.MvpView

interface AddNoteView : MvpView{

    fun onNoteAdded(note: Note)

    fun onNoteError(message: String)

}