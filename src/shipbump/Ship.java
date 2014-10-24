package shipbump;

import java.awt.Container;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;


public class Ship implements Entity {
	private Image image;
	private float x;
	private float y;
	private float newX;
	private float newY;
	private float angle;
	private float angleRotation;
	private Shape shape;
	public static int SIZE_IMAGE_SHIP = 64;
	public boolean isGameOver = false;
	
	public Ship(float x, float y) throws SlickException {
		image = new Image("res/ship1.png");
		this.x = x;
	    this.y = y;
	    this.angleRotation = 0;
	    shape = new Circle(this.x + SIZE_IMAGE_SHIP / 2, this.y + SIZE_IMAGE_SHIP / 2, SIZE_IMAGE_SHIP / 2);
	}
	
	public Ship() throws SlickException {
		image = new Image("res/ship1.png");
		this.x = ShipBumpGame.GAME_WIDTH / 2;
	    this.y = ShipBumpGame.GAME_HEIGHT / 2;
	    this.angleRotation = 0;
	}
	
	public void draw() {
		this.image.draw(this.x, this.y);
	}
	
	public float getY() {
		return this.y;
	}

	public float getX() {
		return this.x;
	}
	
	public void movement(float mouseX, float mouseY) {
		newX = mouseX - image.getWidth() / 2;
		newY = ShipBumpGame.GAME_HEIGHT - mouseY - image.getHeight() / 2;
		shipRotation();
		if (!ShipBumpGame.IS_GAME_OVER) {
			shipPosition();
		}

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
			this.angleRotation = this.angle;
			image.setRotation((float) (180 * angle / Math.PI));
		}
		else {
			this.angleRotation = -this.angle;
			image.setRotation((float) (-180 * angle / Math.PI));
			
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
	
	public float shipCenterX() {
		return this.x + this.image.getWidth() / 2.0f;
	}
	
	public float shipCenterY() {
		return this.y + this.image.getHeight() / 2.0f;
	}
	
	public float getAngleRotation() {
		return this.angleRotation;
	}
	
	@Override
	public void render(Graphics graphics) {
		draw();
	}

	@Override
	public void update(GameContainer container, int delta) {
		movement(Mouse.getX(), Mouse.getY());
		updateShape();
		statusGame();
	}

	private void statusGame() {
		this.isGameOver = ShipBumpGame.IS_GAME_OVER;
	}

	private void updateShape() {
		shape.setCenterX(this.x + SIZE_IMAGE_SHIP / 2);
		shape.setCenterY(this.y + SIZE_IMAGE_SHIP / 2);
	}

	@Override
	public boolean isDeletable() {
		return false;
	}

	public Shape getShape() {
		return shape;
	}

	public float getDirX() {
		return this.angleRotation;
	}
	
	public float getDirY() {
		return this.angleRotation;
	}
}
