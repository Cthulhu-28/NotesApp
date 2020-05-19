package cr.ac.tec.proyecto.notesapp.steven.ui.activities.main

import cr.ac.tec.proyecto.notesapp.steven.data.DataProvider
import cr.ac.tec.proyecto.notesapp.steven.data.base.DataEvent
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.BasePresenter
import java.util.*
import kotlin.random.Random

class MainPresenter : BasePresenter<MainView>(){

    private val dataProvider: DataProvider = DataProvider.getInstance()


    fun loadNotes(){
        dataProvider.dataManager.listNotes(DataEvent(
            onSuccess = {
                mvpView?.onNotesLoaded(it.data)
            },
            onError = {
                mvpView?.onNotesError(it)
            }
        ))
    }


    fun deleteNotes(notes: List<Note>){
        val event = DataEvent<List<Note>>(
            onSuccess = {
                mvpView?.onNotesDeleted(it.data)
            },
            onError = {
                mvpView?.onNotesDeletedError(it)
            }
        )
        dataProvider.dataManager.deleteNotes(notes, event)
    }

}