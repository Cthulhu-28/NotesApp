package cr.ac.tec.proyecto.notesapp.steven.ui.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import cr.ac.tec.proyecto.notesapp.steven.R
import kotlinx.android.synthetic.main.dialog_confirmation.view.*


class ConfirmationDialog(private val activity: Activity, private val title: String, private val event: ConfirmationEvent){

    var dialog: AlertDialog? = null

    fun showDialog() {
        val builder = AlertDialog.Builder(activity, R.style.DialogNoBar)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_confirmation, null)
        view.dialog_confirmation_tv_title.text = title
        view.dialog_confirmation_btn_accept.setOnClickListener {
            dialog!!.dismiss()
            event.onAccept()
        }

        view.dialog_confirmation_btn_cancel.setOnClickListener {
            dialog!!.dismiss()
        }
        dialog = builder
            .setView(view)
            .setCancelable(false)
            .create()
        dialog!!.setOnDismissListener { event.onCancel() }
        val window = dialog?.window!!
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setLayout(view.measuredWidth, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        dialog?.show()

    }
    fun dismissDialog(){
        dialog?.dismiss()
    }

    interface ConfirmationEvent{

        fun onAccept()

        fun onCancel()

    }


}