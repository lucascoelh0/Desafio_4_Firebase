package com.example.desafio4_firebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.desafio4_firebase.databinding.FragmentCadastroBinding
import com.example.desafio4_firebase.entities.EventObserver
import com.example.desafio4_firebase.viewmodels.MainViewModel

class CadastroFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.includeCadastroBody.btnCreateAccount.setOnClickListener {
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
}