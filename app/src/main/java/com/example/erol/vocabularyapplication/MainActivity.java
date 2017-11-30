package com.example.erol.vocabularyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etWord;
    private TextView tvWord, tvDefinition, tvSynonym, tvExample;
    private CheckBox checkDefinition, checkSynonym, checkExample;
    private Button btnFromText, btnRandomly, btnSave, btnList;
    LinearLayout linearLayoutDefinition,linearLayoutSynonym, linearLayoutExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetViewElements();
        SetOnClickListeners();
        checkDefinition.setChecked(true);
        checkSynonym.setChecked(true);
        checkExample.setChecked(true);
    }

    @Override
    public void onClick(View view) {

        String text  = etWord.getText().toString();
        switch (view.getId()){
            case R.id.btnFromText:
                String url = String.format("https://wordsapiv1.p.mashape.com/words/" + text + "/");
                GetWordTask task = new GetWordTask(getApplicationContext(),tvWord, tvDefinition, tvSynonym, tvExample);
                task.execute(url);
                break;
            case R.id.btnRandomly:
                url = String.format("https://wordsapiv1.p.mashape.com/words/?random=true");
                task = new GetWordTask(getApplicationContext(),tvWord, tvDefinition, tvSynonym, tvExample);
                task.execute(url);

                break;
            case R.id.btnSave:

                break;
            case R.id.btnList:
                Intent intent = new Intent(this, ListActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkDefinition:
                if(checked){
                    linearLayoutDefinition.setVisibility(View.VISIBLE);

                }else{
                    linearLayoutDefinition.setVisibility(View.GONE);
                }

                break;
            case R.id.checkSynonym:
                if(checked){
                    linearLayoutSynonym.setVisibility(View.VISIBLE);
                }else{

                    linearLayoutSynonym.setVisibility(View.GONE);
                }

                break;
            case R.id.checkExample:
                if(checked){
                    linearLayoutExample.setVisibility(View.VISIBLE);
                }else{
                    linearLayoutExample.setVisibility(View.GONE);
                }

                break;
        }
    }


    private void SetOnClickListeners(){
        btnFromText.setOnClickListener(this);
        btnRandomly.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    private void SetViewElements(){
        etWord = (EditText)findViewById(R.id.etWord);
        tvWord = (TextView)findViewById(R.id.tvWord);
        tvDefinition = (TextView)findViewById(R.id.tvDefinition);
        tvSynonym = (TextView)findViewById(R.id.tvSynonym);
        tvExample = (TextView)findViewById(R.id.tvExample);
       // tvGeneric = (TextView)findViewById(R.id.tvGeneric);
        checkDefinition = (CheckBox)findViewById(R.id.checkDefinition);
        checkSynonym = (CheckBox)findViewById(R.id.checkSynonym);
        checkExample = (CheckBox)findViewById(R.id.checkExample);
        btnFromText = (Button)findViewById(R.id.btnFromText);
        btnRandomly = (Button)findViewById(R.id.btnRandomly);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnList = (Button)findViewById(R.id.btnList);
        linearLayoutDefinition = (LinearLayout)findViewById(R.id.linearDef);
        linearLayoutSynonym = (LinearLayout)findViewById(R.id.lineaSynonym);
        linearLayoutExample = (LinearLayout)findViewById(R.id.linearExample);
    }
}
