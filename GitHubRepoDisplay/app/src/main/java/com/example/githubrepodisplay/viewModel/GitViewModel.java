package com.example.githubrepodisplay.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.githubrepodisplay.service.apiRepository.AppExecutor;
import com.example.githubrepodisplay.service.dataRepository.UserDBClient;
import com.example.githubrepodisplay.service.dataRepository.UserDBDatabase;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.service.model.UserDB;
import com.example.githubrepodisplay.view.ui.MainActivity;

import java.util.List;


public class GitViewModel extends AndroidViewModel {

    private final LiveData<List<Items>> listLiveData;

    private UserDBDatabase database;

    public GitViewModel(@NonNull Application application) {
        super(application);
     database = UserDBClient.getInstance(application).getUserDBDatabase();
     listLiveData = database.userDBDao().getAll();

    }

    public LiveData<List<Items>> getList(){
        return  listLiveData;
    }

}
