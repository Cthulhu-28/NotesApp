package cr.ac.tec.proyecto.notesapp.steven.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.atomic.AtomicLong

open class BaseActivity: AppCompatActivity(){
    private var mActivityId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityId = if (savedInstanceState != null)savedInstanceState.getLong(KEY_ACTIVITY_ID) else NEXT_ID.getAndDecrement()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, mActivityId)
    }

    companion object{
        @JvmStatic
        private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        @JvmStatic
        private val NEXT_ID = AtomicLong(0)
    }
}