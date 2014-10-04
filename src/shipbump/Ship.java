package shipbump;

import java.awt.Container;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Ship implements Entity {
	private Image image;
	private float x;
	private float y;
	private float newX;
	private float newY;
	private float angle;
	private float angleRotation;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public static float SIZE_IMAGE_SHIP = 64;
	
	public Ship(float x, float y) throws SlickException {
		image = new Image("res/ship1.png");
		this.x = x;
	    this.y = y;
	    this.angleRotation = 0;
	}
	
	public void draw() {
		this.image.draw(this.x, this.y);
	}
	
	public float getCenterY() {
		return this.y;
	}

	public float getCenterX() {
		return this.x;
	}
	
	public void movement(float mouseX, float mouseY) {
		newX = mouseX - image.getWidth() / 2;
		newY = ShipBumpGame.GAME_HEIGHT - mouseY - image.getHeight() / 2;			
		shipRotation();
		shipPosition();
	}

	private void shipPosition() {
		this.x = newX;
		this.y = newY;
	}

	private void shipRotation() {
		if (newX != this.x && newY != this.y) {
			calculateAngleRotate();
			shipSetRotation();
		}
		else {
			angle = 0;
		}
	}

	private void shipSetRotation() {
		if (newY > this.y) {
			this.angleRotation = ((float) (180 * angle / Math.PI)) % 360;
			image.setRotation((float) (this.angleRotation));
		}
		else {
			this.angleRotation = ((float) (-180 * angle / Math.PI)) % 360;
			image.setRotation((float) (this.angleRotation));
		}
		System.out.println(this.angleRotation);
	}

	private void calculateAngleRotate() {
		angle = (float) Math.acos(getDeltaX() / getDistance());
	}

	private float getDeltaX() {
		return newX - this.x;
	}
	
	private float getDeltaY() {
		return newY - this.y;
	}	
	
	private float getDistance() {
		return (float)(Math.sqrt(getDeltaX() * getDeltaX() + getDeltaY() * getDeltaY()));
	}

	public void clickShootingGun() {
		bullets.add(new Bullet(CenterX(), CenterY(), -this.angleRotation)); // Bug
		System.out.println(-angleRotation + 180);
	}
	
	public float CenterX() {
		return this.x + this.image.getWidth() / 2.0f;
	}
	
	public float CenterY() {
		return this.y + this.image.getHeight() / 2.0f;
	}
	
	@Override
	public void render(Graphics graphics) {
		draw();
		for (Bullet bullet : bullets) {
		      bullet.render(graphics);
		}
	}

	@Override
	public void update(GameContainer container, int delta) {
		movement(Mouse.getX(), Mouse.getY());
		Input input = container.getInput();
//		if (input.isMouseButtonDown(button)(0)) {
		if (input.isMousePressed(0)) {
			clickShootingGun();
		}
		System.out.println(this.image.getRotation());
//		for (Bullet bullet : bullets) {
		for (int i = 0; i < bullets.size(); i++) {
//			bullet.update(container, delta);
			bullets.get(i).update(container, delta);
//		    if (bullet.isDeletable()) {
			if (bullets.get(i).isDeletable()) {
		    	System.out.println("RemoveBullet");
		    	bullets.remove(i);
		    }
		}
	}

	@Override
	public boolean isDeletable() {
		return false;
	}


}
