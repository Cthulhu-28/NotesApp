package cr.ac.tec.proyecto.notesapp.steven.ui.activities.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import cr.ac.tec.proyecto.notesapp.steven.R
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import kotlinx.android.synthetic.main.item_note.view.*
import javax.annotation.Resource
import javax.inject.Inject

class NoteAdapter @Inject constructor(val event: NoteAdapterEvent): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val selected = mutableMapOf<Note, Boolean>()
    private var notes: List<Note> = listOf()
    private var noteSelected = false
    val  selectedNotes
        get() = selected.keys.filter { selected[it]!! }.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view).bindEvents()
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        if(note.isReminder.not()){
            holder.tvContent.text = note.content
            holder.tvContent.visibility = View.VISIBLE
            holder.tvReminder.visibility = View.GONE
        }else{
            holder.tvReminder.text = note.reminderTime
            holder.tvReminder.visibility = View.VISIBLE
            holder.tvContent.visibility = View.GONE
        }
        if(selected.containsKey(note)){
            holder.selected.visibility = if(selected[note]!!)View.VISIBLE else View.INVISIBLE
        }
        holder.tvTitle.text = note.title
        Glide
            .with(holder.itemView.context)
            .load(resolvePosition(position))
            .into(holder.imgDot)
    }


    @Resource
    private fun resolvePosition(position: Int): Int{
        return when(position){
            0-> dots[0]
            else->{
                val sum = if(position % 2 == 0) position + position - 1 else position + position + 1
                val n = (sum-3)/4
                dots[if(n%2 == 0) 1 else 0]
            }
        }
    }

    fun updateList(list: List<Note>){
        this.notes = list
        this.selected.clear()
        list.forEach { selected.put(it, false) }
        notifyDataSetChanged()
    }

    fun <T : RecyclerView.ViewHolder> T.bindEvents(): T {

        itemView.setOnClickListener{
            event.onNoteClicked(notes[adapterPosition])
        }

        itemView.setOnLongClickListener {
            val note = notes[adapterPosition]
            if(selected.containsKey(note)){
                selected[note] = selected[note]!!.not()
            }
            notifyItemChanged(adapterPosition)
            checkSelected()
            true
        }

        return this
    }


    private fun checkSelected(){
        val notify = selected.values.reduce { acc, b ->  acc || b}
        if(noteSelected){
            if(!notify){
                noteSelected = false
                event.onAllNotesUnselected()
            }
        }else{
            if(notify){
                event.onFirstNoteSelected()
                noteSelected = true
            }
        }
    }

    fun unselectAll(){
        selected.keys.forEach { selected[it] = false }
        noteSelected = false
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imgDot = view.item_note_img_dot
        var tvTitle = view.item_note_tv_title
        var tvContent = view.item_note_tv_content
        var tvReminder = view.item_note_tv_reminder
        var selected = view.item_note_select
    }

    interface NoteAdapterEvent{

        fun onNoteClicked(note: Note)

        fun onFirstNoteSelected()

        fun onAllNotesUnselected()

    }

    companion object{
        @Resource
        private val dots = arrayOf(R.drawable.ic_round_1, R.drawable.ic_round_2)
    }
}