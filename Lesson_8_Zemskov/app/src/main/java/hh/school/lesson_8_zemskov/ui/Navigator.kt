package hh.school.lesson_8_zemskov.ui

import androidx.fragment.app.Fragment

interface Navigator {
    fun startFragment(fragment: Fragment)
    fun popBackStack()
}