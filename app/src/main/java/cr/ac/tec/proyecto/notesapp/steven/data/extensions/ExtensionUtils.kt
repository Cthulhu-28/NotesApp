package cr.ac.tec.proyecto.notesapp.steven.data.extensions

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

fun <T: Connection?>T?.closeSafely(){
    if(this != null){
        try{
            this.close()
        }catch (e: SQLException){ /* ignored */}
    }
}

fun <T: PreparedStatement?>T?.closeSafely(){
    if(this != null){
        try{
            this.close()
        }catch (e: SQLException){ /* ignored */}
    }
}

fun <T: ResultSet?>T?.closeSafely(){
    if(this != null){
        try{
            this.close()
        }catch (e: SQLException){ /* ignored */}
    }
}