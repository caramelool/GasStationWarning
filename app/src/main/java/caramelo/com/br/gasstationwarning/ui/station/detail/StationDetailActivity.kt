package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.Menu
import android.view.MenuItem
import android.view.View
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.BaseActivity
import caramelo.com.br.gasstationwarning.ui.station.comment.StationCommentFragment
import kotlinx.android.synthetic.main.activity_station_detail.*
import kotlinx.android.synthetic.main.content_station_detail.*
import org.kodein.di.generic.instance
import caramelo.com.br.gasstationwarning.ui.station.detail.StationDetailAdapterHandler.Description as HandlerDescription

const val EXTRA_STATION = "extra_station"

class StationDetailActivity : BaseActivity(stationDetailModule.init), DetailViewModelProvider {

    companion object {
        fun getIntent(
                context: Context,
                station: Station
        ) = Intent(context, StationDetailActivity::class.java).apply {
            putExtra(EXTRA_STATION, station)
        }
    }

    override val viewModel: StationDetailViewModel by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        pager.adapter = Adapter(supportFragmentManager)
        tabs.setupWithViewPager(pager)

        viewModel.detailLiveData?.observe(this, detailObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_station_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_share -> share()
        }
        return super.onOptionsItemSelected(item)
    }

    private val detailObserver = Observer<StationDetailHandler> {
        val handler = it ?: return@Observer
        when(handler) {
            is StationDetailHandler.Detail -> showDetail(handler.station)
            is StationDetailHandler.Loading -> showLoading()
        }
    }

    private fun showDetail(station: Station) {
        container.visibility = View.VISIBLE
        loading.visibility = View.GONE
        with(station) {
            showWarning(!hasFuel)
            toolbarLayout.title = name
        }
    }

    private fun showWarning(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        stationWarningTextView.visibility = visibility
    }

    private fun showLoading() {
        container.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }

    private fun share() {
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name))
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, viewModel.shareBody)
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share)))
    }

    private inner class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> StationDetailInfoFragment.newInstance()
                1 -> StationDetailMapFragment.newInstance()
                else -> StationCommentFragment.newInstance(viewModel.station)
            }
        }

        override fun getCount() = 3

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> getString(R.string.tab_info)
                1 -> "Mapa"
                else -> getString(R.string.tab_comment)
            }
        }
    }
}

interface DetailViewModelProvider {
    val viewModel: StationDetailViewModel
}
