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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	RelativeLayout Btn;
	ImageView ImageButton;
	TextView txt;
	MediaPlayer fx;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fx = MediaPlayer.create(getApplicationContext(), R.raw.titlescreen);
		fx.start();
		
		Btn = (RelativeLayout) findViewById(R.id.btn_start);
		ImageButton = (ImageView) findViewById(R.id.img_btn);
		txt = (TextView) findViewById(R.id.text_start);
		
		Typeface Custom = Typeface.createFromAsset(getAssets(), "ALGER.TTF");
		txt.setTypeface(Custom);
		
		Btn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
				
			}
		});
		
		Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fx.stop();
				Intent myIntent = new Intent(MainActivity.this, Game.class);
				startActivity(myIntent);
			}
		});
	}
}


/**	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
//	}
	} */
