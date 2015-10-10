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

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
	Bitmap BackBitmap;
	int x,y;
	int ScreenWidth;
	int Count_Background;
	GamePanel root_gamePanel;
	
	public Background(Bitmap bitmap, int Screen_w, GamePanel Game_panel) {
		this.BackBitmap = bitmap;
		this.x = 0;
		this.y = 0;
		this.ScreenWidth = Screen_w;
		Count_Background = ScreenWidth/BackBitmap.getWidth()*1;
		root_gamePanel = Game_panel;
		
	}
	public void draw(Canvas canvas) {
		canvas.drawBitmap(BackBitmap, 750,750, null);	
	}
		
	
	
	public void update(float dt) {
		
		
	}

}
