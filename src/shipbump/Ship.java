package shipbump;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ship {
	private Image image;
	private float x;
	private float y;
	private float newX;
	private float newY;
	private float angle;
	private float velocity;
	
	public Ship(float x, float y) throws SlickException {
		image = new Image("res/ship.png");
		this.x = x;
	    this.y = y;
	    velocity = 5;
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
//		this.x += Math.cos(Math.floor(image.getRotation())) * velocity;
//		this.y += Math.sin(Math.floor(image.getRotation())) * velocity;

//		System.out.println("Old X : " + this.x + " New X : " + newX +" Old Y : " + this.y + " New Y : " + newY + " Angle : " + angle);
	}

	private void shipPosition() {
		this.x = newX;
		this.y = newY;
	}

	private void shipRotation() {
		if (newX != this.x && newY != this.y) {
			calculateAngleRotate();
			if (newY > this.y) {
				image.setRotation((float) (180 * angle / Math.PI));
			}
			else {
				image.setRotation((float) (-180 * angle / Math.PI));
			}
			
		}
		else {
			angle = 0;
		}
	}

	private void calculateAngleRotate() {
		// dotProduct Vector arccos
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

	public void clickMoveForward() {
//		this.x += Math.cos(image.getRotation()) * velocity;
//		this.y += Math.sin(image.getRotation()) * velocity;
	}


}
