package com.example.tictoctoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private  int emptyX = 3;
    private  int emptyY = 3;
    private RelativeLayout group_relative_layout;
    private Button[][] buttons;
    private int[] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        loadViews();
        loadNumbers();
        generateNumbers();
        loadDataToViews();
    }

    // app ga kirganda 16 ta buttonga raqam yozib chiqadi
    private void loadDataToViews() {
        emptyX = 3;
        emptyY = 3;
        //group_relative_layout.getChildCount() - bu layoutdagi bolalarini sonini qaytaradi har safar 16 tani.
        for (int i = 0; i < group_relative_layout.getChildCount() - 1; i++) {
            buttons[i/4][i%4].setText(String.valueOf(tiles[i]));
            buttons[i/4][i%4].setBackgroundResource(R.drawable.btn_default);

            buttons[emptyX][emptyY].setText("");
            buttons[emptyX][emptyY].setBackgroundResource(R.color.bg_button);
        }
    }

    // har safar kirganda  har xil raqam berish random orqali
    private void generateNumbers() {
        int n = 15;
        Random random = new Random();
        while (n>1){
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[n];
            tiles[n] = temp;
        }
        if (!isSolvable())
            generateNumbers();
    }

    private boolean isSolvable() {
        int countInversions = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++) {
                if (tiles[j]>tiles[i])
                    countInversions++;
            }
        }
        return  countInversions%2 == 0;
    }

    // dastur ishga tushganda buttonlarga raqam berish
    private void loadNumbers() {
        tiles = new int[16];
        for (int i = 0; i < group_relative_layout.getChildCount() - 1; i++) {
            tiles[i] = i + 1;
        }
    }

    // dastur ishga tushganda RelativeLayout,Buttonlar yaratib olinyabdi
    private void loadViews() {
        group_relative_layout = findViewById(R.id.gropup_relative_layout);

        buttons = new Button[4][4];

        // massivaga button joylab chiqadi
        for (int i = 0; i < group_relative_layout.getChildCount(); i++) {
            buttons[i/4][i%4] = (Button) group_relative_layout.getChildAt(i);
        }

    }

    // g'alab bo'lganda chiqadi
    private void chechWin() {
        boolean isWin = false;

        if (emptyX==3&&emptyY==3){
            for (int i = 0; i < group_relative_layout.getChildCount() - 1; i++) {
                if (buttons[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                    isWin=true;
                }else {
                    isWin=false;
                    break;
                }
            }
        }
        if (isWin){
            Toast.makeText(this, "Win!!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < group_relative_layout.getChildCount()-1; i++) {
                buttons[i/4][i%4].setClickable(false);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void buttonClick(View view) {

        Button button = (Button) view;

        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if ((Math.abs(emptyX-x)==1&&emptyY==y)||(Math.abs(emptyY-y)==1&&emptyX==x)){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(R.color.bg_button);
            emptyX = x;
            emptyY = y;
            chechWin();
        }
    }
}