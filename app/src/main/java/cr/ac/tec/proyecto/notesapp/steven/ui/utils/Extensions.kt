package cr.ac.tec.proyecto.notesapp.steven.ui.utils

import android.os.Build
import android.widget.TimePicker

fun TimePicker.setTime(hour: Int, minute: Int){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        this.hour = hour
        this.minute = minute
    }else{
        this.currentHour = hour
        this.currentMinute = minute
    }
}

fun TimePicker.selectHour(): Int{
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) hour else currentHour
}

fun TimePicker.selectMinute(): Int{
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) minute else currentMinute
}