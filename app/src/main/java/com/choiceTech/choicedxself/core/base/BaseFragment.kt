package com.choiceTech.choicedxself.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.choiceTech.choicedxself.R

abstract class BaseFragment<VB: ViewBinding>(
    private val inflaterBinding: (LayoutInflater, ViewGroup, Boolean) -> VB
): Fragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_base_container, container, false)
        val container: FrameLayout = root.findViewById(R.id.container)
        _binding = inflaterBinding(inflater, container, false)
        container.addView(binding.root)

        init()
        observe()

        return root
    }

    open fun init() {}
    open fun observe() {}
}