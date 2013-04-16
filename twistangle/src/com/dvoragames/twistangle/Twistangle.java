package com.dvoragames.twistangle;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Twistangle implements ApplicationListener {
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	//Controls Direction and Placement of spawned circles
	private double circleSpeed = 2;
	private double deltaX;
	private double deltaY;
	private float centerX,centerY;
	
	private Texture purpleproj,greenproj,yellowproj,circlepic,projectilepic;
	private Texture purplecircle,greencircle,yellowcircle;
	private Texture triangle;
	
	private Triangle myTriangle;
	
	private static float rotate = 0;
	
	private Array<Bogey> Bogeys;
	private Array<Circle> bogeys;
	private Array<Double> angles;
	private Array<Integer> colors;
	private Array<Projectile> projectiles;
	
	long lastSpawnTime;
	
	public static boolean isShooting() {
		return isShooting;
	}

	public void setShooting(boolean isShooting) {
		Twistangle.isShooting = isShooting;
	}

	private static boolean isShooting = false;
	
	@Override
	public void create() {
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		purpleproj = new Texture(Gdx.files.internal("PurpleProjectile.png"));
		greenproj = new Texture(Gdx.files.internal("GreenProjectile.png"));
		yellowproj = new Texture(Gdx.files.internal("YellowProjectile.png"));
		purplecircle = new Texture(Gdx.files.internal("Purplecircle.png"));
		greencircle = new Texture(Gdx.files.internal("Greencircle.png"));
		yellowcircle = new Texture(Gdx.files.internal("Yellowcircle.png"));
		triangle = new Texture(Gdx.files.internal("Triangle.png"));
		circlepic = new Texture(Gdx.files.internal("Circle.png"));
		projectilepic = new Texture(Gdx.files.internal("Projectile.png"));
		
		myTriangle = new Triangle(240,400,triangle.getWidth(),triangle.getHeight(),(int) rotate);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		Bogeys = new Array<Bogey>();
		bogeys = new Array<Circle>();
		angles = new Array<Double>();
		projectiles = new Array<Projectile>();
		colors = new Array<Integer>();
		
		spawnLeftRight();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// Draw the triangle
		//batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY)
		batch.draw(triangle, 240 - triangle.getWidth() / 2, (float) (400 - (triangle.getWidth()) * .289), triangle.getWidth() / 2, (float) ((triangle.getWidth()) * .289), triangle.getWidth(), triangle.getHeight(), 1, 1, rotate, 0, 0, triangle.getWidth(), triangle.getHeight(), false, false);
		
		// Draw the circles
/*		for (Circle bogey: bogeys){
//			batch.draw(circlepic, bogey.x - circlepic.getWidth() / 2, bogey.y - circlepic.getHeight() / 2);
		}*/
		
/*		for (Bogey bogey: Bogeys){
			batch.draw(circlepic, bogey.getCenterX() - circlepic.getWidth() / 2, bogey.getCenterY() - circlepic.getHeight() / 2);
		}*/
		
		for (int i = 0; i < Bogeys.size; i++){
			Bogey b =  (Bogey) Bogeys.get(i);
			//batch.draw(circlepic, b.getCenterX() - circlepic.getWidth() / 2, b.getCenterY() - circlepic.getHeight() / 2);
			if (b.getColor() == 0){
				batch.draw(yellowcircle, b.getCenterX() - circlepic.getWidth() / 2, b.getCenterY() - circlepic.getHeight() / 2);
			}else if (b.getColor() == 1){
				batch.draw(greencircle, b.getCenterX() - circlepic.getWidth() / 2, b.getCenterY() - circlepic.getHeight() / 2);
			}else if (b.getColor() == 2){
				batch.draw(purplecircle, b.getCenterX() - circlepic.getWidth() / 2, b.getCenterY() - circlepic.getHeight() / 2);
			}
		}
		
		// Draw the projectiles
		for (Projectile p: projectiles){
			if (p.getColor() == 0){
				batch.draw(yellowproj, p.getX() - projectilepic.getWidth() / 2, p.getY() - projectilepic.getHeight() / 2,projectilepic.getWidth() / 2,projectilepic.getHeight() / 2,projectilepic.getWidth(),projectilepic.getHeight(),1,1,p.getAngle() - 90,0,0,projectilepic.getWidth(),projectilepic.getHeight(),false,false);
			}else if(p.getColor() == 1){
				batch.draw(greenproj, p.getX() - projectilepic.getWidth() / 2, p.getY() - projectilepic.getHeight() / 2,projectilepic.getWidth() / 2,projectilepic.getHeight() / 2,projectilepic.getWidth(),projectilepic.getHeight(),1,1,p.getAngle() - 90,0,0,projectilepic.getWidth(),projectilepic.getHeight(),false,false);
			}else if(p.getColor() == 2){
				batch.draw(purpleproj, p.getX() - projectilepic.getWidth() / 2, p.getY() - projectilepic.getHeight() / 2,projectilepic.getWidth() / 2,projectilepic.getHeight() / 2,projectilepic.getWidth(),projectilepic.getHeight(),1,1,p.getAngle() - 90,0,0,projectilepic.getWidth(),projectilepic.getHeight(),false,false);
			}
		}
		batch.end();
		
		//Touch input

		if (Gdx.input.isKeyPressed(Keys.SPACE)){
			 myTriangle.shoot();
		}
		
		if(Gdx.input.isTouched()){		
			Vector3 touchpos = new Vector3();
			touchpos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchpos);
			rotate += Gdx.input.getDeltaX();
			myTriangle.setAngle((int) rotate);
		}
		
		/*if(Gdx.input.isKeyPressed(Keys.LEFT)) rotate+=2;
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) rotate-=2;*/
		
		if(TimeUtils.nanoTime() - lastSpawnTime > 2000000000){
			if (MathUtils.randomBoolean()){
				spawnLeftRight();
			}else{
				spawnLeftRight();
				//spawnTopBottom();
			}
		}
		
		for (int i = 0; i < Bogeys.size; i++){
			Bogey b = Bogeys.get(i);
			b.update();
			if (b.getCenterX() < 242 && b.getCenterX() > 238){
				Bogeys.removeIndex(i);
			}
		}
		
		projectiles = myTriangle.getProjectiles();
		for (int i = 0; i < projectiles.size; i++){
			Projectile p = projectiles.get(i);
			
			if (p.isVisible()){
				p.update();
			}else {
				projectiles.removeIndex(i);
			}
		}
	}
	
	public static float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		Twistangle.rotate = rotate;
	}
	
	public void spawnLeftRight() {

		if (MathUtils.randomBoolean()) {
			centerX = 0;
		} else {
			centerX = 480;
		}

		centerY = MathUtils.random(800);

		if (centerY == 0) {
			centerY = 1;
		}

		deltaX = centerX - 240;
		deltaY = centerY - 400;

		double theta = Math.atan(deltaY / deltaX);

		if (centerX > 240) {
			theta += MathUtils.PI;
		}

		if (theta < 0) {
			theta += 2 * MathUtils.PI;
		}
		int color = MathUtils.random(2);
		
		Bogey bogey = new Bogey(centerX,centerY,theta,color);

		Bogeys.add(bogey);
		angles.add(theta);
//		colors.add(color);
		lastSpawnTime = TimeUtils.nanoTime();
	}
	
/*	public void spawnLeftRight(){
		Circle bogey = new Circle();
		
		if (MathUtils.randomBoolean()){
			bogey.x = 0;
		}else{
			bogey.x = 480;
		}
		
		bogey.y = MathUtils.random(800);
		
		if (bogey.y == 0){
			bogey.y = 1;
		}
		
		deltaX = bogey.x - 240;
		deltaY = bogey.y - 400;
		
		double theta = Math.atan(deltaY / deltaX);
		
		if (bogey.x > 240){
			theta += MathUtils.PI;
		}
		
		if (theta < 0){
			theta += 2 * MathUtils.PI;
		}
		int color = MathUtils.random(2);
		
		bogeys.add(bogey);
		angles.add(theta);
		colors.add(color);
		lastSpawnTime = TimeUtils.nanoTime();
	}*/

//	public void spawnLeftRight2(){
////		Bogey bogey = new Bogey(0,0);
////		
////		if (MathUtils.randomBoolean()){
////			bogey.setCenterX(0);
////		}else{
////			bogey.setCenterX(480);
////		}
////		bogey.setCenterY(MathUtils.random(800));
////		
////		if (bogey.getCenterY() == 0){
////			bogey.setCenterY(1);
////		}
//		
//		if (MathUtils.randomBoolean()){
//			centerX = 0;
//		}else{
//			centerX = 480;
//		}
//		centerY = MathUtils.random(800);
//		
//		if (centerY == 0){
//			centerY = 1;
//		}
//		
//		deltaX = centerX - 240;
//		deltaY = centerY - 400;
//		
//		double theta = Math.atan(deltaY / deltaX);
//		
//		if (centerX > 240){
//			theta += MathUtils.PI;
//		}
//		
//		if (theta < 0){
//			theta += 2 * MathUtils.PI;
//		}
//		
//		System.out.println(theta);
//		
//		Bogey bogey = new Bogey(centerX,centerY,theta);
//		
//		bogeys.add(bogey);
//		angles.add(theta);
//		lastSpawnTime = TimeUtils.nanoTime();
//	}
	
//	public void spawnTopBottom2(){
//		Bogey bogey = new Bogey(0,0);
//		
//		if (MathUtils.randomBoolean()){
//			bogey.setCenterY(0);
//		}else{
//			bogey.setCenterY(800);
//		}
//		bogey.setCenterX(MathUtils.random(480));
//		
//		if (bogey.getCenterY() == 0){
//			bogey.setCenterY(1);
//		}
//		
//		deltaX = bogey.getCenterX()- 240;
//		deltaY = bogey.getCenterY() - 400;
//		
//		double theta = Math.atan(deltaY / deltaX);
//		
//		if (bogey.getCenterX() > 240){
//			theta += MathUtils.PI;
//		}
//		
//		if (theta < 0){
//			theta += 2 * MathUtils.PI;
//		}
//		
//		bogeys.add(bogey);
//		angles.add(theta);
//		lastSpawnTime = TimeUtils.nanoTime();
//	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
