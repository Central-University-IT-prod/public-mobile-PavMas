package com.trifcdr.lifestylehub.presentation.fragments.leisure

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.databinding.FragmentLeisureBinding
import com.trifcdr.lifestylehub.databinding.LeisureDialogBinding
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.presentation.recycler.BaseRecyclerAdapter
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.LeisureItem
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId
import com.trifcdr.lifestylehub.presentation.recycler.listener.CustomItemClickListener
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by trifcdr.
 */

@AndroidEntryPoint
class LeisureFragment : Fragment() {

    private lateinit var binding: FragmentLeisureBinding
    private val vm: LeisureViewModel by viewModels()

    @Inject lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var recycler: RecyclerView
    private val items = mutableListOf<StringId>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentLeisureBinding.inflate(inflater, container, false)
        recycler = binding.lRecycler
        return binding.root
    }

    @SuppressLint("InflateParams", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemsAdapter = BaseRecyclerAdapter(viewHoldersManager, CustomItemClickListener {venue, id, type ->
            if (type == 0){
                binding.progressCircular.visibility = View.VISIBLE
                vm.deleteLeisure(id!!)
            }
            if(type == 1){
                val action = LeisureFragmentDirections.actionLeisureFragmentToVenueFragment(venue)
                Navigation.findNavController(binding.root).navigate(action)
            }
        })
        recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = itemsAdapter
        }
        binding.lAddBtn.setOnClickListener {
            var leisure: Leisure
            val leisureViewBinding = LeisureDialogBinding.inflate(layoutInflater)
            leisureViewBinding.lDateEt.setOnClickListener {
                val materialDatePickerBuilder: MaterialDatePicker.Builder<*> =
                    MaterialDatePicker.Builder
                        .datePicker()
                        .setTitleText(getString(R.string.selectDate))
                val materialDatePicker = materialDatePickerBuilder.build()
                materialDatePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
                materialDatePicker.addOnPositiveButtonClickListener {
                    leisureViewBinding.lDateEt.setText(materialDatePicker.headerText)
                }
            }
            val alertDialog = MaterialAlertDialogBuilder(requireActivity())
                .setTitle(getString(R.string.createNote))
                .setView(leisureViewBinding.root)
                .setPositiveButton(getString(R.string.saveStr)){ dialog, _ ->
                    binding.progressCircular.visibility = View.VISIBLE
                    if(leisureViewBinding.lNameEt.text.toString() != "" && leisureViewBinding.lDateEt.text.toString() != "") {
                        leisure = Leisure(
                            name = leisureViewBinding.lNameEt.text.toString(),
                            date = leisureViewBinding.lDateEt.text.toString(),
                            notes = leisureViewBinding.lNoteEt.text.toString()
                        )
                        lifecycleScope.launch {
                            leisure.let { it1 -> vm.insertLeisure(it1) }
                        }
                    }
                    else Toast.makeText(context, getString(R.string.fill), Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }
                .setNegativeButton(getString(R.string.cancelStr)){ dialog, _ ->
                    dialog.cancel()
                }
                .create()
            alertDialog.show()

        }
        vm.getLeisureList()
            .onEach{
                if (it is DomainResource.Success){
                    itemsAdapter.submitList(null)
                    items.clear()
                    it.result.forEach{item ->
                        items.add(
                            LeisureItem(
                                leisureId = item.id,
                                venue = item.venueId,
                                name = item.name,
                                date = item.date,
                                note = item.notes
                            )
                        )
                    }
                    itemsAdapter.submitList(items)
                }
                if (it is DomainResource.Empty){
                    binding.progressCircular.visibility = View.GONE
                    itemsAdapter.submitList(null)
                    items.clear()
                }
                binding.progressCircular.visibility = View.GONE
            }.launchIn(lifecycleScope)
        vm.invoke()

    }
}