package github.idmeetrious.btccalc.domain.model

import java.sql.Timestamp

data class Exchange(
    val currency: String = "",
    val rate: String = "",
    val timestamp: String = ""
)
