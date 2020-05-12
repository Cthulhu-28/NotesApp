package cr.ac.tec.proyecto.notesapp.steven.data.mysql


import cr.ac.tec.proyecto.notesapp.steven.data.base.DataEvent
import cr.ac.tec.proyecto.notesapp.steven.data.base.DataManager
import cr.ac.tec.proyecto.notesapp.steven.data.base.Response
import cr.ac.tec.proyecto.notesapp.steven.data.model.Note
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySqlDataManager @Inject constructor() : DataManager {

    private var connection = MySqlConnection(
        "us-cdbr-east-06.cleardb.net",
        3306,
        "heroku_cad5d849da1e2ca",
        "b4e1b8c20cd5e4",
        "27fa3cfa"
    )

    /*private fun enableConnection(event: DataEvent<Connection>){
        if(connection != null){
            event.onSuccess(Response(connection!!))
        }else {
            connection = Connection("us-cdbr-east-06.cleardb.net", "b4e1b8c20cd5e4", "27fa3cfa", 38066, "heroku_cad5d849da1e2ca", object : IConnectionInterface {
                override fun actionCompleted() {
                    event.onSuccess(Response(connection!!))
                }

                override fun handleMySQLConnException(ex: MySQLConnException?) {
                    event.onError(ex?.message!!)
                }

                override fun handleException(ex: Exception?) {
                    event.onError(ex?.message!!)
                }

                override fun handleIOException(ex: IOException?) {
                    event.onError(ex?.message!!)
                }

                override fun handleMySQLException(ex: MySQLException?) {
                    event.onError(ex?.message!!)
                }

                override fun handleInvalidSQLPacketException(ex: InvalidSQLPacketException?) {
                    event.onError(ex?.message!!)
                }
            })
        }
    }*/

    override fun listNotes(event: DataEvent<List<Note>>) {
        val function = {
            connection.buildConnection()
        }
        Executor<java.sql.Connection?>(
            DataEvent(
                onSuccess = {
                    event.onSuccess(Response(listOf()))
                },
                onError = {
                    event.onError("")
                }
            )
        ).execute(function)
    }

    override fun getNode(id: Int, event: DataEvent<Note>) {

    }

    override fun addNote(note: Note, event: DataEvent<Note>) {

    }

    override fun editNote(note: Note, event: DataEvent<Note>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteNote(note: Note, event: DataEvent<Note>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteNotes(notes: List<Note>, event: DataEvent<List<Note>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}