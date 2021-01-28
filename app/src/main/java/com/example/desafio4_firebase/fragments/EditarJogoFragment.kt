package com.example.desafio4_firebase.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.desafio4_firebase.databinding.FragmentEditarJogoBinding
import com.example.desafio4_firebase.entities.EventObserver
import com.example.desafio4_firebase.entities.Jogo
import com.example.desafio4_firebase.viewmodels.MainViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog

class EditarJogoFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var alertDialog: AlertDialog
    private lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000
    private var _binding: FragmentEditarJogoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditarJogoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        alertDialog = SpotsDialog.Builder().setContext(context).build()

        binding.civFotoJogo.setOnClickListener {
            setIntent()
        }

        if (viewModel.jogoClicado != Jogo()) {
            atualizarCampos()
        }

        binding.includeEditarJogoBody.btnSave.setOnClickListener {
            if (binding.includeEditarJogoBody.etNome.text.toString().isNotEmpty()) {
                val jogo = viewModel.getDados(
                    binding.includeEditarJogoBody.etNome.text.toString(),
                    binding.includeEditarJogoBody.etAnoLancamento.text.toString(),
                    binding.includeEditarJogoBody.etDescricao.text.toString()
                )

                viewModel.salvarJogo(jogo)

            } else {
                Toast.makeText(context, "Informe o nome do jogo", Toast.LENGTH_LONG).show()
            }
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

    private fun atualizarCampos() {

        val jogoClicado = viewModel.jogoClicado

        binding.apply {
            includeEditarJogoBody.etNome.setText(jogoClicado.nome)
            includeEditarJogoBody.etAnoLancamento.setText(jogoClicado.anoLancamento)
            includeEditarJogoBody.etDescricao.setText(jogoClicado.descricao)
            if (jogoClicado.urlImagem.isNotEmpty()) {
                viewModel.urlAntiga = jogoClicado.urlImagem
                Glide.with(binding.root.context).load(jogoClicado.urlImagem).into(civFotoJogo)
            }
        }
    }

    private fun setIntent() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "captura imagem"), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG && data != null && data.data != null) {

            storageReference = FirebaseStorage.getInstance().getReference(data.dataString!!)
            alertDialog.show()

            val uploadTask = storageReference.putFile(data.data!!)
            uploadTask.continueWithTask {
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.urlAntiga = viewModel.jogoClicado.urlImagem
                    viewModel.urlImagem = task.result.toString()
                    Glide.with(binding.root).load(viewModel.urlImagem).into(binding.civFotoJogo)
                    alertDialog.dismiss()
                }
            }
        }
    }
}