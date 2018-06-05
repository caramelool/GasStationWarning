package caramelo.com.br.gasstationwarning.ui

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

open class BaseViewHolder(
        parent: ViewGroup,
        @LayoutRes layout: Int
) : RecyclerView.ViewHolder(inflate(parent, layout))


private fun inflate(
        parent: ViewGroup,
        @LayoutRes layout: Int
) = LayoutInflater.from(parent.context)
        .inflate(layout, parent, false)