package com.arturofilio.animals_mvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arturofilio.animals_mvvm.R
import com.arturofilio.animals_mvvm.model.Animal
import com.arturofilio.animals_mvvm.util.getProgressDrawable
import com.arturofilio.animals_mvvm.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(){

    fun updateAnimalList(newAnimalList: List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal_name.text = animalList[position].name
        holder.view.animalImage.loadImage(animalList[position].imageUrl, getProgressDrawable(holder.view.context))
    }

    class AnimalViewHolder(var view: View): RecyclerView.ViewHolder(view)
}