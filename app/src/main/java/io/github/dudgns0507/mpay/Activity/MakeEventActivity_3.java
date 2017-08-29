package io.github.dudgns0507.mpay.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.Common;
import io.github.dudgns0507.mpay.models.CommonRequest;
import io.github.dudgns0507.mpay.models.Data;
import io.github.dudgns0507.mpay.models.Member;
import io.github.dudgns0507.mpay.models.User_info;
import io.github.dudgns0507.mpay.util.NewEvent;
import io.github.dudgns0507.mpay.util.NewGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeEventActivity_3 extends AppCompatActivity {

    private static final String TAG = "MakeEventActivity_3";
    private Data data = Data.getInstance();
    private ProgressDialog asyncDialog;
    private String title;
    private ArrayAdapter mAdapter;
    private ArrayList<String> arrayList = new ArrayList<>();

    @BindView(R.id.title_small) TextView title_small;
    @BindView(R.id.listview) ListView listview;
    @BindView(R.id.apply_btn) Button apply_btn;
    @BindView(R.id.searchbox) EditText searchbox;

    @BindString(R.string.baseurl) String baseUrl;

    @OnClick(R.id.apply_btn) void onApplyClicked() {
        if(!searchbox.getText().toString().trim().equals("")) {
            SparseBooleanArray array = listview.getCheckedItemPositions();
            int sum = 0;
            for (int i = 0; i < mAdapter.getCount(); i++) {
                if (array.get(i)) {
                    arrayList.set(i, data.getRequest().getMember()[i].getName() + "\t\t" + searchbox.getText().toString());
                    data.getRequest().getMember()[i].setPay(Integer.parseInt(searchbox.getText().toString()));
                    listview.setItemChecked(i, false);
                }

                sum += data.getRequest().getMember()[i].getPay();
            }
            data.getRequest().setTotal(sum);
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.fin) void onFinClicked() {
        boolean flag = false;
        for(int i = 0; i < data.getRequest().getMember().length; i++) {
            if(data.getRequest().getMember()[i].getPay() == -1) {
                flag = true;
            }
        }

        if(!flag) {
            asyncDialog = new ProgressDialog(this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("이벤트 생성중입니다.. ");

            asyncDialog.show();

            Data data = Data.getInstance();
            CommonRequest req = data.getRequest();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NewEvent newEvent = retrofit.create(NewEvent.class);

            Call<Common> call = newEvent.newEvent(req);
            call.enqueue(new Callback<Common>() {
                @Override
                public void onResponse(Call<Common> call, Response<Common> response) {
                    Common res = response.body();

                    switch (res.getResult().getState()) {
                        case "200":
                            Snackbar.make(getWindow().getDecorView().getRootView(), "추가가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
                            onBackPressed();
                            break;
                        case "201":
                            Snackbar.make(getWindow().getDecorView().getRootView(), "잠시후 다시시도 해주세요.", Snackbar.LENGTH_SHORT).show();
                            break;

                    }
                    asyncDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Common> call, Throwable t) {
                    asyncDialog.dismiss();
                    Snackbar.make(getWindow().getDecorView().getRootView(), "잠시후 다시시도 해주세요.", Snackbar.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } else {
            Snackbar.make(getWindow().getDecorView().getRootView(), "모든 멤버의 지불 금액을 설정해주세요.", Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event_3);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        title_small.setText(title);

        for(int i = 0; i < data.getRequest().getMember().length; i++) {
            arrayList.add(data.getRequest().getMember()[i].getName());
        }

        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, arrayList) ;
        listview.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
