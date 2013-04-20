package com.dvoragames.twistangle;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Triangle {
	
	int x,y,w,h,countshoot = 0;

	public int getCountshoot() {
		return countshoot;
	}

	public void setCountshoot(int countshoot) {
		this.countshoot = countshoot;
	}

	float angle;
	
	private Array<Projectile> projectiles = new Array<Projectile>();
	
	// Eventually add color and tipX and tipY
	public Triangle(int centerX,int centerY,int width,int height, int rotation){
		x = centerX;
		y = centerY;
		w = width;
		h = height;
		angle = rotation;
	}
	
	public void update() {
		if (Twistangle.isShooting()){
			countshoot++;
		}else{
			countshoot = 0;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public void shoot() {
		Projectile p1 = new Projectile(x + MathUtils.cosDeg(angle + 90) * 100, y + MathUtils.sinDeg(angle + 90) * 100, angle + 90,0);
		Projectile p2 = new Projectile(x + MathUtils.cosDeg(angle + 120 + 90) * 100, y + MathUtils.sinDeg(angle + 120 + 90) * 100, angle + 120 + 90,1);
		Projectile p3 = new Projectile(x + MathUtils.cosDeg(angle + 240 + 90) * 100, y + MathUtils.sinDeg(angle + 240 + 90) * 100, angle + 240 + 90,2);
		projectiles.add(p1);
		projectiles.add(p2);
		projectiles.add(p3);
	}

	public Array<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(Array<Projectile> projectiles) {
		this.projectiles = projectiles;
	}
}
