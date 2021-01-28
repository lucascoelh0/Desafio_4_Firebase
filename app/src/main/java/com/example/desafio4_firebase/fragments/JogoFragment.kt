package com.example.desafio4_firebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.desafio4_firebase.databinding.FragmentJogoBinding
import com.example.desafio4_firebase.entities.EventObserver
import com.example.desafio4_firebase.entities.Jogo
import com.example.desafio4_firebase.viewmodels.MainViewModel

class JogoFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentJogoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJogoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        atualizarUi()

        binding.fabEditar.setOnClickListener {
            viewModel.onClickFabEditar()
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

    fun atualizarUi() {
        binding.tvNomeJogoCapa.text = viewModel.jogoClicado.nome
        binding.tvNomeJogo.text = viewModel.jogoClicado.nome
        binding.tvAnoLancamento.text = viewModel.jogoClicado.anoLancamento
        binding.tvDescricaoJogo.text = viewModel.jogoClicado.descricao
        Glide.with(binding.root.context).load(viewModel.jogoClicado.urlImagem)
            .into(binding.ivCapaJogo)
    }
}