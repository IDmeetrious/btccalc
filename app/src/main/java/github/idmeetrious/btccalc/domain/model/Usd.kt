package github.idmeetrious.btccalc.domain.model

import com.google.gson.annotations.SerializedName

data class Usd(
    val success: Boolean = true,
    val timestamp: Long = 0L,
    val base: String = "USD",
    val date: String = "",
    @SerializedName("USD")
    val value: Double = 0.0,
    val rates: Rub? = null
)
