package com.example.ayushverma.tictactoe;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int PlayerRed=0;

    private int lastPlayer=-1;
    private ImageView lastimage;
    private int lastplace=-1;
    private String lastchance;
    private boolean undoAcceptability=true;

    private int PlayerBlue=0;

    //Red=0  Blue=1
    private int activePlayer = 0;

    private boolean gameActive = true;

    //Red=0  Blue=1  Empty=2
    private int[] placeState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    private int[][] winingStates = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private String message;

    public void start(View view) {

        ImageView image = (ImageView) view;

        int place = Integer.parseInt(image.getTag().toString());

        if (gameActive) {
            //If game is Active

            if (placeState[place] == 2) {
                //Place is empty to be played

                //For Undo
                lastplace =place;
                lastPlayer=activePlayer;
                lastimage=image;

                if (activePlayer == 0) {
                    //Red Player Chance

                    lastchance="Red";
//                    image.setVisibility(View.INVISIBLE);
                    image.setImageResource(R.drawable.redplayer);
//                    image.setScaleX(.5f);
//                    image.setScaleY(.5f);
                    image.setVisibility(View.VISIBLE);
//                    image.animate().scaleXBy(0.6f).scaleYBy(0.6f).setDuration(1000);
                    placeState[place] = activePlayer;
                    activePlayer = 1;
                    TextView playerChance=findViewById(R.id.PlayerChance);
                    playerChance.setText("Blue");
                    playerChance.setTextColor(Color.BLUE);

                } else {
                    //Blue Player Chance

                    lastchance="Blue";
//                    image.setVisibility(View.INVISIBLE);
                    image.setImageResource(R.drawable.blueplayer);
//                    image.setScaleX(.5f);
//                    image.setScaleY(.5f);
                    image.setVisibility(View.VISIBLE);
//                    image.animate().scaleXBy(0.6f).scaleYBy(0.6f).setDuration(1000);
                    placeState[place] = activePlayer;
                    activePlayer = 0;
                    TextView playerChance=findViewById(R.id.PlayerChance);
                    playerChance.setText("Red");
                    playerChance.setTextColor(Color.RED);
                }

                //check if anyone win
                boolean tem = checkWin();
                if (tem) {
                    //Displaying Winner

                    LinearLayout linearLayout = findViewById(R.id.LinearLayout);
                    TextView textView = findViewById(R.id.displayMessage);
                    textView.setText(message);
                    linearLayout.setVisibility(View.VISIBLE);
                    gameActive = false;

                } else {
                    //Checking Draw

                    boolean temp = checkDraw();
                    if (temp) {
                        message = "Game Draw";
                        LinearLayout linearLayout = findViewById(R.id.LinearLayout);
                        TextView textView = findViewById(R.id.displayMessage);
                        textView.setText(message);
                        linearLayout.setVisibility(View.VISIBLE);
                        gameActive = false;
                    }
                }

            } else {
                //Place is not empty to be played

                Toast.makeText(this, "Sorry! Place is not empty", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void tryAgain(View view) {
        activePlayer = 0;
        gameActive = true;
        for (int i = 0; i < placeState.length; i++)
            placeState[i] = 2;
        LinearLayout linearLayout = findViewById(R.id.LinearLayout);
        linearLayout.setVisibility(View.INVISIBLE);
        TextView textView = findViewById(R.id.displayMessage);
        textView.setText("");

        message = "";

        GridLayout gridLayout = findViewById(R.id.board);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

        }

        TextView playerChance=findViewById(R.id.PlayerChance);
        playerChance.setText("Red");
        playerChance.setTextColor(Color.RED);
        undoAcceptability=true;
    }

    public boolean checkDraw() {

        for (int i : placeState) {
            if (i == 2)
                return false;
        }
        undoAcceptability=false;
        return true;

    }

    public boolean checkWin() {

        for (int[] check : winingStates) {

            if (placeState[check[0]] == placeState[check[1]] && placeState[check[1]] == placeState[check[2]] && placeState[check[2]] != 2) {
                if (placeState[check[0]] == 0) {
                    message = "Congratulations! Player Red, you won";
                    PlayerRed++;
                    TextView rednumber=findViewById(R.id.RedNumber);
                    rednumber.setText(Integer.toString(PlayerRed));
                }
                else{
                    message = "Congratulations! Player Blue, you won";
                    PlayerBlue++;
                    TextView bluenumber=findViewById(R.id.BlueNumber);
                    bluenumber.setText(Integer.toString(PlayerBlue));
                }
                undoAcceptability=false;
                return true;
            }

        }
        return false;
    }

    public void undoClick(View view){
        if(undoAcceptability) {
            if (lastPlayer != -1) {

                activePlayer = lastPlayer;
                placeState[lastplace] = 2;
                lastimage.setImageResource(0);
                TextView lastchan = findViewById(R.id.PlayerChance);
                lastchan.setText(lastchance);
                if(lastchance.equalsIgnoreCase("Red")){
                    lastchan.setTextColor(Color.RED);
                }else{
                    lastchan.setTextColor(Color.BLUE);
                }

            } else {
                //No last Move
                Toast.makeText(this, "Sorry! No last move", Toast.LENGTH_SHORT).show();

            }
        }else{
            //Undo not acceptible
            Toast.makeText(this, "Sorry! Undo not Acceptible", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView rednumber=findViewById(R.id.RedNumber);
        rednumber.setText(Integer.toString(PlayerRed));
        TextView bluenumber=findViewById(R.id.BlueNumber);
        bluenumber.setText(Integer.toString(PlayerBlue));
        TextView playerChance=findViewById(R.id.PlayerChance);
        playerChance.setText("Red");
        playerChance.setTextColor(Color.RED);
    }
}
