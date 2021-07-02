package github.idmeetrious.btccalc.view

import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.btccalc.R

class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title: TextView
    val description: TextView
    val check: RadioButton
    val divider: View

    init {
        view.let {
            title = it.findViewById(R.id.currency_list_item_title_tv)
            description = it.findViewById(R.id.currency_list_item_desrc_tv)
            check = it.findViewById(R.id.currency_list_item_rb)
            divider = it.findViewById(R.id.currency_list_item_divider)
        }
    }
}