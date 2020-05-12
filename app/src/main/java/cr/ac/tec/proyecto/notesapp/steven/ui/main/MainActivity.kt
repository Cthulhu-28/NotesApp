package cr.ac.tec.proyecto.notesapp.steven.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import butterknife.BindView
import butterknife.ButterKnife
import cr.ac.tec.proyecto.notesapp.steven.R
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.BaseActivity
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.GridDecorator
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, NoteAdapter.NoteAdapterEvent{

    lateinit var presenter: MainPresenter
    @Inject
    lateinit var adapter: NoteAdapter

    @BindView(R.id.activity_main_list_notes)
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        presenter = MainPresenter()
        presenter.attachView(this)

    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        adapter = NoteAdapter(this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(GridDecorator(2,24,true,0))
        recyclerView.adapter = adapter
        presenter.loadNotes()
    }

    override fun onNotesLoaded(notes: List<Note>) {
        adapter.updateList(notes)
    }

    override fun onNotesError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onNoteClicked(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}