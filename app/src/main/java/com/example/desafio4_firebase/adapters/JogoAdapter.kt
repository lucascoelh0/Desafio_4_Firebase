package com.example.desafio4_firebase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio4_firebase.databinding.ItemJogoBinding
import com.example.desafio4_firebase.entities.Jogo

class JogoAdapter(
    private val listJogos: List<Jogo>,
    val listener: OnClickJogoListener
) :
    RecyclerView.Adapter<JogoAdapter.JogoHolder>() {

    interface OnClickJogoListener {
        fun onClickJogo(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogoHolder {
        val itemBinding =
            ItemJogoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JogoHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: JogoHolder, position: Int) {
        val jogo: Jogo = listJogos[position]
        holder.bind(jogo)
    }

    override fun getItemCount(): Int = listJogos.size

    inner class JogoHolder(private val itemBinding: ItemJogoBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        fun bind(jogo: Jogo) {
            itemBinding.apply {
                tvNomeJogoItem.text = jogo.nome
                tvAnoLancamentoItem.text = jogo.anoLancamento
                TODO("Pegar url da imagem no firestore e carregar no imageview")
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onClickJogo(position)
            }
        }
    }
}