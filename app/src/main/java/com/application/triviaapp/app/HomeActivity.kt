package com.application.triviaapp.app

import android.os.Bundle
import com.application.triviaapp.R
import com.application.triviaapp.base.BaseActivity
import com.application.triviaapp.databinding.ActivityHomeBinding
import com.application.triviaapp.frags.HomeFragment

class HomeActivity : BaseActivity<ActivityHomeBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        displayIt(
            HomeFragment(),
            HomeFragment::class.java.canonicalName,
            true
        )

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun setContainerLayout(): Int {
        return viewDataBinding.frameContainer.id
    }
}