package minhnq.gvn.com.demokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // su dung "in"
        for (i in 0..10) {
            Log.d("tag", i.toString())
        }

        //for su dung util
        Log.d("tag", "\n======for su dung util======")
        for (i in 0 until 10) {
            Log.d("tag", i.toString())
        }

        //for su dung down to
        Log.d("tag", "\n======for su dung downTo======")
        for (i in 10 downTo 0) {
            Log.d("tag", i.toString())
        }

        //for su dung step
        Log.d("tag", "\n======for su dung step======")
        for (i in 1..10 step 3) {
            Log.d("tag", i.toString())
        }

        //for su dung downTo va step
        Log.d("tag", "\n======for su dung downTo va step======")
        for (i in 10 downTo 0 step 3) {
            Log.d("tag", i.toString())
        }

        //for each
        Log.d("tag", "\n======foreach======")
        var intArray: IntArray = intArrayOf(1,2,3,4,5)
        for( i in intArray){
            Log.d("tag", i.toString())
        }

        var arrayList:ArrayList<Int> = arrayListOf()
        arrayList.add(1)
        arrayList.trimToSize()
    }
}
