package io.github.dudgns0507.mpay.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.Common;
import io.github.dudgns0507.mpay.models.Data;
import io.github.dudgns0507.mpay.models.User_info;
import io.github.dudgns0507.mpay.util.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ProgressDialog asyncDialog;

    @BindView(R.id.logo) ImageView logo;
    @BindView(R.id.textlogo) ImageView textlogo;
    @BindView(R.id.email_edit) EditText email_edit;
    @BindView(R.id.passwd_edit) EditText passwd_edit;

    @BindString(R.string.baseurl) String baseUrl;

    @OnClick(R.id.signup_text) void onSignupClicked() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @OnClick(R.id.go_btn_frame) void onSigninClicked() {
        if(email_edit.getText().toString().trim().equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "이메일을 입력해주십시오.", Snackbar.LENGTH_SHORT).show();
        } else if(passwd_edit.getText().toString().trim().equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "비밀번호를 입력해주십시오.", Snackbar.LENGTH_SHORT).show();
        } else {
            login(email_edit.getText().toString(), passwd_edit.getText().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.logo_white).into(logo);
        Glide.with(this).load(R.drawable.textlogo_white).into(textlogo);

        SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        email_edit.setText(sp.getString("email", ""));

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
                .setPermissions(Manifest.permission.INTERNET)
                .check();
    }

    void login(final String id, final String pw) {
        asyncDialog = new ProgressDialog(LoginActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로그인중입니다.. ");

        asyncDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Login login = retrofit.create(Login.class);

        Call<Common> call = login.login(id, pw);
        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {
                asyncDialog.dismiss();
                Common res = response.body();

                if(res != null) {
                    switch (res.getResult().getState()) {
                        case "200":
                            SharedPreferences sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();

                            edit.putString("email", email_edit.getText().toString());
                            edit.apply();

                            if(res.getResult().getUser_info().length > 0) {
                                Data data = Data.getInstance();
                                User_info user_info = res.getResult().getUser_info()[0];

                                data.set_id(user_info.get_id());
                                data.setBirth(user_info.getBirth());
                                data.setEmail(user_info.getEmail());
                                data.setName(user_info.getName());
                                data.setPhone(user_info.getPhone());

                                Snackbar.make(getWindow().getDecorView().getRootView(), "로그인이 완료되었습니다.", Snackbar.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        finish();
                                    }
                                }, 500);
                            } else {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "잠시후 시도해주세요.", Snackbar.LENGTH_SHORT).show();
                            }
                            break;
                        case "201":
                            Snackbar.make(getWindow().getDecorView().getRootView(), "이메일 및 비밀번호를 확인해주세요.", Snackbar.LENGTH_SHORT).show();
                            break;

                    }
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
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("종료 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoginActivity.super.onBackPressed();
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
