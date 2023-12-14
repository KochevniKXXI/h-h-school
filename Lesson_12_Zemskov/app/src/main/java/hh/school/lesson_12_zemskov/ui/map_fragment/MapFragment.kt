package hh.school.lesson_12_zemskov.ui.map_fragment

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
import hh.school.lesson_12_zemskov.R
import hh.school.lesson_12_zemskov.databinding.FragmentMapBinding
import hh.school.lesson_12_zemskov.ui.BaseFragment
import hh.school.lesson_12_zemskov.ui.UiState
import hh.school.lesson_12_zemskov.ui.model.Bridge
import hh.school.lesson_12_zemskov.ui.views.BridgeShortInfoView
import hh.school.lesson_12_zemskov.ui.views.BridgesClusterView

class MapFragment : BaseFragment(R.layout.fragment_map) {

    private val binding by viewBinding(FragmentMapBinding::bind)
    private val viewModel: MapViewModel by daggerViewModels()
    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<BridgeShortInfoView>
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
                R.drawable.ic_location_on,
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
            binding.standardBottomSheet.bridge = bridge
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInsets()
        initUi()
        viewModel.uiState.observe(viewLifecycleOwner, ::updateUiState)
    }

    /**
     * Инициализирует и настраивает пользовательский интерфейс [MapFragment].
     */
    @SuppressLint("MissingPermission")
    private fun initUi() {
        MapKitFactory.initialize(requireContext())
        mapView = binding.mapView
        standardBottomSheetBehavior = BottomSheetBehavior.from(binding.standardBottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemList -> {
                    val action = MapFragmentDirections.actionMapFragmentToListBridgesFragment()
                    findNavController().navigate(action)
                }
            }
            true
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
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
        binding.mapView.mapWindow.map.addInputListener(mapInputListener)
        clusterizedCollection =
            mapView.mapWindow.map.mapObjects.addClusterizedPlacemarkCollection(
                clusterListener
            )
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

    /**
     * Обрабатывает входящие [WindowInsetsCompat] для [MapFragment].
     */
    private fun initInsets() {
        binding.appBarLayout.setOnApplyWindowInsetsListener { view, windowInsets ->
            val windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(windowInsets)
            view.updatePadding(
                top = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
            )
            windowInsets
        }
    }

    /**
     * Обрабатывет полученное [UiState].
     */
    private fun updateUiState(uiState: UiState<List<Bridge>>) {
        when (uiState) {
            is UiState.Success -> {
                uiState.data.groupBy { it.state }.forEach { (bridgeState, listBridges) ->
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
                            bridgesPlacemarks.add(this)
                        }
                    }
                }
                clusterizedCollection.clusterPlacemarks(40.0, 15)
                binding.screenStateView.setLoadingState(false)
            }

            is UiState.Empty -> {
                binding.screenStateView.setErrorState(getString(R.string.message_list_bridges_empty))
            }

            is UiState.Error -> {
                binding.screenStateView.setErrorState(
                    uiState.error.message,
                    getString(R.string.request_try_again)
                ) {
                    viewModel.loadBridges()
                }
            }

            is UiState.Loading -> {
                binding.screenStateView.setLoadingState(true)
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
        stopLocationUpdates()
        super.onPause()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroyView() {
        clusterizedCollection.clear()
        bridgesPlacemarks.clear()
        super.onDestroyView()
    }

    override fun onTimeTickReceive() {
        bridgesPlacemarks.groupBy { (it.userData as? Bridge)?.state }
            .forEach { (bridgeState, placemarks) ->
                bridgeState?.let {
                    val placemarkSize = resources.getDimensionPixelSize(R.dimen.placemark_size)
                    val imageProvider = ImageProvider.fromBitmap(
                        ResourcesCompat.getDrawable(
                            resources,
                            bridgeState.imageResId,
                            requireContext().theme
                        )?.toBitmap(placemarkSize, placemarkSize)
                    )
                    placemarks.forEach { it.setIcon(imageProvider) }
                }
            }
        clusterizedCollection.clear()
        bridgesPlacemarks.clear()
        updateUiState(checkNotNull(viewModel.uiState.value))
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