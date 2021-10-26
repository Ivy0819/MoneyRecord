package com.swufestu.moneyrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    EditText money_input;
    TextView income,outcome,cancel,finish,record_time;
    GridView GridList_outcome,GridList_income;
    private static MyRecordAdapter adapter_income,adapter_outcome;
    String selectTag,time_set,record_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridList_outcome = findViewById(R.id.GridList_outcome);
        GridList_income = findViewById(R.id.GridList_income);
        outcome = findViewById(R.id.out_come);
        income = findViewById(R.id.in_come);
        record_time = findViewById(R.id.record_time);
        money_input = findViewById(R.id.input_money);
//        GridList_income.setItemsCanFocus (false);
        GridList_income.setChoiceMode (ListView.CHOICE_MODE_SINGLE);
//        GridList_outcome.setItemsCanFocus (false);
        GridList_outcome.setChoiceMode (ListView.CHOICE_MODE_SINGLE);

        //初始化界面
        outcome.setTextColor(getResources().getColor(R.color.gray));
        GridList_income.setVisibility(View.GONE);
        GridList_outcome.setVisibility(View.VISIBLE);
        GridList_outcome.setContentDescription("show");
        GridList_income.setContentDescription("hide");

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        Date date= c.getTime();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time_set = formatter.format(date);
        record_time.setText(time_set);

        //生成GridView
        ArrayList<String> income_record = new ArrayList<String>();
        income_record.addAll(Arrays.asList("工资","兼职","理财","红包"));
        ArrayList<String> outcome_record = new ArrayList<String>();
        outcome_record.addAll(Arrays.asList("消费","餐饮","购物","交通"));

        adapter_income = new MyRecordAdapter(MainActivity.this,
                R.layout.record_item_list,
                income_record);
        adapter_income.defaultTagColor();
        GridList_income.setAdapter(adapter_income);
        adapter_outcome = new MyRecordAdapter(MainActivity.this,
                R.layout.record_item_list,
                outcome_record);
        adapter_outcome.defaultTagColor();
        GridList_outcome.setAdapter(adapter_outcome);

        //绑定监听
        GridList_income.setOnItemClickListener(this);
        GridList_outcome.setOnItemClickListener(this);


    }

    //取消或完成记账
    public void cancel_finish(View btn){
        if (btn.getId() == R.id.cancel_button){
            finish();
        }
        else if (btn.getId() == R.id.finish_button){
            //完成记账后
        }
    }

    //选择收入或支出
    public void income_outcome(View btn){
        if (btn.getId() == R.id.out_come){
            GridList_outcome.setContentDescription("show");
            GridList_income.setContentDescription("hide");
            income.setTextColor(getResources().getColor(R.color.white));
            outcome.setTextColor(getResources().getColor(R.color.gray));
            GridList_income.setVisibility(View.GONE);
            GridList_outcome.setVisibility(View.VISIBLE);
        }
        else if(btn.getId() == R.id.in_come){
            GridList_outcome.setContentDescription("hide");
            GridList_income.setContentDescription("show");
            outcome.setTextColor(getResources().getColor(R.color.white));
            income.setTextColor(getResources().getColor(R.color.gray));
            GridList_outcome.setVisibility(View.GONE);
            GridList_income.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            adapter_income.changeSelected(position);
            adapter_outcome.changeSelected(position);
            String selectTag_income,selectTag_outcome;
            selectTag_outcome = (String)adapter_outcome.getItem(position);
            selectTag_income = (String)adapter_income.getItem(position);
            if (GridList_outcome.getContentDescription().equals("show")){
                selectTag = selectTag_outcome;
            }
            else if (GridList_income.getContentDescription().equals("show")){
                selectTag = selectTag_income;
            }
            Log.i("Page", "setOnItemClickListener: tagStr:" + selectTag);

    }

    //跳转到修改时间界面
    public void time_editor(View btn){
        Intent time_set = new Intent(this,TimeSet.class);
        startActivityForResult(time_set, 1);
    }

    //处理回传的时间
    protected void onActivityResult(int requestcode, int resultCode, Intent data) {
        if(requestcode == 1 && resultCode == 2){
            time_set = data.getStringExtra("time_set");
            record_time.setText(time_set);
        }
        super.onActivityResult(requestcode, resultCode, data);
    }

}

