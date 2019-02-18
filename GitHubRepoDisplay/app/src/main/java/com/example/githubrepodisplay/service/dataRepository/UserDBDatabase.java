package com.example.githubrepodisplay.service.dataRepository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.githubrepodisplay.service.model.UserDB;

@Database(entities = {UserDB.class},version = 1)
public abstract class UserDBDatabase extends RoomDatabase {
    public abstract UserDBDao userDBDao();
}
