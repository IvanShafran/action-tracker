package me.shafran.actiontracker.ui.view.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import me.shafran.actiontracker.R
import me.shafran.actiontracker.di.Scopes
import me.shafran.actiontracker.navigation.AppNavigator
import me.shafran.actiontracker.navigation.Screens
import me.shafran.actiontracker.ui.view.base.BaseActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.Toothpick

class MainActivity : BaseActivity() {

    private val navigatorHolder by lazy {
        Toothpick
                .openScope(Scopes.ROOT_SCOPE)
                .getInstance(NavigatorHolder::class.java)
    }

    private val router by lazy {
        Toothpick
                .openScope(Scopes.ROOT_SCOPE)
                .getInstance(Router::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            router.navigateTo(Screens.ACTIONS)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(AppNavigator(this, R.id.main_content))
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() = router.exit()
}
