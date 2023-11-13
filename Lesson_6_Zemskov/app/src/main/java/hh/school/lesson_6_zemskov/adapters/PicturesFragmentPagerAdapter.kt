package hh.school.lesson_6_zemskov.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hh.school.lesson_6_zemskov.PageFragment

class PicturesFragmentPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    private val pictures = mutableListOf<Pair<String, String>>()

    override fun getItemCount() = pictures.size

    override fun createFragment(position: Int) =
        PageFragment.newInstance(pictures[position].first, pictures[position].second)

    fun setList(listPictures: List<Pair<String, String>>) {
        pictures.clear()
        pictures.addAll(listPictures)
        notifyDataSetChanged()
    }
}