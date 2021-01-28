package com.example.desafio4_firebase.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafio4_firebase.R
import com.example.desafio4_firebase.entities.Event
import com.example.desafio4_firebase.entities.Jogo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MainViewModel : ViewModel() {

    private val _navigateScreen = MutableLiveData<Event<Int>>()
    lateinit var jogoClicado: Jogo
    lateinit var dbFirestore: FirebaseFirestore
    lateinit var collectionReference: CollectionReference

    //Variáveis para saber se o fragment irá adicionar ou editar um jogo
    var adicionar: Boolean = false
    var urlImagem = ""
    var urlAntiga = ""

    val navigateScreen: MutableLiveData<Event<Int>> = _navigateScreen

    fun config() {
        dbFirestore = FirebaseFirestore.getInstance()
        collectionReference = dbFirestore.collection("jogos")

    }

    fun goToCadastro() {
        _navigateScreen.value = Event(R.id.cadastroFragment)
    }

    fun goToHome() {
        urlImagem = ""
        urlAntiga = ""
        _navigateScreen.value = Event(R.id.homeFragment)
    }

    fun goToJogo() {
        _navigateScreen.value = Event(R.id.jogoFragment)
    }

    fun goToEditar() {
        _navigateScreen.value = Event(R.id.editarJogoFragment)
    }

    fun onClickJogo(documentSnapshot: DocumentSnapshot) {
        jogoClicado = documentSnapshot.toObject(Jogo::class.java)!!
        goToJogo()
    }

    fun onClickFabAdicionar() {
        adicionar = true
        goToEditar()
    }

    fun onClickFabEditar() {
        adicionar = false
        goToEditar()
    }

    fun getDados(nome: String, anoLancamento: String, descricao: String): MutableMap<String, Any> {

        val jogo: MutableMap<String, Any> = HashMap()

        jogo["nome"] = nome
        jogo["anoLancamento"] = anoLancamento
        jogo["descricao"] = descricao
        if (urlImagem.isNotEmpty()) {
            jogo["urlImagem"] = urlImagem
        } else {
            jogo["urlImagem"] = jogoClicado.urlImagem
        }

        return jogo
    }

    fun salvarJogo(jogo: MutableMap<String, Any>) {
        if (adicionar) {
            sendJogo(jogo)
        } else {
            updateJogo(jogo)
        }

        urlAntiga = ""
        urlImagem = ""

        goToHome()
    }

    private fun sendJogo(jogo: MutableMap<String, Any>) {
        val dataHoraCriacao = java.util.Calendar.getInstance().time.toString()
        val id = jogo["nome"].toString() + dataHoraCriacao
        jogo["id"] = id
        collectionReference.document(id).set(jogo)
    }

    private fun updateJogo(jogo: MutableMap<String, Any>) {
        if (urlAntiga.isNotEmpty() && urlImagem.isNotEmpty()) {
            FirebaseStorage.getInstance().getReferenceFromUrl(urlAntiga).delete()
        }
        collectionReference.document(jogoClicado.id).update(jogo)
    }

    fun verificarApagarImagemNaoVinculadaSave() {
        if (urlImagem.isNotEmpty()) {
            FirebaseStorage.getInstance().getReferenceFromUrl(urlImagem).delete()
        }
    }
}