package com.dvoragames.twistangle;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Projectile {
	private float x, y;
	float angle;
	private boolean visible;
	private Circle circ;
	private float PROJECTILESPEED = 8;
	private int color;
	
	// Colors correspond in this order:
	// 1. Yellow
	// 2. Green
	// 3. Purple
	public Projectile(float startX,float startY,float rotation, int c) {
		x = startX;
		y = startY;
		angle = rotation;
		color = c;
		visible = true;	
		circ = new Circle();
	}
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public void update(){
		
		if (angle < 0){
			angle += 360;
		}
		
		x += MathUtils.cosDeg(angle) * PROJECTILESPEED;
		y += MathUtils.sinDeg(angle) * PROJECTILESPEED;
		
		if (x > 500 || x < -20 || y > 820 || y < -20){
			visible = false;
		}
		
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
