package com.example.githubrepodisplay.viewModel.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.githubrepodisplay.service.dataRepository.UserDBDao;
import com.example.githubrepodisplay.service.utils.AppExecutor;
import com.example.githubrepodisplay.service.dataRepository.UserDBClient;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.service.model.UserDB;

import java.util.List;

public class LocalRepository {

    private Context context;

    public LocalRepository(Context context){
        this.context = context;
    }

    public void insertData(final List<Items> itemsList, final LocalRepoCallBack callBack){
        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                deleteAllData();

                UserDBDao userDBDao = UserDBClient.getInstance(context).getUserDBDatabase()
                        .userDBDao();
                for (int i=0; i<itemsList.size(); i++) {
                    UserDB userDB = new UserDB();
                    Items item = itemsList.get(i);
                    userDB.setLogin(item.getLogin());
                    userDB.setScore(item.getScore());
                    userDB.setType(item.getType());
                    userDB.setAvatarUrl(item.getAvatarUrl());
                    userDB.setFollowersUrl(item.getFollowersUrl());
                    userDB.setFollowingUrl(item.getFollowingUrl());
                    userDB.setHtmlUrl(item.getHtmlUrl());
                    userDB.setNodeId(item.getNodeId());
                    userDB.setId(item.getId());
                    userDB.setReposUrl(item.getReposUrl());
                    userDB.setStarredUrl(item.getStarredUrl());
                    userDB.setUrl(item.getUrl());

                    userDBDao.insert(userDB);


                }
                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {

                        callBack.isSuccess(true);

                    }
                });
            }
        });

    }

    public void getData(final GetDataCallBack<List<Items>> callBack){

        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Items> liveList = UserDBClient.getInstance(context)
                        .getUserDBDatabase().userDBDao().getAll();

                AppExecutor.getInstance().getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.getAll(liveList);
                    }
                });
            }
        });
    }
    public void deleteAllData(){
        UserDBClient.getInstance(context)
                .getUserDBDatabase().userDBDao().deleteAll();
    }

    public interface LocalRepoCallBack{
        void isSuccess(boolean success);
    }

    public interface GetDataCallBack<T>{
        void getAll(T data);
    }
}
