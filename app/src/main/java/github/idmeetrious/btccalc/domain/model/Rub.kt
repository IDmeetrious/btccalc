package github.idmeetrious.btccalc.domain.model

import com.google.gson.annotations.SerializedName

data class Rub(
    val success: Boolean = true,
    val timestamp: Long = 0L,
    val base: String = "RUB",
    val date: String = "",
    @SerializedName("RUB")
    val value: Double = 0.0,
    val rates: Usd? = null
)
