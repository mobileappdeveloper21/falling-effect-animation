package com.myapplication.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.myapplication.R
import com.myapplication.adapter.EmojiAdapter
import com.myapplication.databinding.EmojiDialogLayoutBinding

class EmojiDialog(val itemClickListener:(String)->Unit) :DialogFragment(){

    private lateinit var  binding:EmojiDialogLayoutBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EmojiDialogLayoutBinding.inflate(layoutInflater)
        setRecyclerView()
        return binding.root
    }

    private fun setRecyclerView() {
        binding.emojiRV.apply {
            adapter = EmojiAdapter(){emoji->
                itemClickListener.invoke(emoji)
                dismiss()
            }
            hasFixedSize()
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())

        // Set window attributes for a transparent background
        val window = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return dialog
    }


}