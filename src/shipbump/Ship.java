package shipbump;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Ship {
	private Image image;
	private float x;
	private float y;
	
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
		this.x = mouseX - image.getWidth() / 2;
		this.y = ShipBumpGame.GAME_HEIGHT - mouseY - image.getHeight() / 2;
	}


}
