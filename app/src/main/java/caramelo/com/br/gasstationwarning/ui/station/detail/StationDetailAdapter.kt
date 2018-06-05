package caramelo.com.br.gasstationwarning.ui.station.detail

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.BaseViewHolder
import kotlinx.android.synthetic.main.adapter_station_detail_description.view.*

class StationDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<StationDetailAdapterHandler> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            TYPE_DESCRIPTION -> DescriptionHolder(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when(holder) {
            is DescriptionHolder -> holder.bind(item as StationDetailAdapterHandler.Description)
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = data[position].type
}

private const val TYPE_DESCRIPTION = 1

sealed class StationDetailAdapterHandler(val type: Int) {
    data class Description(
            val label: String,
            val text: String
    ) : StationDetailAdapterHandler(TYPE_DESCRIPTION)
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