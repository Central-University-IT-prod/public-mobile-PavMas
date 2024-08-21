package com.trifcdr.lifestylehub.presentation.fragments.venue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.databinding.FragmentVenueBinding
import com.trifcdr.lifestylehub.databinding.LeisureDialogBinding
import com.trifcdr.lifestylehub.domain.models.DomainResource
import com.trifcdr.lifestylehub.domain.models.leisure.Leisure
import com.trifcdr.lifestylehub.presentation.recycler.BaseRecyclerAdapter
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.PhotoItem
import com.trifcdr.lifestylehub.presentation.recycler.itemModels.StringId
import com.trifcdr.lifestylehub.presentation.recycler.managers.ViewHoldersManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by trifcdr.
 */

@AndroidEntryPoint
class VenueFragment: Fragment() {

    private lateinit var binding: FragmentVenueBinding
    private val vm: VenueViewModel by viewModels()
    private val args : VenueFragmentArgs by navArgs()

    @Inject lateinit var viewHoldersManager: ViewHoldersManager
    private lateinit var recycler: RecyclerView
    private val items = mutableListOf<StringId>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVenueBinding.inflate(inflater, container, false)
        recycler = binding.photoDetaisRv
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val itemsAdapter = BaseRecyclerAdapter(viewHoldersManager)
        binding.flBtn.setOnClickListener {
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
                    if(leisureViewBinding.lNameEt.text.toString() != "" && leisureViewBinding.lDateEt.text.toString() != "") {
                        leisure = Leisure(
                            venueId = args.venueId,
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


        recycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = itemsAdapter
        }

        lifecycleScope.launch {
            vm.getVenueDetailsById(args.venueId)
                .collect{ networkResource ->
                    when(networkResource){
                        is DomainResource.Success -> {
                            val res = networkResource.result
                            itemsAdapter.submitList(null)
                            Picasso
                                .get()
                                .load(res.bestPhoto)
                                .into(binding.bestPhotoDetails)
                            if (res.bestPhoto == ""){
                                binding.bestPhotoDetails.visibility = View.GONE
                            }
                            if(res.photos.isEmpty()){
                                binding.photoDetaisRv.visibility = View.GONE
                            }
                            res.photos.forEach {
                                items.add(PhotoItem(
                                    photo = it
                                ))
                            }
                            binding.venueName.text = res.name
                            if (res.address != "") binding.address.text = res.address else binding.address.visibility = View.GONE
                            binding.category.text = res.categories[0]
                            var contacts = ""
                            if (res.url != "") contacts += "Сайт: ${res.url}\n"
                            if (res.phone != "") contacts += "Телефон: ${res.phone}"
                            binding.contacts.text = contacts
                            var reasons = ""
                            res.reasons.forEach{
                                reasons += "$it\n"
                            }
                            binding.reasons.text = reasons.dropLast(2)
                            if (res.hoursStatus != "") binding.hours.text = res.hoursStatus else binding.hours.visibility = View.GONE

                            itemsAdapter.submitList(items)
                        }
                        else -> {
                            Toast.makeText(context,
                                getString(R.string.detailsError), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }


        }
    }
