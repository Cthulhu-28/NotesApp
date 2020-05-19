package cr.ac.tec.proyecto.notesapp.steven.ui.activities.addnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.NavUtils
import butterknife.BindView
import butterknife.ButterKnife
import cr.ac.tec.proyecto.notesapp.steven.R
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import cr.ac.tec.proyecto.notesapp.steven.ui.base.BaseActivity
import cr.ac.tec.proyecto.notesapp.steven.ui.utils.*
import java.text.SimpleDateFormat
import java.util.*

class AddReminderActivity : BaseActivity(), AddNoteView{

    lateinit var presenter: AddNotePresenter

    @BindView(R.id.activity_add_reminder_txt_title)
    lateinit var txtTitle: EditText

    @BindView(R.id.activity_add_reminder_time_picker)
    lateinit var timePicker: TimePicker

    lateinit var progress: LoadingDialog

    private var editMode = false

    private lateinit var note: Note

    private val format = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)

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

        timePicker.setIs24HourView(true)

        if(editMode){
            txtTitle.setText(note.title)
            val time = format.parse(note.reminderTime)!!
            val calendar = Calendar.getInstance()
            calendar.time = time
            timePicker.setTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
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
        if(txtTitle.text.isNotEmpty()){
            val time = "${timePicker.selectHour().toString().padStart(2,'0')}:${timePicker.selectMinute().toString().padStart(2,'0')}"
            val note = Note(
                date = Calendar.getInstance().time,
                title = txtTitle.text.toString(),
                content = "[reminder]@$time@HOLA"
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
                    NavUtils.navigateUpFromSameTask(this@AddReminderActivity)
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