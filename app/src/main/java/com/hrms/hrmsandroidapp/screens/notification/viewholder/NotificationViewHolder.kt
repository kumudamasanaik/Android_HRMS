package com.hrms.hrmsandroidapp.screens.notification.viewholder

import android.content.Context
import android.view.View
import com.hrms.hrmsandroidapp.commonadapter.BaseViewholder
import com.hrms.hrmsandroidapp.listener.IAdapterClickListener
import kotlinx.android.extensions.LayoutContainer

class NotificationViewHolder (override val containerView: View, var adapterType: String = "common", var adapterClickListener: IAdapterClickListener? = null) : BaseViewholder(containerView),
    LayoutContainer {

    private var checkbox = false
    var count: Int = 0

    override fun bind(context: Context, item: Any, pos: Int) {
        item.apply {

        }
    }
}