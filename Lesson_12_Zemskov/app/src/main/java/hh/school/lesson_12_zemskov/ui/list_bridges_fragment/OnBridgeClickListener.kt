package hh.school.lesson_12_zemskov.ui.list_bridges_fragment

import hh.school.lesson_12_zemskov.ui.model.Divorce

interface OnBridgeClickListener {
    fun onItemClick(id: Int, divorces: List<Divorce>)
    fun onReminderClick(id: Int, name: String)
}