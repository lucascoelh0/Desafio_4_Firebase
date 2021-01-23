package com.example.desafio4_firebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
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
    ): View? {

        _binding = FragmentJogoBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.posicaoAtual.observe(viewLifecycleOwner, {

            val jogoClicado = viewModel.posicaoAtual.value?.let { it1 ->
                viewModel.listJogos.value?.get(it1)
            }

            if (jogoClicado != null) {
                atualizarUi(jogoClicado)
            }
        })

        binding.fabEditar.setOnClickListener {
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

    fun atualizarUi(jogoClicado: Jogo) {

        TODO("Setar imagem da capa a partir do firestore")

        binding.tvNomeJogoCapa.text = jogoClicado.nome
        binding.tvNomeJogo.text = jogoClicado.nome
        binding.tvAnoLancamento.text = jogoClicado.anoLancamento
        binding.tvDescricaoJogo.text = jogoClicado.descricao
    }

}