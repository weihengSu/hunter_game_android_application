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

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Game extends Activity {
	View controls;
	View PauseMenu;
	RelativeLayout Rel_main_game;
	GamePanel game_panel;
	View rightButton;
	View leftButton;
	View upButton;
	View downButton;
	View killButton;
	MediaPlayer fx;
	
	
	OnClickListener Continue_list = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			PauseMenu.setVisibility(View.GONE);
			controls.setVisibility(View.VISIBLE);
			fx.start();
		}
	};
	
	OnClickListener To_Main_Menu_list = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Game.this.finish();
			
		}
	};
	

	OnClickListener Pause_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			controls.setVisibility(View.GONE);
			PauseMenu.setVisibility(View.VISIBLE);
			fx.pause();
		}
	};

	OnClickListener Right_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			game_panel.player.moveRight();
			ArrayList<Ghost> tList = game_panel.ghostList;
			game_panel.advanceTurn();
			if (GamePanel.checkAllCollision(game_panel.ghostList,game_panel.player)){
				game_panel.ghostList = tList;
				game_panel.player.moveLeft();
			}
			
		}
	};
	
	OnClickListener Left_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			game_panel.player.moveLeft();
			ArrayList<Ghost> tList = game_panel.ghostList;
			game_panel.advanceTurn();
			if (GamePanel.checkAllCollision(game_panel.ghostList,game_panel.player)){
				game_panel.ghostList = tList;
				game_panel.player.moveRight();
			}
			
		}
	};

	OnClickListener Up_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			game_panel.player.moveUp();
			ArrayList<Ghost> tList = game_panel.ghostList;
			game_panel.advanceTurn();
			if (GamePanel.checkAllCollision(game_panel.ghostList,game_panel.player)){
				game_panel.ghostList = tList;
				game_panel.player.moveDown();
			}
			
		}
	};
	
	OnClickListener Down_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			game_panel.player.moveDown();
			ArrayList<Ghost> tList = game_panel.ghostList;
			game_panel.advanceTurn();
			if (GamePanel.checkAllCollision(game_panel.ghostList,game_panel.player)){
				game_panel.ghostList = tList;
				game_panel.player.moveUp();
			}
			
		}
	};

	OnClickListener Action_click = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (! collectableItems().isEmpty()) {
				collectItems();
			}
			if (! killableGhosts().isEmpty() && ! (game_panel.player.getAmmo() == 0)) {
				ghostLoot();
				killGhosts();
				game_panel.player.removeAmmo();
			} else if (! killableGhosts().isEmpty()) {
				game_panel.println("You're out of anti-ghost potion!");
			}
		}
	};
	
	public ArrayList<Ghost> killGhosts() {
		ArrayList<Ghost> toRemove = killableGhosts();
		int killedGhosts = 0;
		fx = MediaPlayer.create(getApplicationContext(), R.raw.ghostkill);
		fx.start();
		killedGhosts = toRemove.size();
		game_panel.ghostList.removeAll(toRemove);
		
		
		
		int rand = (int) (Math.random() * 100);
		if(rand < 20 && toRemove.size() > 1) {
			game_panel.println("You annihilate " + killedGhosts + " ghosts! Multiplier x" + killedGhosts);
		} else if (rand < 40 && toRemove.size() > 1) {
			game_panel.println( "You slaughter " + killedGhosts + " ghosts! Multiplier x" + killedGhosts);
		} else if (rand < 60 && toRemove.size() > 1) {
			game_panel.println( "You vaporize " + killedGhosts + " ghosts! Multiplier x" + killedGhosts);
		} else if (rand < 80 && toRemove.size() > 1) {
			game_panel.println( "You destroy " + killedGhosts + " ghosts! Multiplier x" + killedGhosts);
		} else if (toRemove.size() > 1){
			game_panel.println( "You obliterate " + killedGhosts + " ghosts! Multiplier x" + killedGhosts);
		} else if (rand < 20) {
			game_panel.println( "You annihilate a ghost!");
		} else if (rand < 40 ) {
			game_panel.println("You slaughter a ghost!");
		} else if (rand < 60) {
			game_panel.println("You vaporize a ghost!");
		} else if (rand < 80) {
			game_panel.println( "You destroy a ghost!");
		} else {
			game_panel.println("You obliterate a ghost!");
		}
		
		game_panel.score += killedGhosts*killedGhosts ;
		return toRemove;
	}
	
	public ArrayList<Ghost> killableGhosts() {
		ArrayList<Ghost> toRemove = new ArrayList<Ghost>();
		for (Ghost g : game_panel.ghostList) {
			float px = game_panel.player.getX();
			float py= game_panel.player.getY();
			float gx = g.getX();
			float gy = g.getY();
			if (
				(gx	== px-1 && gy==py-1) ||
				(gx	== px && gy==py-1) ||
				(gx	== px+1 && gy==py-1) ||
				(gx	== px-1 && gy==py) ||
				(gx	== px && gy==py) ||
				(gx	== px+1 && gy==py) ||
				(gx	== px-1 && gy==py+1) ||
				(gx	== px && gy==py+1) ||
				(gx	== px+1 && gy==py+1)
				) {
				toRemove.add(g);
			}
		}
		return toRemove;
	}
	
	public void collectItems() {
		ArrayList<Item> toRemove = collectableItems();
		int numPotions = 0;
		int numCoins = 0;
		for (Item element : toRemove) {
			if (element.getID().equals("Kill Potion")) {
				game_panel.player.addAmmo(1);
				numPotions++;
			}
			if (element.getID().equals("Coin")) numCoins++;
		}
		MediaPlayer fx1 = MediaPlayer.create(getApplicationContext(), R.raw.itemcollect);
		MediaPlayer fx2 = MediaPlayer.create(getApplicationContext(), R.raw.potion);
		game_panel.itemList.removeAll(toRemove);
		game_panel.message1 = game_panel.message2;
		if (numPotions>0 && numCoins==0) {
			fx2.start();
			if (numPotions> 1) {
				game_panel.println( "You collected " + numPotions + " " + toRemove.get(0).getID() + "s"); 
			} else { 
				game_panel.println( "You collected a " + toRemove.get(0).getID()); 
			}
		}
		else if (numPotions==0 && numCoins>0) {
			fx1.start();
			if (numCoins> 1) {
				game_panel.println( "You collected " + numCoins + " " + toRemove.get(0).getID() + "s"); 
			} else { 
				game_panel.println( "You collected a " + toRemove.get(0).getID()); 
			}
		}
		else if (numPotions>0 && numCoins>0){
			game_panel.println("You collected " + numPotions + " Kill Potions and " + numCoins + " Coins");
			fx1.start();
			fx2.start();
		}
		
		game_panel.score += numCoins + numPotions;
		/*for (Item i : toRemove) {
			
		}
		
		game_panel.score += collectedItems*collectedItems ;*/
	}
	
	public ArrayList<Item> collectableItems() {
		ArrayList<Item> toCollect = new ArrayList<Item>();
		for (Item i : game_panel.itemList) {
			float px = game_panel.player.getX();
			float py= game_panel.player.getY();
			float ix = i.getX();
			float iy = i.getY();
			if (
				(ix	== px-1 && iy==py-1) ||
				(ix	== px && iy==py-1) ||
				(ix	== px+1 && iy==py-1) ||
				(ix	== px-1 && iy==py) ||
				(ix	== px && iy==py) ||
				(ix	== px+1 && iy==py) ||
				(ix	== px-1 && iy==py+1) ||
				(ix	== px && iy==py+1) ||
				(ix	== px+1 && iy==py+1)
				) {
				toCollect.add(i);
			}
		}
		return toCollect;
	}
	
	public void ghostLoot() {
		for (Ghost g : killGhosts()) {
			game_panel.itemList.add(new Item("Coin",g.getX(),g.getY(),game_panel.coinBit));
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		Rel_main_game = (RelativeLayout) findViewById(R.id.main_game_ri);
		game_panel = new GamePanel(getApplicationContext(),this);
		Rel_main_game.addView(game_panel);
		DisplayMetrics de = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(de);
		fx = MediaPlayer.create(getApplicationContext(), R.raw.game);
		fx.setLooping(true);
		fx.start();
		
		final int heights = de.heightPixels;
		final int widths = de.widthPixels;
		
		
		getApplicationContext();
		LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		controls = myInflater.inflate(R.layout.pause, null, false);
		Rel_main_game.addView(controls);
		ImageView pauseButton = (ImageView)controls.findViewById(R.id.imgCont);
		pauseButton.setOnClickListener(Pause_click);
		
		
		PauseMenu = myInflater.inflate(R.layout.pause_menu, null, false);
		Rel_main_game.addView(PauseMenu);
		PauseMenu.setVisibility(View.GONE);
		
		ImageView Cont = (ImageView)PauseMenu.findViewById(R.id.imgCont);
		ImageView MainMenuTo = (ImageView)PauseMenu.findViewById(R.id.toMain);
		Cont.setOnClickListener(Continue_list);
		MainMenuTo.setOnClickListener(To_Main_Menu_list);
		
		rightButton = (ImageView) controls.findViewById(R.id.right);
		rightButton.setOnClickListener(Right_click);
		
		leftButton = (ImageView) controls.findViewById(R.id.left);
		leftButton.setOnClickListener(Left_click);
		
		upButton = (ImageView) controls.findViewById(R.id.up);
		upButton.setOnClickListener(Up_click);
		
		downButton = (ImageView) controls.findViewById(R.id.down);
		downButton.setOnClickListener(Down_click);
		
		killButton = (ImageView) controls.findViewById(R.id.kill);
		killButton.setOnClickListener(Action_click);
	}
	

}



