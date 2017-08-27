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
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.Common;
import io.github.dudgns0507.mpay.models.Data;
import io.github.dudgns0507.mpay.util.Login;
import io.github.dudgns0507.mpay.util.MyGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayAdapter admin_adapter;
    private ArrayAdapter group_adapter;

    @BindView(R.id.listview_top) ListView listview_top;
    @BindView(R.id.listview_bottom) ListView listview_bottom;
    @BindView(R.id.top_none) RelativeLayout top_none;
    @BindView(R.id.top) LinearLayout top;
    @BindView(R.id.bottom_none) TextView bottom_none;

    @BindString(R.string.baseurl) String baseUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadGroup();
    }

    private void loadGroup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyGroup myGroup = retrofit.create(MyGroup.class);

        final Data data = Data.getInstance();
        Call<Common> call = myGroup.myGroup(data.get_id());
        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {
                Common res = response.body();

                Log.w(TAG, res.toString());
                switch (res.getResult().getState()) {
                    case "200":
                        if(res.getResult().getAdmin().length != 0) {
                            top_none.setVisibility(View.GONE);
                            top.setVisibility(View.VISIBLE);
                            ArrayList<String> admin = new ArrayList<>();
                            for(int i = 0; i <= res.getResult().getAdmin().length; i++) {
                                admin.add(res.getResult().getAdmin()[i].getName());
                            }
                            admin_adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, admin);
                            listview_top.setAdapter(admin_adapter);
                        }
                        if(res.getResult().getGroup().length != 0) {
                            bottom_none.setVisibility(View.GONE);
                            listview_bottom.setVisibility(View.VISIBLE);
                            ArrayList<String> group = new ArrayList<>();
                            for(int i = 0; i <= res.getResult().getAdmin().length; i++) {
                                if(res.getResult().getGroup()[i].getAdmin() == data.get_id())
                                    continue;
                                group.add(res.getResult().getGroup()[i].getName());
                            }
                            group_adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, group);
                            listview_bottom.setAdapter(group_adapter);
                        }
                        break;
                    case "201":
                        Snackbar.make(getWindow().getDecorView().getRootView(), "로딩 실패", Snackbar.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onFailure(Call<Common> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
