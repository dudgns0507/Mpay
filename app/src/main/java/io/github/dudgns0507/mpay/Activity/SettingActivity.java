package io.github.dudgns0507.mpay.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.dudgns0507.mpay.R;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.mpay_logo)
    ImageView mpay_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.logo_white).into(mpay_logo);
    }
}
