package com.refect.flamethrowerpumpkin;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;

import java.util.ArrayList;
import java.util.Random;

import at.abraxas.amarino.Amarino;

public class MainActivity extends AppCompatActivity implements MessageApi.MessageListener {

    private static final String DEVICE_ADDRESS = "00:06:66:45:0E:97";

    private ImageView pumpkinA;
    private ImageView pumpkinB;
    private ImageView pumpkinC;
    private ImageView pumpkinD;

    private Button btnStart;
    private Button btnReset;

    private Button btnGreen;
    private Button btnRed;
    private Button btnYellow;
    private Button btnBlue;

    private ArrayList<Integer> simonValues;
    private ArrayList<Integer> userValues;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect(); //make sure it's connected to bluetooth
        simonValues = new ArrayList<>();
        userValues = new ArrayList<>();

        btnGreen = (Button) findViewById(R.id.btnGreen);
        btnRed = (Button) findViewById(R.id.btnRed);
        btnYellow = (Button) findViewById(R.id.btnYellow);
        btnBlue = (Button) findViewById(R.id.btnBlue);

        btnBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValues.add(1);
                checkIfCorrectValue();
            }
        });

        btnRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValues.add(2);
                checkIfCorrectValue();
            }
        });

        btnYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValues.add(3);
                checkIfCorrectValue();
            }
        });

        btnGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userValues.add(4);
                checkIfCorrectValue();
            }
        });

        btnStart = (Button) findViewById(R.id.btnStart);
        btnReset = (Button) findViewById(R.id.btnReset);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReset.performClick();
                startGame();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simonValues = new ArrayList<>();
                userValues = new ArrayList<>();
            }
        });

        pumpkinA = (ImageView) findViewById(R.id.imageView1);
        pumpkinA.setSelected(false);
        pumpkinA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pumpkinA.setSelected(!pumpkinA.isSelected());

                if(pumpkinA.isSelected()) {
                    pumpkinA.setAlpha(.5f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'a', 1);
                } else {
                    pumpkinA.setAlpha(1f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'a', 0);
                }
            }
        });

        pumpkinB = (ImageView) findViewById(R.id.imageView2);
        pumpkinB.setSelected(false);
        pumpkinB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pumpkinB.setSelected(!pumpkinB.isSelected());

                if(pumpkinB.isSelected()) {
                    pumpkinB.setAlpha(.5f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'b', 1);
                } else {
                    pumpkinB.setAlpha(1f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'b', 0);
                }
            }
        });

        pumpkinC = (ImageView) findViewById(R.id.imageView3);
        pumpkinC.setSelected(false);
        pumpkinC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pumpkinC.setSelected(!pumpkinC.isSelected());

                if(pumpkinC.isSelected()) {
                    pumpkinC.setAlpha(.5f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'c', 1);
                } else {
                    pumpkinC.setAlpha(1f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'c', 0);
                }
            }
        });

        pumpkinD = (ImageView) findViewById(R.id.imageView4);
        pumpkinD.setSelected(false);
        pumpkinD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pumpkinD.setSelected(!pumpkinD.isSelected());

                if(pumpkinD.isSelected()) {
                    pumpkinD.setAlpha(.5f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'd', 1);
                } else {
                    pumpkinD.setAlpha(1f);
                    Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 'd', 0);
                }
            }
        });
    }

    /**
     * Initial start to game.
     * Will get called after user
     * has entered all their values
     * correctly
     */
    private void startGame() {
        simonValues.add(generateRandomInt());
        btnGreen.setClickable(false);
        btnRed.setClickable(false);
        btnYellow.setClickable(false);
        btnBlue.setClickable(false);
        playValues();
        btnGreen.setClickable(true);
        btnRed.setClickable(true);
        btnYellow.setClickable(true);
        btnBlue.setClickable(true);
        userInput();
    }

    /**
     * Will play back all values in list
     */
    private void playValues() {
        int[] data = new int[simonValues.size()];
        for(int i = 0; i < simonValues.size(); i++) {
            data[i] = simonValues.get(i);
        }

        Amarino.sendDataToArduino(getApplicationContext(), DEVICE_ADDRESS, 's', data);

        for(int i = 0; i < simonValues.size(); i++) {
            Log.i("Simon: ", simonValues.get(i) + "");
        }
    }

    /**
     * Allow user to input the sequence back
     */
    private void userInput() {
        userValues.clear();
        //button presses. idk
    }

    /**
     * Checks to see if the number
     * entered is the same as Simon's
     */
    private void checkIfCorrectValue() {
        if(userValues.get(userValues.size() - 1) != simonValues.get(userValues.size() - 1)) {
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
            btnReset.performClick();
        } else if(userValues.size() == simonValues.size()) {
            startGame();
        }
    }

    private int generateRandomInt() {
        Random random = new Random();
        return random.nextInt(5 - 1) + 1;
    }

    /**
     * Connect to the Arduino via bluetooth
     */
    protected void connect() {
        if (!Amarino.isCorrectAddressFormat(DEVICE_ADDRESS)) {
        }
        //_some check to see if connected_
        Amarino.connect(this, DEVICE_ADDRESS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String fullMessage = new String(messageEvent.getData());
        Toast.makeText(getApplicationContext(), fullMessage, Toast.LENGTH_SHORT).show();
        if(fullMessage.equals("blue")) {
            btnBlue.performClick();
        } else if(fullMessage.equals("red")) {
            btnRed.performClick();
        } else if(fullMessage.equals("yellow")) {
            btnYellow.performClick();
        } else if(fullMessage.equals("green")) {
            btnGreen.performClick();
        } else if(fullMessage.equals("start")) {
            btnStart.performClick();
        }
    }
}
