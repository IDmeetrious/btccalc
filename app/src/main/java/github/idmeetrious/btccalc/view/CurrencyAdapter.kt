package github.idmeetrious.btccalc.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.btccalc.R
import github.idmeetrious.btccalc.domain.model.Currency
import github.idmeetrious.btccalc.domain.model.Exchange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CurrencyAdapter(private var list: List<*>): RecyclerView.Adapter<CurrencyViewHolder>() {

    private var _exchange: MutableStateFlow<Exchange?> = MutableStateFlow(null)
    val exchange get() = _exchange

    private var _currency: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val currency get() = _currency

    fun updateList(list: List<*>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_currencies_list_item, parent, false)
        return CurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = list[position]
        when(item){
            is Currency -> {
                holder.title.text = item.name
                holder.description.text = item.id
                holder.itemView.setOnClickListener {
                    holder.check.isChecked = true
                    CoroutineScope(Dispatchers.IO).launch {
                        _currency.emit(item)
                    }
                }
            }
            is Exchange -> {
                holder.title.text = item.currency
                holder.description.text = item.currency
                holder.itemView.setOnClickListener {
                    holder.check.isChecked = true
                    CoroutineScope(Dispatchers.IO).launch {
                        _exchange.emit(item)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size
}