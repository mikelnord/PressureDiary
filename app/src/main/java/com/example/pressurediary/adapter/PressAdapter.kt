package com.example.pressurediary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pressurediary.databinding.ItemPressBinding
import com.example.pressurediary.model.PressDat
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject


class PressAdapter(query: Query) :
        FirestoreAdapter<PressAdapter.ViewHolder>(query) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemPressBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getSnapshot(position))
        }

        class ViewHolder(private val binding: ItemPressBinding) : RecyclerView.ViewHolder(binding.root) {

            fun bind(
                snapshot: DocumentSnapshot) {

                val pressDat = snapshot.toObject<PressDat>()
                if(pressDat==null){
                    return
                }

                binding.textPress.text="${pressDat.date} - ${pressDat.high}/${pressDat.low}  puls:${pressDat.puls}"
            }
        }
    }