package com.example.githubrepodisplay.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.githubrepodisplay.service.dataRepository.UserDBClient;
import com.example.githubrepodisplay.service.dataRepository.UserDBDatabase;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.viewModel.local.LocalRepository;
import com.example.githubrepodisplay.viewModel.remote.RemoteRespository;

import java.util.List;


public class GitViewModel extends AndroidViewModel implements RemoteRespository.MyCall {

    private final LiveData<List<Items>> listLiveData;
    private UserDBDatabase database;
    Application application;

    public GitViewModel(@NonNull Application application) {
        super(application);
     database = UserDBClient.getInstance(application).getUserDBDatabase();
     listLiveData = database.userDBDao().getAll();
     this.application = application;

    }

    public LiveData<List<Items>> getList(){
        return  listLiveData;
    }

    public void fetchUserList(Context context, String s){

         new RemoteRespository(context).getUsersList(s,this);
    }


    public void insert(Context context, List<Items> list){
        new LocalRepository(context).insertData(list);
    }

    @Override
    public void callBack(List<Items> uList) {
        new LocalRepository(application).insertData(uList);
    }
}
