package com.example.githubrepodisplay.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.githubrepodisplay.service.dataRepository.UserDBClient;
import com.example.githubrepodisplay.service.dataRepository.UserDBDatabase;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.view.ui.MainActivity;
import com.example.githubrepodisplay.viewModel.local.LocalRepository;
import com.example.githubrepodisplay.viewModel.remote.RemoteRespository;

import java.util.List;


public class GitViewModel extends AndroidViewModel implements
        RemoteRespository.MyCall,
        LocalRepository.LocalRepoCallBack,
        LocalRepository.GetDataCallBack<List<Items>> {

    private MutableLiveData<List<Items>> mutableLiveData;

    public GitViewModel(@NonNull Application application) {
        super(application);
    this.mutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Items>> getList(){
        return  mutableLiveData;
    }

    public void fetchUserList(String s){
         new RemoteRespository(getApplication()).getUsersList(s,this);
    }

    @Override
    public void callBack(List<Items> uList) {
        Log.d("list", "callBack: "+(uList == null ? 0 : uList.size()));
        new LocalRepository(getApplication()).insertData(uList,this);
    }

    @Override
    public void isSuccess(boolean success) {
        new LocalRepository(getApplication()).getData(this);
    }


    @Override
    public void getAll(List<Items> data) {
        Log.d("list", "getAll: "+(data == null ? 0 : data.size()));
        mutableLiveData.postValue(data);
    }
}
