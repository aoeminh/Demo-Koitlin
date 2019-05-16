package minhnq.gvn.com.demokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // mac dinh phai khai bao gia tri cho bien
    var mTextView: TextView? = null
    // su dung lateinit, bien se duoc gan gia tri sau
    lateinit var mTextView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // tu khoa var cho phep thay doi gia tri cua bien
        var name: String = "name"
        name = "John" //ok

        // tu khoa val khong cho thay doi gia tri bien
        val name1: String = "name1"
      //  name1 = "Peter" // error

        // var hoac val co the tu dong detect kieu du lieu
        var name2 = "David" //name2 kieu String
        val age = 20    // age kieu Int

        // ? cho phep number nhan gia tri null
        var number: Int? = 1
        number = null // ok

        // !! khong cho phep number2 nhan gia tri null,
        var number2: Int = 2!!
    //    number2 = null //error

        //mac dinh khong khai bao !! hoac ?.  bien se nhan !!

        var number3: Int = 3
      //  number3 = null //error

        // Ep kieu
        // Ep kieu rong
        val number4: Int = 10
        var number5: Double = number4.toDouble() // number5 = 10.0
        Log.d("tag",number5.toString());

        //ep kieu hep
        val number6: Double = 10.5
        var number7: Int = number6.toInt() // number7 = 10
        Log.d("tag",number7.toString());

        // Ep kieu object
        val str1: String? = "str1"
        if( str1 is String){
            val str2: String = str1 as String
            Log.d("tag",str2);

        }

        //safe cast
        if( str1 is String){
            val str2: String? = str1 as? String
            Log.d("tag",str2);

        }
    }


}
