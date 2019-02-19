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

public class RemoteRespository {
    List<Items> usersLists;
    Context context;
    UsersList user;
    LocalRepository localRepository;

    public RemoteRespository(Context context){
        this.context = context;
        localRepository = new LocalRepository(context);
    }

    public void getUsersList(String string, final MyCall myCall){
    /*   final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait Sometime...");
        progressDialog.show();*/

        Api.getClient().getUsersList(string).enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, retrofit2.Response<UsersList> response) {
                user = response.body();
                usersLists = user.getItems();
                (myCall).callBack(usersLists);
                Log.d("list", usersLists.toString());
                //localRepository.insertData(usersLists);
                //progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
               // progressDialog.dismiss();
            }
        });
    }

    public interface MyCall{
        public void callBack(List<Items> list);
    }
}
