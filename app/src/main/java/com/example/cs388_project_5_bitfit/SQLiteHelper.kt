package com.example.cs388_project_5_bitfit

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "foodtracker.db"
        private const val TABLE_FOODTRACKER = "tbl_foodtracker"
        //private const val ID =  "id"
        private const val FOOD = "food"
        private const val CALORIES = "calories"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableFoodTracker = ("CREATE TABLE " + TABLE_FOODTRACKER + "("
                //+ ID + "INTEGER PRIMARY KEY, "
                + FOOD + " TEXT,"
                + CALORIES + " TEXT"
                + ")")

        db?.execSQL(createTableFoodTracker)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_FOODTRACKER")
        onCreate(db)
    }

    fun insertFood(track: TrackerModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        //contentValues.put(ID, track.id)
        contentValues.put(FOOD, track.food)
        contentValues.put(CALORIES, track.calories)


        // Insert row and close database connection
        val success = db.insert(TABLE_FOODTRACKER, null, contentValues)
        db.close()
        return success
    }

    // Method to read data
    @SuppressLint("Range")
    fun getAllFood(): ArrayList<TrackerModel> {
        val foodList: ArrayList<TrackerModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_FOODTRACKER"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var food: String
        var calories: Int

        if (cursor.moveToFirst()) {
            do {
                //id = cursor.getInt(cursor.getColumnIndex("id"))
                food = cursor.getString(cursor.getColumnIndex("food"))
                calories = cursor.getInt(cursor.getColumnIndex("calories"))

                //val foodProperty = TrackerModel(id = id, food = food, calories = calories)
                val foodProperty = TrackerModel(food = food, calories = calories)
                foodList.add(foodProperty)
            } while (cursor.moveToNext())
        }

        return foodList
    }

    @SuppressLint("Range")
    fun getTotalCalories(): Int {
        val db = this.readableDatabase
        val selectQuery = "SELECT $CALORIES FROM $TABLE_FOODTRACKER"
        val cursor = db.rawQuery(selectQuery, null)

        var totalCalories = 0

        if (cursor.moveToFirst()) {
            do {
                val calories = cursor.getInt(cursor.getColumnIndex(CALORIES))
                totalCalories += calories
            } while (cursor.moveToNext())
        }

        cursor.close()
        return totalCalories
    }

    @SuppressLint("Range")
    fun getAverageCalories(): Double {
        val db = this.readableDatabase
        val selectQuery = "SELECT AVG($CALORIES) AS averageCalories FROM $TABLE_FOODTRACKER"
        val cursor = db.rawQuery(selectQuery, null)

        var averageCalories = 0.0

        if (cursor.moveToFirst()) {
            averageCalories = cursor.getDouble(cursor.getColumnIndex("averageCalories"))
        }

        cursor.close()
        return averageCalories
    }

    fun deleteAllFood(): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_FOODTRACKER, null, null)
    }

}