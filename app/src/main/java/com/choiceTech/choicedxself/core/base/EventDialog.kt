package com.choiceTech.choicedxself.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.choiceTech.choicedxself.R

abstract class EventDialog<VB: ViewBinding>(): DialogFragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun inflateBinding(inflater: LayoutInflater): VB

    abstract fun dialogWidth(): Int
    abstract fun dialogHeight(): Int
    open fun dialogBackground(): Int = R.drawable.bg_event_dialog

    open fun onInit() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onInit()
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        dialog?.setCanceledOnTouchOutside(true)

        dialog?.window?.apply {
            setBackgroundDrawableResource(dialogBackground())
            setLayout(
                resources.getDimensionPixelSize(dialogWidth()),
                resources.getDimensionPixelSize(dialogHeight())
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}