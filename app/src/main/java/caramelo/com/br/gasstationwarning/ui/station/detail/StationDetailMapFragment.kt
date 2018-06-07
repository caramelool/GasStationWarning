package caramelo.com.br.gasstationwarning.ui.station.detail

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class StationDetailMapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = StationDetailMapFragment()
    }

    private var viewModel: StationDetailViewModel? = null
    private var map: GoogleMap? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = (context as? DetailViewModelProvider)?.viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_station_detail_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onDetach() {
        super.onDetach()
        viewModel = null
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.uiSettings?.setAllGesturesEnabled(false)
        viewModel?.detailLiveData?.observe(this, detailObserver)
    }

    private val detailObserver = Observer<StationDetailHandler> {
        map?.clear()
        val handler = it ?: return@Observer
        when(handler) {
            is StationDetailHandler.Detail -> {
                val station = handler.station
                val latlng = LatLng(
                        station.location?.latitude ?: 0.0,
                        station.location?.longitude ?: 0.0)
                viewModel?.detailLiveData?.value
                map?.addMarker(MarkerOptions().position(latlng)
                        .title(station.name)
                        .snippet(station.address)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_gas_station)))
                        ?.showInfoWindow()
                map?.moveCamera(CameraUpdateFactory.newLatLng(latlng))
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 19f))
            }
        }
    }
}
