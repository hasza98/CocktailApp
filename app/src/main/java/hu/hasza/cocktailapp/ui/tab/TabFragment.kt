package hu.hasza.cocktailapp.ui.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayoutMediator
import hu.hasza.cocktailapp.R
import hu.hasza.cocktailapp.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_tab.*


class TabFragment : Fragment() {

    val titlesArray = arrayOf(
        "Cocktails",
        "Favourites"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = pager
        val tabLayout = tab_layout
        val fab = add_fab

        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titlesArray[position]
        }.attach()

        fab.setOnClickListener {
            Navigation.findNavController(this.view!!)
                .navigate(R.id.action_cocktail_list_tab_fragment_to_cocktail_addorupdate_fragment)
        }
    }
}