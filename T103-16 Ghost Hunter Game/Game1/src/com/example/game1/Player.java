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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player implements Movable {

	private float[] position;
	private int score;
	private int ammo;

	public Player(float initX, float initY) {
		position = new float[2];
		this.position[0] = initX;
		this.position[1] = initY;

		this.score = 0;
		this.ammo = 3;
	}

	public void moveUp() {

		float dp = position[1] - 1;
		if(!(dp < 0 || dp > 8)) position[1] = dp;

	}

	public void moveDown() {

		float dp = position[1] + 1;
		if(!(dp < 0 || dp > 8))position[1] = dp;

	}

	public void moveRight() {

		float dp = position[0] + 1;
		if(!(dp < 0 || dp > 9)) position[0] = dp;

	}

	public void moveLeft() {

		float dp = position[0] - 1;
		if(!(dp < 0 || dp > 9)) position[0] = dp;

	} 
	
	public void moveUpRight() {
		float dx = position[0] + 1;
		float dy = position[1] - 1;
		
		if(!((dy < 0 || dy > 8) || (dx < 0 || dx > 9))) {
			position[0] = dx;
			position[1] = dy;
		}
	}
	public void moveUpLeft() {
		float dx = position[0] - 1;
		float dy = position[1] - 1;
		
		if(!((dy < 0 || dy > 8) || (dx < 0 || dx > 9))) {
			position[0] = dx;
			position[1] = dy;
		}
	}
	public void moveDownRight() {
		float dx = position[0] + 1;
		float dy = position[1] + 1;
		
		if(!((dy < 0 || dy > 8) || (dx < 0 || dx > 9))) {
			position[0] = dx;
			position[1] = dy;
		}
	}
	public void moveDownLeft() {
		float dx = position[0] - 1;
		float dy = position[1] + 1;
		
		if(!((dy < 0 || dy > 8) || (dx < 0 || dx > 9))) {
			position[0] = dx;
			position[1] = dy;
		}
	}
	
	public float getX() {
		return this.position[0];
	}
	public float getY() {
		return this.position[1];
	}
	public void removeAmmo() {
		if(this.ammo > 0) {
			this.ammo--;
		}
	}
	public int getAmmo() {
		return this.ammo;
	}
	public void addAmmo(int n) {
		this.ammo = this.ammo + n;
	}

}
