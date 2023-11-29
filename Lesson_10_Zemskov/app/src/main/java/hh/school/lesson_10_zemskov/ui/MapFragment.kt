package hh.school.lesson_10_zemskov.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import hh.school.lesson_10_zemskov.R
import hh.school.lesson_10_zemskov.databinding.FragmentMapBinding
import hh.school.lesson_10_zemskov.model.Bridge
import hh.school.lesson_10_zemskov.model.Divorce
import hh.school.lesson_10_zemskov.utils.Time
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()
    private lateinit var mapView: MapView
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private val imageProviderCurrentLocation by lazy {
        ImageProvider.fromBitmap(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.baseline_location_on_24,
                activity?.theme
            )?.toBitmap()
        )
    }
    private lateinit var userPlacemark: PlacemarkMapObject
    private lateinit var locationCallback: LocationCallback
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            requireContext()
        )
    }
    private val locationRequest = LocationRequest.Builder(10_000).build()

    @Suppress("UNCHECKED_CAST")
    private val placemarkTapListener = MapObjectTapListener { mapObject, _ ->
        if (standardBottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
            standardBottomSheetBehavior.state =
                BottomSheetBehavior.STATE_EXPANDED
        }
        (mapObject.userData as? Pair<*, *>)?.let { (id, divorces) ->
            viewModel.updateBridgeInfoById(id as Int, divorces as List<Divorce>)
        }
        true
    }
    private val mapInputListener = object : InputListener {
        override fun onMapTap(p0: Map, p1: Point) {
            if (standardBottomSheetBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
                standardBottomSheetBehavior.state =
                    BottomSheetBehavior.STATE_HIDDEN
            }
        }

        override fun onMapLongTap(p0: Map, p1: Point) {}

    }

    @SuppressLint("MissingPermission")
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { granted ->
        val isGranted = granted.values.any()
        if (isGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                userPlacemark = mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                    geometry = Point(location.latitude, location.longitude)
                    setIcon(imageProviderCurrentLocation)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        binding.appBarLayout.setOnApplyWindowInsetsListener { view, windowInsets ->
            val windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets)
            view.updatePadding(
                top = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
            )
            windowInsets
        }
        MapKitFactory.initialize(requireContext())
        mapView = binding.mapView
        standardBottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    userPlacemark.geometry = Point(location.latitude, location.longitude)
                }
            }
        }
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(59.938784, 30.314997),
                12.5f,
                0f,
                0f
            )
        )
        lifecycleScope.launch {
            viewModel.mapUiState.collect { uiState ->
                when (uiState) {
                    is UiState.Success -> onMapUiStateSuccess(uiState)
                    is UiState.Loading -> onLoading()
                    is UiState.Error -> onError(uiState)
                    is UiState.Empty -> onEmpty()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.bridgeUiState.collect { uiState ->
                when (uiState) {
                    is UiState.Success -> onBridgeUiStateSuccess(uiState)
                    is UiState.Loading -> { /* TODO */ }
                    is UiState.Error -> { /* TODO */ }
                    is UiState.Empty -> { /* TODO */ }
                }
            }
        }
        binding.mapView.mapWindow.map.addInputListener(mapInputListener)
        binding.floatingActionButtonToMyLocation.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                mapView.mapWindow.map.move(
                    CameraPosition(
                        Point(location.latitude, location.longitude),
                        12.5f,
                        0f,
                        0f
                    )
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun onMapUiStateSuccess(uiState: UiState.Success<List<Bridge>>) = with(binding.layoutInfo) {
        uiState.data.forEach { bridge ->
            if (bridge.lat != null && bridge.lng != null) {
                val imageResId = getBridgeImageResId(bridge.divorces)
                val imageProvider = ImageProvider.fromBitmap(
                    ResourcesCompat.getDrawable(
                        resources,
                        imageResId,
                        activity?.theme
                    )?.toBitmap()
                )
                mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                    geometry = Point(bridge.lat, bridge.lng)
                    setIcon(imageProvider)
                    userData = bridge.id to bridge.divorces
                    addTapListener(placemarkTapListener)
                }
            }
        }
        textViewInfo.isVisible = false
        buttonRetry.isVisible = false
        circularProgressIndicator.hide()
        root.isVisible = false
    }

    private fun onBridgeUiStateSuccess(uiState: UiState.Success<Bridge>) {
        val imageResId = getBridgeImageResId(uiState.data.divorces)
        binding.imageViewBridgeState.setImageResource(imageResId)
        binding.textViewName.text = uiState.data.name
        binding.textViewDivorces.text = uiState.data.getDivorcesAsString()
    }

    private fun onEmpty() = with(binding.layoutInfo) {
        textViewInfo.text = getString(R.string.text_view_info_empty_list_bridges)
        buttonRetry.apply {
            text = getString(R.string.button_retry_text_search)
            setOnClickListener {
                viewModel.updateMapUiState()
            }
        }
        root.isVisible = true
        textViewInfo.isVisible = true
        buttonRetry.isVisible = true
        circularProgressIndicator.hide()
    }

    private fun onError(uiState: UiState.Error) = with(binding.layoutInfo) {
        textViewInfo.text = uiState.error.message
        buttonRetry.apply {
            text = getString(R.string.button_retry_text)
            setOnClickListener {
                viewModel.updateMapUiState()
            }
        }
        root.isVisible = true
        textViewInfo.isVisible = true
        buttonRetry.isVisible = true
        circularProgressIndicator.hide()
    }

    private fun onLoading() = with(binding.layoutInfo) {
        root.isVisible = true
        circularProgressIndicator.show()
        textViewInfo.isVisible = false
        buttonRetry.isVisible = false
    }

    private fun getBridgeImageResId(divorces: List<Divorce>): Int {
        val calendar = Calendar.getInstance()
        val currentTime = Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
        val divorcesAsTime = divorces.map {
            try {
                Time.parse(it.start)..Time.parse(it.end)
            } catch (e: Exception) {
                null
            }
        }
        val imageResId = if (divorcesAsTime.any { it != null && currentTime in it }) {
            R.drawable.ic_brige_late
        } else if (divorcesAsTime.any { it != null && currentTime.until(it.start) <= Time(1, 0) }) {
            R.drawable.ic_brige_soon
        } else {
            R.drawable.ic_brige_normal
        }
        return imageResId
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
