package io.github.dudgns0507.mpay.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.CommonRequest;
import io.github.dudgns0507.mpay.models.Data;

public class MakeEventActivity_1 extends AppCompatActivity {

    private static final String TAG = "MakeEventActivity_1";
    private Data data = Data.getInstance();
    private int color = 1, id;
    private String title;

    @BindView(R.id.title_small) TextView title_small;
    @BindView(R.id.group_name) EditText event_name;
    @BindView(R.id.group_invite_message) EditText event_message;
    @BindView(R.id.group_budget) EditText event_due_date;

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.palette1) void onPalette1Clicked() {
        color = 1;
    }
    @OnClick(R.id.palette2) void onPalette2Clicked() {
        color = 2;
    }
    @OnClick(R.id.palette3) void onPalette3Clicked() {
        color = 3;
    }
    @OnClick(R.id.palette4) void onPalette4Clicked() {
        color = 4;
    }
    @OnClick(R.id.palette5) void onPalette5Clicked() {
        color = 5;
    }
    @OnClick(R.id.palette6) void onPalette6Clicked() {
        color = 6;
    }
    @OnClick(R.id.palette7) void onPalette7Clicked() {
        color = 7;
    }

    @OnClick(R.id.fin) void onFinClicked() {
        boolean flag = false;
        if (event_name.getText().toString().trim().equals("")) {
            event_name.setError("이벤트 이름을 입력해주세요");
            flag = true;
        }
        if(event_message.getText().toString().trim().equals("")) {
            event_message.setError("이벤트 메세지를 입력해주세요");
            flag = true;
        }
        if(event_due_date.getText().toString().trim().equals("")) {
            event_due_date.setError("마감 날짜를 입력해주세요.");
            flag = true;
        }
        if(!flag) {
            CommonRequest req = data.getRequest();
            if(req == null)
                req = new CommonRequest();
            req.setTag_color(color);
            req.setName(event_name.getText().toString());
            StringBuilder due_date = new StringBuilder(event_due_date.getText().toString());
            due_date.insert(4, "/");
            due_date.insert(7, "/");
            req.setDue_date(due_date.toString());
            req.setGroup_id(id);
            data.setRequest(req);

            Intent intent = new Intent(MakeEventActivity_1.this, MakeEventActivity_2.class);
            intent.putExtra("title", title);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event_1);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
        title = intent.getStringExtra("title");
        title_small.setText(title);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
