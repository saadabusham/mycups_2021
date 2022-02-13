package com.raantech.mycups.utils.extensions

import android.transition.TransitionManager
import android.view.ViewGroup

fun ViewGroup.startTransitionDelay() {
    TransitionManager.endTransitions(this)
    TransitionManager.beginDelayedTransition(this)
}