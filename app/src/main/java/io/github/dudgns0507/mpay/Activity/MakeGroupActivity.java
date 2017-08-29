package io.github.dudgns0507.mpay.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

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

public class MakeGroupActivity extends AppCompatActivity {

    private static final String TAG = "MakeGroupActivity";
    private ProgressDialog asyncDialog;
    ArrayList<String> bankList = new ArrayList<>(
            Arrays.asList("NH농협", "KB국민", "신한", "우리", "하나", "IBK기업", "외환", "SC제일", "씨티", "KDB산업", "새마을", "대구", "광주", "우체국", "신협", "전북", "경남", "부산", "수협", "제주", "저축은행", "산림조합", "케이뱅크", "카카오", "HSBC", "중국공상", "JP모간", "도이치", "BNP파리바", "BOA")
    );
    private String bank = "";
    public ListViewAdapter mAdapter;
    public ListViewAdapter mAdapter2;
    private boolean page = false;

    @BindView(R.id.spinner) Spinner sp;
    @BindView(R.id.group_name) EditText group_name;
    @BindView(R.id.group_budget) EditText group_budget;
    @BindView(R.id.group_account) EditText group_account;
    @BindView(R.id.group_invite_message) EditText group_message;
    @BindView(R.id.first) LinearLayout first;
    @BindView(R.id.second) LinearLayout second;
    @BindView(R.id.searchbox) EditText search_edit;
    @BindView(R.id.search_listview) ListView search_listview;
    @BindView(R.id.add_listview) ListView add_listview;

    @BindString(R.string.baseurl) String baseUrl;

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
                group_message.setError("초대 메세지를 입력해주세요.");
                flag = true;
            }
            if(group_account.getText().toString().trim().equals("") || bank.equals("")) {
                group_account.setError("계좌 번호를 입력해주세요.");
                flag = true;
            }

            if(!flag) {
                first.setVisibility(View.INVISIBLE);
                second.setVisibility(View.VISIBLE);
                page = true;
            }
        } else {
            asyncDialog = new ProgressDialog(MakeGroupActivity.this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("그룹 생성중입니다.. ");

            asyncDialog.show();

            Data data = Data.getInstance();

            CommonRequest req = new CommonRequest();
            req.setName(group_name.getText().toString());
            req.setAdmin(data.get_id());
            req.setExplain(group_message.getText().toString());
            req.setBudget(Integer.parseInt(group_budget.getText().toString()));
            req.setAccount(bank + " " + group_account.getText().toString());

            Member[] members = new Member[mAdapter2.getCount()];
            for(int i = 0; i < mAdapter2.getCount(); i++) {
                User_info user_info = (User_info) mAdapter2.getItem(i);
                Member member = new Member();
                member.setUser_id(user_info.get_id());
                if(user_info.get_id() == data.get_id())
                    member.setState("admin");
                else
                    member.setState("none");

                members[i] = member;
            }

            req.setMember(members);

            Log.w(TAG, req.toString());

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NewGroup newGroup = retrofit.create(NewGroup.class);

            Call<Common> call = newGroup.newGroup(req);
            call.enqueue(new Callback<Common>() {
                @Override
                public void onResponse(Call<Common> call, Response<Common> response) {
                    Common res = response.body();

                    switch (res.getResult().getState()) {
                        case "200":
                            Snackbar.make(getWindow().getDecorView().getRootView(), "추가가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
                            onBackPressed();
                            break;
                        case "201":
                            Snackbar.make(getWindow().getDecorView().getRootView(), "잠시후 다시시도 해주세요.", Snackbar.LENGTH_SHORT).show();
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

    @OnClick(R.id.search_btn) void onSearchClicked() {
        if(search_edit.getText().toString().trim().equals("")) {
            search_edit.setError("검색어를 입력해주세요.");
        } else {
            asyncDialog = new ProgressDialog(MakeGroupActivity.this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("검색중입니다.. ");

            asyncDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Find find = retrofit.create(Find.class);

            Call<Common> call = find.find(search_edit.getText().toString(), -1);
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
            final ViewHolder holder;

            if(convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.search_list_item, null);

                holder.title = (TextView) convertView.findViewById(R.id.text);
                holder.email = (TextView)convertView.findViewById(R.id.email);
                holder.add = (ImageView)convertView.findViewById(R.id.add_btn);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
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
