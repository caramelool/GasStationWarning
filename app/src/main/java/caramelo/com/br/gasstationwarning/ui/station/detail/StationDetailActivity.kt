package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
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
            showWarning(!hasFull)
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

    private inner class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> StationDetailInfoFragment()
                else -> StationCommentFragment.newInstance(viewModel.station)
            }
        }

        override fun getCount() = 2

        override fun getPageTitle(position: Int): CharSequence? {
            return when(position) {
                0 -> getString(R.string.tab_info)
                else -> getString(R.string.tab_comment)
            }
        }
    }
}

interface DetailViewModelProvider {
    val viewModel: StationDetailViewModel
}
