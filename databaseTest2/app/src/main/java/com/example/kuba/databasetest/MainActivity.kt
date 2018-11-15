package com.example.kuba.databasetest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    lateinit var RegistLink: TextView
    lateinit var LoginButton: Button
    lateinit var LoginLog: EditText
    lateinit var PasswordLog: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RegistLink=findViewById(R.id.registLink)
        LoginButton=findViewById(R.id.loginButton)
        LoginLog=findViewById(R.id.loginLog)
        PasswordLog=findViewById(R.id.passwordLog)

        goRegist()
        logIn()
    }

    fun goRegist(){
        RegistLink.setOnClickListener(View.OnClickListener{

            val Intent = Intent(this,RegView::class.java)
            startActivity(Intent);
            return@OnClickListener
        })
    }

    fun logIn (){
        LoginButton.setOnClickListener(View.OnClickListener {
            var userExist: Boolean =false
            val myDb = DatabaseHelper(this)
            val res = myDb.getAllData()

            if(res.getCount()==0){

                return@OnClickListener
            }else{
                while (res.moveToNext()){
                    if(LoginLog.text.toString().trim()==res.getString(1)&& PasswordLog.text.toString().trim()==res.getString(2)){
                        val Intent = Intent(this,addFood::class.java)
                        startActivity(Intent);
                        userExist =true
                    }else{

                    }
                }
                if(!userExist){
                    Toast.makeText(applicationContext,"Wrong login or password", Toast.LENGTH_SHORT).show()
                }


            }
        })
    }
}
