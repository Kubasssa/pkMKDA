package com.example.kuba.databasetest

import android.os.Bundle
import android.app.Activity
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Button

class addFood : Activity() {

    lateinit var AddButton: Button
    lateinit var myDb: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        AddButton=findViewById(R.id.addButton)

        myDb = DatabaseHelper(this)


         AddData()
        viewAllData()
    }

    fun AddData(){
        //myDb.insertFoodData("apple", "fruit",50)

    }

    fun viewAllData(){


        AddButton.setOnClickListener(View.OnClickListener{
            val res = myDb.getAllFoodData()

            if(res.getCount()==0){

                return@OnClickListener
            }else{
                val buffer = StringBuffer()
                while (res.moveToNext()){
                    buffer.append("id:"+res.getString(0)+"\n")
                    buffer.append("Food Name:"+res.getString(1)+"\n")
                    buffer.append("Type:"+res.getString(2)+"\n")
                    buffer.append("Calories:"+res.getString(3)+"\n\n")
                }

                showMessage("Data", buffer.toString())
                res.close()
            }
        })
    }

    private fun showMessage(title: String, message: String?) {

        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

}
