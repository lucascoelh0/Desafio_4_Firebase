package com.example.desafio4_firebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafio4_firebase.adapters.JogoAdapter
import com.example.desafio4_firebase.databinding.FragmentHomeBinding
import com.example.desafio4_firebase.entities.EventObserver
import com.example.desafio4_firebase.entities.Jogo
import com.example.desafio4_firebase.viewmodels.MainViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query

class HomeFragment : Fragment(), JogoAdapter.OnClickJogoListener {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private var jogoAdapter: JogoAdapter? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        val query =
            viewModel.dbFirestore.collection("produtos").orderBy("nome", Query.Direction.ASCENDING)

        val options =
            FirestoreRecyclerOptions.Builder<Jogo>().setQuery(query, Jogo::class.java).build()

        jogoAdapter = JogoAdapter(options, this)
        binding.rvJogo.adapter = jogoAdapter
        binding.rvJogo.layoutManager = GridLayoutManager(context, 2)

        binding.fabAdicionar.setOnClickListener {
            viewModel.apply {
                adicionar = true
                editar = false
                goToEditar()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        jogoAdapter?.startListening()
    }

    override fun onStop() {
        super.onStop()

        if (jogoAdapter != null) {
            jogoAdapter?.stopListening()
        }
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

    override fun onClickJogo(documentSnapshot: DocumentSnapshot, position: Int) {
        viewModel.jogoClicado = documentSnapshot.toObject(Jogo::class.java)!!
        viewModel.goToJogo()
    }
}