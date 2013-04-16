package com.dvoragames.twistangle;

import com.badlogic.gdx.math.Circle;

public class Bogey {
	
	private Circle circle;
	private float centerX, centerY;
	private double theta;
	private float circleSpeed = 2;
	private int color;
	
	public Bogey(float x,float y, double angle, int c){
		circle = new Circle();
		theta = angle;
		centerX = x;
		centerY = y;
		color = c;
		circle.set(x, y, 20);
	}
	
	public void update() {
/*		circle.x += circleSpeed * Math.cos(theta);
		circle.y += circleSpeed * Math.sin(theta);*/
		centerX += circleSpeed * Math.cos(theta);
		centerY += circleSpeed * Math.sin(theta);
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
}
