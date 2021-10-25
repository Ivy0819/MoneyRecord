package com.swufestu.moneyrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    TextView income,outcome,cancel,finish;
    GridView GridList_outcome,GridList_income;
    private static MyRecordAdapter adapter_income,adapter_outcome;
    String selectTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridList_outcome = findViewById(R.id.GridList_outcome);
        GridList_income = findViewById(R.id.GridList_income);
        outcome = findViewById(R.id.out_come);
        income = findViewById(R.id.in_come);
//        GridList_income.setItemsCanFocus (false);
        GridList_income.setChoiceMode (ListView.CHOICE_MODE_SINGLE);
//        GridList_outcome.setItemsCanFocus (false);
        GridList_outcome.setChoiceMode (ListView.CHOICE_MODE_SINGLE);

        //初始化界面
        outcome.setTextColor(getResources().getColor(R.color.gray));
        GridList_income.setVisibility(View.GONE);
        GridList_outcome.setVisibility(View.VISIBLE);

        //生成GridView
        ArrayList<String> income_record = new ArrayList<String>();
        income_record.addAll(Arrays.asList("工资","兼职","理财","红包"));
        ArrayList<String> outcome_record = new ArrayList<String>();
        outcome_record.addAll(Arrays.asList("消费","餐饮","购物","交通"));

        adapter_income = new MyRecordAdapter(MainActivity.this,
                R.layout.record_item_list,
                income_record);
        GridList_income.setAdapter(adapter_income);
        adapter_outcome = new MyRecordAdapter(MainActivity.this,
                R.layout.record_item_list,
                outcome_record);
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
            income.setTextColor(getResources().getColor(R.color.white));
            outcome.setTextColor(getResources().getColor(R.color.gray));
            GridList_income.setVisibility(View.GONE);
            GridList_outcome.setVisibility(View.VISIBLE);
        }
        else if(btn.getId() == R.id.in_come){
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
//        selectTag = (String)adapter_income.getItem(position);
//        Log.i("Page", "setOnItemClickListener: tagStr:" + selectTag);

    }
}

