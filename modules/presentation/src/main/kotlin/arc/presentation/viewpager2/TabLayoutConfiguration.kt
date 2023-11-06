package arc.presentation.viewpager2

import android.graphics.drawable.Drawable

/**
 * @author jimlyas
 * @since 13 Jan 2023
 * @param listTitle [List] of [String] used as [com.google.android.material.tabs.TabLayout]'s title
 * @param listIcon [List] of [Drawable] used as [com.google.android.material.tabs.TabLayout]'s icon
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class TabLayoutConfiguration(
	var listTitle: List<String>? = null, var listIcon: List<Drawable?>? = null
) {

	/**
	 * Function to define titles for the tab
	 * @param listTitle list of [String] for the tab title
	 */
	fun withTitles(listTitle: List<String>) {
		this.listTitle = listTitle
	}

	/**
	 * Function to define list of icon for the tab
	 * @param listIcon list of [Drawable] for the tab icon
	 */
	fun withIcons(listIcon: List<Drawable?>) {
		this.listIcon = listIcon
	}
}

typealias TabLayoutDeclaration = TabLayoutConfiguration.() -> Unit
