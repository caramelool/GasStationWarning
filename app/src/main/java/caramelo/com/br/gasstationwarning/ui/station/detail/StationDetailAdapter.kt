package caramelo.com.br.gasstationwarning.ui.station.detail

import android.support.v7.widget.RecyclerView
import android.text.util.Linkify
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.ui.BaseViewHolder
import kotlinx.android.synthetic.main.adapter_station_detail_description.view.*
import kotlinx.android.synthetic.main.adapter_station_detail_rating.view.*

class StationDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_DESCRIPTION = 1
        const val TYPE_FUEL = 2
        const val TYPE_PHONE = 3
        const val TYPE_LINK = 4
        const val TYPE_RATING = 5
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
            TYPE_PHONE -> PhoneHolder(parent)
            TYPE_LINK -> LinkHolder(parent)
            TYPE_RATING -> RatingHolder(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        when(holder) {
            is DescriptionHolder -> holder.bind(item as StationDetailAdapterHandler.Description)
            is FuelHolder -> holder.bind(item as StationDetailAdapterHandler.Fuel)
            is PhoneHolder -> holder.bind(item as StationDetailAdapterHandler.Phone)
            is LinkHolder -> holder.bind(item as StationDetailAdapterHandler.Link)
            is RatingHolder -> holder.bind(item as StationDetailAdapterHandler.Rating)
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
    data class Phone(
            val label: String,
            val phone: String
    ) : StationDetailAdapterHandler(StationDetailAdapter.TYPE_PHONE)
    data class Link(
            val label: String,
            val link: String
    ) : StationDetailAdapterHandler(StationDetailAdapter.TYPE_LINK)
    data class Rating(
            val value: Float
    ) : StationDetailAdapterHandler(StationDetailAdapter.TYPE_RATING)
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

private class PhoneHolder(
        parent: ViewGroup
) : BaseViewHolder(parent, R.layout.adapter_station_detail_description) {

    fun bind(description: StationDetailAdapterHandler.Phone) {
        with(itemView) {
            descriptionText.autoLinkMask = Linkify.PHONE_NUMBERS
            descriptionLabel.text = description.label
            descriptionText.text = description.phone
        }
    }
}

private class LinkHolder(
        parent: ViewGroup
) : BaseViewHolder(parent, R.layout.adapter_station_detail_description) {

    fun bind(description: StationDetailAdapterHandler.Link) {
        with(itemView) {
            descriptionText.autoLinkMask = Linkify.WEB_URLS
            descriptionLabel.text = description.label
            descriptionText.text = description.link
        }
    }
}

private class RatingHolder(
        parent: ViewGroup
) : BaseViewHolder(parent, R.layout.adapter_station_detail_rating) {

    fun bind(rating: StationDetailAdapterHandler.Rating) {
        with(itemView) {
            ratingBar.rating = rating.value
            ratingText.text = rating.value.toString()
        }
    }
}