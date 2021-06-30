package github.idmeetrious.btccalc.domain.model

import com.google.gson.annotations.SerializedName

data class Currency(
    val id: String = "BTC",
    val name: String = "Bitcoin",
    val price: Double = 0.0,
    @SerializedName("price_date")
    val date: String = ""
)
