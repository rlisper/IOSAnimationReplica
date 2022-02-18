package com.rlisper.iosanimationreplica.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.transition.ChangeTransform
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.*
import com.rlisper.iosanimationreplica.R
import com.rlisper.iosanimationreplica.ui.main.customviews.IOSActionBar
import com.rlisper.iosanimationreplica.ui.main.customviews.IOSActionBar2

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private val attentionDuration = 500L
    private val increaseDuration = 80L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<IOSActionBar2>(R.id.panel2)?.let { iosReplicaPanel ->
            context?.let { context ->
                val sceneRoot = iosReplicaPanel as ViewGroup
                val scene1 =
                    Scene.getSceneForLayout(sceneRoot, R.layout.ios_action_bar_state_small, context)
                scene1.enter()

                view.setOnClickListener {
                    TransitionManager.go(scene1, ChangeBounds().apply {
                        duration = increaseDuration
                    })
                }

                iosReplicaPanel.setOnLongClickListener {
                    val scene2 =
                        Scene.getSceneForLayout(
                            sceneRoot,
                            R.layout.ios_action_bar_state_small_attention,
                            context
                        )
                    val scene3 = Scene.getSceneForLayout(
                        sceneRoot,
                        R.layout.ios_action_bar_state_fullsize,
                        context
                    )

                    val transitionIncrease = ChangeBounds().apply {
                        duration = increaseDuration
                    }

                    val transitionFade = Fade().apply {
                        duration = increaseDuration
                        addTarget(LinearLayout::class.java)
                    }

                    val transitionIncreaseSet = TransitionSet().apply {
                        addTransition(transitionIncrease)
                        addTransition(transitionFade)
                    }

                    val transitionAttention = ChangeBounds().apply {
                        duration = attentionDuration
                        addListener(object: Transition.TransitionListener {
                            override fun onTransitionStart(transition: Transition) {
                            }

                            override fun onTransitionEnd(transition: Transition) {
                                TransitionManager.go(scene3, transitionIncreaseSet)
                            }

                            override fun onTransitionCancel(transition: Transition) {
                            }

                            override fun onTransitionPause(transition: Transition) {
                            }

                            override fun onTransitionResume(transition: Transition) {
                            }
                        })
                    }

                    TransitionManager.go(scene2, transitionAttention)

                    true
                }
            }
        }

        view.findViewById<IOSActionBar>(R.id.panel)?.let { iosReplicaPanel ->
            iosReplicaPanel.adapter = context?.let { ActionIconAdapter(it) }

            if(iosReplicaPanel.layoutTransition == null) {
                val toHeightDp = 300
                val toWidthDp = 250
                iosReplicaPanel.setOnItemLongClickListener { adapterView, _, i, l ->
                    val main = view.findViewById<ConstraintLayout>(R.id.main) as ViewGroup

                    val changeBoundTransitionIncrease = ChangeBounds().apply {
                        duration = increaseDuration
                        resizeClip = true
                        this.addListener(object: Transition.TransitionListener {
                            override fun onTransitionStart(transition: Transition) {}

                            override fun onTransitionEnd(transition: Transition) {
                                val nParams = adapterView.layoutParams
                                nParams.height = toHeightDp.dpToPx
                                nParams.width = toWidthDp.dpToPx
                                adapterView.layoutParams = nParams
                            }

                            override fun onTransitionCancel(transition: Transition) {}

                            override fun onTransitionPause(transition: Transition) {}

                            override fun onTransitionResume(transition: Transition) {}

                        })
                    }

                    val changeBoundTransitionAttention = ChangeBounds().apply {
                        duration = attentionDuration
                        resizeClip = true
                    }

                    val ts = TransitionSet().apply {
                        ordering = TransitionSet.ORDERING_SEQUENTIAL
                        addTransition(changeBoundTransitionAttention)
                        addTransition(changeBoundTransitionIncrease.apply { startDelay = attentionDuration })
                    }

                    TransitionManager.beginDelayedTransition(main, ts)
                    val nParams = adapterView.layoutParams
                    nParams.height = toHeightDp.dpToPx
                    nParams.width = toWidthDp.dpToPx
                    adapterView.layoutParams = nParams

                    true
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}