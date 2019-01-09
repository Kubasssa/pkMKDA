package com.example.kuba.databasetest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {



        val DATABASE_NAME = "foodEmperor"
        val DATABASE_VERSION = 1

        val TABLE_NAME = "user"
        val FOOD_TAB = "Products"
        val EATEN_TAB = "EatenProducts"

        val COL_1 = "userId"
        val COL_2 = "login"
        val COL_3 = "password"
        val COL_4 = "sex"

        val FOOD_ID = "ProductsId"
        val FOOD_NAME = "name"
        val FOOD_TYPE = "type"
        val FOOD_CALORIES = "calories"


    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(ID INTEGER PRIMARY KEY AUTOINCREMENT , login STRING, password TEXT, sex TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS $FOOD_TAB(foodId INTEGER PRIMARY KEY AUTOINCREMENT , foodName TEXT NOT NULL, foodPortion TEXT NOT NULL, foodCalories DOUBLE NOT NULL, foodCrabs DOUBLE NOT NULL, foodFat DOUBLE NOT NULL, foodProteins DOUBLE NOT NULL) ")
        db.execSQL("CREATE TABLE IF NOT EXISTS $EATEN_TAB(foodId INTEGER PRIMARY KEY AUTOINCREMENT , foodName TEXT NOT NULL, foodPortion TEXT NOT NULL, foodCalories DOUBLE NOT NULL, foodCrabs DOUBLE NOT NULL, foodFat DOUBLE NOT NULL, foodProteins DOUBLE NOT NULL) ")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $FOOD_TAB")
        onCreate(db)
    }

    fun insertUserData(login: String, password: String, sex: String): Boolean? {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_2, login)
        cv.put(COL_3, password)
        cv.put(COL_4, sex)
        val res = db.insert(TABLE_NAME, null, cv)
        db.close()
        return !res.equals(-1)
    }

    fun insertFoodData(name: String, type: String, calories: Int): Boolean? {

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(FOOD_NAME, name)
        cv.put(FOOD_TYPE, type)
        cv.put(FOOD_CALORIES, calories)

        val res = db!!.insert(FOOD_TAB, null, cv)
        db.close()

        return !res.equals(-1)
    }

    fun getAllData(): Cursor {

        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun showShitInDatabase()
    {
        val db = this.readableDatabase
        var e = ArrayList<String>()
        val d = db.rawQuery("SELECT * FROM $EATEN_TAB", null)
        while(d.moveToNext()) {
            e.add(d.getString(0))
            e.add(d.getString(1))
        }
        println(e)
    }

    fun getAllFoodData(): Cursor {

        val db = this.readableDatabase
        val check = db.rawQuery("SELECT COUNT(*) FROM $FOOD_TAB", null)
        check.moveToFirst();
        var icount = check.getInt(0);
        if(icount == 0)
        {
            populateDatabase()
            //populateEatenbase()
        }
        return db!!.rawQuery("SELECT foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins FROM $FOOD_TAB", null)
    }

    fun getAllEatenData(): Cursor {

        val db = this.readableDatabase
        return db!!.rawQuery("SELECT foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins FROM $EATEN_TAB", null)
    }


    fun updateData(id: String, login: String, password: String, sex: String): Boolean? {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_1, id)
        cv.put(COL_2, login)
        cv.put(COL_3, password)
        cv.put(COL_4, sex)

        db.update(TABLE_NAME, cv, "userId=?", arrayOf(id))
        return true
    }

    fun deleteData(id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "ID=?", arrayOf(id))
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");

    }

    fun deleteData(id: Int) {
        val db = this.writableDatabase
        val check = db.rawQuery("SELECT MAX(foodId) FROM $EATEN_TAB", null)
        var e = ArrayList<Int>()
        while(check.moveToNext()) {e.add(check.getInt(0))}
        db.execSQL("DELETE FROM $EATEN_TAB WHERE foodId='"+id+"' ")
        println("(Database)Position deleted: " +id)
        db.execSQL("UPDATE $EATEN_TAB SET foodId = '"+id+"' WHERE foodId = '"+e.get(0)+"' ")

    }

    fun populateDatabase()
    {
        val db = this.writableDatabase
//        val cv = ContentValues()
//        cv.put("foodName", "Agrest")
//        cv.put("foodPortion", "100g")
//        cv.put("foodCalories", 41.0)
//        cv.put("foodCrabs", 0.2)
//        cv.put("foodFat", 11.8)
//        cv.put("foodProteins", 0.8)
//        var res = db!!.insert(FOOD_TAB, null, cv)
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Agrst','100g', 41.0 ,0.2 , 11.8, 0.8)")
         }

    fun eatProduct(item: Item)
    {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO $EATEN_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('"+item.text1+"','"+item.text2+"','"+item.calories+"','"+item.carbs+"','"+item.fat+"','"+item.proteins+"')")
    }

    fun populateEatenbase()
    {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO $EATEN_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Banana1','100g', 41.0 ,0.2 , 11.8, 0.8)")
        db.execSQL("INSERT INTO $EATEN_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Banana22','100g', 41.0 ,0.2 , 11.8, 0.8)")
        db.execSQL("INSERT INTO $EATEN_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Banana333','100g', 41.0 ,0.2 , 11.8, 0.8)")
        db.execSQL("INSERT INTO $EATEN_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Banana4444','100g', 41.0 ,0.2 , 11.8, 0.8)")
        db.execSQL("INSERT INTO $EATEN_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Banana55555','100g', 41.0 ,0.2 , 11.8, 0.8)")

    }
}