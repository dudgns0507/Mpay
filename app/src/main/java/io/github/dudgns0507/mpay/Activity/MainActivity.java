package io.github.dudgns0507.mpay.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.Common;
import io.github.dudgns0507.mpay.models.Data;
import io.github.dudgns0507.mpay.models.Group;
import io.github.dudgns0507.mpay.models.Result;
import io.github.dudgns0507.mpay.util.Login;
import io.github.dudgns0507.mpay.util.MyGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Data data = Data.getInstance();
    private ArrayAdapter admin_adapter;
    private ArrayAdapter group_adapter;
    private ArrayList<String> admin = new ArrayList<>();
    private ArrayList<String> group = new ArrayList<>();
    private ArrayList<Integer> admin_id = new ArrayList<>();
    private ArrayList<Integer> group_id = new ArrayList<>();

    @BindView(R.id.listview_top) ListView listview_top;
    @BindView(R.id.listview_bottom) ListView listview_bottom;
    @BindView(R.id.top_none) RelativeLayout top_none;
    @BindView(R.id.top) LinearLayout top;
    @BindView(R.id.bottom_none) TextView bottom_none;

    @BindString(R.string.baseurl) String baseUrl;

    @OnClick(R.id.setting_btn) void onSettingClicked() {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @OnClick(R.id.addgroup) void onAddClicked() {
        Intent intent = new Intent(MainActivity.this, MakeGroupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @OnClick(R.id.addgroup_none) void onAddClicked2() {
        Intent intent = new Intent(MainActivity.this, MakeGroupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadGroup();
        listview_top.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                intent.putExtra("i", i);
                intent.putExtra("type", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        listview_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EventListActivity.class);
                intent.putExtra("i", i);
                intent.putExtra("type", 2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void loadGroup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyGroup myGroup = retrofit.create(MyGroup.class);

        Call<Common> call = myGroup.myGroup(data.get_id());
        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {
                Result res = response.body().getResult();

                switch (res.getState()) {
                    case "200":
                        if(group_adapter != null)
                            group_adapter.clear();
                        if(admin_adapter != null)
                            admin_adapter.clear();

                        if(res.getAdmin().length != 0) {
                            top_none.setVisibility(View.GONE);
                            top.setVisibility(View.VISIBLE);

                            for(int i = 0; i < res.getAdmin().length; i++) {
                                admin.add(res.getAdmin()[i].getName());
                                admin_id.add(res.getAdmin()[i].get_id());
                            }

                            admin_adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, admin);
                            listview_top.setAdapter(admin_adapter);

                            data.setAdmin(res.getAdmin());
                        }
                        if(res.getGroup().length != 0) {
                            bottom_none.setVisibility(View.GONE);
                            listview_bottom.setVisibility(View.VISIBLE);

                            Group[] glist = new Group[res.getGroup().length];
                            int cnt = 0;
                            for(int i = 0; i < res.getGroup().length; i++) {
                                if(res.getGroup()[i].getAdmin() == data.get_id())
                                    continue;
                                group.add(res.getGroup()[i].getName());
                                group_id.add(res.getGroup()[i].get_id());
                                glist[cnt++] = res.getGroup()[i];
                            }

                            group_adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, group);
                            listview_bottom.setAdapter(group_adapter);

                            data.setGroup(glist);
                        }
                        break;
                    case "201":
                        Snackbar.make(getWindow().getDecorView().getRootView(), "로딩 실패", Snackbar.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onFailure(Call<Common> call, Throwable t) {
                Snackbar.make(getWindow().getDecorView().getRootView(), "로딩 실패", Snackbar.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGroup();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("종료 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
