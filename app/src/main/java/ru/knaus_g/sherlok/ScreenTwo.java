package ru.knaus_g.sherlok;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ScreenTwo extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_two);

        Toast toast = Toast.makeText(getApplicationContext(), "Выберите ответ", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.show();
    }

    public static String THIEF = "ru.knaus_g.sherlok.THIEF";


    public void onRadioClick(View v){
        Intent answerIntent = new Intent();

        switch (v.getId()){

            case R.id.radioDog:
                answerIntent.putExtra(THIEF, "Ответ: Песик. \nДа, ты угадал)");
                break;
            case R.id.radioCrown:
                answerIntent.putExtra(THIEF, "Ответ: Ворона. \nНет, ворона держдит во рту сыр)");
                break;
            case R.id.radioCat:
                answerIntent.putExtra(THIEF,"Ответ: Кот Васька. \nНет, котик не мог)");
                break;

            default:
                break;
        }

        setResult(RESULT_OK, answerIntent);
        finish();
    }

}
