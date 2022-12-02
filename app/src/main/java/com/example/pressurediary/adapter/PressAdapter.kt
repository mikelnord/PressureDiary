package com.example.pressurediary.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pressurediary.databinding.ItemPressBinding
import com.example.pressurediary.model.PressDat
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject


class PressAdapter(query: Query) :
    FirestoreAdapter<PressAdapter.ViewHolder>(query) {
    private var maxDay = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPressBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position))
    }

    inner class ViewHolder(private val binding: ItemPressBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(
            snapshot: DocumentSnapshot
        ) {

            val pressDat = snapshot.toObject<PressDat>() ?: return
            val day = pressDat.day ?: 0
            when {
                day != maxDay -> {
                    maxDay = day
                    binding.textGrup.visibility = View.VISIBLE
                    binding.textGrup.text = maxDay.toString()
                }
                else -> {
                    binding.textGrup.visibility = View.GONE
                }
            }

            binding.textPress.text =
                "${pressDat.day}:${pressDat.month} - ${pressDat.high}/${pressDat.low}  puls:${pressDat.puls}"
        }
    }
}