package cr.ac.tec.proyecto.notesapp.steven.ui.activities.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.floatingactionbutton.FloatingActionButton
import cr.ac.tec.proyecto.notesapp.steven.R
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.activities.addnote.AddNoteActivity
import cr.ac.tec.proyecto.notesapp.steven.ui.activities.addnote.AddReminderActivity
import cr.ac.tec.proyecto.notesapp.steven.ui.base.BaseActivity
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.ConfirmationDialog
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.GridDecorator
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.InformationDialog
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.LoadingDialog
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView,
    NoteAdapter.NoteAdapterEvent {

    lateinit var presenter: MainPresenter
    @Inject
    lateinit var adapter: NoteAdapter

    @BindView(R.id.activity_main_list_notes)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.activity_main_fab_plus)
    lateinit var fabPlus: FloatingActionButton

    @BindView(R.id.activity_main_fab_delete)
    lateinit var fabDelete: FloatingActionButton

    @BindView(R.id.activity_main_fab_reminder)
    lateinit var fabReminder: FloatingActionButton

    @BindView(R.id.activity_main_fab_note)
    lateinit var fabNote: FloatingActionButton

    @BindView(R.id.activity_main_overlay)
    lateinit var overlay: View

    @BindView(R.id.activity_main_tv_reminder)
    lateinit var tvReminder: TextView

    @BindView(R.id.activity_main_tv_note)
    lateinit var tvNote: TextView

    lateinit var fabOpen: Animation

    lateinit var fabClose: Animation

    lateinit var fabCloseFull: Animation

    lateinit var fabOpenFull: Animation

    lateinit var fabClockwise: Animation

    lateinit var fabAnticlockwise: Animation

    lateinit var progress: LoadingDialog

    lateinit var deletingNotes: LoadingDialog

    var open = false

    var wasOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        presenter = MainPresenter()
        presenter.attachView(this)
        progress = LoadingDialog(this, applicationContext.getString(R.string.activity_main_loading_notes))
        deletingNotes = LoadingDialog(this, applicationContext.getString(R.string.activity_main_deleting_notes))
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        fabOpen = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fabClose = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        fabClockwise = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_clockwise)
        fabAnticlockwise = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_anti_clockwise)
        fabOpenFull = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open_full)
        fabCloseFull = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close_full)

        fabPlus.setOnClickListener { onFabAddClicked(it) }
        fabNote.setOnClickListener { onFabNoteClicked(it) }
        fabDelete.setOnClickListener { onFabDeleteClicked(it) }
        fabReminder.setOnClickListener { onFabReminderClicked(it) }

        adapter = NoteAdapter(this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(GridDecorator(2,applicationContext.resources.getDimension(R.dimen.fab_margin_end).toInt(),true,0))
        recyclerView.adapter = adapter
        progress.showDialog()
        presenter.loadNotes()
    }

    private fun onFabAddClicked(view: View){
        if(open){
            fabNote.startAnimation(fabClose)
            fabReminder.startAnimation(fabClose)
            fabPlus.startAnimation(fabAnticlockwise)
            fabReminder.isClickable = false
            fabNote.isClickable = false
            open = false
        }else{
            fabNote.startAnimation(fabOpen)
            fabReminder.startAnimation(fabOpen)
            fabPlus.startAnimation(fabClockwise)
            fabReminder.isClickable = true
            fabNote.isClickable = true
            open = true
        }
    }

    private fun onFabNoteClicked(view: View){
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("TITLE", applicationContext.getString(R.string.activity_add_note_tile))
        startActivity(intent)
    }

    private fun onFabReminderClicked(view: View){
        val intent = Intent(this, AddReminderActivity::class.java)
        intent.putExtra("TITLE", applicationContext.getString(R.string.activity_add_reminder_tile))
        startActivity(intent)
    }

    private fun onFabDeleteClicked(view: View){
        ConfirmationDialog(
            this,
            applicationContext.getString(R.string.activity_main_delete_notes),
            object : ConfirmationDialog.ConfirmationEvent{

                override fun onAccept() {
                    deletingNotes.showDialog()
                    presenter.deleteNotes(adapter.selectedNotes.toList())
                }

                override fun onCancel() {
                    if(wasOpen){
                        fabPlus.startAnimation(fabAnticlockwise)
                        wasOpen = false
                    }
                    fabPlus.visibility = View.VISIBLE
                    fabDelete.visibility = View.GONE
                    fabPlus.isClickable = true
                    adapter.unselectAll()
                }
            }
        ).showDialog()
    }

    override fun onNotesLoaded(notes: List<Note>) {
        progress.dismissDialog()
        adapter.updateList(notes)
        adapter.notifyDataSetChanged()
    }

    override fun onNotesError(msg: String) {
        progress.dismissDialog()
        InformationDialog(
            activity = this,
            title = applicationContext.getString(R.string.activity_main_error_notes),
            event = object : InformationDialog.Event{
                override fun onAccept() {

                }
            },
            isError = true
        ).showDialog()
    }

    override fun onNotesDeleted(notes: List<Note>) {
        deletingNotes.dismissDialog()
        InformationDialog(
            activity = this,
            title = applicationContext.getString(R.string.activity_main_success_delete),
            event = object: InformationDialog.Event{
                override fun onAccept() {
                    progress.showDialog()
                    presenter.loadNotes()
                }
            },
            isError = false
        ).showDialog()
    }

    override fun onNotesDeletedError(msg: String) {
        deletingNotes.dismissDialog()
        InformationDialog(
            activity = this,
            title = msg,
            event = object: InformationDialog.Event{
                override fun onAccept() {}
            },
            isError = true
        ).showDialog()
    }

    override fun onNoteClicked(note: Note) {
        if(!note.isReminder){
            val intent = Intent(this, AddNoteActivity::class.java)
            intent.putExtra("TITLE", applicationContext.getString(R.string.activity_edit_note_tile))
            intent.putExtra("EDIT", true)
            intent.putExtra("NOTE", note)
            startActivity(intent)
        }else{
            val intent = Intent(this, AddReminderActivity::class.java)
            intent.putExtra("TITLE", applicationContext.getString(R.string.activity_edit_note_tile))
            intent.putExtra("EDIT", true)
            intent.putExtra("NOTE", note)
            startActivity(intent)
        }
    }

    override fun onFirstNoteSelected() {
        if(open){
            fabNote.startAnimation(fabClose)
            fabReminder.startAnimation(fabClose)
            fabReminder.isClickable = false
            fabNote.isClickable = false
            open = false
            wasOpen = true
        }
        fabPlus.isClickable = false
        fabDelete.visibility = View.VISIBLE
        fabPlus.visibility = View.INVISIBLE

    }

    override fun onAllNotesUnselected() {
        if(wasOpen){
            fabPlus.startAnimation(fabAnticlockwise)
            wasOpen = false
        }
        fabPlus.visibility = View.VISIBLE
        fabDelete.visibility = View.GONE
        fabPlus.isClickable = true
    }



}