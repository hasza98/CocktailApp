package hu.hasza.cocktailapp.ui.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import hu.hasza.cocktailapp.ui.list.all.CocktailListFragment
import hu.hasza.cocktailapp.ui.list.favourites.FavCocktailListFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CocktailListFragment()
            1 -> return FavCocktailListFragment()
        }
        return CocktailListFragment()
    }
}