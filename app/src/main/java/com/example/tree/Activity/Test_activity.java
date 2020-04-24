package com.example.tree.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tree.Bean.Answerstate;
import com.example.tree.Bean.Question;
import com.example.tree.Bean.TestRecord;
import com.example.tree.Bean.TestType;
import com.example.tree.Bean._User;
import com.example.tree.R;
import com.example.tree.Utils.Randomtool;
import com.example.tree.Utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class Test_activity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Rong";
    private Context context = Test_activity.this;
    private Integer testType;

    private LinearLayout test_ll_simpleSelectView;//单选选择框

    private TextView test_tv_questionType;//题目类型
    private TextView test_tv_questionNo;//目前第几题
    private TextView test_tv_question;//题目
    private TextView test_tv_questionRight;//提示

    private Button test_btn_next;

    private RadioGroup test_rg_simpleSelect;//单选按钮组

    private int quesNum;//问题数量
    private int position=0;//初始化当前题目位置


    List<Question> mQuestions=new ArrayList<>();//存放bmob查询下来所有题目的链表
    List<Question> testQuestions=new ArrayList<>();//存放随机10道题的链表
    List<Answerstate> mAnswerstates=new ArrayList<>();// 题目回答状态列表

    RadioButton[] mRadioButtons=new RadioButton[4]; //单选按钮

    Randomtool mRandomtool=new Randomtool();//随机出题
    Object[] rannum=new Object[20];
    private int grade=0;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_layout);
        initView();//初始化View
        queryQuestion();//从bmob中查询题目
    }

    /*  初始化View  */
    private void initView() {
        test_rg_simpleSelect = findViewById(R.id.test_rg_simpleSelect);

        test_tv_questionType =  findViewById(R.id.test_tv_questionType);
        test_tv_questionNo = findViewById(R.id.test_tv_questionNo);
        test_tv_question = findViewById(R.id.test_tv_question);
        test_tv_questionRight = findViewById(R.id.test_tv_questionRight);
        test_btn_next = findViewById(R.id. test_btn_next);

        mRadioButtons[0] = findViewById(R.id.test_rb_answerA);
        mRadioButtons[1] = findViewById(R.id.test_rb_answerB);
        mRadioButtons[2] = findViewById(R.id.test_rb_answerC);
        mRadioButtons[3] = findViewById(R.id.test_rb_answerD);


        findViewById(R.id.test_btn_back).setOnClickListener(this);
        findViewById(R.id.test_btn_next).setOnClickListener(this);

        getRaidoButtonselect();//单选按钮的监听
    }


    private void queryQuestion(){//查询题目
        Intent intent = getIntent();
        testType = intent.getIntExtra("testType",0);
        BmobQuery<Question> QuestionBmobQuery = new BmobQuery<Question>();
        QuestionBmobQuery.setLimit(1000);
        QuestionBmobQuery.addWhereEqualTo("questionType",testType);
        QuestionBmobQuery.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> object, BmobException e) {

                if (e == null) {
                    mQuestions.addAll(object);
                    Log.i(TAG,String.valueOf(mQuestions.size()));
                    initdata();//随机从mQuestions列表中取题目进行出题
                    setQuestionType();//根据测试类型，选择显示界面。
                } else {
                    Log.e("Question:", e.toString());
                    Toast.makeText( context, "查询失败", Toast.LENGTH_SHORT ).show();
                }
            }
        });

    }

    //初始化题目
    public void initdata(){
        Log.i(TAG,"题目总数"+mQuestions.size());
        rannum = mRandomtool.ali(mQuestions.size());
        for (int i = 0;i<10;i++){
            testQuestions.add(mQuestions.get((int)rannum[i]));
        }
        quesNum=testQuestions.size();//获取测试题目链表的长度
        initAnswerState(quesNum);//初始化10道题的答题状态
    }
    //初始化20道题的答题状态
    public void initAnswerState(int quesNum){
        for (int i = 0; i<quesNum;i++) {
            Answerstate answerState = new Answerstate();
            answerState.setSelectitem(null);
            answerState.setFinishState(false);
            mAnswerstates.add(answerState);
        }
    }
    //根据题目类型，选择显示的界面。
    public void setQuestionType(){
        Log.i(TAG,"位置"+position);
        Question question=testQuestions.get(position);
        Log.i(TAG,"问题:"+question.getQuestion());
        test_tv_question.setText(question.getQuestion());
        test_tv_questionNo.setText((position+1)+"/"+quesNum);
        mAnswerstates.get(position).setFinishState(false);
        if(position==quesNum-1){
            test_btn_next.setText("最后一题");
        }
        switch(testType){
            case 1:
                test_tv_questionType.setText("抑郁测试题");
                break;
            case 2:
                test_tv_questionType.setText("SCL-心理测试");
                break;
        }
    }
    //单选按钮监听
    public void getRaidoButtonselect(){
        test_rg_simpleSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i!=-1){
                    RadioButton rb=findViewById(radioGroup.getCheckedRadioButtonId());
                    mAnswerstates.get(position).setSelectitem(rb.getText().toString().trim());
                    mAnswerstates.get(position).setFinishState(true);
                    mAnswerstates.get(position).setItem_id(i);
                    showMessage("", R.color.colorAccent);
                    Log.i(TAG,mAnswerstates.get(position).getSelectitem());
                }
            }
        });
    }

    private void recordGrade() {
       String selectItem=mAnswerstates.get(position).getSelectitem();
       String A=mRadioButtons[0].getText().toString();
       String B=mRadioButtons[1].getText().toString();
       String C=mRadioButtons[2].getText().toString();
       String D=mRadioButtons[3].getText().toString();
       if(selectItem==A){
           grade=grade+10;
           Log.i(TAG,"选了A");
       }else if(selectItem==B){
           grade=grade+7;
           Log.i(TAG,"选了B");
       }else if(selectItem==C){
           grade=grade+4;
           Log.i(TAG,"选了C");
       }else if(selectItem==D){
           grade=grade+1;
           Log.i(TAG,"选了D");
       }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_btn_next:
                Log.i(TAG,"点击了");
                if(!mAnswerstates.get(position).getFinishState()){
                    unFinish();//未完成答题
                }else{
                    Finished();//完成答题
                }
                break;
            case R.id.test_btn_back:
                showDialog("小一，你要离开这次测试吗？");
                break;

        }
    }
    //题目状态未完成
    public void unFinish(){
        String selectItem=mAnswerstates.get(position).getSelectitem();//用户答案
        if(selectItem==null||selectItem==""){//没有回答
            showMessage("答案不能为空", R.color.colorAccent);
        }
    }
    public void Finished(){
        recordGrade();
        test_tv_questionRight.setText(null);//清楚提示状态
        test_rg_simpleSelect.clearCheck();//清空上一题的单选选中状态
        setClickableState(true);//使单选可以选
        position++;
        if(position<quesNum){
            setQuestionType();
        }else{
            endTest();
        }

    }
    public void setClickableState(boolean state){
        mAnswerstates.get(position).setFinishState(true);
        mRadioButtons[0].setClickable(state);
        mRadioButtons[1].setClickable(state);
        mRadioButtons[2].setClickable(state);
        mRadioButtons[3].setClickable(state);
    }
    //结束考试
    public void endTest(){
        upgrade();
        Intent intent = new Intent(Test_activity.this, Testfinish_activity.class);
        intent.putExtra("grade",grade);
        Log.i(TAG,"总成绩是："+grade);
        startActivity(intent);
        finish();

    }


    //没有回答或回答错误时弹出提示
    public void showMessage(String message, int color){
        test_tv_questionRight.setTextColor(getResources().getColor(color));
        test_tv_questionRight.setText(message);
    }
    public void showDialog(String message){
        AlertDialog.Builder dialog=new AlertDialog.Builder(Test_activity.this);
        dialog.setTitle("提醒");
        dialog.setMessage(message);
        dialog.setPositiveButton("离开", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("继续", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
        dialog.show();
    }
    public void upgrade(){
        intent = getIntent();
        int testType = intent.getIntExtra("testType", 0);
        //获取当前用户
        _User user= BmobUser.getCurrentUser(_User.class);
        TestType testtype=new TestType();
        if (testType == 1){
            testtype.setObjectId("4EAh888q");
        }else if (testType == 2){
            testtype.setObjectId("wxi9888B");
        }
        TestRecord testRecord=new TestRecord();
        testRecord.setTesttype(testtype);
        testRecord.setUsername(user);
        testRecord.setTestscore(String.valueOf(grade));
        testRecord.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    ToastUtils.show(context,"测试结果上传成功!!");
                }else{
                    ToastUtils.show(context,"创建数据失败：" + e.getMessage());
                }

            }
        });
    }
}
