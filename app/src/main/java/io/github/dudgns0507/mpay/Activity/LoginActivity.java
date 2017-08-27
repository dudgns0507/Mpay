package io.github.dudgns0507.mpay.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ProgressDialog asyncDialog;

    @BindView(R.id.logo) ImageView logo;
    @BindView(R.id.textlogo) ImageView textlogo;
    @BindView(R.id.email_edit) EditText email_edit;
    @BindView(R.id.passwd_edit) EditText passwd_edit;

    @OnClick(R.id.signup_text) void onSignupClicked() {
    }

    @OnClick(R.id.go_btn_frame) void onSigninClicked() {
        if(email_edit.getText().toString().replace(" ", "").equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "이메일을 입력해주십시오.", Snackbar.LENGTH_SHORT).show();
        } else if(passwd_edit.getText().toString().replace(" ", "").equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호를 입력해주십시오.", Snackbar.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
            login("", "");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.logo_white).into(logo);
        Glide.with(this).load(R.drawable.textlogo_white).into(textlogo);


        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.w(TAG, "Permission Granted");
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Log.w(TAG, "Permission Denied");
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한 허가하시지 않을 경우, 앱의 일부 서비스를 사용 못할 수 있습니다. \n\n[설정] -> [권한]에서 권한을 설정해 주세요.")
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
    }

    void login(final String id, final String pw) {
        asyncDialog = new ProgressDialog(LoginActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로그인중입니다.. ");

        asyncDialog.show();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://0hoon.xyz")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Login login = retrofit.create(Login.class);
//
//        Call<UserData> call = login.login(id, pw);
//        call.enqueue(new Callback<UserData>() {
//            @Override
//            public void onResponse(Call<UserData> call, Response<UserData> response) {
//                asyncDialog.dismiss();
//                UserData res = response.body();
//
//                if(response.body().getType()) {
//                    LocalData mData = (LocalData) getApplicationContext();
//                    mData.setCreate_date(res.getCreate_date());
//                    mData.setEmail(res.getEmail());
//                    mData.setId(res.getId());
//                    mData.setName(res.getName());
//                    mData.setStudy_time(res.getStudy_time());
//                    mData.setWordLists(res.getWordLists());
//
//                    SharedPreferences sp = getSharedPreferences("dudgns0507.learndoin", MODE_PRIVATE);
//                    SharedPreferences.Editor edit = sp.edit();
//
//                    edit.putString("id", id);
//                    edit.putString("pw", pw);
//
//                    edit.commit();
//
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                        }
//                    },500);
//                } else {
//                    Toast.makeText(LoginActivity.this, "ID 및 PW를 확인해주세요.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserData> call, Throwable t) {
//                Log.w(TAG, "Login Failed");
//            }
//        });
    }
}
