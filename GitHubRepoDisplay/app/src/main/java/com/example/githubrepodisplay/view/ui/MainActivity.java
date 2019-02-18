package com.example.githubrepodisplay.view.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubrepodisplay.service.dataRepository.Crud;
import com.example.githubrepodisplay.service.model.UserDB;
import com.example.githubrepodisplay.service.apiRepository.Api;
import com.example.githubrepodisplay.service.apiRepository.ApiInterface;
import com.example.githubrepodisplay.service.apiRepository.AppExecutor;
import com.example.githubrepodisplay.service.dataRepository.UserDBClient;
import com.example.githubrepodisplay.view.adapter.HorizontalAdapter;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.R;
import com.example.githubrepodisplay.service.model.UsersList;
import com.example.githubrepodisplay.service.apiRepository.Utils;
import com.example.githubrepodisplay.view.adapter.VerticalAdapter;
import com.example.githubrepodisplay.viewModel.GitViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements VerticalAdapter.IMethodCaller, HorizontalAdapter.CallDifferentUserLists {
    RecyclerView horiRecyclerView, verRecyclerView;
    LinearLayoutManager linearLayoutManager;
    HorizontalAdapter horizontalAdapter;
    VerticalAdapter verticalAdapter;
    Button addToCart;
    TextView textView;
    int count=0;
    long result;
    ImageView cart;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiInterface apiInterface;
    UsersList user;
    List<Items> usersLists, databaseList;
    private GitViewModel gitViewModel;
    Crud crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main);

        apiInterface = Api.getClient();

        textView = findViewById(R.id.notification);

        crud = new Crud(this);

        List<String> list1 = new ArrayList<String>();
        list1.add("JAVA");
        list1.add("PYTHON");
        list1.add("C++");
        list1.add("KOTLIN");
        list1.add("ASSEMBLY");
        list1.add("SWIFT");
        list1.add("JAVASCRIPT");
        list1.add("C");



        horiRecyclerView = findViewById(R.id.horiRecyclerView);
        verRecyclerView = findViewById(R.id.verRecyclerView);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        horiRecyclerView.setLayoutManager(linearLayoutManager);
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(this,list1);
        horiRecyclerView.setAdapter(horizontalAdapter);


        cart = findViewById(R.id.cartIcon);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = (MainActivity.this).getPreferences(Context.MODE_PRIVATE);
                int status = sharedPreferences.getInt("status",0);
                //Log.d("statusRohit",status+"");
                if (status == 1){
                    Utils.changeTheme(MainActivity.this, Utils.THEME_DEFAULT);
                }else if (status == 2){
                    Utils.changeTheme(MainActivity.this, Utils.THEME_BLACK);
                }

            }
        });

        verRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final VerticalAdapter verticalAdapter = new VerticalAdapter(this, new ArrayList<Items>());
        verRecyclerView.setAdapter(verticalAdapter);

        gitViewModel = ViewModelProviders.of(this).get(GitViewModel.class);

        gitViewModel.getList().observe(MainActivity.this, new Observer<List<Items>>() {
            @Override
            public void onChanged(@Nullable List<Items> items) {
                verticalAdapter.addItems(items);
            }
        });


    }

    public void getUsersList(String string){
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait Sometime...");
        progressDialog.show();

        apiInterface.getUsersList(string).enqueue(new Callback<UsersList>() {
            @Override
            public void onResponse(Call<UsersList> call, Response<UsersList> response) {
                user = response.body();
                usersLists = user.getItems();
                crud.insertData(usersLists);
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UsersList> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                Log.e("roh",t.toString());
                progressDialog.dismiss();
            }
        });

    }
    

    @Override
    public void onClickNotify() {
        count++;
        textView.setText(String.valueOf(count));
    }

    @Override
    public void onClickJavaList() {
        Toast.makeText(getApplicationContext(),"Java",Toast.LENGTH_SHORT).show();
        getUsersList("language:java");
    }

    @Override
    public void onClickJsList() {
        Toast.makeText(getApplicationContext(),"JavaScript",Toast.LENGTH_SHORT).show();
        getUsersList("language:js");
    }

    @Override
    public void onClickKotlinList() {
        Toast.makeText(getApplicationContext(),"Kotlin",Toast.LENGTH_SHORT).show();
        getUsersList("language:kotlin");
    }

    @Override
    public void onClickSwiftList() {
        Toast.makeText(getApplicationContext(),"Swift",Toast.LENGTH_SHORT).show();
        getUsersList("language:swift");
    }

    @Override
    public void onClickPythonList() {
        Toast.makeText(getApplicationContext(),"Python",Toast.LENGTH_SHORT).show();
        getUsersList("language:python");
    }

    @Override
    public void onClickCList() {
        Toast.makeText(getApplicationContext(),"C",Toast.LENGTH_SHORT).show();
        getUsersList("language:c");
    }

    @Override
    public void onClickCppList() {
        Toast.makeText(getApplicationContext(),"Cpp",Toast.LENGTH_SHORT).show();
        getUsersList("language:cpp");
    }

    @Override
    public void onClickAssemblyList() {
        Toast.makeText(getApplicationContext(),"Assembly",Toast.LENGTH_SHORT).show();
        getUsersList("language:assembly");
    }
}
