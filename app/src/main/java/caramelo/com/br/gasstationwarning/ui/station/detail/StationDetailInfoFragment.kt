package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_station_detail_info.*

class StationDetailInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() = StationDetailInfoFragment()
    }

    private var viewModel: StationDetailViewModel? = null
    private val adapter = StationDetailAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = (context as? DetailViewModelProvider)?.viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_station_detail_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = GridLayoutManager(context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapter.getItemViewType(position)) {
                        StationDetailAdapter.TYPE_FUEL -> 1
                        else -> 2
                    }
                }
            }

        }
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        viewModel?.detailLiveData?.observe(this, detailObserver)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel = null
    }

    private val detailObserver = Observer<StationDetailHandler> {
        val handler = it ?: return@Observer
        when(handler) {
            is StationDetailHandler.Detail -> bindAdapter(handler.station)
        }
    }

    private fun bindAdapter(station: Station) {
        val list = mutableListOf<StationDetailAdapterHandler>()
        with(station) {
            val rating = StationDetailAdapterHandler.Rating(rating)
            list.add(rating)
            if (!name.isNullOrEmpty()) {
                val handler = StationDetailAdapterHandler.Description(
                        getString(R.string.name),
                        name!!
                )
                list.add(handler)
            }
            fuels?.forEach {
                val handler = StationDetailAdapterHandler.Fuel(
                        it.name,
                        it.price
                )
                list.add(handler)
            }
            if (!address.isNullOrEmpty()) {
                val handler = StationDetailAdapterHandler.Description(
                        getString(R.string.address),
                        address!!
                )
                list.add(handler)
            }
            if (!phone.isNullOrEmpty()) {
                val handler = StationDetailAdapterHandler.Phone(
                        getString(R.string.phone),
                        phone!!
                )
                list.add(handler)
            }
            if (!description.isNullOrEmpty()) {
                val handler = StationDetailAdapterHandler.Description(
                        getString(R.string.description),
                        description!!
                )
                list.add(handler)
            }
            if (!link.isNullOrEmpty()) {
                val handler = StationDetailAdapterHandler.Link(
                        getString(R.string.link),
                        link!!
                )
                list.add(handler)
            }
        }
        adapter.data = list
    }

}
