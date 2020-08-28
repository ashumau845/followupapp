package com.example.followupapp.DatabaseHelper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insertAll(User users);

    @Delete
    void delete(User user);

    @Query("SELECT COUNT(*) from user")
    int countUsers();


    @Query("SELECT * FROM user where company_name LIKE  :firstName AND customer_name LIKE :lastName")
    User findByName(String firstName, String lastName);

}
