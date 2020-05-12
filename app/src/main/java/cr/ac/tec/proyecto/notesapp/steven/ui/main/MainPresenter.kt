package cr.ac.tec.proyecto.notesapp.steven.ui.main

import cr.ac.tec.proyecto.notesapp.steven.data.DataProvider
import cr.ac.tec.proyecto.notesapp.steven.data.base.DataEvent
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.BasePresenter
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class MainPresenter : BasePresenter<MainView>(){

    private var dataProvider: DataProvider = DataProvider.getInstance()


    fun loadNotes(){

//        mvpView?.onNotesLoaded(list)
        dataProvider.dataManager.listNotes(DataEvent(
            onSuccess = {
                var rnd = Random(Date().time)
                var list = listOf(
                    Note(Date(),"Reminder 1","[reminder]@13:45@Content"),
                    Note(Date(),"Reminder 2","A B C D E".repeat(rnd.nextInt(3, 10))),
                    Note(Date(),"Reminder 3","[reminder]@23:45@Content"),
                    Note(Date(),"Reminder 2","A B C D E".repeat(rnd.nextInt(3, 10))),
                    Note(Date(),"Reminder 5","[reminder]@01:45@Content"),
                    Note(Date(),"Reminder 2","A B C D E".repeat(rnd.nextInt(3, 10))),
                    Note(Date(),"Reminder 5","[reminder]@01:45@Content"),
                    Note(Date(),"Reminder 5","[reminder]@01:45@Content"),
                    Note(Date(),"Reminder 2","A B C D E".repeat(rnd.nextInt(3, 10)))
                )
                mvpView?.onNotesLoaded(list)
            },
            onError = {
                mvpView?.onNotesError(it)
            }
        ))
    }

}