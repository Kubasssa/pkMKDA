package com.example.kuba.databasetest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME, null, DATABASE_VERSION){



    companion object {

        val DATABASE_NAME = "Student"
        val DATABASE_VERSION = 1

        val TABLE_NAME = "student_table"
        val FOOD_TAB = "food_table"

        val COL_1 = "ID"
        val COL_2 = "login"
        val COL_3 = "password"
        val COL_4 = "sex"

        val FOOD_ID = "foodId"
        val FOOD_NAME = "foodName"
        val FOOD_TYPE = "foodType"
        val FOOD_CALORIES = "foodCalories"


    }

    override fun onCreate(db: SQLiteDatabase?) {

            db!!.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(ID INTEGER PRIMARY KEY AUTOINCREMENT , login TEXT, password TEXT, sex TEXT)")
            db.execSQL("CREATE TABLE IF NOT EXISTS $FOOD_TAB(foodId INTEGER PRIMARY KEY AUTOINCREMENT , foodName TEXT NOT NULL, foodType TEXT NOT NULL, foodCalories INTEGER NOT NULL) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $FOOD_TAB")
        onCreate(db)
    }

    fun insertUserData (login: String,password: String, sex: String): Boolean? {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_2,login)
        cv.put(COL_3,password)
        cv.put(COL_4,sex)

        val res = db.insert(TABLE_NAME,null,cv)
        db.close()

        return !res.equals(-1)
    }

    fun insertFoodData (name: String,type: String, calories: Int): Boolean? {

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(FOOD_NAME,name)
        cv.put(FOOD_TYPE,type)
        cv.put(FOOD_CALORIES,calories)

        val res = db!!.insert(FOOD_TAB,null,cv)
        db.close()

        return !res.equals(-1)
    }

    fun getAllData(): Cursor {

        val db =this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME",null)
    }


    fun getAllFoodData(): Cursor {

        val db = this.writableDatabase
        return db!!.rawQuery("SELECT * FROM $FOOD_TAB",null)
    }


    fun updateData (id:String, login: String,password: String, sex: String): Boolean? {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1,id)
        cv.put(COL_2,login)
        cv.put(COL_3,password)
        cv.put(COL_4,sex)

        db.update(TABLE_NAME,cv,"ID=?", arrayOf(id))
        return true
    }

    fun deleteData(id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "ID=?", arrayOf(id))
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");

    }
}
