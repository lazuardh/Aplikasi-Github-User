package com.bangkit.aplikasigithubuser.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.aplikasigithubuser.data.entity.UserFavorite

@Dao
interface FavoriteUserDao {

   //insert
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   fun addToFavorite(userFavorite: com.bangkit.aplikasigithubuser.data.entity.UserFavorite)

   //get list user from database
   @Query("SELECT * FROM user_favorite")
   fun getAllFavorite(): LiveData<List<UserFavorite>>

   //get user by id
   @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
   fun findById(id: Int) : Int

   //delete user by id from database
   @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
   fun delete(id : Int) : Int
}