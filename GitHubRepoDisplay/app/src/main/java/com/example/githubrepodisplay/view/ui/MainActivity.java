package com.example.githubrepodisplay.view.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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

    private RecyclerView horiRecyclerView, verRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private HorizontalAdapter horizontalAdapter;
    private VerticalAdapter verticalAdapter;
    private Button addToCart;
    private TextView textView;
    private int count=0;
    private long result;
    private ImageView cart;
    private SharedPreferences sharedPreferences;
    private GitViewModel gitViewModel;
    private RemoteRespository remoteResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_main);

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

        LiveData<List<Items>> uItemsList = gitViewModel.getList();
        uItemsList.observe(MainActivity.this, new Observer<List<Items>>() {
            @Override
            public void onChanged(@Nullable List<Items> items) {
                Log.d("WASTE","items: "+(items == null? 0 : items.size()));
                verticalAdapter.replaceItems(items);
            }
        });
    }



    @Override
    public void onClickNotify() {
        count++;
        textView.setText(String.valueOf(count));
    }

    @Override
    public void onClickJavaList(int position) {
        switch (position){
            case 0:
                Toast.makeText(getApplicationContext(),"Java",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:java");
                break;
            case 1:
                Toast.makeText(getApplicationContext(),"Python",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:python");

                break;
            case 2:
                Toast.makeText(getApplicationContext(),"Cpp",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:cpp");
                break;
            case 3:
                Toast.makeText(getApplicationContext(),"Kotlin",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:kotlin");
                break;
            case 4:
                Toast.makeText(getApplicationContext(),"Assembly",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:assembly");
                break;
            case 5:
                Toast.makeText(getApplicationContext(),"Swift",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:swift");
                break;
            case 6:
                Toast.makeText(getApplicationContext(),"JavaScript",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:js");
                break;
            case 7:
                Toast.makeText(getApplicationContext(),"C",Toast.LENGTH_SHORT).show();
                gitViewModel.fetchUserList("language:c");

                break;
        }

    }

}
