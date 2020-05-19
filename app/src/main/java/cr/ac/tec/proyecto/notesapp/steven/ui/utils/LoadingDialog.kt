package cr.ac.tec.proyecto.notesapp.steven.ui.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import cr.ac.tec.proyecto.notesapp.steven.R
import kotlinx.android.synthetic.main.dialog_loading.view.*

class LoadingDialog(private val activity: Activity, private val text: String){

    var dialog: AlertDialog? = null

    fun showDialog() {
        val builder = AlertDialog.Builder(activity, R.style.DialogNoBar)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_loading, null)
        view.dialog_loading_tv_text.text = text
        dialog = builder
            .setView(view)
            .setCancelable(false)
            .create()
        val window = dialog?.window!!
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setLayout(view.measuredWidth, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        dialog?.show()

    }
    fun dismissDialog(){
        dialog?.dismiss()
    }


}