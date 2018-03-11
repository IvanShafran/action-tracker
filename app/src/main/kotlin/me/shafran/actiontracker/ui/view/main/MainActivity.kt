package me.shafran.actiontracker.ui.view.main

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import me.shafran.actiontracker.R
import me.shafran.actiontracker.ui.presentation.main.MainPresenter
import me.shafran.actiontracker.ui.presentation.main.MainView
import me.shafran.actiontracker.ui.view.actions.ActionsFragment
import me.shafran.actiontracker.ui.view.base.BaseActivity

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_content, ActionsFragment())
                .commit()
    }

}
