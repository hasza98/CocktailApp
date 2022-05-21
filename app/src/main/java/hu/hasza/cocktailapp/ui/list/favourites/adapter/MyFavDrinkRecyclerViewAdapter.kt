package hu.hasza.cocktailapp.ui.list.favourites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.hasza.cocktailapp.data.model.DetailedDrink
import hu.hasza.cocktailapp.data.model.Drink
import hu.hasza.cocktailapp.databinding.CocktailListItemBinding
import hu.hasza.cocktailapp.databinding.FragmentCocktailListBinding


class MyFavDrinkRecyclerViewAdapter(
    private val values: List<Drink>, private val onClick: DrinkListClickListener
) : RecyclerView.Adapter<MyFavDrinkRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            CocktailListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemView.setOnClickListener {
            onClick.onClickDrinkList(item.idDrink)
        }
        holder.titleTv.text = item.strDrink
        Glide.with(holder.itemView.context)
            .load(item.strDrinkThumb)
            .into(holder.drinkImage)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: CocktailListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTv: TextView = binding.drinkTitle
        val drinkImage: ImageView = binding.drinkImage
    }

    interface DrinkListClickListener {
        fun onClickDrinkList(drinkId: Int)
    }

}