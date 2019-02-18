package com.example.githubrepodisplay.service.dataRepository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.service.model.UserDB;

import java.util.List;

@Dao
public interface UserDBDao {
    @Insert
    long insert(UserDB userDB);

    @Query("DELETE FROM UserDB")
    void deleteAll();

    @Update
    int update(UserDB userDB);

    @Query("select * from UserDB")
    LiveData<List<Items>> getAll();
}
