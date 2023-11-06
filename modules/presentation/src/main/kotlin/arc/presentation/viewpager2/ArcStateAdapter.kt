package arc.presentation.viewpager2

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import arc.presentation.fragment.ArcFragment

/**
 * [ArcStateAdapter] an adapter for configuring [androidx.viewpager2.widget.ViewPager2]
 * @author jimlyas
 * @since 13 Jan 2023
 * @param manager [FragmentManager] to inflate the [ArcFragment]
 * @param lifeCycle bind the [androidx.viewpager2.widget.ViewPager2] to the [Lifecycle]
 * @param listFragment [List] of [ArcFragment] to inflate into the [androidx.viewpager2.widget.ViewPager2]
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class ArcStateAdapter(
	manager: FragmentManager,
	lifeCycle: Lifecycle,
	private val listFragment: List<ArcFragment<*>>
) : FragmentStateAdapter(manager, lifeCycle) {

	override fun getItemCount() = listFragment.size

	override fun createFragment(position: Int) = listFragment[position]
}