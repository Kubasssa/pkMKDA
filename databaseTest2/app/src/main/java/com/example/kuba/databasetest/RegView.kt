package com.example.kuba.databasetest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast



class RegView : AppCompatActivity() {

    lateinit var Regist: Button
    lateinit var ViewAllButton: Button
    lateinit var DeleteButton: Button
    lateinit var PasswordReg: EditText
    lateinit var LoginReg: EditText
    lateinit var SexText: EditText
    lateinit var myDb: DatabaseHelper
    var howManyDatas :Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)

        Regist=findViewById(R.id.regist)
        ViewAllButton=findViewById(R.id.viewAllButton)
        DeleteButton=findViewById(R.id.deleteButton)
        PasswordReg=findViewById(R.id.passwordReg)
        LoginReg=findViewById(R.id.loginReg)
        SexText=findViewById(R.id.sexText)

        myDb = DatabaseHelper(this)


        AddData()
      //  deleteData()
        viewAllData()
    }

    fun AddData(){

        Regist.setOnClickListener(View.OnClickListener{
            val login = LoginReg.text.toString().trim()
            val password = PasswordReg.text.toString().trim()
            val sex = SexText.text.toString().trim()


            if(TextUtils.isEmpty(login)){
                LoginReg.error = "Enter Login"
                return@OnClickListener
            }

            if(TextUtils.isEmpty(password)){
                PasswordReg.error = "Enter Password"
                return@OnClickListener
            }

            if(TextUtils.isEmpty(sex)){
                SexText.error = "Enter Sex"
                return@OnClickListener
            }

            val isInserted = myDb.insertUserData(login, password,sex)

            if(isInserted==true){
                Toast.makeText(applicationContext,"Data Inserted",Toast.LENGTH_SHORT).show()
                val Intent = Intent(this,LoginActivity::class.java)
                startActivity(Intent);
            }else{
                Toast.makeText(applicationContext,"Data Could not be insereted",Toast.LENGTH_SHORT).show()
            }
            howManyDatas++

        })
    }

    fun viewAllData(){

        ViewAllButton.setOnClickListener(View.OnClickListener{
            val res = myDb.getAllData()

            if(res.getCount()==0){

                return@OnClickListener
            }else{
                val buffer = StringBuffer()
                while (res.moveToNext()){
                    buffer.append("id:"+res.getString(0)+"\n")
                    buffer.append("login:"+res.getString(1)+"\n")
                    buffer.append("paasword:"+res.getString(2)+"\n")
                    buffer.append("sex:"+res.getString(3)+"\n\n")
                }

                showMessage("Data", buffer.toString())
                res.close()
            }
        })
    }

//    fun deleteData() {
//        DeleteButton.setOnClickListener (View.OnClickListener{
//            val res = myDb.getAllData()
//            val id = res.getCount().toString().trim()
//
//            myDb.deleteData(id)
//
//            if (res.getCount()> 0 ) {
//                Toast.makeText(applicationContext, "Data deleted ", Toast.LENGTH_SHORT).show()
//
//            } else {
//                Toast.makeText(applicationContext, "Data could not deleted ", Toast.LENGTH_SHORT).show()
//                return@OnClickListener
//
//            }
//
//        })
//
//    }

//    fun updateData() {
//
//        mUpdatedBtn.setOnClickListener(View.OnClickListener {
//            val id = mId.getText().toString().trim()
//            val name = LoginText.text.toString().trim()
//            val profession = PasswordText.text.toString().trim()
//            val salary = SexText.text.toString().trim()
//
//
//
//            if (TextUtils.isEmpty(name)) {
//                LoginText.error = "Enter login"
//                return@OnClickListener
//            }
//
//            if (TextUtils.isEmpty(profession)) {
//               PasswordText.error = "Enter profession"
//                return@OnClickListener
//            }
//            if (TextUtils.isEmpty(salary)) {
//                SexText.error = "Enter sex"
//                return@OnClickListener
//            }
//
//            val isUpdated = myDb.updateData(id, name, profession, salary)
//            if (isUpdated == true) {
//                Toast.makeText(applicationContext, "Data updated ", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(applicationContext, "Data could not be updated ", Toast.LENGTH_SHORT).show()
//
//            }
//        })
//
//
//    }

    private fun showMessage(title: String, message: String?) {

        val builder = AlertDialog.Builder(this)
        builder.create()
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }
}
