package com.example.desafio4_firebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.desafio4_firebase.databinding.FragmentEditarJogoBinding
import com.example.desafio4_firebase.entities.EventObserver
import com.example.desafio4_firebase.viewmodels.MainViewModel

class EditarJogoFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentEditarJogoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditarJogoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.civFotoJogo.setOnClickListener {
            //Implementar funcionalidade para adicionar foto
        }

        binding.includeEditarJogoBody.btnSave.setOnClickListener {
            if (binding.includeEditarJogoBody.etNome.text.toString().isNotEmpty()) {
                val jogo = getDados()
                if (!updateJogo(jogo)) {
                    sendJogo(jogo)
                }
            }
            viewModel.goToHome()
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

    fun getDados(): MutableMap<String, Any> {
        val jogo: MutableMap<String, Any> = HashMap()

        jogo["nome"] = binding.includeEditarJogoBody.etNome.text.toString()
        jogo["anoLancamento"] = binding.includeEditarJogoBody.etAnoLancamento.text.toString()
        jogo["descricao"] = binding.includeEditarJogoBody.etDescricao.text.toString()
        jogo["urlImagem"] = ""

        return jogo
    }

    fun sendJogo(jogo: MutableMap<String, Any>) {
        val nome = binding.includeEditarJogoBody.etNome.text.toString()
        viewModel.collectionReference.document(nome).set(jogo)
    }

    private fun updateJogo(jogo: MutableMap<String, Any>): Boolean {
        var retorno = false
        val nome = binding.includeEditarJogoBody.etNome.text.toString()
        viewModel.collectionReference.document(nome).update(jogo).addOnSuccessListener {
            retorno = true
        }

        return retorno
    }
}