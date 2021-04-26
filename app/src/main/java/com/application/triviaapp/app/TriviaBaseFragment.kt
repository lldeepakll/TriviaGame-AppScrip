package com.application.triviaapp.app

import android.content.Context
import android.widget.EditText
import androidx.databinding.ViewDataBinding
import com.application.triviaapp.base.BaseFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// by :- Deepak Kumar
// at :- Netset Software
// in :- kotlin

abstract class TriviaBaseFragment<T : ViewDataBinding?> : BaseFragment<T>(), FragmentView {

    private lateinit var mContainerActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContainerActivity = context as HomeActivity
    }

    fun getContainerActivity(): HomeActivity {
        return mContainerActivity
    }

    /* Validate EditText that checks empty. */
    fun validateEditText(et: EditText): Boolean {

        return if (et.text.toString() == "") {
            et.requestFocus()
            et.error = "This field can't be empty"
            false
        } else
            true
    }

    @Throws(ParseException::class)
    fun getDateFromMillies(timeInMilis: Long): String {
        val format = SimpleDateFormat("dd MMMM, yyyy hh:ss aa", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(timeInMilis)
        return format.format(calendar.getTime())
    }

}
