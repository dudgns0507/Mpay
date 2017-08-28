package io.github.dudgns0507.mpay.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;

public class MakeGroupActivity extends AppCompatActivity {

    private static final String TAG = "MakeGroupActivity";

    ArrayList<String> bankList = new ArrayList<>(
            Arrays.asList("NH농협", "KB국민", "신한", "우리", "하나", "IBK기업", "외환", "SC제일", "씨티", "KDB산업", "새마을", "대구", "광주", "우체국", "신협", "전북", "경남", "부산", "수협", "제주", "저축은행", "산림조합", "케이뱅크", "카카오", "HSBC", "중국공상", "JP모간", "도이치", "BNP파리바", "BOA")
    );
    private String bank = "";
    private boolean page = false;

    @BindView(R.id.spinner) Spinner sp;
    @BindView(R.id.group_name) EditText group_name;
    @BindView(R.id.group_budget) EditText group_budget;
    @BindView(R.id.group_account) EditText group_account;
    @BindView(R.id.group_invite_message) EditText group_message;
    @BindView(R.id.first) LinearLayout first;
    @BindView(R.id.second) LinearLayout second;

    @OnClick(R.id.go_btn_frame) void onGoClicked() {
        if(!page) {
            boolean flag = false;
            if (group_name.getText().toString().trim().equals("")) {
                group_name.setError("그룹 이름을 입력해주세요");
                flag = true;
            }
            if(group_account.getText().toString().trim().equals("")) {
                group_account.setError("현재 자금을 입력해주세요");
                flag = true;
            }
            if(group_message.getText().toString().trim().equals("")) {
                group_message.setError("초대 메세질ㄹ 입력해주세요.");
                flag = true;
            }
            if(group_account.getText().toString().trim().equals("") || bank.equals("")) {
                group_account.setError("계좌 번호를 입력해주세요.");
                flag = true;
            }

            if(!flag) {
                first.setVisibility(View.GONE);
                second.setVisibility(View.VISIBLE);
                page = true;
            }
        } else {

        }
    }

    @OnClick(R.id.search_btn) void onSearchClicked() {

    }

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_group);
        ButterKnife.bind(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, bankList);

        sp.setPrompt("은행 선택");
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank = bankList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
