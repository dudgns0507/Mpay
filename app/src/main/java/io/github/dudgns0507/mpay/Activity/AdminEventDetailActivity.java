package io.github.dudgns0507.mpay.Activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.dudgns0507.mpay.R;
import io.github.dudgns0507.mpay.models.Data;
import io.github.dudgns0507.mpay.models.Events;
import io.github.dudgns0507.mpay.models.User_info;

public class AdminEventDetailActivity extends AppCompatActivity {

    private static final String TAG = "AdminEventDetailActivity";

    private Data data = Data.getInstance();
    private int i, j;
    private ListViewAdapter mAdapter;

    @BindView(R.id.title_small) TextView title_small;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.current_money) TextView budget;
    @BindView(R.id.due_date) TextView due_date;
    @BindView(R.id.leftover) TextView leftover;
    @BindView(R.id.party) TextView party;
    @BindView(R.id.listview) ListView listView;
    @BindView(R.id.color_bar) ImageView color_bar;

    @OnClick(R.id.back_btn) void onBackClicked() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        i = intent.getIntExtra("i", 0);
        j = intent.getIntExtra("j", 0);

        title.setText(data.getEvents()[j].getName());
        title_small.setText(data.getAdmin()[i].getName());
        budget.setText("총 금액 : " + data.getEvents()[j].getTotal() + "원");
        leftover.setText(data.getEvents()[j].getTotal() - data.getEvents()[j].getTotal_paid() + "원");
        due_date.setText(data.getEvents()[j].getDue_date());
        party.setText(data.getEvents().length + "명");


        String id_str = "palette" + data.getEvents()[j].getTag_color();
        int id = getResources().getIdentifier(id_str, "color", getPackageName());
        color_bar.setBackgroundResource(id);

        mAdapter = new ListViewAdapter(this);
        listView.setAdapter(mAdapter);

        for(int l = 0; l < data.getEvents()[j].getUser().length; l++) {
            mAdapter.addItem(data.getEvents()[j].getUser()[l]);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static class ViewHolder {
        public TextView name;
        public TextView money;
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
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
                convertView = inflater.inflate(R.layout.lvadapter_payment, null);

                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.money = (TextView)convertView.findViewById(R.id.money);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            final User_info mData = mListData.get(position);

            holder.name.setText(mData.getName());
            holder.money.setText((mData.getPay() - mData.getPaid()) + "원");

            return convertView;
        }

        public void addItem(User_info event){
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
