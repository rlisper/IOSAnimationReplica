package com.rlisper.iosanimationreplica.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.rlisper.iosanimationreplica.R

class ActionIconAdapter(private val context: Context): BaseAdapter() {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return actionResourcesPreset.size
    }

    override fun getItem(p0: Int): Any {
        return actionResourcesPreset[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val actionCard = inflater.inflate(R.layout.action_card_view, p2, false)
        actionCard.apply {
            findViewById<ImageView>(R.id.action_image_view)
                ?.setImageResource(getItem(p0) as? Int ?: R.drawable.ic_launcher_foreground)
        }
        return actionCard
    }

    private val actionResourcesPreset = arrayOf(
        R.drawable.action_1,
        R.drawable.action_1,
        R.drawable.action_1,
        R.drawable.action_1,
        R.drawable.action_1,
        R.drawable.action_1
    )
}