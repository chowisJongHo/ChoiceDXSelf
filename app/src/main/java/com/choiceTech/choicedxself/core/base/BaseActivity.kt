package com.choiceTech.choicedxself.core.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {
    protected lateinit var binding: VB
    abstract fun getViewBinding(): VB

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)

//        observe()
    }

//    open fun observe() {}
}