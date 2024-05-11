package com.example.passwordmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import kotlinx.coroutines.CompletableJob;

@Dao
public interface PasswordDao {
    @Query("SELECT * FROM password")
    List<Password> getAll();

    @Query(value = "SELECT * FROM password WHERE name = :passwordName")
    Password get(String passwordName);

    @Insert
    void insert(Password password);

    @Delete
    void remove(Password password);

    @Query("DELETE FROM password")
    void removeAll();

    @Update
    void Update(Password password);
}
