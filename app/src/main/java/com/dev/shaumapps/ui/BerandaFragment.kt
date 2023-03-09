package com.dev.shaumapps.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dev.shaumapps.databinding.FragmentBerandaBinding
import com.dev.shaumapps.ui.doa.DoaActivity
import com.dev.shaumapps.ui.hadis.HadisActivity
import com.dev.shaumapps.ui.jadwal_shalat.JadwalShalatActivity
import com.dev.shaumapps.ui.kutipan.KutipanActivity
import com.dev.shaumapps.ui.tasbih.TasbihActivity

/**
 * A simple [Fragment] subclass.
 */
class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.doa.setOnClickListener {
            val intent = Intent(requireContext(), DoaActivity::class.java)
            startActivity(intent)
        }

        binding.bgTasbih.setOnClickListener {
            val intent = Intent(requireContext(), TasbihActivity::class.java)
            startActivity(intent)
        }

        binding.circleBgHadits.setOnClickListener {
            startActivity(Intent(requireContext(), HadisActivity::class.java))
        }

        binding.bgJadwalSholat.setOnClickListener {
            startActivity(Intent(requireContext(), JadwalShalatActivity::class.java))
        }
        binding.circleBgKutipan.setOnClickListener {
            startActivity(Intent(requireContext(), KutipanActivity:: class.java))
        }
    }
}