package pl.edu.kasprzak.grazzegarem;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GraActivity extends AppCompatActivity {

    // Dzięki static stan zmiennych nie  zmienia się
    static boolean runningClock = false;
    static int counter = 100;

    private Runnable worker;
    private Button action;
    private TextView clock;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gra);
        action = (Button)findViewById(R.id.action);
        clock = (TextView)findViewById(R.id.clock);
        worker = new Runnable() {
            @Override
            public void run() {
                clock.setText("" + counter);
                --counter;
                if (runningClock) {
                    handler.postDelayed(worker, 1);
                }
            }
        };
        handler = new Handler();
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!runningClock) {
                    counter = 100;
                    runningClock = true;
                    handler.postDelayed(worker, 1);
                } else {
                    runningClock = false;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(worker);

        // zatrzymanie
        Log.d("CYKL_ZYCIA", "ONPAUSE");
    }

    @Override
    protected void onResume() {
        //if clock running
        super.onResume();
        handler.postDelayed(worker, 1);
        //wznowienie
        Log.d("CYKL_ZYCIA", "ONRESUME");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CYKL_ZYCIA", "ONSTART");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CYKL_ZYCIA", "ONSTOP");
    }
}
