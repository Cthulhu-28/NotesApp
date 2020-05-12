package cr.ac.tec.proyecto.notesapp.steven.data.mysql

import android.util.Log
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class MySqlConnection(
    val host: String,
    val port: Int = 8080,
    val database: String,
    val user: String,
    val password: String) {

    fun buildConnection(): ExecutorResponse<Connection?>{
        try {
            Class.forName("com.mysql.jdbc.Driver")
            return ExecutorResponse(DriverManager.getConnection("jdbc:mysql://$host:$port/$database", user, password))
        }catch(e: Exception){
            Log.e("NOTE-JDBC-ERR",e.message!!)
            return ExecutorResponse(e)
        }

    }

}