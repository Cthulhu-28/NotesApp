package cr.ac.tec.proyecto.notesapp.steven.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

open class Note(var date: Date, var title: String, var content: String) : Parcelable {

    var id = 0
        private set(value){
            field = value
        }

    val isReminder
        get() = content.startsWith("[reminder]")

    val reminderTime
        get() = if(isReminder) content.split("@")[1] else ""

    val reminderContent
        get() = if(isReminder) content.split("@")[2] else ""

    constructor(parcel: Parcel) : this(Date(),"","") {
        readFromParcel(parcel)
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest!!.writeString(title)
        dest.writeString(content)
        dest.writeInt(id)
        dest.writeLong(date.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    private fun readFromParcel(parcel: Parcel){
        title = parcel.readString()!!
        content = parcel.readString()!!
        id = parcel.readInt()
        date = Date(parcel.readLong())
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}