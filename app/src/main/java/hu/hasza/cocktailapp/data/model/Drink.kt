package hu.hasza.cocktailapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "drinks")
data class Drink (
    @SerializedName("strDrink")
    var strDrink: String,
    @SerializedName("strDrinkThumb")
    var strDrinkThumb: String,
    @SerializedName("idDrink")
    @PrimaryKey
    var idDrink: Int
    ) {
    constructor(detailedDrink: DetailedDrink) : this(
        strDrink = detailedDrink.strDrink,
        strDrinkThumb = detailedDrink.strDrinkThumb,
        idDrink = detailedDrink.idDrink
    )

}
