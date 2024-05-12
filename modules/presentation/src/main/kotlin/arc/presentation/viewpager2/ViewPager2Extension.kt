package arc.presentation.viewpager2

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author jimlyas
 * @since 0.1.0
 * Function to configure [ViewPager2]
 * @param configuration configuration for the [ViewPager2]
 * @receiver [ViewPager2]
 * Copyright © 2022-2024 jimlyas. All rights reserved.
 */
fun ViewPager2.configure(configuration: ViewPagerDeclaration) {
	val config = ViewPager2Configuration().apply(configuration)
	this.adapter = when {
		config.fragment != null -> {
			ArcStateAdapter(
				config.fragment!!.childFragmentManager,
				config.fragment!!.lifecycle,
				config.listFragment
			)
		}

		config.activity != null -> {
			ArcStateAdapter(
				config.activity!!.supportFragmentManager,
				config.activity!!.lifecycle,
				config.listFragment
			)
		}

		else -> null
	}

	config.tabLayout?.let { tab ->
		TabLayoutMediator(tab, this) { currentTab, position ->
			currentTab.text = config.tabLayoutConfiguration?.listTitle?.get(position)
			currentTab.icon = config.tabLayoutConfiguration?.listIcon?.get(position)
		}.attach()
	}
}


