package cr.ac.tec.proyecto.notesapp.steven.data.mysql


import android.util.Log
import cr.ac.tec.proyecto.notesapp.steven.data.base.DataEvent
import cr.ac.tec.proyecto.notesapp.steven.data.base.DataManager
import cr.ac.tec.proyecto.notesapp.steven.data.base.Response
import cr.ac.tec.proyecto.notesapp.steven.data.extensions.closeSafely
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Exception

@Singleton
class MySqlDataManager @Inject constructor() : DataManager {

    private var connection = MySqlConnection(
        "us-cdbr-east-06.cleardb.net",
        3306,
        "heroku_cad5d849da1e2ca",
        "b4e1b8c20cd5e4",
        "27fa3cfa"
    )

    override fun listNotes(event: DataEvent<List<Note>>) {
        val function = {
            var con: Connection? = null
            val notes = mutableListOf<Note>()
            var exp: Exception? = null
            var pst: PreparedStatement? = null
            var rs: ResultSet? = null
            try{
                con = connection.buildConnection()
                pst = con.prepareStatement("select * from note order by date_time desc")
                rs = pst.executeQuery()
                while(rs.next()){
                    notes.add(
                        Note(
                        id = rs.getInt(NoteFields.ID.value),
                        date = Date(rs.getTimestamp(NoteFields.DATE.value).time),
                        title = rs.getString(NoteFields.TITLE.value),
                        content = rs.getString(NoteFields.CONTENT.value)
                    ))
                }

            }catch (e: Exception){
                Log.e("ERR-LIST-NOTES",e.message!!)
                exp = e

            }finally {
                rs.closeSafely()
                pst.closeSafely()
                con.closeSafely()
            }
            if(exp!=null) ExecutorResponse(exp) else ExecutorResponse(notes.toList())
        }
        Executor<List<Note>>(
            DataEvent(
                onSuccess = {
                    event.onSuccess(Response(it.data))
                },
                onError = {
                    event.onError("Couldn't load the notes")
                }
            )
        ).execute(function)
    }

    override fun getNote(id: Int, event: DataEvent<Note>) {

    }

    override fun addNote(note: Note, event: DataEvent<Note>) {
        val function = {
            var con: Connection? = null
            var exp: Exception? = null
            var pst: PreparedStatement? = null
            var pstGetId: PreparedStatement? = null
            var rs: ResultSet? = null
            try{
                con = connection.buildConnection()
                pst = con.prepareStatement("insert into note (${NoteFields.DATE.value}, ${NoteFields.TITLE.value}, ${NoteFields.CONTENT.value}) values(current_timestamp, ?, ?)")
                pst.setString(1, note.title)
                pst.setString(2, note.content)
                val result = pst.executeUpdate()

                pstGetId = con.prepareStatement("select last_insert_id() as id")
                rs = pstGetId.executeQuery()

                if(rs.next()){
                    note.id = rs.getInt(NoteFields.ID.value)
                }else{
                    exp = Exception("No id was returned")
                    Log.e("ERR-NO-ID-NOTES",exp.message!!)
                }

            }catch(e: Exception){
                Log.e("ERR-NO-INSERT-NOTES",e.message!!)
                exp = e
            }finally {
                rs.closeSafely()
                pstGetId.closeSafely()
                pst.closeSafely()
                con.closeSafely()
            }
            if(exp!=null) ExecutorResponse(exp) else ExecutorResponse(note)
        }
        Executor<Note>(
            DataEvent(
                onSuccess = {
                    event.onSuccess(Response(it.data))
                },
                onError = {
                    event.onError("The note couldn't be saved")
                }
            )
        ).execute(function)

    }

    override fun editNote(note: Note, event: DataEvent<Note>) {
        val function = {
            var con: Connection? = null
            var exp: Exception? = null
            var pst: PreparedStatement? = null
            try{
                con = connection.buildConnection()
                pst = con.prepareStatement("update note set ${NoteFields.DATE.value}=current_timestamp, ${NoteFields.TITLE.value}=?, ${NoteFields.CONTENT.value}=? where ${NoteFields.ID.value}=?")
                pst.setString(1, note.title)
                pst.setString(2, note.content)
                pst.setInt(3, note.id)
                val result = pst.executeUpdate()
            }catch(e: Exception){
                Log.e("ERR-NO-UPDATE-NOTES",e.message!!)
                exp = e
            }finally {
                pst.closeSafely()
                con.closeSafely()
            }
            if(exp!=null) ExecutorResponse(exp) else ExecutorResponse(note)
        }
        Executor<Note>(
            DataEvent(
                onSuccess = {
                    event.onSuccess(Response(it.data))
                },
                onError = {
                    event.onError("The note couldn't be updated")
                }
            )
        ).execute(function)
    }

    override fun deleteNote(note: Note, event: DataEvent<Note>) {
        val function = {
            var con: Connection? = null
            var exp: Exception? = null
            var pst: PreparedStatement? = null
            try{
                con = connection.buildConnection()
                pst = con.prepareStatement("delete from note where ${NoteFields.ID.value}=?")
                pst.setInt(1, note.id)
                val result = pst.executeUpdate()
            }catch(e: Exception){
                Log.e("ERR-NO-DELETE-NOTES",e.message!!)
                exp = e
            }finally {
                pst.closeSafely()
                con.closeSafely()
            }
            if(exp!=null) ExecutorResponse(exp) else ExecutorResponse(note)
        }
        Executor<Note>(
            DataEvent(
                onSuccess = {
                    event.onSuccess(Response(it.data))
                },
                onError = {
                    event.onError("The note couldn't be deleted")
                }
            )
        ).execute(function)
    }

    override fun deleteNotes(notes: List<Note>, event: DataEvent<List<Note>>) {
        val function = {
            var con: Connection? = null
            var exp: Exception? = null
            var pst: PreparedStatement? = null
            try{
                con = connection.buildConnection()
                val whereClause = notes.joinToString(separator = " or ", transform = {"${NoteFields.ID.value}=${it.id}"}, prefix = "(", postfix = ")")
                pst = con.prepareStatement("delete from note where $whereClause")
                val result = pst.executeUpdate()
            }catch(e: Exception){
                Log.e("ERR-NO-DELETES-NOTES",e.message!!)
                exp = e
            }finally {
                pst.closeSafely()
                con.closeSafely()
            }
            if(exp!=null) ExecutorResponse(exp) else ExecutorResponse(notes)
        }
        Executor<List<Note>>(
            DataEvent(
                onSuccess = {
                    event.onSuccess(Response(it.data))
                },
                onError = {
                    event.onError("The notes couldn't be deleted")
                }
            )
        ).execute(function)
    }

    enum class NoteFields(val value: String){
        ID("id"),
        DATE("date_time"),
        CONTENT("content"),
        TITLE("title")
    }
}