package ml.arjunnair.excelapp.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.ramotion.circlemenu.CircleMenuView;
import android.support.annotation.NonNull;

import ml.arjunnair.excelapp.EventFragment;
import ml.arjunnair.excelapp.R;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Initialize fragment
        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, new HomeFragment());
        fragmentTransaction.commit();


        final com.ramotion.circlemenu.CircleMenuView circleMenu = (CircleMenuView) findViewById(R.id.circle_menu);
        circleMenu.setEventListener(new CircleMenuView.EventListener() {

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
                Log.d("D", "onButtonClickAnimationEnd| index: " + index);
                changeFragment(index);
            }
        });

        circleMenu.postDelayed(new Runnable() {
            @Override
            public void run() {
                fadeInDropDown(circleMenu);
            }
        }, 1500);
    }

    private void changeFragment(int index) {
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.fragmentContainer);

        Class newFragmentClass = HomeFragment.class;

        switch (index) {
            case 0:
                newFragmentClass = HomeFragment.class;
                break;
            case 1:
                newFragmentClass = EventFragment.class;
                break;
            case 2:
                newFragmentClass = CompetitionsFragment.class;
                break;
        }

        if (currentFragment.getClass() == newFragmentClass) {
            return;
        }
        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, (Fragment) newFragmentClass.newInstance());
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }


    void fadeInDropDown(final View button) {
        final Animation moveDown = new TranslateAnimation(0, 0, -30, 0);
        moveDown.setInterpolator(new AccelerateDecelerateInterpolator());
        moveDown.setDuration(500);
        moveDown.setFillAfter(true);
        button.setVisibility(View.VISIBLE);
        button.startAnimation(moveDown);
    }

}
