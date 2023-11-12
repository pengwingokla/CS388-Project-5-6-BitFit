package com.example.cs388_project_6_bitfit

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "foodtracker.db"

        private const val TABLE_FOODTRACKER = "tbl_foodtracker"
        private const val FOOD = "food"
        private const val CALORIES = "calories"

        private const val TABLE_TARGET_CALORIES = "tbl_target_calories"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TARGET_CALORIES = "target_calories"
        private const val DEFAULT_ROW_ID = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableFoodTracker = ("CREATE TABLE " + TABLE_FOODTRACKER + "("
                //+ ID + "INTEGER PRIMARY KEY, "
                + FOOD + " TEXT,"
                + CALORIES + " TEXT"
                + ")")

        val createTableQuery = """
            CREATE TABLE $TABLE_TARGET_CALORIES (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_TARGET_CALORIES INTEGER
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)

        // Insert a default row with the specified ID
        if (db != null) {
            insertDefaultRow(db)
        }

        Log.e("SQLiteHelper ---->","Created table")
        db?.execSQL(createTableFoodTracker)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_FOODTRACKER")
        onCreate(db)
    }

    fun insertData(foodName: String, calories: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(FOOD, foodName)
            put(CALORIES, calories)
        }
        val newRowId = db.insert(TABLE_FOODTRACKER, null, values)
        db.close()
        return newRowId
    }

    @SuppressLint("Range")
    fun getAllFoodEntries(): List<FoodItem> {
        val foodEntries = mutableListOf<FoodItem>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_FOODTRACKER", null)

        while (cursor.moveToNext()) {
            val foodName = cursor.getString(cursor.getColumnIndex(FOOD))
            val calories = cursor.getInt(cursor.getColumnIndex(CALORIES))
            foodEntries.add(FoodItem(foodName, calories))
        }

        cursor.close()
        db.close()

        return foodEntries
    }
    private fun insertDefaultRow(db: SQLiteDatabase) {
        val contentValues = ContentValues().apply {
            put(COLUMN_ID, DEFAULT_ROW_ID)
            put(COLUMN_TARGET_CALORIES, 0) // Set the initial target calories to 0 or any default value
        }

        db.insertWithOnConflict(TABLE_TARGET_CALORIES, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
    }
    @SuppressLint("Range")
    fun getTargetCalories(): Int {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TARGET_CALORIES,
            arrayOf(COLUMN_TARGET_CALORIES),
            "$COLUMN_ID = ?",
            arrayOf(DEFAULT_ROW_ID.toString()),
            null,
            null,
            null
        )

        var targetCalories = 0

        if (cursor.moveToFirst()) {
            targetCalories = cursor.getInt(cursor.getColumnIndex(COLUMN_TARGET_CALORIES))
        }

        cursor.close()
        db.close()

        return targetCalories
    }

    fun updateTargetCalories(targetCalories: Int): Int {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TARGET_CALORIES, targetCalories)
        }

        val rowsAffected = db.update(
            TABLE_TARGET_CALORIES,
            contentValues,
            "$COLUMN_ID = ?",
            arrayOf(DEFAULT_ROW_ID.toString())
        )

        db.close()

        return rowsAffected
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

    fun deleteAllFood(): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_FOODTRACKER, null, null)
    }

//    fun setTargetCalories(targetCalories: Int): Long {
//        val db = this.writableDatabase
//
//        val contentValues = ContentValues()
//        contentValues.put(TARGET_CALORIES, targetCalories)
//
//        // Replace the existing target calories value if it already exists
//        val existingTargetCalories = getTargetCalories()
//        if (existingTargetCalories != null) {
//            val whereClause = "$TARGET_CALORIES = ?"
//            val whereArgs = arrayOf(existingTargetCalories.toString())
//            Log.e("SQLiteHelper ---->","existingTargetCalories != null")
//            db.update(TABLE_TARGET_CALORIES, contentValues, whereClause, whereArgs)
//        } else {
//            Log.e("SQLiteHelper ---->","else")
//            // If the target calories value doesn't exist, insert a new one
//            val success = db.insert(TABLE_TARGET_CALORIES, null, contentValues)
//            db.close()
//            return success
//        }
//
//        db.close()
//        return 0
//    }

//    // Helper method to get the current target calories value
//    @SuppressLint("Range")
//    public fun getTargetCalories(): Int? {
//        val db = this.readableDatabase
//        val selectQuery = "SELECT $TARGET_CALORIES FROM $TABLE_TARGET_CALORIES"
//        val cursor = db.rawQuery(selectQuery, null)
//
//        var targetCalories: Int? = null
//
//        if (cursor.moveToFirst()) {
//            targetCalories = cursor.getInt(cursor.getColumnIndex(TARGET_CALORIES))
//        }
//
//        cursor.close()
//        return targetCalories
//    }

//    // Method to read data
//    @SuppressLint("Range")
//    fun getAllFood(): ArrayList<TrackerModel> {
//        val foodList: ArrayList<TrackerModel> = ArrayList()
//        val selectQuery = "SELECT * FROM $TABLE_FOODTRACKER"
//        val db = this.readableDatabase
//
//        val cursor: Cursor?
//
//        try {
//            cursor = db.rawQuery(selectQuery, null)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            db.execSQL(selectQuery)
//            return ArrayList()
//        }
//
//        var food: String
//        var calories: Int
//
//        if (cursor.moveToFirst()) {
//            do {
//                //id = cursor.getInt(cursor.getColumnIndex("id"))
//                food = cursor.getString(cursor.getColumnIndex("food"))
//                calories = cursor.getInt(cursor.getColumnIndex("calories"))
//
//                //val foodProperty = TrackerModel(id = id, food = food, calories = calories)
//                val foodProperty = TrackerModel(food = food, calories = calories)
//                foodList.add(foodProperty)
//            } while (cursor.moveToNext())
//        }
//
//        return foodList
//    }
//


}