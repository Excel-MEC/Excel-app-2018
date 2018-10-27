package ml.arjunnair.excelapp.ui;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import ml.arjunnair.excelapp.R;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // previously invisible view
        final View excelLogo = getView().findViewById(R.id.imExcelLogo);


        excelLogo.postDelayed(new Runnable() {
            @Override
            public void run() {
                logoAnimation(excelLogo);

            }
        }, 1000);

    }

    void logoAnimation(View logo) {
        AnimationSet animationSet = new AnimationSet(false);

        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = logo.getWidth() / 2;
            int cy = logo.getHeight() / 2;

            // get the final radius for the clipping circle
            float finalRadius = (float) Math.hypot(cx, cy);

            // create the animator for this view (the start radius is zero)
            Animator reveal =
                    ViewAnimationUtils.createCircularReveal(logo, cx, cy, 0, finalRadius);

            // make the view visible and start the animation
            logo.setVisibility(View.VISIBLE);
            ConstraintLayout bgLayout = getView().findViewById(R.id.home_constraint);
            bgLayout.setBackgroundResource(R.drawable.animation_list);
            TransitionDrawable bgTrans = (TransitionDrawable) bgLayout.getBackground();
            bgTrans.startTransition(500);

            Animation moveUp = new TranslateAnimation(0, 0, 80, 0);
            moveUp.setInterpolator(new OvershootInterpolator());
            moveUp.setDuration(1000);
            moveUp.setFillAfter(true);

            Animation scaleUp = new ScaleAnimation((float)0.8, (float)1, (float)0.8, (float)1,
                    logo.getPivotX(), logo.getPivotY());
            scaleUp.setInterpolator(new OvershootInterpolator(2));
            scaleUp.setFillAfter(true);
            scaleUp.setDuration(1000);
//                    Animation scaleDown = new ScaleAnimation((float)1, (float)(1/1.1), (float)1, (float)(1/1.1),
//                            logo.getPivotX(), logo.getPivotY());
//                    scaleDown.setFillAfter(true);
//                    scaleDown.setStartOffset(1010);
//                    scaleDown.setDuration(500);

//                    Animation moveDown = new TranslateAnimation(0, 0, 0, 5);
//                    moveDown.setInterpolator(new DecelerateInterpolator(2));
//                    moveDown.setStartOffset(1010);
//                    moveDown.setDuration(500);
//                    moveDown.setFillAfter(true);

            AnimationSet jumpAnim = new AnimationSet(false);
            jumpAnim.setFillAfter(true);
            jumpAnim.addAnimation(moveUp);
            jumpAnim.addAnimation(scaleUp);
//                    jumpAnim.addAnimation(scaleDown);
//                    jumpAnim.addAnimation(moveDown);
            Log.d(TAG, "run: " + jumpAnim.getDuration());

            logo.startAnimation(jumpAnim);

            reveal.setDuration(500);
            reveal.setInterpolator(new AccelerateInterpolator(3));
            reveal.start();
        } else {
            logo.setVisibility(View.VISIBLE);
        }

    }

}
