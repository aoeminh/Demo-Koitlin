package minhnq.gvn.com.demokotlin

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var mAdapter: NumberAdapter? = null
    var arrList: ArrayList<Int>?= arrayListOf()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrList = initList()
        initRecyclerview()
        setActionAdd()
        setActionRemove()
        setActionSoft()

    }

    fun setActionAdd(){
        btn_add.setOnClickListener(View.OnClickListener {
            if (edt_main.text.isEmpty()){
                Toast.makeText(this,"Insert number",Toast.LENGTH_SHORT).show()
            }else{
                arrList?.add(edt_main.text.toString().toInt())
                arrList?.let { it1 -> mAdapter?.appendList(it1) }
                edt_main.setText("")
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun setActionRemove(){
        btn_remove.setOnClickListener(View.OnClickListener {
            if (edt_main.text.isEmpty()){
                Toast.makeText(this,"Insert number",Toast.LENGTH_SHORT).show()
            }else{
                var numberRemove: Int = edt_main.text.toString().toInt()
                val mutableIterator = arrList!!.iterator()
                for(i in mutableIterator){
                        if(i == numberRemove){
                        Log.d("tag", i.toString())
                        mutableIterator.remove()
                    }
                }
                arrList?.let {it1 -> mAdapter?.appendList(it1) }
                edt_main.setText("")

            }

        })
    }

    fun setActionSoft(){
        btn_sort.setOnClickListener(View.OnClickListener {
            arrList?.sort()
            arrList?.let {it1 -> mAdapter?.appendList(it1) }
            edt_main.setText("")

        })
    }


    fun initRecyclerview(){
        var mLinearLayoutManager: LinearLayoutManager? = LinearLayoutManager(this)
        mLinearLayoutManager?.orientation = LinearLayoutManager.VERTICAL
        mAdapter = NumberAdapter(this,initList())
        rv_main.layoutManager = mLinearLayoutManager
        rv_main.adapter = mAdapter

    }


    fun initList(): ArrayList<Int> {
        var arrayList: ArrayList<Int> = arrayListOf()

        for( i in 1..10){
            arrayList.add(i)
        }
        return arrayList
    }


}
