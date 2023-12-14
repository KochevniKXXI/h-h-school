package hh.school.lesson_12_zemskov.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import hh.school.lesson_12_zemskov.ui.details_bridge_fragment.DetailsBridgeFragment
import hh.school.lesson_12_zemskov.ui.list_bridges_fragment.ListBridgesFragment
import hh.school.lesson_12_zemskov.ui.map_fragment.MapFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun listBridgesFragment(): ListBridgesFragment

    @ContributesAndroidInjector
    fun detailsBridgeFragment(): DetailsBridgeFragment

    @ContributesAndroidInjector
    fun mapFragment(): MapFragment
}