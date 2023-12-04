package hh.school.lesson_10_zemskov.ui

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.ScreenRect
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import dagger.hilt.android.AndroidEntryPoint
import hh.school.lesson_10_zemskov.R
import hh.school.lesson_10_zemskov.databinding.FragmentMapBinding
import hh.school.lesson_10_zemskov.model.Bridge
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var mapView: MapView
    private lateinit var locationCallback: LocationCallback
    private lateinit var clusterizedCollection: ClusterizedPlacemarkCollection

    @SuppressLint("MissingPermission")
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { granted ->
        val isGranted = granted.values.any()
        if (isGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                userPlacemark.geometry = Point(location.latitude, location.longitude)
            }
        }
    }
    private val userPlacemark by lazy {
        val imageProvider = ImageProvider.fromBitmap(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.baseline_location_on_24,
                activity?.theme
            )?.toBitmap()
        )
        mapView.mapWindow.map.mapObjects.addPlacemark()
            .apply { setIcon(imageProvider) }
    }
    private val bridgesPlacemarks = mutableListOf<PlacemarkMapObject>()
    private val fusedLocationClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            requireContext()
        )
    }
    private val locationRequest = LocationRequest.Builder(10_000).build()
    private val placemarkTapListener = MapObjectTapListener { mapObject, _ ->
        (mapObject.userData as? Bridge)?.let { bridge ->
            binding.imageViewBridgeState.setImageResource(bridge.state.imageResId)
            binding.textViewName.text = bridge.name
            binding.textViewDivorces.text = bridge.getDivorcesAsString()
        }
        if (standardBottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
            standardBottomSheetBehavior.state =
                BottomSheetBehavior.STATE_EXPANDED
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
    private val clusterTapListener = ClusterTapListener { selectedCluster ->
        val listPoints = selectedCluster.placemarks
        val topPadding = binding.appBarLayout.measuredHeight +
                resources.getDimension(R.dimen.screen_padding)
        val leftPadding = resources.getDimension(R.dimen.screen_padding)
        val rightPadding = binding.floatingActionButtonToMyLocation.measuredWidth
        val bottomPadding = binding.standardBottomSheet.measuredHeight +
                binding.floatingActionButtonToMyLocation.measuredHeight
        val cameraPosition = mapView.mapWindow.map.cameraPosition(
            Geometry.fromBoundingBox(
                BoundingBox(
                    Point(
                        listPoints.minOf { it.geometry.latitude },
                        listPoints.minOf { it.geometry.longitude }
                    ),
                    Point(
                        listPoints.maxOf { it.geometry.latitude },
                        listPoints.maxOf { it.geometry.longitude }
                    )
                )
            ),
            ScreenRect(
                ScreenPoint(leftPadding, topPadding),
                ScreenPoint(
                    mapView.mapWindow.width().toFloat() - rightPadding,
                    mapView.mapWindow.height().toFloat() - bottomPadding
                )
            )
        )
        mapView.mapWindow.map.move(cameraPosition)
        true
    }
    private val clusterListener = ClusterListener { cluster ->
        cluster.addClusterTapListener(clusterTapListener)
        cluster.appearance.setView(
            ViewProvider(
                BridgesClusterView(requireContext()).apply {
                    setData(cluster.placemarks.mapNotNull { (it.userData as? Bridge)?.state })
                }
            )
        )
    }
    private val timeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            bridgesPlacemarks.groupBy { (it.userData as? Bridge)?.state }
                .forEach { (bridgeState, placemark) ->
                    bridgeState?.let {
                        val imageProvider = ImageProvider.fromBitmap(
                            ResourcesCompat.getDrawable(
                                resources,
                                bridgeState.imageResId,
                                requireContext().theme
                            )?.toBitmap()
                        )
                        placemark.forEach { it.setIcon(imageProvider) }
                    }
                }
        }

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
                    is UiState.Success -> onUiStateSuccess(uiState.data)
                    is UiState.Loading -> onUiStateLoading()
                    is UiState.Error -> onUiStateError(uiState.error)
                    is UiState.Empty -> onUiStateEmpty()
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
        requireContext().registerReceiver(timeReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
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

    override fun onDestroyView() {
        clusterizedCollection.clear()
        bridgesPlacemarks.clear()
        requireContext().unregisterReceiver(timeReceiver)
        _binding = null
        super.onDestroyView()
    }

    private fun onUiStateSuccess(bridges: List<Bridge>) =
        with(binding.layoutInfo) {
            clusterizedCollection =
                mapView.mapWindow.map.mapObjects.addClusterizedPlacemarkCollection(clusterListener)
            bridges.groupBy { it.state }.forEach { (bridgeState, listBridges) ->
                val placemarkSize = resources.getDimensionPixelSize(R.dimen.placemark_size)
                val imageProvider = ImageProvider.fromBitmap(
                    ResourcesCompat.getDrawable(
                        resources,
                        bridgeState.imageResId,
                        activity?.theme
                    )?.toBitmap(placemarkSize, placemarkSize)
                )
                listBridges.forEach { bridge ->
                    clusterizedCollection.addPlacemark().apply {
                        if (bridge.lat != null && bridge.lng != null) {
                            geometry = Point(bridge.lat, bridge.lng)
                            setIcon(imageProvider)
                            userData = bridge
                            addTapListener(placemarkTapListener)
                        }
                    }
                }
            }
            clusterizedCollection.clusterPlacemarks(40.0, 15)
            textViewInfo.isVisible = false
            buttonRetry.isVisible = false
            circularProgressIndicator.hide()
            root.isVisible = false
        }

    private fun onUiStateEmpty() = with(binding.layoutInfo) {
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

    private fun onUiStateError(error: Throwable) = with(binding.layoutInfo) {
        textViewInfo.text = error.message
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

    private fun onUiStateLoading() = with(binding.layoutInfo) {
        root.isVisible = true
        circularProgressIndicator.show()
        textViewInfo.isVisible = false
        buttonRetry.isVisible = false
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

    companion object {
        @JvmStatic
        fun newInstance() = MapFragment()
    }
}
