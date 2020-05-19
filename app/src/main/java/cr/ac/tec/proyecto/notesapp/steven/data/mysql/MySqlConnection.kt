package cr.ac.tec.proyecto.notesapp.steven.data.mysql

import android.util.Log
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class MySqlConnection(
    val host: String,
    val port: Int = 8080,
    val database: String,
    val user: String,
    val password: String) {

    fun buildConnectionAsExecutor(): ExecutorResponse<Connection?>{
        return try {
            Class.forName("com.mysql.jdbc.Driver")
            ExecutorResponse(DriverManager.getConnection("jdbc:mysql://$host:$port/$database", user, password))
        }catch(e: Exception){
            Log.e("NOTE-JDBC-ERR",e.message!!)
            ExecutorResponse(e)
        }

    }

    @Throws(SQLException::class)
    fun buildConnection(): Connection{
        return try {
            Class.forName("com.mysql.jdbc.Driver")
            DriverManager.getConnection("jdbc:mysql://$host:$port/$database", user, password)
        }catch(e: SQLException){
            Log.e("NOTE-JDBC-ERR",e.message!!)
            throw e
        }
    }

}