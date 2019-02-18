package com.example.githubrepodisplay.service.dataRepository;

import android.content.Context;

import com.example.githubrepodisplay.service.apiRepository.AppExecutor;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.service.model.UserDB;
import com.example.githubrepodisplay.view.ui.MainActivity;

import java.util.List;

public class Crud {

    private Context context;

    public Crud(Context context){
        this.context = context;
    }
    public void insertData(final List<Items> itemsList){
        AppExecutor.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {

                deleteAllData();

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

                    UserDBClient.getInstance(context).getUserDBDatabase()
                            .userDBDao()
                            .insert(userDB);
                }

            }
        });

    }

    public void deleteAllData(){
        UserDBClient.getInstance(context)
                .getUserDBDatabase().userDBDao().deleteAll();
    }

}
