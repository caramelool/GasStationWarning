package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_station_detail.*
import kotlinx.android.synthetic.main.content_station_detail.*
import org.kodein.di.generic.instance
import caramelo.com.br.gasstationwarning.ui.station.detail.StationDetailAdapterHandler.Description as HandlerDescription

const val EXTRA_STATION = "extra_station"

class StationDetailActivity : BaseActivity(stationDetailModule.init) {

    companion object {
        fun getIntent(
                context: Context,
                station: Station
        ) = Intent(context, StationDetailActivity::class.java).apply {
            putExtra(EXTRA_STATION, station)
        }
    }

    private val viewModel: StationDetailViewModel by kodein.instance()
    private val adapter = StationDetailAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        stationRecyclerView.layoutManager = LinearLayoutManager(this)
        stationRecyclerView.setHasFixedSize(true)
        stationRecyclerView.isNestedScrollingEnabled = false
        stationRecyclerView.adapter = adapter

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
            stationNameTextView.text = name
            stationAddressTextView.text = address
            bindAdapter(this)
        }
    }

    private fun showWarning(show: Boolean) {
        val y = if (show) 0 else -stationWarningTextView.height
        stationWarningTextView.animate()
                .y(y.toFloat())
                .setDuration(100)
                .start()
    }

    private fun bindAdapter(station: Station) {
        val list = mutableListOf<StationDetailAdapterHandler>()
        with(station) {
            if (!phone.isNullOrEmpty()) {
                val handler = HandlerDescription(
                        getString(R.string.phone),
                        phone!!
                )
                list.add(handler)
            }
            if (!description.isNullOrEmpty()) {
                val handler = HandlerDescription(
                        getString(R.string.description),
                        description!!
                )
                list.add(handler)
            }
        }
        adapter.data = list
    }

    private fun showLoading() {
        container.visibility = View.GONE
        loading.visibility = View.VISIBLE
    }
}
