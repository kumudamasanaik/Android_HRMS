package com.hrms.hrmsandroidapp.commonadapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hrms.hrmsandroidapp.R
import com.hrms.hrmsandroidapp.listener.IAdapterClickListener
import com.hrms.hrmsandroidapp.screens.leavehistory.viewholder.HistoryVewHolder
import com.hrms.hrmsandroidapp.util.inflate
import com.hrms.hrmsandroidapp.util.withNotNullNorEmpty

class BaseRecAdapter(var context: Context, var type: Int, var adapterType: String = "common", var adapterClickListener: IAdapterClickListener? = null)
    : RecyclerView.Adapter<BaseViewholder>() {

    var list: ArrayList<*>? = null


    fun addList(list: ArrayList<*>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewholder {
        val view = parent.inflate(type)
        lateinit var holder: BaseViewholder
        when (type) {
             R.layout.partialy_history_list_items -> holder = HistoryVewHolder(view, adapterType, adapterClickListener)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return if (list != null && list!!.size > 0) list!!.size else 6
    }

    override fun onBindViewHolder(holder: BaseViewholder, position: Int) {
        list.withNotNullNorEmpty {
            holder.bind(context, list!![position], position)
            return
        }
        holder.bind(context, holder, position)
    }
}