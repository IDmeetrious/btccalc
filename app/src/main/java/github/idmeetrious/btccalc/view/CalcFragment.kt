package github.idmeetrious.btccalc.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import github.idmeetrious.btccalc.databinding.FragmentCalcBinding
import github.idmeetrious.btccalc.viewmodel.CalcViewModel

class CalcFragment: Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(CalcViewModel::class.java)
    }
    private var _binding: FragmentCalcBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalcBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Fetch Btc rate from view model
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}