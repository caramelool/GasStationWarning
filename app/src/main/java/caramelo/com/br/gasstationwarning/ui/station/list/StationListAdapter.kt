package caramelo.com.br.gasstationwarning.ui.station.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Station
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
    ) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.adapter_station, parent, false)) {

        fun bind(station: Station) {
            with(itemView) {

                stationNameTextView.text = station.name
                stationAddressTextView.text = station.address

                setOnClickListener {
                    onStationClicked(station)
                }
            }
        }

    }
}