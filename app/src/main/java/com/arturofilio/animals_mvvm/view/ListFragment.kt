package com.arturofilio.animals_mvvm.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager

import com.arturofilio.animals_mvvm.R
import com.arturofilio.animals_mvvm.model.Animal
import com.arturofilio.animals_mvvm.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    private val animalListDataObserver = Observer<List<Animal>> {list ->
        list?.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }

    private val loadingLiveData = Observer<Boolean> {isLoading ->
        loadingView.visibility = if(isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            list_error.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> {isError ->
        list_error.visibility = if(isError) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveData)
        viewModel.loadError.observe(this, errorLiveDataObserver)
        viewModel.refresh()

        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refresh_layout.setOnRefreshListener {
            animalList.visibility = View.GONE
            list_error.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refresh_layout.isRefreshing = false
        }
    }
}
