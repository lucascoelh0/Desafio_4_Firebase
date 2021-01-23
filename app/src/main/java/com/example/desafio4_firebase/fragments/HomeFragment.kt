package com.example.desafio4_firebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafio4_firebase.R
import com.example.desafio4_firebase.adapters.JogoAdapter
import com.example.desafio4_firebase.databinding.FragmentHomeBinding
import com.example.desafio4_firebase.databinding.FragmentLoginBinding
import com.example.desafio4_firebase.entities.EventObserver
import com.example.desafio4_firebase.viewmodels.MainViewModel

class HomeFragment : Fragment(), JogoAdapter.OnClickJogoListener {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var jogoAdapter: JogoAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.listJogos.observe(viewLifecycleOwner, {

            jogoAdapter = viewModel.listJogos.value?.let { JogoAdapter(it, this) }!!

            val recyclerView = binding.rvJogo

            recyclerView.adapter = jogoAdapter
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.setHasFixedSize(true)
        })

        binding.fabAdicionar.setOnClickListener {
            viewModel.adicionar = true
            viewModel.editar = false
            viewModel.goToEditar()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        viewModel.navigateScreen.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickJogo(position: Int) {
        viewModel.posicaoAtual.value = position
        viewModel.goToJogo()
    }
}