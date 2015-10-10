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

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	// Creates the threads
	public MainThread thread;

	// boolean to store if the game is paused
	public boolean Pause_game;

	// private Background background;

	// Stores the player
	public Player player;

	// Stores Ghosts, items, and friends (Green ghosts)
	public ArrayList<Ghost> ghostList;
	public ArrayList<Item> itemList;
	public ArrayList<Friend> friendList;

	// Persistent Bitmaps
	public Bitmap playerbit;
	public Bitmap backBit;
	public Bitmap backgroundBit;

	public Bitmap clearghostBit;
	public Bitmap greyGhost1;
	public Bitmap greyGhost2;
	public Bitmap greyGhost3;
	public Bitmap redGhost;
	public Bitmap greenGhost;

	public Bitmap targetBit;
	public Bitmap potionBit;
	public Bitmap coinBit;

	RelativeLayout Rel_main_game;
	View rightButton;
	View leftButton;
	View upButton;
	View downButton;
	View killButton;

	public int turn = 0;

	int score;

	Paint textStyle;
	Paint clearStyle;

	String message1;
	String message2;
	String message3;

	int ghostSize;

	public ArrayList<Ghost> tempKillList;
	public int killNextTurn;
	
	public GamePanel(Context context, Game game) {
		super(context);
		getHolder().addCallback(this);
		thread = new MainThread(getHolder(), this);
		setFocusable(true);

		// this.background = new
		// Background(BitmapFactory.decodeResource(getResources(),
		// R.drawable.background_tiles),750,this);
		this.backgroundBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.backgroundbit);
		// Decodes bitmaps from /res
		this.playerbit = BitmapFactory.decodeResource(getResources(),
				R.drawable.personfinal);

		this.clearghostBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghostfinal);
		this.greyGhost1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghostfinalgrey1);
		this.greyGhost2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghostfinalgrey2);
		this.greyGhost3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghostfinalgrey3a);
		this.redGhost = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghostfinalred);
		this.greenGhost = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghostfinalgreen);

		this.targetBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghosttarget);
		this.potionBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.potion);
		this.coinBit = BitmapFactory.decodeResource(getResources(),
				R.drawable.loot);

		textStyle = new Paint();

		textStyle.setColor(Color.WHITE);
		textStyle.setStyle(Style.FILL);
		textStyle.setTextSize(20);

		clearStyle = new Paint();
		clearStyle.setColor(Color.BLACK);
		clearStyle.setStyle(Style.FILL);

		this.player = new Player(0, 0);
		this.ghostList = new ArrayList<Ghost>();
		this.itemList = new ArrayList<Item>();
		this.ghostSize = 0;

		this.friendList = new ArrayList<Friend>();
		this.tempKillList = new ArrayList<Ghost>();

		ghostList.add(new Ghost(5, 5, this.greyGhost1));

		itemList.add(new Item("Kill Potion", 5, 3, potionBit));

		message1 = "Welcome, player!";
		message2 = "Move with arrows. Use center button to kill ghosts";
		message3 = "and pick up items while adjacent";

		score = 0;
		killNextTurn = 0;
	}

	public boolean onTouchEvent(MotionEvent event) {
		Log.d("Tap Pos:", "{" + event.getRawX() + "," + event.getRawY() + "}");
		return super.onTouchEvent(event);
	}

	public void println(String message) {
		this.message1 = this.message2;
		this.message2 = this.message3;
		this.message3 = message;
	}

	void Draw(Canvas canvas) {

		// canvas.drawPaint(textStyle);
		canvas.drawRect(0, 0, 1280, 1280, clearStyle);
		canvas.drawBitmap(this.backgroundBit, 0, 0, null);

		canvas.drawBitmap(this.playerbit, translate(player.getX()),
				translate(player.getY()), null);

		ArrayList<Ghost> tempGhosts = this.ghostList;
		ArrayList<Item> tempItems = this.itemList;
		ArrayList<Friend> tempFriends = this.friendList;
		try {
			for (Ghost g : tempGhosts) {
				canvas.drawBitmap(g.ghostMap, translate(g.getX()),
						translate(g.getY()), null);
			}
	
			for (Item o : tempItems) {
				canvas.drawBitmap(o.map, translate(o.getX()), translate(o.getY()),
						null);
			}
	
			for(Friend f : tempFriends) {
				canvas.drawBitmap(f.ghostMap, translate(f.getX()), translate(f.getY()), null);
			}
			
			
		} catch (Exception e) {
			
		}

		canvas.drawText("Score: " + this.score, 815, 150, textStyle);
		canvas.drawText("Ghosts: " + this.ghostSize, 815, 200, textStyle);
		canvas.drawText("Potions: " + this.player.getAmmo(), 815, 250, textStyle);
		canvas.drawText("Turn: " + this.turn, 815, 300, textStyle);

		canvas.drawText(this.message1, 815, 400, textStyle);
		canvas.drawText(this.message2, 815, 450, textStyle);
		canvas.drawText(this.message3, 815, 500, textStyle);


	}

	void Update(float dt) {

	}

	public static int randX() {
		return (int) (Math.random() * 10);
	}

	public static int randY() {
		return (int) (Math.random() * 8);
	}

	public void spawnGhost() {

		double rand = Math.random() * 100;
		if (rand <= 33) {
			this.ghostList.add(new Ghost(randX(), randY(), this.greyGhost1));
		} else if (rand <= 66) {
			this.ghostList.add(new Ghost(randX(), randY(), this.greyGhost2));
		} else {
			this.ghostList.add(new Ghost(randX(), randY(), this.greyGhost3));
		}

		println("An enemy ghost has spawned!");
	}

	public void spawnFriend() {
		if (this.friendList.size() == 0) {
			this.friendList.add(new Friend(randX(), randY(), this.greenGhost));
			println("A friendly ghost has spawned!");
			println("Your friend will kill the next ghost it touches");
		}
	}

	public void spawnPotion() {

		this.itemList.add(new Item("Kill Potion", randX(), randY(), potionBit));

		println("A potion has spawned!");

	}

	public void advanceTurn() {

		turn++;
		moveGhosts();

		int rand = (int) (Math.random() * 100);
		if (rand < turn / 5) {
			spawnGhost();
		} else if ((int) rand % 5 == 0) {
			spawnFriend();
		}
		int rand2 = (int) (Math.random() * 100);
		if (turn % 10 == 0) {
			spawnPotion();
		}
		ArrayList<Ghost> killList = new ArrayList<Ghost>();

		for (Ghost g : ghostList) {
			if(this.friendList.isEmpty()) {
				float px = this.player.getX();
				float py = this.player.getY();
				float gx = g.getX();
				float gy = g.getY();

				boolean playerClose = ((gx == px - 1 && gy == py - 1) || (gx == px && gy == py - 1)
						|| (gx == px + 1 && gy == py - 1)
						|| (gx == px - 1 && gy == py) || (gx == px && gy == py)
						|| (gx == px + 1 && gy == py)
						|| (gx == px - 1 && gy == py + 1)
						|| (gx == px && gy == py + 1)
						|| (gx == px + 1 && gy == py + 1));
				if(playerClose) {
					g.ghostMap = this.redGhost;
				} else {
					g.ghostMap = g.originalMap;
				}
			} else {
				float px = this.player.getX();
				float py = this.player.getY();
				float gx = g.getX();
				float gy = g.getY();
				float fx = this.friendList.get(0).getX();
				float fy = this.friendList.get(0).getY();
				boolean playerClose = (gx == px - 1 && gy == py - 1) || (gx == px && gy == py - 1)
						|| (gx == px + 1 && gy == py - 1)
						|| (gx == px - 1 && gy == py) || (gx == px && gy == py)
						|| (gx == px + 1 && gy == py)
						|| (gx == px - 1 && gy == py + 1)
						|| (gx == px && gy == py + 1)
						|| (gx == px + 1 && gy == py + 1);
				boolean friendClose = ((gx == fx - 1 && gy == fy - 1)
						|| (gx == fx && gy == fy - 1)
						|| (gx == fx + 1 && gy == fy - 1)
						|| (gx == fx - 1 && gy == fy) || (gx == fx && gy == fy)
						|| (gx == fx + 1 && gy == fy)
						|| (gx == fx - 1 && gy == fy + 1)
						|| (gx == fx && gy == fy + 1) || (gx == fx + 1 && gy == fy + 1)
						);
				if(playerClose || friendClose) {
					g.ghostMap = this.redGhost;
				} else {
					g.ghostMap = g.originalMap;
				}
				
				if(friendClose) {
					tempKillList.add(g);
					this.friendList = new ArrayList<Friend>();
					this.killNextTurn = turn;
				}
			}
			
		}

		if(turn == killNextTurn + 1) {
			this.score = this.score + tempKillList.size();
			this.ghostList.removeAll(tempKillList);
			killNextTurn = 0;
			tempKillList = new ArrayList<Ghost>();
		}
		this.ghostSize = this.ghostList.size();
	}

	public void moveGhosts() {
		for (Friend f: friendList) {
			double rand = Math.random();
			int randInt = (int) (rand * 100);

			if (randInt > 0 && randInt < 25) {
				f.moveDown();

			}
			if (randInt > 25 && randInt < 50) {
				f.moveUp();

			}
			if (randInt > 50 && randInt < 75) {
				f.moveLeft();

			}
			if (randInt > 75 && randInt < 100) {
				f.moveRight();

			}
		}

		for (Ghost g : ghostList) {

			double rand = Math.random();
			int randInt = (int) (rand * 100);
			if (randInt > 0 && randInt < 25) {
				g.moveDown();

			}
			if (randInt > 25 && randInt < 50) {
				g.moveUp();

			}
			if (randInt > 50 && randInt < 75) {
				g.moveLeft();

			}
			if (randInt > 75 && randInt < 100) {
				g.moveRight();

			}
			/*
			 * 
			 * float playerPosX = this.player.getX(); float playerPosY =
			 * this.player.getY();
			 * 
			 * float ghostPosX = g.getX(); float ghostPosY = g.getY();
			 * 
			 * float vectorX = playerPosX - ghostPosX; float vectorY =
			 * playerPosY - ghostPosY; double angle = 0;
			 * 
			 * try { angle = Math.atan(vectorY / vectorX); } catch (Exception e)
			 * { if (ghostPosY > playerPosY) { g.moveDown(); } else if
			 * (ghostPosY < playerPosY) { g.moveUp(); } else break; }
			 * 
			 * 
			 * if(playerPosX > ghostPosX) { if(angle >= -Math.PI/4 && angle <=
			 * Math.PI/4) { g.moveRight(); } else if ( angle > Math.PI/4) {
			 * g.moveUp(); } else g.moveDown(); } else if (playerPosX <
			 * ghostPosX) { if(angle >= -Math.PI/4 && angle <=Math.PI/4) {
			 * g.moveLeft(); } else if ( angle > Math.PI/4) { g.moveUp(); } else
			 * g.moveDown(); }
			 */
		}
	}

	// translates board position to actual pixel position.
	public static float translate(float x) {
		return 35+x * 70;
	}

	public static boolean checkCollision(Movable m1, Movable m2) {
		if (m1.getX() == m2.getX() && m1.getY() == m2.getY()) {
			return true;
		} else
			return false;
	}

	public static boolean checkAllCollision(ArrayList<Ghost> ghostList2,
			Movable m) {
		for (Movable mv : ghostList2) {
			if (mv.getX() == m.getX() && mv.getY() == m.getY()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.setRunning(false);
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
