package com.example.desafio4_firebase.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafio4_firebase.R
import com.example.desafio4_firebase.entities.Event
import com.example.desafio4_firebase.entities.Jogo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainViewModel : ViewModel() {

    private val _navigateScreen = MutableLiveData<Event<Int>>()
    lateinit var jogoClicado: Jogo
    lateinit var dbFirestore: FirebaseFirestore
    lateinit var collectionReference: CollectionReference

    //Variáveis para saber se o fragment irá adicionar ou editar um jogo
    var adicionar: Boolean = false
    var editar: Boolean = false

    val navigateScreen: MutableLiveData<Event<Int>> = _navigateScreen

    fun config() {
        dbFirestore = FirebaseFirestore.getInstance()
        collectionReference = dbFirestore.collection("produtos")

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
}