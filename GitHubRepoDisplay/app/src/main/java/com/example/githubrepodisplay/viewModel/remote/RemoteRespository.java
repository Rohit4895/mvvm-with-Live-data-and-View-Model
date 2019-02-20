package com.example.githubrepodisplay.viewModel.remote;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.githubrepodisplay.service.apiRepository.Api;
import com.example.githubrepodisplay.service.apiRepository.ApiInterface;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.service.model.UsersList;
import com.example.githubrepodisplay.viewModel.GitViewModel;
import com.example.githubrepodisplay.viewModel.local.LocalRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class RemoteRespository {

    private Context context;

    public RemoteRespository(Context context){
        this.context = context;
    }

    public void getUsersList(String string, final MyCall myCall){


        Api.getClient().getUsersList(string).enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, retrofit2.Response<UsersList> response) {

                if(response.code() != 200){
                    myCall.callBack(null);
                    return;

                }

                UsersList user = response.body();
                if(user == null){
                    myCall.callBack(null);
                    return;
                }

                List<Items> usersLists = user.getItems();
                myCall.callBack(usersLists);
                Log.d("list", "userList: "+(usersLists == null ? 0 : usersLists.size()));


            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                myCall.callBack(null);
            }
        });
    }

    public interface MyCall{
        public void callBack(List<Items> list);
    }
}
