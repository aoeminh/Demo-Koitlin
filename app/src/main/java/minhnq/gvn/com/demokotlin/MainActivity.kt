package minhnq.gvn.com.demokotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var textView: TextView? = null
    lateinit var editText: EditText
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView  = findViewById(R.id.textview_main)
        // editText va button duoc khai bao lateinit => phai duoc khoi tao truoc khi su dung
        editText = findViewById<EditText>(R.id.editText)
        button = findViewById(R.id.button_main)

        // neu khong duoc khoi tao se crash khi su dung
        button.setOnClickListener(View.OnClickListener {
            /* textView su dung ?. => cho phep nhan gia tri null =>  khong crash khi textView =null
                neu su dung !! =>  khong cho phep nhan gia tri null => crash khi textView= null */
            // demo if-else tra ve ket qua
            var a:Int =1
            var b: Int = 2
            var max = if (a>b) a else b
            Log.d("tag", max.toString()) // max =2
            //end

            var point: Int?= null
            if(!editText.text.isEmpty()){
                point= editText.text.toString().toInt()
            }else{
                textView?.text = "Insert Point"
            }

            // when thay the cho switch case, qua moi case khong can goi break
            when(point){
                in 1..4 -> textView?.text = "Fail" // tuong duong (point >=1 && point <=4)
                5,6 -> textView?.text = "Ok"
                7,8 -> textView?.text = "Good"
                9,10 -> textView?.text = "Exellent"
                else -> textView?.text = "Invalid Point"

            }
        })
    }
}
