package com.example.desafio4_firebase.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio4_firebase.R
import com.example.desafio4_firebase.entities.Event
import com.example.desafio4_firebase.entities.Jogo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _navigateScreen = MutableLiveData<Event<Int>>()
    val navigateScreen: MutableLiveData<Event<Int>> = _navigateScreen
    val listJogos = MutableLiveData<List<Jogo>>()
    val posicaoAtual = MutableLiveData<Int>()

    //Variáveis para saber se o fragment irá adicionar ou editar um jogo
    var adicionar: Boolean = false
    var editar: Boolean = false

    private var dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var collectionReference: CollectionReference

    init {
        collectionReference = dbFirestore.collection("jogos")
    }

    fun goToCadastro() {
        _navigateScreen.value = Event(R.id.cadastroFragment)
    }

    fun goToHome() {
        _navigateScreen.value = Event(R.id.homeFragment)
    }

    fun goToJogo() {
        _navigateScreen.value = Event(R.id.jogoFragment)
    }

    fun goToEditar() {
        _navigateScreen.value = Event(R.id.editarJogoFragment)
    }

    fun popListJogos() {
        viewModelScope.launch {
            TODO("Popular lista de jogos a partir do firestore")
        }
    }

    fun getDados(): MutableMap<String, Any> {
        val jogo: MutableMap<String, Any> = HashMap()



        return jogo
    }

    fun sendJogo(jogo: MutableMap<String, Any>) {

        val prod: MutableMap<String, Any> = HashMap()

        collectionReference.document()


    }

    private fun atualizarJogo(jogo: MutableMap<String, Any>) {
        collectionReference.document(TODO("Jogo para atualizar")).update(jogo)
    }

    private fun deletarJogo() {
        collectionReference.document(TODO("Jogo para deletar")).delete()
    }



}