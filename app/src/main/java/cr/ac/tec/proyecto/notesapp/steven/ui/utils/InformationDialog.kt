package cr.ac.tec.proyecto.notesapp.steven.ui.utils

import android.app.Activity
import android.app.AlertDialog
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.bumptech.glide.Glide
import cr.ac.tec.proyecto.notesapp.steven.R
import kotlinx.android.synthetic.main.dialog_info.view.*
import kotlinx.android.synthetic.main.dialog_loading.view.*


class InformationDialog(private val activity: Activity, private val title: String, private val event: Event, private var isError: Boolean){

    var dialog: AlertDialog? = null

    fun showDialog() {
        val builder = AlertDialog.Builder(activity, R.style.DialogNoBar)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_info, null)
        view.dialog_info_tv_title.text = title
        view.dialog_info_btn_accept.setOnClickListener { dialog!!.dismiss() }
        if(isError){
            Glide.with(activity).load(R.drawable.ic_error_pink_36dp).into(view.dialog_info_icon)
        }
        dialog = builder
            .setView(view)
            .setCancelable(false)
            .create()
        dialog!!.setOnDismissListener { event.onAccept() }
        val window = dialog?.window!!
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setLayout(view.measuredWidth, WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        dialog?.show()

    }
    fun dismissDialog(){
        dialog?.dismiss()
    }

    interface Event{

        fun onAccept()

    }


}