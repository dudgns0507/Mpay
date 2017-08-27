package io.github.dudgns0507.mpay.Activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.Common;
import io.github.dudgns0507.mpay.util.Signup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private ProgressDialog asyncDialog;

    @BindView(R.id.name_edit) EditText name_edit;
    @BindView(R.id.name_text) TextView name_text;
    @BindView(R.id.birth_edit) EditText birth_edit;
    @BindView(R.id.birth_text) TextView birth_text;
    @BindView(R.id.email_edit) EditText email_edit;
    @BindView(R.id.email_text) TextView email_text;
    @BindView(R.id.phone_edit) EditText phone_edit;
    @BindView(R.id.phone_text) TextView phone_text;
    @BindView(R.id.passwd_edit) EditText passwd_edit;
    @BindView(R.id.passwd_text) TextView passwd_text;
    @BindView(R.id.passwd_confirm_edit) EditText passwd_confirm_edit;
    @BindView(R.id.passwd_confirm_text) TextView passwd_confirm_text;

    @BindString(R.string.baseurl) String baseUrl;

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.done_btn_frame) void onDoneClicked() {
        boolean flag = false;

        if(name_edit.getText().toString().replace(" ", "").equals("")) {
            name_text.setText("아이디를 입력해주세요.");
            flag = true;
        } else {
            name_text.setText("");
        }
        if(birth_edit.getText().toString().replace(" ", "").equals("")) {
            birth_text.setText("생년월일을 입력해주세요.");
            flag = true;
        } else {
            birth_text.setText("");
        }
        if(email_edit.getText().toString().replace(" ", "").equals("")) {
            email_text.setText("이메일을 입력해주세요.");
            flag = true;
        } else {
            email_text.setText("");
        }
        if(phone_edit.getText().toString().replace(" ", "").equals("")) {
            phone_text.setText("휴대폰 번호를 입력해주세요.");
            flag = true;
        } else {
            phone_text.setText("");
        }
        if(passwd_edit.getText().toString().replace(" ", "").equals("")) {
            passwd_text.setText("비밀번호를 입력해주세요.");
            flag = true;
        } else {
            passwd_text.setText("");
        }
        if(!passwd_edit.getText().toString().equals(passwd_confirm_edit.getText().toString())) {
            passwd_confirm_text.setText("비밀번호가 다릅니다.");
            flag = true;
        } else {
            passwd_confirm_text.setText("");
        }

        if(!flag) {
            asyncDialog = new ProgressDialog(SignupActivity.this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("가입 요청중입니다.. ");

            asyncDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Signup signup = retrofit.create(Signup.class);

            Call<Common> call = signup.signup(name_edit.getText().toString(), email_edit.getText().toString(), passwd_edit.getText().toString(), phone_edit.getText().toString());

            call.enqueue(new Callback<Common>() {
                @Override
                public void onResponse(Call<Common> call, Response<Common> response) {
                    Common common = response.body();

                    Log.w(TAG, common.toString());
                    if(common.getResult().getState().equals("200")) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "가입이 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        }, 500);
                    } else if(common.getResult().getState().equals("202")) {
                        email_text.setText("이미 등록된 이메일입니다.");
                    }

                    asyncDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Common> call, Throwable t) {
                    asyncDialog.dismiss();
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
