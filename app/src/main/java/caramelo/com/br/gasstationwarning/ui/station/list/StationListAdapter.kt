package caramelo.com.br.gasstationwarning.ui.station.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.BaseViewHolder
import kotlinx.android.synthetic.main.adapter_station.view.*

class StationListAdapter(
        val onStationClicked : (Station) -> Unit
) : RecyclerView.Adapter<StationListAdapter.Holder>() {

    var data = listOf<Station>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val station = data[position]
        holder.bind(station)
    }

    inner class Holder(
            parent: ViewGroup
    ) : BaseViewHolder(parent, R.layout.adapter_station) {

        fun bind(station: Station) {
            with(itemView) {
                stationNameTextView.text = station.name
                stationAddressTextView.text = station.address
                if (station.hasFuel) {
                    warningFuelImage.visibility = View.GONE
                    itemView.alpha = 1f
                } else {
                    warningFuelImage.visibility = View.VISIBLE
                    itemView.alpha = 0.5f
                }
                setOnClickListener {
                    onStationClicked(station)
                }
            }
        }

    }
}