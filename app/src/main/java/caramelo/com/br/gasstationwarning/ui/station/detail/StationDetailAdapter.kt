package caramelo.com.br.gasstationwarning.ui.station.detail

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.BaseViewHolder
import kotlinx.android.synthetic.main.adapter_station_detail_description.view.*

class StationDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_DESCRIPTION = 1
        const val TYPE_FUEL = 2
    }

    var data: List<StationDetailAdapterHandler> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_DESCRIPTION -> DescriptionHolder(parent)
            TYPE_FUEL -> FuelHolder(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when(holder) {
            is DescriptionHolder -> holder.bind(item as StationDetailAdapterHandler.Description)
            is FuelHolder -> holder.bind(item as StationDetailAdapterHandler.Fuel)
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = data[position].type
}

sealed class StationDetailAdapterHandler(val type: Int) {
    data class Description(
            val label: String,
            val text: String
    ) : StationDetailAdapterHandler(StationDetailAdapter.TYPE_DESCRIPTION)
    data class Fuel(
            val name: String,
            val price: String
    ) : StationDetailAdapterHandler(StationDetailAdapter.TYPE_FUEL)
}

private class DescriptionHolder(
        parent: ViewGroup
) : BaseViewHolder(parent, R.layout.adapter_station_detail_description) {

    fun bind(description: StationDetailAdapterHandler.Description) {
        with(itemView) {
            descriptionLabel.text = description.label
            descriptionText.text = description.text
        }
    }
}

private class FuelHolder(
        parent: ViewGroup
) : BaseViewHolder(parent, R.layout.adapter_station_detail_description) {

    fun bind(fuel: StationDetailAdapterHandler.Fuel) {
        with(itemView) {
            descriptionLabel.text = fuel.name
            descriptionText.text = fuel.price
        }
    }
}