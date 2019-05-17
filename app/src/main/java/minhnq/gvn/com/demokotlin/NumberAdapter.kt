package minhnq.gvn.com.demokotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NumberAdapter(var context:Context, var arrayList: ArrayList<Int>) : RecyclerView.Adapter<NumberAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_rv_main,viewGroup,false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView?.text = arrayList.get(position).toString()
    }

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView:TextView?= itemView.findViewById(R.id.tv_item_rv_main)

    }

    fun appendList(arrayList: ArrayList<Int>){
        this.arrayList.clear()
        this.arrayList.addAll(arrayList)
        notifyDataSetChanged()
    }
}