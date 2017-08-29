package io.github.dudgns0507.mpay.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.Common;
import io.github.dudgns0507.mpay.models.CommonRequest;
import io.github.dudgns0507.mpay.models.Data;
import io.github.dudgns0507.mpay.models.Events;
import io.github.dudgns0507.mpay.models.Result;
import io.github.dudgns0507.mpay.models.User_info;
import io.github.dudgns0507.mpay.util.FindEvents;
import io.github.dudgns0507.mpay.util.FindGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListActivity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";

    private Data data = Data.getInstance();
    private int i, type, id;
    private ListViewAdapter mAdapter;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.listview) ListView listview;
    @BindView(R.id.budget) TextView budget;
    @BindView(R.id.add_btn) ImageView add_btn;

    @BindString(R.string.baseurl) String baseUrl;

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }
    @OnClick(R.id.add_btn) void onAddClicked() {
        if(type == 1) {
            Intent intent = new Intent(EventListActivity.this, MakeEventActivity_1.class);
            intent.putExtra("title", title.getText().toString());
            intent.putExtra("id", data.getAdmin()[i].get_id());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        i = intent.getIntExtra("i", 0);
        type = intent.getIntExtra("type", 1);

        if(type == 1) {
            title.setText(data.getAdmin()[i].getName());
            budget.setText("자금 : " + data.getAdmin()[i].getBudget() + "원");
            Glide.with(this).load(R.drawable.ic_add_white_48dp).into(add_btn);
        }

        if(type == 2) {
            title.setText(data.getGroup()[i].getName());
            budget.setText("자금 : " + data.getGroup()[i].getBudget() + "원");
        }

        mAdapter = new ListViewAdapter(this);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(data.getEvents() != null && type == 2) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    clipboardManager.setText(data.getEvents()[i].getPay() + "원 " + data.getGroup()[i].getAccount());

                    Snackbar.make(getWindow().getDecorView().getRootView(), "클립보드에 복사 되었습니다.", Snackbar.LENGTH_SHORT).show();
                } else if(type == 1) {
                    Intent intent = new Intent(EventListActivity.this, AdminEventDetailActivity.class);
                    intent.putExtra("i", EventListActivity.this.i);
                    intent.putExtra("j", i);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    void loadEvents() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindEvents findEvents = retrofit.create(FindEvents.class);

        if(type == 1) {
            id = data.getAdmin()[i].get_id();
        } else {
            id = data.getGroup()[i].get_id();
        }

        Call<Common> call = findEvents.findEvents(id);

        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {
                Result res = response.body().getResult();
                if(res != null) {
                    data.setEvents(res.getEvents());
                    switch (res.getState()) {
                        case "200":
                            mAdapter.clear();
                            for (int i = 0; i < res.getEvents().length; i++) {
                                mAdapter.addItem(res.getEvents()[i]);
                            }
                            mAdapter.dataChange();
                            break;
                        case "201":
                            Snackbar.make(getWindow().getDecorView().getRootView(), "로딩 실패", Snackbar.LENGTH_SHORT).show();
                            break;
                    }
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
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEvents();
    }

    public static class ViewHolder {
        public TextView title;
        public Button state;
        public ImageView tag;
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
        private ArrayList<Events> mListData = new ArrayList<>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if(convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.lvadapter_event, null);

                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.state = (Button)convertView.findViewById(R.id.state);
                holder.tag = (ImageView)convertView.findViewById(R.id.tag);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final Events mData = mListData.get(position);

            holder.title.setText(mData.getName());

            if(type == 1) {
                holder.state.setText(mData.getStatus());
                if(mData.getStatus().equals("진행"))
                    holder.state.setBackgroundResource(R.color.tagRed);
                else
                    holder.state.setBackgroundResource(R.color.tagGreen);
            } else {
                for (int i = 0; i < mData.getUser().length; i++) {
                    if (mData.getUser()[i].get_id() == mData.get_id()) {
                        holder.state.setText(mData.getUser()[i].getState());
                        if (mData.getUser()[i].getState().equals("미납"))
                            holder.state.setBackgroundResource(R.color.tagRed);
                        else
                            holder.state.setBackgroundResource(R.color.tagGreen);
                    }
                }
            }

            String id_str = "palette" + mData.getTag_color();
            int id = mContext.getResources().getIdentifier(id_str, "color", getPackageName());
            holder.tag.setBackgroundResource(id);

            return convertView;
        }

        public void addItem(Events event){
            mListData.add(event);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void clear() {
            mListData.clear();
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        data.setEvents(null);
    }
}