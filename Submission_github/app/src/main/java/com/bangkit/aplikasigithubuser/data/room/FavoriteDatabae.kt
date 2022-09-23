package com.bangkit.aplikasigithubuser.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.aplikasigithubuser.data.entity.UserFavorite

@Database(entities = [UserFavorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase(){
   companion object {
       private var INSTANCE : FavoriteDatabase? = null

       fun getDatabase(context: Context): FavoriteDatabase?{
           if (INSTANCE == null){
               synchronized(FavoriteDatabase::class){
                   INSTANCE = Room.databaseBuilder(context.applicationContext, FavoriteDatabase::class.java, "user_database")
                       .build()
               }
           }
           return INSTANCE
       }
   }
    abstract fun favoriteUserDao() : FavoriteUserDao
}