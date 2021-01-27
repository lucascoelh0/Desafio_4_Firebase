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
    private var urlImagem = ""
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
                val jogo = getDados()
                if (!updateJogo(jogo)) {
                    sendJogo(jogo)
                }
                viewModel.goToHome()
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

    fun atualizarCampos() {

        val jogoClicado = viewModel.jogoClicado

        binding.apply {
            includeEditarJogoBody.etNome.setText(jogoClicado.nome)
            includeEditarJogoBody.etAnoLancamento.setText(jogoClicado.anoLancamento)
            includeEditarJogoBody.etDescricao.setText(jogoClicado.descricao)
            if (jogoClicado.urlImagem.isNotEmpty()) {
                Glide.with(binding.root.context).load(jogoClicado.urlImagem).into(civFotoJogo)
            }
        }
    }

    fun setIntent() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "captura imagem"), CODE_IMG)
    }

    fun getDados(): MutableMap<String, Any> {
        val jogo: MutableMap<String, Any> = HashMap()

        jogo["nome"] = binding.includeEditarJogoBody.etNome.text.toString()
        jogo["anoLancamento"] = binding.includeEditarJogoBody.etAnoLancamento.text.toString()
        jogo["descricao"] = binding.includeEditarJogoBody.etDescricao.text.toString()
        jogo["urlImagem"] = urlImagem

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {

            storageReference = FirebaseStorage.getInstance().getReference(data!!.dataString!!)
            alertDialog.show()

            val uploadTask = storageReference.putFile(data.data!!)
            uploadTask.continueWithTask {
                storageReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    urlImagem = task.result.toString()
                    Glide.with(binding.root).load(urlImagem).into(binding.civFotoJogo)
                    alertDialog.dismiss()
                }
            }
        }
    }
}