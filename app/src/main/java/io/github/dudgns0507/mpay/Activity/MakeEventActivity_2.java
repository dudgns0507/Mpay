package io.github.dudgns0507.mpay.Activity;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
import io.github.dudgns0507.mpay.util.Find;
import io.github.dudgns0507.mpay.util.NewGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeEventActivity_2 extends AppCompatActivity {

    private static final String TAG = "MakeEventActivity_2";
    private Data data = Data.getInstance();
    private ProgressDialog asyncDialog;
    private String title;
    public ListViewAdapter mAdapter;
    public ListViewAdapter mAdapter2;

    @BindView(R.id.search_listview) ListView search_listview;
    @BindView(R.id.add_listview) ListView add_listview;
    @BindView(R.id.title_small) TextView title_small;
    @BindView(R.id.searchbox) EditText search_edit;

    @BindString(R.string.baseurl) String baseUrl;

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.search_btn) void onSearchClicked() {
        if(search_edit.getText().toString().trim().equals("")) {
            search_edit.setError("검색어를 입력해주세요.");
        } else {
            asyncDialog = new ProgressDialog(this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("검색중입니다.. ");

            asyncDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Find find = retrofit.create(Find.class);

            Call<Common> call = find.find(search_edit.getText().toString(), data.getRequest().getGroup_id());
            call.enqueue(new Callback<Common>() {
                @Override
                public void onResponse(Call<Common> call, Response<Common> response) {
                    Common res = response.body();

                    switch (res.getResult().getState()) {
                        case "200":
                            mAdapter.clear();
                            for(int i = 0; i < res.getResult().getUser_info().length; i++) {
                                mAdapter.addItem(res.getResult().getUser_info()[i], false);
                            }
                            mAdapter.dataChange();
                            break;
                        case "201":
                            Snackbar.make(getWindow().getDecorView().getRootView(), "검색 결과가 없습니다.", Snackbar.LENGTH_SHORT).show();
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
        }
    }

    @OnClick(R.id.fin) void onFinClicked() {
        Data data = Data.getInstance();

        CommonRequest req = data.getRequest();

        Member[] members = new Member[mAdapter2.getCount()];
        for(int i = 0; i < mAdapter2.getCount(); i++) {
            User_info user_info = (User_info) mAdapter2.getItem(i);
            Member member = new Member();
            member.setUser_id(user_info.get_id());
            member.setState("미납");
            member.setName(user_info.getName());
            member.setPaid(0);
            member.setPay(-1);
            members[i] = member;
        }

        req.setMember(members);

        Intent intent = new Intent(MakeEventActivity_2.this, MakeEventActivity_3.class);
        intent.putExtra("title", title);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_event_2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        title_small.setText(title);

        mAdapter = new ListViewAdapter(this);
        mAdapter2 = new ListViewAdapter(this);
        search_listview.setAdapter(mAdapter);
        add_listview.setAdapter(mAdapter2);

        search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean tmp = false;
                for(int j = 0; j < mAdapter2.getCount() ; j++) {
                    if(mAdapter2.getItem(j) == mAdapter.getItem(i)) {
                        tmp = true;
                    }
                }
                if(!tmp) {
                    mAdapter2.addItem((User_info)mAdapter.getItem(i), true);
                    mAdapter2.dataChange();
                }
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
        public TextView email;
        public ImageView add;
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
        private boolean flag = false;
        private ArrayList<User_info> mListData = new ArrayList<>();

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
            final MakeGroupActivity.ViewHolder holder;

            if(convertView == null) {
                holder = new MakeGroupActivity.ViewHolder();

                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.search_list_item, null);

                holder.title = (TextView) convertView.findViewById(R.id.text);
                holder.email = (TextView)convertView.findViewById(R.id.email);
                holder.add = (ImageView)convertView.findViewById(R.id.add_btn);

                convertView.setTag(holder);
            } else {
                holder = (MakeGroupActivity.ViewHolder)convertView.getTag();
            }

            final User_info mData = mListData.get(position);

            holder.title.setText(mData.getName());
            holder.email.setText(mData.getEmail());

            if(flag) {
                Glide.with(mContext).load(R.drawable.ic_clear_white_48dp).into(holder.add);
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapter2.remove(position);
                        mAdapter2.dataChange();
                    }
                });
            }

            return convertView;
        }

        public void addItem(User_info user_info, boolean flag){
            mListData.add(user_info);
            this.flag = flag;
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
            mAdapter2.notifyDataSetChanged();
        }
    }
}
