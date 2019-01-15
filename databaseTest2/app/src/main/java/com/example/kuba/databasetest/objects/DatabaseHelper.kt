package com.example.kuba.databasetest.objects

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object
    {
        /***** DATABASE *****/
        const val DATABASE_NAME = "foodEmperor"
        const val DATABASE_VERSION = 1

        /** 'user' table**/
        const val TABLE_NAME = "user"
        const val COL_1 = "userId"
        const val COL_2 = "login"
        const val COL_3 = "password"
        const val COL_4 = "sex"

        /** 'Products' table**/
        const val FOOD_TAB = "Products"

        /** 'EatenProducts' table**/
        const val EATEN_TAB = "EatenProducts"

        /** 'Profile settings (sex, height, weight) table**/
        const val PROFILE_TAB = "ProfileSettings"
        const val COL_7 = "sex"
        const val COL_8 = "height"
        const val COL_9 = "weight"
        const val COL_10 = "age"

        /** 'Calories' table**/
        const val CALORIES_TAB = "Calories"
        const val COL_5 = "amountOfCaloriesToEat"
        const val COL_6 = "caloriesAlreadyEaten"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "login TEXT, " +
                "password TEXT, " +
                "sex TEXT)")

        db.execSQL("CREATE TABLE IF NOT EXISTS $FOOD_TAB(" +
                "foodId INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "foodName TEXT NOT NULL, " +
                "foodPortion TEXT NOT NULL, " +
                "foodCalories DOUBLE NOT NULL, " +
                "foodCrabs DOUBLE NOT NULL, " +
                "foodFat DOUBLE NOT NULL, " +
                "foodProteins DOUBLE NOT NULL) ")
        populateDatabase(db)

        db.execSQL("CREATE TABLE IF NOT EXISTS $EATEN_TAB(" +
                "foodId INTEGER PRIMARY KEY, " +
                "foodName TEXT NOT NULL, " +
                "foodPortion TEXT NOT NULL, " +
                "foodCalories INTEGER NOT NULL, " +
                "foodCrabs DOUBLE NOT NULL, " +
                "foodFat DOUBLE NOT NULL, " +
                "foodProteins DOUBLE NOT NULL) ")

        db.execSQL("CREATE TABLE IF NOT EXISTS $PROFILE_TAB(" +
                "profile INTEGER PRIMARY KEY, " +
                "sex TEXT NOT NULL, " +
                "height INTEGER NOT NULL, " +
                "weight INTEGER NOT NULL, " +
                "age INTEGER NOT NULL) " )


        db.execSQL("CREATE TABLE IF NOT EXISTS $CALORIES_TAB(" +
                "caloriesId INTEGER PRIMARY KEY, " +
                "amountOfCaloriesToEat INTEGER, " +
                "amountOfCarbsToEat DOUBLE, " +
                "amountOfFatToEat DOUBLE, " +
                "amountOfProteinsToEat DOUBLE, " +
                "caloriesAlreadyEaten INTEGER, " +
                "carbsAlreadyEaten DOUBLE, " +
                "fatAlreadyEaten DOUBLE, " +
                "proteinsAlreadyEaten DOUBLE) ")


        /***** test user *****/
        /*
        db.execSQL("INSERT INTO $TABLE_NAME (login, password, sex) VALUES ('a','a','Female')")
        db.execSQL("INSERT INTO $PROFILE_TAB (sex, height, age) VALUES ('Female','160','25')")

        db.execSQL("INSERT INTO $CALORIES_TAB (" +
                "caloriesId, " +
                "amountOfCaloriesToEat, " +
                "amountOfCarbsToEat, " +
                "amountOfFatToEat, " +
                "amountOfProteinsToEat, " +
                "caloriesAlreadyEaten, " +
                "carbsAlreadyEaten, " +
                "fatAlreadyEaten, " +
                "proteinsAlreadyEaten) " +
                "VALUES (1, 2150, 358.4, 47.8, 161.3, 0, 0.0, 0.0, 0.0)")
         */

        //TODO: IMPLEMENT \/
        /** Carbs       =50%;   1g = 3kcal
         *  Fat         =20%;   1g = 9kcal
         *  Proteins    =30%;   1g = 4kcal **/


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $FOOD_TAB")
        onCreate(db)
    }

    private fun populateDatabase(db: SQLiteDatabase?) {
        //val db = this.writableDatabase
        println("CALLED populateDatabase() METHOD") //for debugging
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Grapes','100g', 41.0, 11.8, 0.2 , 0.8)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('White Bread','100g', 238.0 ,50.0 , 1.2, 6.15)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Spaghetti','300g', 450.0 ,54.0 , 5.11, 7.12)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Pork','100g', 135.0 ,0.0 , 8.52, 13.5)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Orange','100g', 94.0 ,24.0 , 0.2, 1.8)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Sunflower Seeds','100g', 564.0 ,20.0 , 50.0, 24.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Lamb','100g', 294.0 ,0.0 , 21.0, 25.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('BigMac','100g', 257.0 ,20.2 , 15.1, 12.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Potatoes','100g', 76.0 ,17.0 , 0.1, 2.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Broccoli','100g', 33.0 ,7.0 , 0.4, 2.8)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Fries','100g', 311.0 , 41.0 , 15.5, 3.4)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Apple','100g', 52.0 ,13.81 , 0.17, 0.88)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Beef','100g', 228.0 ,0.0 , 17.07, 17.37)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Avocado','100g', 160.0 ,8.53 , 14.66, 2)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Cucumber','100g', 12.0 ,2.16 , 0.16, 0.59)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Lettuce','100g', 16.0 ,17.4 , 0.1, 2.1)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Dark chocolate','100g', 600.0 ,45.2 , 42.68, 6.98)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Milk chocolate','100g', 534.0 ,58.8 , 29.72, 7.66)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('White chocolate','100g', 536.0 ,59.8 , 32.2, 5.36)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Strawberry','100g', 32.0 ,8.0 , 0.3, 0.7)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Almond','100g', 579.0 ,21.55 ,49.93 ,21.15 )")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Kaki','100g', 70.0 , 18.59 , 0.19, 0.58)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Artichoke','100g', 47.0 , 10.51 , 0.15, 3.27)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Buckwheat groats','100g', 346.0, 74.95 , 2.71, 11.73)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Beer','100g', 43.0 ,3.6 , 0.0, 0.5)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Wine','100g', 82.0 ,2.7 , 0.0, 0.1)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Whisky','100g', 250.0 ,0.1 , 0.0, 0.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Vodka','100g', 231.0 ,0.0 , 0.0, 0.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Coca-Cola','100g', 37.0 ,10.0 , 0.0, 0.1)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Juice','100g', 54.0 ,13.0 , 0.0, 0.2)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Water','100g', 0.0 ,0.0 , 0.0, 0.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Chocolate Bar','100g', 555.0 ,60.0 , 32.0, 6.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Lettuce','100g', 14.0 ,2.9 , 0.2, 1.4)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Donut','100g', 452.0 ,51.0 , 25.0, 4.9)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Cottage cheese','100g', 98.0 ,3.6 , 4.3, 11.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Cheese','100g', 402.0 ,1.3 , 33.0, 25.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Ham','100g', 145.0 ,1.5 , 6.0, 21.0)")
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('Tomato','100g', 20.0 ,3.0 , 0.2, 1.0)")


    }

    //TODO add fun addNewProduct(item: Item)
    fun addNewProduct(item: Item)
    {
        val db = this.writableDatabase
        db!!.execSQL("INSERT INTO $FOOD_TAB (foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('"+item.text1+"','"+item.text2+"','"+item.calories+"','"+item.carbs+"','"+item.fat+"','"+item.proteins+"')")
    }

    private fun getLastEatenProductId(): Int {
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

    fun insertUserData(login: String, password: String, sex: String): Boolean? {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_2, login)
        cv.put(COL_3, password)
        cv.put(COL_4, sex)
        val res = db.insert(TABLE_NAME, null, cv)
        return !res.equals(-1)
    }

    fun getAllUsersData(): Cursor {

        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun getAllFoodData(): Cursor {

        val db = this.readableDatabase
        return db!!.rawQuery("SELECT foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins FROM $FOOD_TAB", null)
    }

    fun getAllEatenData(): Cursor {

        val db = this.readableDatabase
        return db!!.rawQuery("SELECT foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins FROM $EATEN_TAB", null)
    }

    fun removeProductFromEaten(id: Int) {
        val db = this.writableDatabase

        /**** get top foodId****/
        val topIndex = getLastEatenProductId()
        println("Top foodId index: "+topIndex)

        /**** delete item ****/
        db.execSQL("DELETE FROM $EATEN_TAB WHERE foodId='"+id+"' ")
        println("(Database)Position deleted: " +id)

        /**** updating primary key in database ****/
        for(i in (id+1)..topIndex) { db.execSQL("UPDATE $EATEN_TAB SET foodId = '" + (i - 1) + "' WHERE foodId = '" + i + "' ") }
    }

    fun eatProduct(item: Item) {
        val db = this.writableDatabase
        val index = getLastEatenProductId()
        db.execSQL("INSERT INTO $EATEN_TAB (foodId, foodName, foodPortion, foodCalories, foodCrabs, foodFat, foodProteins) VALUES ('"+(index+1)+"','"+item.text1+"','"+item.text2+"','"+item.calories+"','"+item.carbs+"','"+item.fat+"','"+item.proteins+"')")
    }

    fun insertProfileSettings(sex: String, height: Int, weight: Int,age: Int): Boolean? {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_7, sex)
        cv.put(COL_8, height)
        cv.put(COL_9, weight)
        cv.put(COL_10, age)
        val res = db.insert(PROFILE_TAB, null, cv)
        return !res.equals(-1)
    }

    fun getProfileSettings(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $PROFILE_TAB", null)

    }
    //TODO: fun for Goal variables ?
    fun setUserNutritionGoal(cal: Int): Int {
        val db = this.writableDatabase
        var caloriesGoal = cal
        var carbsGoal = ((cal*0.5)/3) //carb in grams
        var fatGoal = ((cal*0.2)/9) //fat in grams
        var proteinsGoal = ((cal*0.3)/4) //proteins in grams
        val cv = ContentValues()

        cv.put("caloriesId", 1)
        cv.put(COL_5, caloriesGoal)
        cv.put("amountOfCarbsToEat", carbsGoal)
        cv.put("amountOfFatToEat",fatGoal)
        cv.put("amountOfProteinsToEat", proteinsGoal)
        cv.put("caloriesAlreadyEaten", 0)
        cv.put("carbsAlreadyEaten", 0.0)
        cv.put("fatAlreadyEaten", 0.0)
        cv.put("proteinsAlreadyEaten", 0.0)
        val res = db.insert(CALORIES_TAB, null, cv)
        db.close()
        return res.toInt()
    }

    fun updateTotalCalories( cal: Int){
        val db = this.writableDatabase
        var caloriesGoal = cal
        var carbsGoal = ((cal*0.5)/3) //carb in grams
        var fatGoal = ((cal*0.2)/9) //fat in grams
        var proteinsGoal = ((cal*0.3)/4) //proteins in grams
        db.execSQL("UPDATE $CALORIES_TAB SET " +
                "amountOfCaloriesToEat = '" + caloriesGoal + "'," +
                "amountOfCarbsToEat = '" + carbsGoal + "'," +
                "amountOfFatToEat = '" + fatGoal + "'," +
                "amountOfProteinsToEat = '" +proteinsGoal +"'" +
                "WHERE caloriesId = 1")
    }

    fun getCaloriesToEat():Int{
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT amountOfCaloriesToEat FROM $CALORIES_TAB",null)

        var e = ArrayList<Int>()
        while(tmp.moveToNext()) {e.add(tmp.getInt(0))}
        if (e.get(0) == 0 || e.get(0) == null)
            return 0
        else
            return e.get(0)
    }

    fun getCarbsToEat():Double{
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT ROUND(amountOfCarbsToEat,1) FROM $CALORIES_TAB",null)

        var e = ArrayList<Double>()
        while(tmp.moveToNext()) {e.add(tmp.getDouble(0))}
        if (e.get(0) == 0.0 || e.get(0) == null)
            return 0.0
        else
            return e.get(0)
    }

    fun getFatToEat():Double{
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT ROUND(amountOfFatToEat,1) FROM $CALORIES_TAB",null)

        var e = ArrayList<Double>()
        while(tmp.moveToNext()) {e.add(tmp.getDouble(0))}
        if (e.get(0) == 0.0 || e.get(0) == null)
            return 0.0
        else
            return e.get(0)
    }

    fun getProteinsToEat():Double{
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT ROUND(amountOfProteinsToEat,1) FROM $CALORIES_TAB",null)

        var e = ArrayList<Double>()
        while(tmp.moveToNext()) {e.add(tmp.getDouble(0))}
        if (e.get(0) == 0.0 || e.get(0) == null)
            return 0.0
        else
            return e.get(0)
    }

    fun getAlreadyEatenCalories():Int {
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT caloriesAlreadyEaten FROM $CALORIES_TAB",null)

        var e = ArrayList<Int>()
        while(tmp.moveToNext())
        {
            e.add(tmp.getInt(0))
        }

        if (e.get(0) == 0 || e.get(0) == null)
            return 0
        else
            return e.get(0)
    }

    fun getAlreadyEatenCarbs():Double {
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT ROUND(carbsAlreadyEaten,1) FROM $CALORIES_TAB",null)

        var e = ArrayList<Double>()
        while(tmp.moveToNext())
        {
            e.add(tmp.getDouble(0))
        }

        if (e.get(0) == 0.0 || e.get(0) == null)
            return 0.0
        else
            return e.get(0)
    }

    fun getAlreadyEatenFat():Double {
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT ROUND(fatAlreadyEaten,1) FROM $CALORIES_TAB",null)

        var e = ArrayList<Double>()
        while(tmp.moveToNext())
        {
            e.add(tmp.getDouble(0))
        }

        if (e.get(0) == 0.0 || e.get(0) == null)
            return 0.0
        else
            return e.get(0)
    }

    fun getAlreadyEatenProteins():Double {
        val db = this.readableDatabase
        val tmp = db.rawQuery("SELECT ROUND(proteinsAlreadyEaten,1) FROM $CALORIES_TAB",null)

        var e = ArrayList<Double>()
        while(tmp.moveToNext())
        {
            e.add(tmp.getDouble(0))
        }

        if (e.get(0) == 0.0 || e.get(0) == null)
            return 0.0
        else
            return e.get(0)
    }

    fun addAlreadyEatenCalories(cal: Int){
        val db = this.writableDatabase
        var currentValue = getAlreadyEatenCalories() + cal
        db.execSQL("UPDATE $CALORIES_TAB SET caloriesAlreadyEaten = '" + currentValue + "' WHERE caloriesId = 1")
    }

    fun addAlreadyEatenCarbs(cal: Double){
        val db = this.writableDatabase
        var currentValue = getAlreadyEatenCarbs() + cal
        db.execSQL("UPDATE $CALORIES_TAB SET carbsAlreadyEaten = '" + currentValue + "' WHERE caloriesId = 1")
    }

    fun addAlreadyEatenFat(cal: Double){
        val db = this.writableDatabase
        var currentValue = getAlreadyEatenFat() + cal
        db.execSQL("UPDATE $CALORIES_TAB SET fatAlreadyEaten = '" + currentValue + "' WHERE caloriesId = 1")
    }

    fun addAlreadyEatenProteins(cal: Double){
        val db = this.writableDatabase
        var currentValue = getAlreadyEatenProteins() + cal
        db.execSQL("UPDATE $CALORIES_TAB SET proteinsAlreadyEaten = '" + currentValue + "' WHERE caloriesId = 1")
    }

    /*** prints foodId and foodName from food_eaten table - used in debugging***/
    fun showShitInDatabase() {
        val db = this.readableDatabase
        var e = ArrayList<String>()
        val d = db.rawQuery("SELECT * FROM $EATEN_TAB", null)
        while(d.moveToNext()) {
            e.add(d.getString(0))
            e.add(d.getString(1))
        }
        println(e)
    }

    fun resetAll(){
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.execSQL("DELETE FROM $EATEN_TAB")
        db.execSQL("DELETE FROM $CALORIES_TAB")
        db.execSQL("DELETE FROM $PROFILE_TAB")
        db.execSQL("VACUUM")
    }
}


/***** unused methods *****/

//    fun getUserId(loginTmp: String): Int{
//        val db = this.readableDatabase
//        val tmp = db.rawQuery("SELECT ID FROM $TABLE_NAME Where login= '"+loginTmp+"'",null)
//
//        val x = tmp.getInt(0)
//        return x
//    }
//    /***** needed? maybe just use addAlreadyEatenCalories with negative integer value ? yeaaaaa.......****/
//    fun subtractAlreadyEatenCalories(cal: Int){
//        val db = this.writableDatabase
//        var currentCalories = getAlreadyEatenCalories() - cal
//        db.execSQL("UPDATE $CALORIES_TAB SET caloriesAlreadyEaten = '" + currentCalories + "' WHERE caloriesId = 1")
//    }
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
//fun removeProductFromEaten(id: String) {
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