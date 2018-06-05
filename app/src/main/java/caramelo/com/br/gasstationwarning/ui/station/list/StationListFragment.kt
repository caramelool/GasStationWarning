package caramelo.com.br.gasstationwarning.ui.station.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.BaseFragment
import caramelo.com.br.gasstationwarning.ui.station.detail.StationDetailActivity
import kotlinx.android.synthetic.main.fragment_station_list.*
import org.kodein.di.generic.instance

class StationListFragment : BaseFragment(stationListModule.init) {

    companion object {
        fun newInstance() = StationListFragment()
    }

    private val viewModel: StationListViewModel by kodein.instance()

    private val adapter = StationListAdapter { station ->
        context?.let {
            val intent = StationDetailActivity.getIntent(it, station)
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_station_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stationRecyclerView.layoutManager = LinearLayoutManager(context)
        stationRecyclerView.setHasFixedSize(true)
        stationRecyclerView.adapter = adapter

        viewModel.stationLiveData?.observe(this, stationObserver)
    }

    private val stationObserver = Observer<StationListHandle> { handle ->
        when(handle) {
            is StationListHandle.Receiver -> {
                showList()
                adapter.data = handle.list
            }
            is StationListHandle.Empty -> showEmpty()
            is StationListHandle.Loading -> showLoading()
        }
    }

    private fun showList() {
        loading.visibility = View.GONE
        emptyGroup.visibility = View.GONE
        stationRecyclerView.visibility = View.VISIBLE
    }

    private fun showLoading() {
        loading.visibility = View.VISIBLE
        emptyGroup.visibility = View.GONE
        stationRecyclerView.visibility = View.GONE
    }

    private fun showEmpty() {
        loading.visibility = View.GONE
        emptyGroup.visibility = View.VISIBLE
        stationRecyclerView.visibility = View.GONE
    }
}
