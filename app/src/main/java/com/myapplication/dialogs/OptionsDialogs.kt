package com.myapplication.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.myapplication.R
import com.myapplication.databinding.OptionsDialogLayoutBinding
import com.myapplication.utils.setWidthPercent

class OptionsDialogs (val itemClickListener:(String)->Unit) : DialogFragment() {

    private lateinit var binding:OptionsDialogLayoutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(85)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OptionsDialogLayoutBinding.inflate(layoutInflater)

        setView()
        return binding.root
    }

    private fun setView() {

        binding.showBtn.setOnClickListener {

            val text = binding.enterText.text.trim()
            if(text.isNotEmpty()){
                itemClickListener.invoke(text.toString())
                dismiss()
            }else{
                binding.enterText.error = "Please Enter Text"
            }

        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), R.style.WideDialog)

        // Set window attributes for a transparent background
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }

}