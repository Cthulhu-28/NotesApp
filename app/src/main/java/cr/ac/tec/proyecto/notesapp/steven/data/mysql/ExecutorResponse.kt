package cr.ac.tec.proyecto.notesapp.steven.data.mysql

class ExecutorResponse<T>{

    constructor(exception: Exception){
        this.exception = exception
        success = false
    }

    constructor(data: T){
        this.data = data
        success = true
    }

    var data: T? = null
    var success = true
    var exception: Exception? = null
}