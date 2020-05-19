package cr.ac.tec.proyecto.notesapp.steven.ui.activities.addnote

import cr.ac.tec.proyecto.notesapp.steven.data.DataProvider
import cr.ac.tec.proyecto.notesapp.steven.data.base.DataEvent
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.BasePresenter

class AddNotePresenter : BasePresenter<AddNoteView>(){

    private val dataProvider: DataProvider = DataProvider.getInstance()

    fun addNote(note: Note){
        dataProvider.dataManager.addNote(
            note = note,
            event = DataEvent(
                onError = { mvpView?.onNoteError(it) },
                onSuccess = { mvpView?.onNoteAdded(note) }
            )
        )
    }

    fun editNote(note: Note){
        dataProvider.dataManager.editNote(
            note = note,
            event = DataEvent(
                onError = { mvpView?.onNoteError(it) },
                onSuccess = { mvpView?.onNoteAdded(note) }
            )
        )
    }
}