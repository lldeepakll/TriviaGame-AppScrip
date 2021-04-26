package com.application.triviaapp.frags

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.triviaapp.R
import com.application.triviaapp.app.TriviaBaseFragment
import com.application.triviaapp.base.adapter.RecyclerViewGenricAdapter
import com.application.triviaapp.database.GameDao
import com.application.triviaapp.database.TriviaGame
import com.application.triviaapp.databinding.FragHistoryLayoutBinding
import com.application.triviaapp.databinding.ItemHistoryLayoutBinding
import com.application.triviaapp.view.MyDividerItemDecoration
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//by : Deepak Kumar
//at : Netset Software
//in : Kotlin

class HistoryFragment : TriviaBaseFragment<FragHistoryLayoutBinding>() {

    private var gameDao: GameDao? = null

    private var mAdapter: RecyclerViewGenricAdapter<TriviaGame, ItemHistoryLayoutBinding>? = null
    private var mList : ArrayList<TriviaGame> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameDao = database.gameDao()


        Observable.fromCallable {

            val dbGames = gameDao!!.getCards()
            Log.e("Games",dbGames.size.toString())

            mList.addAll(dbGames)


        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        mAdapter =
            RecyclerViewGenricAdapter(mList, R.layout.item_history_layout) {
                    binder, model, position, itemView ->


                binder.gameTV.text = "GAME ${position + 1} : ${getDateFromMillies(model.playedAt!!)}"
                binder.nameTV.text = model.playedName
                binder.answerTV.text = model.answerOne
                binder.answer2TV.text = model.answerTwo


            }

        val mLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        viewDataBinding.rvHistory.addItemDecoration(
            MyDividerItemDecoration(
                getContainerActivity(),
                LinearLayoutManager.VERTICAL,
                16
            )
        )
        viewDataBinding.rvHistory.layoutManager = mLayoutManager
        viewDataBinding.rvHistory.adapter = mAdapter

        Handler(Looper.getMainLooper()).postDelayed({

            mAdapter!!.notifyDataSetChanged()


        }, 1000)

    }

    override fun getCurrentFragment(): Fragment {
        return this
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_history_layout
    }

}

