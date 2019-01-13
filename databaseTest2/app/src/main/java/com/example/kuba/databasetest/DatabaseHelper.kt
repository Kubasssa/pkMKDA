package com.example.kuba.databasetest

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object
    {
        const val DATABASE_NAME = "foodEmperor"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "user"
        const val FOOD_TAB = "Products"
        const val EATEN_TAB = "EatenProducts"
        const val CALORIES_TAB = "Calories"

        const val COL_1 = "userId"
        const val COL_2 = "login"
        const val COL_3 = "password"
        const val COL_4 = "sex"

        const val COL_5 = "amountOfCaloriesToEat"
        const val COL_6 = "CaloriesAlreadyEaten"
    }

    override fun onCreate(db: SQLiteDatabase?)
    {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(ID INTEGER PRIMARY KEY AUTOINCREMENT , login TEXT, password TEXT, sex TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS $FOOD_TAB(foodId INTEGER PRIMARY KEY AUTOINCREMENT , foodName TEXT NOT NULL, foodPortion TEXT NOT NULL, foodCalories DOUBLE NOT NULL, foodCrabs DOUBLE NOT NULL, foodFat DOUBLE NOT NULL, foodProteins DOUBLE NOT NULL) ")
        db.execSQL("CREATE TABLE IF NOT EXISTS $EATEN_TAB(foodId INTEGER PRIMARY KEY, foodName TEXT NOT NULL, foodPortion TEXT NOT NULL, foodCalories int NOT NULL, foodCrabs DOUBLE NOT NULL, foodFat DOUBLE NOT NULL, foodProteins DOUBLE NOT NULL) ")
        db.execSQL("CREATE TABLE IF NOT EXISTS $CALORIES_TAB(caloriesId INTEGER PRIMARY KEY AUTOINCREMENT, amountOfCaloriesToEat int, CaloriesAlreadyEaten int) ")
        db.execSQL("INSERT INTO $TABLE_NAME (login, password, sex) VALUES ('a','a','kobieta')")
        db.execSQL("INSERT INTO $CALORIES_TAB (amountOfCaloriesToEat) VALUES (2150)")
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
        //db.close()
        return !res.equals(-1)
    }

    fun getAllData(): Cursor {

        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun showShitInDatabase()
    { /***** prints foodId and foodName from food_eaten table - used in debugging*****/
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
        var icount = getTopId()
        if(icount == 0)
        {
            populateDatabase()
        }
        return db!!.rawQuery("SELECT foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins FROM $FOOD_TAB", null)
    }

    fun getAllEatenData(): Cursor {

        val db = this.readableDatabase
        return db!!.rawQuery("SELECT foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins FROM $EATEN_TAB", null)
    }


    fun deleteData(id: Int) {
        val db = this.writableDatabase

        /**** get top foodId****/
        val topIndex = getTopId()
        println("Top foodId index: "+topIndex)

        /**** delete item ****/
        db.execSQL("DELETE FROM $EATEN_TAB WHERE foodId='"+id+"' ")
        println("(Database)Position deleted: " +id)

        /**** updating primary key in database ****/
        for(i in (id+1)..topIndex) { db.execSQL("UPDATE $EATEN_TAB SET foodId = '" + (i - 1) + "' WHERE foodId = '" + i + "' ") }

    }

    fun populateDatabase()
    {
        val db = this.writableDatabase
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Grapes','100g', 41.0 ,0.2 , 11.8, 0.8)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('White Bread','100g', 238.0 ,50.0 , 1.2, 6.15)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Spaghetti','300g', 450.0 ,54.0 , 5.11, 7.12)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Pork','200g', 271.0 ,0.0 , 17.04, 27.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Orange','50g', 47.0 ,12.0 , 0.1, 0.9)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Sunflower Seeds','25g', 141.0 ,5.0 , 12.5, 6.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Lamb','100g', 294.0 ,0.0 , 21.0, 25.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('BigMac','200g', 514.0 ,40.4 , 30.2, 24.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Potatoes','100g', 76.0 ,17.0 , 0.1, 2.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Broccoli','100g', 33.0 ,7.0 , 0.4, 2.8)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Fries','100g', 311.0 , 41.0 , 15.5, 3.4)")
    }


    fun eatProduct(item: Item)
    {
        val db = this.writableDatabase
        val index = getTopId()
        db.execSQL("INSERT INTO $EATEN_TAB (foodId, foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('"+(index+1)+"','"+item.text1+"','"+item.text2+"','"+item.calories+"','"+item.carbs+"','"+item.fat+"','"+item.proteins+"')")
    }

    fun getTopId(): Int {
        val db = this.readableDatabase
        /**** get top foodId****/
        val check = db.rawQuery("SELECT MAX(foodId) FROM $EATEN_TAB", null)
        var e = ArrayList<Int>()
        while(check.moveToNext()) {e.add(check.getInt(0))}
        if (e.get(0) == 0 || e.get(0) == null)
            return 0
        else
            return e.get(0)
    }

    fun addTotalCaloriesToEat( cal: Int): Int {
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COL_5, cal)

        val res = db.insert(CALORIES_TAB, null, cv)
        db.close()
        return res.toInt()

    }

    fun getSumOfCalories():Int{
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT amountOfCaloriesToEat FROM $CALORIES_TAB",null)

        var e = ArrayList<Int>()
        while(tmp.moveToNext()) {e.add(tmp.getInt(0))}
        if (e.get(0) == 0 || e.get(0) == null)
            return 0
        else
            return e.get(0)
    }

    fun getAlreadyEatenCalories():Int
        {
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT CaloriesAlreadyEaten FROM $CALORIES_TAB",null)

        var e = ArrayList<Int>()
        while(tmp.moveToNext())
        {
            e.add(tmp.getInt(0))
        }

            if(e.size>0)
            {
                for (i in 0..e.size-1)
                {
                    println(e.get(i))
                }
            }

        if (e.get(0) == 0 || e.get(0) == null)
            return 0
        else
            return e.get(0)
    }

    fun addAlreadyEatenCalories(cal: Int): Int{
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(COL_6, cal)

        val res = db.insert(CALORIES_TAB, null, cv)
        return res.toInt()
    }

    fun getUserId(loginTmp: String): Int{
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT ID FROM $TABLE_NAME Where login= '"+loginTmp+"'",null)

        val x = tmp.getInt(0)
        return x
    }

}


/***** unused methods *****/


//
//fun updateData(id: String, login: String, password: String, sex: String): Boolean? {
//    val db = this.writableDatabase
//    val cv = ContentValues()
//    cv.put(COL_1, id)
//    cv.put(COL_2, login)
//    cv.put(COL_3, password)
//    cv.put(COL_4, sex)
//
//    db.update(TABLE_NAME, cv, "userId=?", arrayOf(id))
//    return true
//}
//
//fun deleteData(id: String) {
//    val db = this.writableDatabase
//    db.delete(TABLE_NAME, "ID=?", arrayOf(id))
//    db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
//
//}
//    fun insertFoodData(name: String, type: String, calories: Int): Boolean? {
//
//        val db = this.writableDatabase
//        val cv = ContentValues()
//        cv.put(FOOD_NAME, name)
//        cv.put(FOOD_TYPE, type)
//        cv.put(FOOD_CALORIES, calories)
//
//        val res = db!!.insert(FOOD_TAB, null, cv)
//        db.close()
//
//        return !res.equals(-1)
//    }