package caramelo.com.br.gasstationwarning.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.about.AboutFragment
import caramelo.com.br.gasstationwarning.ui.station.list.StationListFragment
import kotlinx.android.synthetic.main.activity_home.*

private const val EXTRA_SELECTED_ITEM_ID = "EXTRA_SELECTED_ITEM_ID"

class HomeActivity : AppCompatActivity() {

    companion object {
        fun getIntent(
                context: Context
        )  = Intent(context, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(StationListFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                loadFragment(AboutFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        navigation.selectedItemId = savedInstanceState?.getInt(EXTRA_SELECTED_ITEM_ID, R.id.navigation_home)
                ?: R.id.navigation_home

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(EXTRA_SELECTED_ITEM_ID, navigation.selectedItemId)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
    }
}
