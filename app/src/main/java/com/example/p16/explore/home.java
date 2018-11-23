package com.example.p16.explore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class home extends MyMenu {


    ViewPager viewPager;
    LinearLayout sliderDots;
    private int dotCount;
    private ImageView[] dots;
    GoogleSignInClient mGoogleSignInClient;




    public void homeMethod(View v) {
        Toast.makeText(this, "you are on home page",Toast.LENGTH_LONG).show();
    }


    public void BookmarkMethod(View v) {
        Intent i = new Intent(this, Bookmark.class);
        startActivity(i);
    }
    public void SearchMethod(View v) {
        Intent i = new Intent(this, Search_Page.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button  culture_page = (Button) findViewById(R.id.btnCulture);
        culture_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPage = new Intent(home.this,Culture_page.class);
                startActivity(newPage);

            }
        });

        Button  tour_page = (Button) findViewById(R.id.btnTour);
        tour_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPage = new Intent(home.this,Tour_page.class);
                startActivity(newPage);

            }
        });


       //slider code starts here

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDots = (LinearLayout) findViewById(R.id.SliderDots);

         Timer time = new Timer();
         time.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotCount];

        for(int i=0; i<dotCount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonselected_circle));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 8 , 8, 8);
            sliderDots.addView(dots[i],params);

        }
     dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_circle));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int position) {

                for(int j =0; j<dotCount; j++){
                    dots[j].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonselected_circle));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.selected_circle));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //slider code ends here
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    }else{
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
