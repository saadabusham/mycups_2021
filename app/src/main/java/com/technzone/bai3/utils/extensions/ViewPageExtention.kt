package com.technzone.bai3.utils.extensions

import android.os.CountDownTimer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.technzone.bai3.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.technzone.bai3.ui.main.fragments.home.adapters.BannerIndicatorRecyclerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

fun ViewPager2.connectToHomeIndicator(rvIndicator: RecyclerView) {
    val indicatorRecyclerAdapter = (rvIndicator.adapter as BaseBindingRecyclerViewAdapter<*>)
    indicatorRecyclerAdapter as BannerIndicatorRecyclerAdapter
    var pagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            try {
                CoroutineScope(Dispatchers.Main).launch {
                    indicatorRecyclerAdapter.items.withIndex().singleOrNull { it.value }?.let {
                        indicatorRecyclerAdapter.items[it.index] = false
                    }
                    indicatorRecyclerAdapter.items[position] = true
                    indicatorRecyclerAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {

            }
        }
    }
    registerOnPageChangeCallback(pagerCallback)
    val pagerAdapter = (this.adapter as BaseBindingRecyclerViewAdapter<*>)
    pagerAdapter.items.let {
        it.withIndex().forEach {
            indicatorRecyclerAdapter.submitItem(it.index == 0)
        }
    }
}

fun ViewPager2?.startSlider(delay: Long = 2000, period: Long = 2000) {
    CoroutineScope(Dispatchers.Main).launch {
        val page = this@startSlider?.adapter
        val itemCount = (page?.itemCount ?: 0) - 1
        if (itemCount == 0)
            return@launch
        var timer : CountDownTimer? = null
        timer = object : CountDownTimer(delay,period){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (this@startSlider?.currentItem ?: 0 < itemCount) {
                    this@startSlider?.setCurrentItem((this@startSlider.currentItem) + 1, true)
                } else
                    this@startSlider?.currentItem = 0
                timer?.start()
            }

        }
        timer.start()
//        val sliderTimer = SliderTimer(object : OnRun {
//            override fun run() {
//                if (this@startSlider?.currentItem ?: 0 < itemCount) {
//                    this@startSlider?.setCurrentItem((this@startSlider.currentItem) + 1, true)
//                } else
//                    this@startSlider?.currentItem = 0
//            }
//        })
//        val timer = Timer()
//        timer.scheduleAtFixedRate(sliderTimer, delay, period)
    }
}