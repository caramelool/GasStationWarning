package caramelo.com.br.gasstationwarning.ui.station.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_station_detail.*
import org.kodein.di.generic.instance

const val EXTRA_STATION = "extra_station"

class StationDetailActivity : BaseActivity(stationDetailModule) {

    companion object {
        fun getIntent(
                context: Context,
                station: Station
        ) = Intent(context, StationDetailActivity::class.java).apply {
            putExtra(EXTRA_STATION, station)
        }
    }

    private val viewModel: StationDetailViewModel by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
