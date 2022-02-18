package com.rlisper.iosanimationreplica.ui.main.customviews

import android.animation.LayoutTransition
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.GridView
import androidx.core.animation.doOnEnd
import androidx.core.view.updateLayoutParams
import androidx.transition.TransitionManager
import com.rlisper.iosanimationreplica.ui.main.TAG
import com.rlisper.iosanimationreplica.ui.main.dpToPx
import com.rlisper.iosanimationreplica.ui.main.pxToDp
import java.util.*
import kotlin.math.roundToInt

class IOSActionBar(context: Context, attrs: AttributeSet): GridView(context, attrs) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        Log.d(TAG, "bad bad bad")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        isLongClickable = true

        val toHeightDp = 700
        val toWidthDp = 350

        /***
         * Set animateLayoutChanges to True. Easy but hard customization
         */
        layoutTransition?.let {
            it.enableTransitionType(LayoutTransition.CHANGING)
            it.disableTransitionType(LayoutTransition.APPEARING)
            setOnItemLongClickListener { adapterView, view, i, l ->
                updateLayoutParams {
                    height = toHeightDp.dpToPx
                    width = toWidthDp.dpToPx
                }
                true
            }
        }

        if (layoutTransition == null) {

            /***
             * Bad FPS(onLayout) but creating custom ViewGroup consumes too much time for test work
             */
            // quadratish simplifier
            if (false) {
                setOnItemLongClickListener { adapterView, view, i, l ->
                    val decreaseAnim =
                        ValueAnimator.ofInt(layoutParams.height, 90.dpToPx).let { anim ->
                            anim.duration = 200
                            anim.addUpdateListener {
                                updateLayoutParams {
                                    height = it.animatedValue as Int
                                    width = it.animatedValue as Int
                                }
                            }
                            anim
                        }

                    val startHeight = height
                    val startWidth = width
                    val toHeight = toHeightDp.dpToPx
                    val toWidth = toWidthDp.dpToPx
                    val dHeight = toHeight - startHeight
                    val dWidth = toWidth - startWidth
                    val increaseAnim =
                        ValueAnimator.ofFloat(0f, 1f).let { anim ->
                            anim.duration = 300
                            anim.interpolator = AccelerateInterpolator(30f)
                            anim.addUpdateListener {
                                updateLayoutParams {
                                    height =
                                        startHeight + ((it.animatedValue as Float) * dHeight).roundToInt()
                                    width =
                                        startWidth + ((it.animatedValue as Float) * dWidth).roundToInt()
                                }
                            }
                            anim
                        }

                    decreaseAnim.doOnEnd {
                        increaseAnim.start()
                    }
                    decreaseAnim.start()

                    true
                }
            }
        }
    }



}
