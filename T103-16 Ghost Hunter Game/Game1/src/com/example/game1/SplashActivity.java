/**
 * T103-16
 * 
 * Sanjana Mendu (sm7gc)
 * Yanre Wu (yx7fa)
 * Weiheng Su (ws2ta)
 * Anne Gent (aeg5gf)
 * Jacob Saltzman (jss7kj)
 */

package com.example.game1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {
	public static final int DURATION = 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		TextView splashText = (TextView) findViewById(R.id.splash_txt);
		TextView splashText2 = (TextView) findViewById(R.id.splash_txt2);
		Typeface font = Typeface.createFromAsset(getAssets(), "ALGER.TTF");
		splashText.setTypeface(font, Typeface.BOLD);
		splashText2.setTypeface(font, Typeface.BOLD);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}, DURATION);
	}
}
