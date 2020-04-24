package com.example.tree.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tree.Bean.TestRecord;
import com.example.tree.R;

import java.util.List;

public class TestRecordAdapter extends RecyclerView.Adapter<TestRecordAdapter.TestHolder>{

    private static final String TAG ="Rong";
    Context context;
    private List<TestRecord> testRecordList;
    int grade;

    public TestRecordAdapter(Context context, List<TestRecord> testRecordList) {
        this.context = context;
        this.testRecordList = testRecordList;
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate ( R.layout.testrecord_recycle_item,parent,false);
        TestHolder testHolder = new TestHolder (view);
        return testHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder holder, int position) {
        TestRecord item = testRecordList.get (position);
        holder.testrecord_item_tv_testDatetime.setText (item.getCreatedAt());
        holder.testrecord_item_tv_testscore.setText (item.getTestscore()+"分");
        holder.testrecord_item_tv_testType.setText (item.getTesttype().getTestName());
        grade=Integer.valueOf(item.getTestscore());
        Log.i(TAG,"成绩"+item.getTestscore());
        showIcon(holder);
    }
    public void showIcon(TestHolder holder){//显示Icon
        switch(grade/10){
            case 0:
            case 1:
            case 2:
                holder.testrecord_item_iv_testresultIcon.setImageResource( R.drawable.testrecord_testresult_superunhappy );
                break;
            case 3:
            case 4:
                holder.testrecord_item_iv_testresultIcon.setImageResource( R.drawable.testrecord_testresult_unhappy );
                break;
            case 5:
            case 6:
                holder.testrecord_item_iv_testresultIcon.setImageResource( R.drawable.testrecord_testresult_normal );
                break;
            case 7:
            case 8:
                holder.testrecord_item_iv_testresultIcon.setImageResource( R.drawable.testrecord_testresult_happy );
                break;
            case 9:
            case 10:
                holder.testrecord_item_iv_testresultIcon.setImageResource( R.drawable.testrecord_testresult_superhappy );
                break;
        }
    }

    @Override
    public int getItemCount() {
        return testRecordList.size ();
    }

    class TestHolder extends RecyclerView.ViewHolder{
        RelativeLayout itemLayout;
        TextView testrecord_item_tv_testDatetime;
        TextView testrecord_item_tv_testscore;
        ImageView testrecord_item_iv_testresultIcon;
        TextView testrecord_item_tv_testType;
        public TestHolder(View itemView) {
            super (itemView);
            itemLayout = itemView.findViewById (R.id.testrecord_recycle_item);
            testrecord_item_tv_testDatetime = itemView.findViewById(R.id.testrecord_item_tv_testDatetime);
            testrecord_item_tv_testscore=itemView.findViewById(R.id.testrecord_item_tv_testscore);
            testrecord_item_iv_testresultIcon=itemView.findViewById(R.id.testrecord_item_iv_testresultIcon);
            testrecord_item_tv_testType=itemView.findViewById(R.id.testrecord_item_tv_testType);
        }
    }
}
