package com.application.triviaapp.frags

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.application.triviaapp.R
import com.application.triviaapp.app.TriviaBaseFragment
import com.application.triviaapp.database.GameDao
import com.application.triviaapp.database.TriviaGame
import com.application.triviaapp.databinding.FragHomeLayoutBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


//by : Deepak Kumar
//at : Netset Software
//in : Kotlin

class HomeFragment : TriviaBaseFragment<FragHomeLayoutBinding>() {

    private var gameDao: GameDao? = null
    private var bestCricketer : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameDao = database.gameDao()

        viewDataBinding.nextBtn.setOnClickListener {

            when (viewDataBinding.signUpViewFlipper.displayedChild) {

                0 -> {

                    if (validateEditText(viewDataBinding.stepOne.nameET)) {

                        viewDataBinding.historyClick.visibility = View.GONE
                        viewDataBinding.signUpViewFlipper.showNext()

                    }
                }

                1 -> {



                    val radioButtonID = viewDataBinding.questOne.radioGroup.checkedRadioButtonId

                    val radioButton = viewDataBinding.questOne.radioGroup.findViewById<RadioButton>(radioButtonID)

                    if (radioButton != null){

                        bestCricketer = radioButton.text as String

                        Log.e("Select", bestCricketer)

                        viewDataBinding.signUpViewFlipper.showNext()

                    }else{
                        showToast("Please select any one option.")
                    }


                }

                2 -> {

                    if (viewDataBinding.questTwo.cbOne.isChecked || viewDataBinding.questTwo.cbTwo.isChecked
                        || viewDataBinding.questTwo.cbThree.isChecked || viewDataBinding.questTwo.cbFour.isChecked){


                            val flagColor = ArrayList<String>()

                        if (viewDataBinding.questTwo.cbOne.isChecked)
                            flagColor.add(viewDataBinding.questTwo.cbOne.text.toString())

                        if (viewDataBinding.questTwo.cbTwo.isChecked)
                            flagColor.add(viewDataBinding.questTwo.cbTwo.text.toString())

                        if (viewDataBinding.questTwo.cbThree.isChecked)
                            flagColor.add(viewDataBinding.questTwo.cbThree.text.toString())

                        if (viewDataBinding.questTwo.cbFour.isChecked)
                            flagColor.add(viewDataBinding.questTwo.cbFour.text.toString())


                        val game  = TriviaGame()
                        game.playedAt = System.currentTimeMillis()
                        game.playedName = viewDataBinding.stepOne.nameET.text.toString()
                        game.answerOne = bestCricketer
                        game.answerTwo = flagColor.joinToString { it }

                        Observable.fromCallable {

                            gameDao!!.insertCard(game)

                        }.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()


                        val bundle = Bundle()
                        bundle.putString("NAME", viewDataBinding.stepOne.nameET.text.toString())
                        bundle.putString("BEST_CRICKETER", bestCricketer)
                        bundle.putString("FLAG_COLOR", flagColor.joinToString { it })
                        displayIt(setArguments(
                            SummaryFragment(),bundle),
                            SummaryFragment::class.java.canonicalName,
                            true
                        )

                    }else{
                        showToast("Please select atleast one option.")
                    }


                }

            }
        }


        viewDataBinding.historyClick.setOnClickListener {

            displayIt(
                HistoryFragment(),
                HistoryFragment::class.java.canonicalName,
                true
            )
        }

    }

    override fun getCurrentFragment(): Fragment {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_home_layout
    }

}

