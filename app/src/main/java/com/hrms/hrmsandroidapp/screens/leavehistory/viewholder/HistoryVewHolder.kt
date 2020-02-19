package com.hrms.hrmsandroidapp.screens.leavehistory.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import com.hrms.hrmsandroidapp.commonadapter.BaseViewholder
import com.hrms.hrmsandroidapp.constants.Constants
import com.hrms.hrmsandroidapp.listener.IAdapterClickListener
import com.hrms.hrmsandroidapp.model.outputmodel.history.LeaveItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.partialy_notification_list_items.view.*

class HistoryVewHolder(
    override val containerView: View,
    var adapterType: String = "common",
    var adapterClickListener: IAdapterClickListener? = null
) : BaseViewholder(containerView), LayoutContainer {

    lateinit var textview: TextView
    override fun bind(context: Context, item: Any, pos: Int) {
        item.apply {
            if (item is LeaveItem) {
                if (!item.date.isNullOrEmpty()) {
                    itemView.date_tv.text = item.date
                }
                if (!item.reason!!.isNullOrEmpty()){
                    itemView.tv_reason.text = item.reason
                }
                if (!item.type!!.isNullOrEmpty()){
                    itemView.tv_type.text = item.type
                }

                itemView.cancel_leave_img.setOnClickListener{
                    if(adapterClickListener!=null){
                        adapterClickListener!!.onclick(item,adapterPosition,adapterType,Constants.CANCEL,view = containerView)
                    }
                }
            }

        }
    }
}