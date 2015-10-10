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

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	
	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;
	public boolean running;
	float dt;

	public MainThread(SurfaceHolder holder, GamePanel gamePanel) {
		this.surfaceHolder = holder;
		this.gamePanel = gamePanel; 
		dt = 0;
	}
	
	void setRunning(boolean running) {
		this.running = running;
	
	}
	
	@Override
	public void run() {
		Canvas canvas = null;
		
		while (running) {
			if (!gamePanel.Pause_game) {
				long StartDraw = System.currentTimeMillis();
				canvas = null;
				try{
					canvas = this.surfaceHolder.lockCanvas();
					syschronized (surfaceHolder); {
						gamePanel.Update(dt);
						gamePanel.Draw(canvas);
						
					}
				} catch (NullPointerException e ) {
					
				} finally {
					if (canvas != null)
						surfaceHolder.unlockCanvasAndPost(canvas);
					else break;
				}
				
				long tmDraw = System.currentTimeMillis(); 
				dt = (tmDraw - StartDraw)/1000.f;
				
			}
		}
	}

	private void syschronized(SurfaceHolder surfaceHolder2) {
		// TODO Auto-generated method stub
		
	}

}
