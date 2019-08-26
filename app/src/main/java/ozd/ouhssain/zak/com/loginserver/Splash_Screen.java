package ozd.ouhssain.zak.com.loginserver;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash_Screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        /*Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(4500);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent obj = new Intent(Splash_Screen.this, Login.class);
                    startActivity(obj);
                }
            }

        };
        th.start();*/

        StartAnimations();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.splash);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.mypic);
        TextView tv = (TextView)findViewById(R.id.textView);
        iv.clearAnimation();
        iv.startAnimation(anim);

        tv.clearAnimation();
        tv.startAnimation(anim);

        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splash_Screen.this,
                            Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splash_Screen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splash_Screen.this.finish();
                }

            }
        };
        th.start();


    }

}
