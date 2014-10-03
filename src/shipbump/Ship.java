package shipbump;

import java.awt.Container;

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
			image.setRotation((float) (180 * angle / Math.PI));
		}
		else {
			image.setRotation((float) (-180 * angle / Math.PI));
		}
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
		
	}

	@Override
	public void render(Graphics graphics) {
		draw();
	}

	@Override
	public void update(GameContainer container, int delta) {
		movement(Mouse.getX(), Mouse.getY());
		Input input = container.getInput();
		if (input.isMouseButtonDown(0)) {
			clickShootingGun();
		}
//		System.out.println(this.image.getRotation());
	}


}
