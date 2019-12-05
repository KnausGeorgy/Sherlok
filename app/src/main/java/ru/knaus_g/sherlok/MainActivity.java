package ru.knaus_g.sherlok;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOError;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    // Звук кота
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mSoundcat, mSoundDog;
    private int mStreamID;


    // Идентификатор уведомления
    //private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    public static final String CHANNEL_ID = "Cat channel 1";

    private int counter = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Звук Кота
        createNewSoundPool();

        mAssetManager = getAssets();


    }

    static final private int Choose_Thief = 0;

    public void onButtonClick(View view) {
        Intent questionIntent = new Intent(MainActivity.this, ScreenTwo.class);
        startActivityForResult(questionIntent, Choose_Thief);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView infoTextView = findViewById(R.id.textView0);

        if(requestCode  == Choose_Thief){
            if(resultCode == RESULT_OK) {
                String thiefName = data.getStringExtra(ScreenTwo.THIEF);
                infoTextView.setText(thiefName);
            }else{
                infoTextView.setText("");
            }
        }
    }

    //Показать уведомление
    public void notificationShow(String setContentText){

        Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent =PendingIntent.getActivity(MainActivity.this, 0 , notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long[] vibrate = new long[] {100, 300};


        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_camera_black_24dp)
                .setContentTitle("Предупреждение")
                .setContentText(setContentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setVibrate(vibrate)
                .setAutoCancel(true)
                .setContentIntent(contentIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
        notificationManagerCompat.notify(counter++, builder.build());

    }


    public void onCameraClick(View view) {

        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popupmenu);

        popupMenu.
                setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.menu1:

                                //Звук кота
                                //mSoundcat = loadSound("cat.ogg");
                                playSound(mSoundcat);


                                // Создание оповещение о просмотре камер
                                notificationShow("Пользователь смотрел камеру кота");


                                // проверяем реакцию git
                                // Переход на камеру с котом
                                Intent intent = new Intent(MainActivity.this, Camera_movie.class);
                                startActivity(intent);

                                return true;
                                case R.id.menu2:

                                // Создание оповещение о просмотре камер
                                notificationShow("Пользователь пытался смотреть камеру собаки");

                                //Звук собаки
                                //mSoundDog = loadSound("dog.ogg");
                                playSound(mSoundDog);

                                Toast.makeText(MainActivity.this, "Камера еще не установлена", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu3:
                                notificationShow("Пользователь пытался смотреть камеру вороны");
                                Toast.makeText(MainActivity.this, "Камера еще не установлена.", Toast.LENGTH_SHORT).show();
                                return true;

                                default:
                                    return false;
                        }
                    }
                });

        // Обработка тостами случай закрытия меню
       /* popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu popupMenu) {
                Toast.makeText(MainActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
            }
        });
        */

        popupMenu.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAboutProgrammerClick(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, About_programmer.class);
        startActivity(intent);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();
    }

    private int playSound(int sound){

        if(sound>0){
            mStreamID = mSoundPool.play(sound,1,1,1,0,1);
        }
        return mStreamID;
    }


    private int loadSound(String filename){
        AssetFileDescriptor afd;
        try{
            afd = mAssetManager.openFd(filename);
        }catch(IOException e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Не удалось загрузить файл " + filename, Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }


    @Override
    protected void onPause(){
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }


}
