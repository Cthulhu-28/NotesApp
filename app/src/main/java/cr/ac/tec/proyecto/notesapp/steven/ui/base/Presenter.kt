package cr.ac.tec.proyecto.notesapp.steven.ui.base

interface Presenter<V: MvpView>{

    fun attachView(mvpView: V)

    fun detachView()
}