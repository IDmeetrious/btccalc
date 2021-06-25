package github.idmeetrious.btccalc.domain.model

import com.google.gson.annotations.SerializedName

data class Btc(
    val time: String = "",
    @SerializedName("asset_id_base")
    val baseId: String = "BTC",
    @SerializedName("asset_id_quote")
    val quoteId: String = "USD",
    val rate: Double = 3260.3514321215056208129867667
)
