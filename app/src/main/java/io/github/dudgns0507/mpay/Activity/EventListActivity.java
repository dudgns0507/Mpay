package io.github.dudgns0507.mpay.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;

public class EventListActivity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";

    private int id;

    @BindView(R.id.title) TextView title;

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        id = intent.getIntExtra("id", 0);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}