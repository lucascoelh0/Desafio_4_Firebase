package com.example.desafio4_firebase.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio4_firebase.databinding.ItemJogoBinding
import com.example.desafio4_firebase.entities.Jogo
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class JogoAdapter(
    options: FirestoreRecyclerOptions<Jogo>,
    var listener: OnClickJogoListener
) :

    FirestoreRecyclerAdapter<Jogo, JogoAdapter.JogoViewHolder>(options) {

    interface OnClickJogoListener {
        fun onClickJogo(documentSnapshot: DocumentSnapshot, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogoViewHolder {
        val itemBinding =
            ItemJogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JogoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(jogoViewHolder: JogoViewHolder, position: Int, jogo: Jogo) {
        jogoViewHolder.bind(jogo)
    }

    inner class JogoViewHolder(private val itemBinding: ItemJogoBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(jogo: Jogo) {
            itemBinding.apply {
                tvNomeJogoItem.text = jogo.nome
                tvAnoLancamentoItem.text = jogo.anoLancamento
            }

            itemBinding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClickJogo(snapshots.getSnapshot(position), position)
                }
            }
        }
    }
}