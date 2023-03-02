package com.example.shaumapps.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shaumapps.R
import com.example.shaumapps.databinding.FragmentBerandaBinding
import com.example.shaumapps.ui.doa.DoaActivity
import com.example.shaumapps.ui.hadis.HadisActivity
import com.example.shaumapps.ui.tasbih.TasbihActivity

/**
 * A simple [Fragment] subclass.
 */
class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding  get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.circleBgDoa.setOnClickListener {
            val intent = Intent(requireContext(), DoaActivity::class.java)
            startActivity(intent)
        }

        binding.circleBgTasbih.setOnClickListener {
            val intent = Intent(requireContext(), TasbihActivity::class.java)
            startActivity(intent)
        }

        binding.circleBgHadits.setOnClickListener {
            startActivity(Intent(requireContext(), HadisActivity::class.java))
        }

    }
}