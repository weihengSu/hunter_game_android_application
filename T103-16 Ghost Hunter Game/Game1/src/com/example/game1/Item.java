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

public class Item {
	private float[] position;
	String id;
	Bitmap map;
	
	public Item (String type, float initX, float initY, Bitmap map) {
		this.id = type;
		position = new float[2];
		this.position[0] = initX;
		this.position[1] = initY;
		this.map = map;
	}
	
	public Bitmap getMap() {
		return map;
	}

	public void setMap(Bitmap map) {
		this.map = map;
	}

	public float getX() {
		return this.position[0];
	}
	
	public float getY() {
		return this.position[1];
	}
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
}
