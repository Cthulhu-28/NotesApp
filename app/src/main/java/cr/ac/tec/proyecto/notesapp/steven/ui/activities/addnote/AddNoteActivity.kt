package cr.ac.tec.proyecto.notesapp.steven.ui.activities.addnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.NavUtils
import butterknife.BindView
import butterknife.ButterKnife
import cr.ac.tec.proyecto.notesapp.steven.R
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.BaseActivity
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.InformationDialog
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.LoadingDialog
import java.util.*

class AddNoteActivity : BaseActivity(), AddNoteView{

    lateinit var presenter: AddNotePresenter

    @BindView(R.id.activity_add_note_txt_title)
    lateinit var txtTitle: EditText

    @BindView(R.id.activity_add_note_txt_content)
    lateinit var txtContent: EditText

    lateinit var progress: LoadingDialog

    private var editMode = false

    private lateinit var note: Note;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        val intent = intent
        this.title = intent.getStringExtra("TITLE")
        this.editMode = intent.getBooleanExtra("EDIT", false)
        if(editMode){
            note = intent.getParcelableExtra<Note>("NOTE")!!
        }

        presenter = AddNotePresenter()
        presenter.attachView(this)
        progress = LoadingDialog(this, applicationContext.getString(R.string.activity_add_note_saving))

        ButterKnife.bind(this)

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        if(editMode){
            txtTitle.setText(note.title)
            txtContent.setText(note.content)

        }
        super.onPostCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater;
        inflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            R.id.action_save->{
                saveNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(){
        if(txtContent.text.trim().isNotEmpty() && txtTitle.text.isNotEmpty()){
            val note = Note(
                date = Calendar.getInstance().time,
                title = txtTitle.text.toString(),
                content = txtContent.text.toString()
            )
            progress.showDialog()
            if(editMode){
                note.id = this.note.id
                presenter.editNote(note)
            }else{
                presenter.addNote(note)
            }
        }
    }

    override fun onNoteAdded(note: Note) {
        progress.dismissDialog()
        InformationDialog(
            activity = this,
            title = applicationContext.getString(R.string.activity_add_note_success),
            event = object: InformationDialog.Event{
                override fun onAccept() {
                    NavUtils.navigateUpFromSameTask(this@AddNoteActivity)
                }
            },
            isError = false
        ).showDialog()
    }

    override fun onNoteError(message: String) {
        progress.dismissDialog()
        InformationDialog(
            activity = this,
            title = message,
            event = object: InformationDialog.Event{
                override fun onAccept() {

                }
            },
            isError = true
        ).showDialog()
    }
}