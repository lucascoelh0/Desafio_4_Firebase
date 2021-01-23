package com.example.desafio4_firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class MainViewModel {

    private var dbFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var collectionReference: CollectionReference

    init {
        collectionReference = dbFirestore.collection("jogos")
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