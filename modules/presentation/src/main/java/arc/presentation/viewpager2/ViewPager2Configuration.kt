package arc.presentation.viewpager2

import arc.presentation.activity.ArcActivity
import arc.presentation.fragment.ArcFragment
import com.google.android.material.tabs.TabLayout

/**
 * @author jimlyas
 * @since 13 Jan 2023
 * @param activity [ArcActivity] where the [androidx.viewpager2.widget.ViewPager2] is inflated
 * @param fragment [ArcFragment] where the [androidx.viewpager2.widget.ViewPager2] is inflated
 * @param listFragment list of [ArcFragment] to include inside the [androidx.viewpager2.widget.ViewPager2]
 * @param tabLayout [TabLayout] to linked with the [androidx.viewpager2.widget.ViewPager2]
 * @param tabLayoutConfiguration configuration for the [TabLayout]
 *
 * Copyright © 2022-2023 jimlyas. All rights reserved.
 */
class ViewPager2Configuration(
    var activity: ArcActivity<*>? = null,
    var fragment: ArcFragment<*>? = null,
    var listFragment: List<ArcFragment<*>> = listOf(),
    var tabLayout: TabLayout? = null,
    var tabLayoutConfiguration: TabLayoutConfiguration? = null
) {
    /**
     * Function to define [ArcActivity] that inflated the [androidx.viewpager2.widget.ViewPager2]
     ** @param activity [ArcActivity] where the [androidx.viewpager2.widget.ViewPager2] is inflated
     */
    fun withActivity(activity: ArcActivity<*>) = apply { this.activity = activity }

    /**
     * Function to define [ArcFragment] that inflated the [androidx.viewpager2.widget.ViewPager2]
     * @param fragment [ArcFragment] where the [androidx.viewpager2.widget.ViewPager2] is inflated
     */
    fun withFragment(fragment: ArcFragment<*>) = apply { this.fragment = fragment }

    /**
     * Function to define the list of fragments to include inside the ViewPager
     * @param listFragment list of [ArcFragment] to include inside the [androidx.viewpager2.widget.ViewPager2]
     */
    fun withListFragment(listFragment: List<ArcFragment<*>>) =
        apply { this.listFragment = listFragment }

    /**
     * Function to bind [TabLayout] with the [androidx.viewpager2.widget.ViewPager2]
     * @param tabLayout [TabLayout] to linked with the [androidx.viewpager2.widget.ViewPager2]
     * @param configuration configuration for the [TabLayout]
     */
    fun bindWithTabLayout(tabLayout: TabLayout, configuration: TabLayoutDeclaration? = null) =
        apply {
            this.tabLayout = tabLayout
            configuration?.let { this.tabLayoutConfiguration = TabLayoutConfiguration().apply(it) }
        }
}

typealias ViewPagerDeclaration = ViewPager2Configuration.() -> Unit