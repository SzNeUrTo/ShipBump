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
	
	public Ship(float x, float y) throws SlickException {
		image = new Image("res/ship.png");
		this.x = x;
	    this.y = y;
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
		
		float angle;
		
		if (newX != this.x && newY != this.y) {
			angle = (float) Math.acos((newX - this.x) / (Math.sqrt((newX - this.x) * (newX - this.x) + (newY - this.y) * (newY - this.y))));
			if (newY > this.y) {
				image.setRotation((float) (180 * angle / Math.PI));
			}
			else {
				image.setRotation((float) (-180 * angle / Math.PI));
			}
			
				
			System.out.println();
		}
		else {
			angle = 0;
		}
		this.x = newX;
		this.y = newY;
		
		System.out.println("Old X : " + this.x + " New X : " + newX +" Old Y : " + this.y + " New Y : " + newY + " Angle : " + angle);
	}


}