package com.choiceTech.choicedxself.ui.main

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.choiceTech.choicedxself.core.base.BaseFragment
import com.choiceTech.choicedxself.databinding.FragmentMainBinding
import com.choiceTech.choicedxself.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okio.Timeout
import timber.log.Timber

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::inflate
) {
    override fun shouldShowHeaderView(): Boolean = false

    private val viewModel: MainViewModel by viewModels()

    override fun init() {
        super.init()

        binding.mainVersionText.apply {
            text = viewModel.getVersionName()
            setOnClickListener(
                hiddenClickListener {
                    viewModel.onSettingsButton()
                }
            )
        }
    }

    override fun observe() {
        super.observe()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.settingsShared.collect {
                        findNavController().safeNavigate(
//                            MainFragmentDirections.actionMainToLogin(false)
                            MainFragmentDirections.testNav()
                        )
                    }
                }
            }
        }
    }

    private fun hiddenClickListener(
        requiredClickCount: Int = 7,
        clickTime: Long = 1000L,
        onComplete: () -> Unit
    ): View.OnClickListener {
        return object : View.OnClickListener {
            var lastTime: Long = 0L
            var clickCount = 0

            override fun onClick(p0: View?) {
                val nowTime = System.currentTimeMillis()
                if (lastTime == 0L || nowTime - lastTime < clickTime) {
                    clickCount++
                    if (clickCount == requiredClickCount) {
                        onComplete()
                        clickCount = 0
                        lastTime = 0L
                    }
                } else {
                    clickCount = 1
                    lastTime = nowTime
                }
            }
        }
    }
}