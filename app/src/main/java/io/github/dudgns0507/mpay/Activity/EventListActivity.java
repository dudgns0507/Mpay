package io.github.dudgns0507.mpay.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import io.github.dudgns0507.mpay.models.User_info;
import io.github.dudgns0507.mpay.util.FindEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventListActivity extends AppCompatActivity {

    private static final String TAG = "EventListActivity";

    private int id;
    private ListViewAdapter mAdapter;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.listview) ListView listview;

    @BindString(R.string.baseurl) String baseUrl;

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

        mAdapter = new ListViewAdapter(this);
        listview.setAdapter(mAdapter);

        loadEvents();
    }

    void loadEvents() {
        Data data = Data.getInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindEvents findEvents = retrofit.create(FindEvents.class);

        Call<Common> call = findEvents.findEvents(id, data.get_id());

        call.enqueue(new Callback<Common>() {
            @Override
            public void onResponse(Call<Common> call, Response<Common> response) {
                Common res = response.body();

                switch (res.getResult().getState()) {
                    case "200":
                        mAdapter.clear();
                        for(int i = 0; i < res.getResult().getEvents().length; i++) {
                            mAdapter.addItem(res.getResult().getEvents()[i]);
                        }
                        mAdapter.dataChange();
                        break;
                    case "201":
                        Snackbar.make(getWindow().getDecorView().getRootView(), "로딩 실패", Snackbar.LENGTH_SHORT).show();
                        break;

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
            holder.state.setText(mData.getState());
            if(mData.getState().equals("미납"))
                holder.state.setBackgroundResource(R.color.tagRed);
            else
                holder.state.setBackgroundResource(R.color.tagGreen);

            String id_str = "palette" + mData.getTag_color();
            Log.w(TAG, id_str);
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
}