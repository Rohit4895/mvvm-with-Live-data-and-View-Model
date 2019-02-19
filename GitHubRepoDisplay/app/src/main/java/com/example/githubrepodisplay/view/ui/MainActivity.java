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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubrepodisplay.service.apiRepository.ApiInterface;
import com.example.githubrepodisplay.view.adapter.HorizontalAdapter;
import com.example.githubrepodisplay.service.model.Items;
import com.example.githubrepodisplay.R;
import com.example.githubrepodisplay.service.utils.Utils;
import com.example.githubrepodisplay.view.adapter.VerticalAdapter;
import com.example.githubrepodisplay.viewModel.GitViewModel;
import com.example.githubrepodisplay.viewModel.remote.RemoteRespository;

import java.util.ArrayList;
import java.util.List;

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
    private GitViewModel gitViewModel;
    RemoteRespository remoteResponse;
    List<Items> uItemsList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main);

        //remoteResponse = new RemoteRespository(this);

        textView = findViewById(R.id.notification);


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

    public void getProgressBar(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait Sometime...");
        progressDialog.show();
    }

    @Override
    public void onClickNotify() {
        count++;
        textView.setText(String.valueOf(count));
    }

    @Override
    public void onClickJavaList() {
        Toast.makeText(getApplicationContext(),"Java",Toast.LENGTH_SHORT).show();
      gitViewModel.fetchUserList(this,"language:java");
    }

    @Override
    public void onClickJsList() {
        Toast.makeText(getApplicationContext(),"JavaScript",Toast.LENGTH_SHORT).show();
        gitViewModel.fetchUserList(this,"language:js");
    }

    @Override
    public void onClickKotlinList() {
        Toast.makeText(getApplicationContext(),"Kotlin",Toast.LENGTH_SHORT).show();
        gitViewModel.fetchUserList(this,"language:kotlin");
    }

    @Override
    public void onClickSwiftList() {
        Toast.makeText(getApplicationContext(),"Swift",Toast.LENGTH_SHORT).show();
        gitViewModel.fetchUserList(this,"language:swift");
    }

    @Override
    public void onClickPythonList() {
        Toast.makeText(getApplicationContext(),"Python",Toast.LENGTH_SHORT).show();
        gitViewModel.fetchUserList(this,"language:python");
    }

    @Override
    public void onClickCList() {
        Toast.makeText(getApplicationContext(),"C",Toast.LENGTH_SHORT).show();
        gitViewModel.fetchUserList(this,"language:c");
    }

    @Override
    public void onClickCppList() {
        Toast.makeText(getApplicationContext(),"Cpp",Toast.LENGTH_SHORT).show();
        gitViewModel.fetchUserList(this,"language:cpp");
    }

    @Override
    public void onClickAssemblyList() {
        Toast.makeText(getApplicationContext(),"Assembly",Toast.LENGTH_SHORT).show();
        gitViewModel.fetchUserList(this,"language:assembly");
    }
}
