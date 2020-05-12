package cr.ac.tec.proyecto.notesapp.steven.data.mysql

import android.os.AsyncTask
import cr.ac.tec.proyecto.notesapp.steven.data.base.DataEvent
import cr.ac.tec.proyecto.notesapp.steven.data.base.Response

class Executor<T>(var event: DataEvent<T>): AsyncTask<()-> ExecutorResponse<T>, Void, ExecutorResponse<T>>(){

    override fun doInBackground(vararg params: (() -> ExecutorResponse<T>)?): ExecutorResponse<T> {
        return params[0]!!()
    }

    override fun onPostExecute(result: ExecutorResponse<T>?) {
        if(result!!.success){
            event.onSuccess(Response(result.data!!))
        }else{
            event.onError(result.exception?.message!!)
        }
    }

}