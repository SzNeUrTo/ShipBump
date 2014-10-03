package shipbump;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ExtraterrestrialMaterial implements Entity {

	private Image image;
	private float angle=0;
	private float x;
	private float y;
	private float targetDummyX;
	private float targetDummyY;
	private float velocity = 5;
	private float deltaX;
	private float deltaY;
	private float distance;
	private float dirX;
	private float dirY;
	private Random random = new Random();
	private boolean isCollisionTargetDummyXY = false;
	private boolean isCollisionBorderX = false;
	private boolean isCollisionBorderY = false;
	
	public ExtraterrestrialMaterial() throws SlickException {
		image = new Image("res/obtacle.png");
		initPosition();
		initTargetDummyXY();
		initDirXY();

	}

	private void initDirXY() {
		this.deltaX = targetDummyX - this.x;
		this.deltaY = targetDummyY - this.y;
		this.distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		this.dirX = (float) Math.sin(deltaX / distance);
		this.dirY = (float) Math.sin(deltaY / distance);
	}
	
	private void initTargetDummyXY() {
		this.targetDummyX = random.nextInt(ShipBumpGame.GAME_WIDTH * 3 / 5) + ShipBumpGame.GAME_WIDTH / 5;
		this.targetDummyY = random.nextInt(ShipBumpGame.GAME_HEIGHT * 3 / 5) + ShipBumpGame.GAME_HEIGHT / 5;
	}

	private void initPosition() {
		selectQuadrant();
	}
	
	private void selectQuadrant() {
		int quadrant = random.nextInt(4) + 1;
		if (quadrant == 1) {
			quadrant_1(); 
		} else if (quadrant == 2) {
			quadrant_2();
		} else if (quadrant == 3) {
			quadrant_3();
		} else { // quadrant == 4
			quadrant_4();
		}
	}
	
	private void quadrant_1() {
		this.x = ShipBumpGame.GAME_WIDTH + this.image.getWidth() * 2;
		this.y = random.nextInt(ShipBumpGame.GAME_HEIGHT * 5 / 4) - ShipBumpGame.GAME_HEIGHT / 4;
	}

	private void quadrant_2() {
		this.x = random.nextInt(ShipBumpGame.GAME_WIDTH * 5 / 4) - ShipBumpGame.GAME_WIDTH / 4; 
		this.y = ShipBumpGame.GAME_HEIGHT + this.image.getHeight() * 2;
	}
	
	private void quadrant_3() {
		this.x = -this.image.getWidth() * 2; 
		this.y = random.nextInt(ShipBumpGame.GAME_HEIGHT * 5 / 4) - ShipBumpGame.GAME_HEIGHT / 4;
	}
	
	private void quadrant_4() {
		this.x = random.nextInt(ShipBumpGame.GAME_WIDTH * 5 / 4) - ShipBumpGame.GAME_WIDTH / 4; 
		this.y = -this.image.getHeight() * 2;
	}
	
	@Override
	public void render(Graphics graphics) {
		this.image.draw(this.x, this.y);	
	}
	
	@Override
	public void update(GameContainer container, int delta) {
		em_setAngleRotation();	
		checkCollisionTargetDummy();
		checkCollisionBorder();
		updateDirectionVelocity();
		updatePosition();	
		System.out.println("this.x = " + this.x + " this.y = " + this.y);
		System.out.println("TarX = " + targetDummyX + "TarY = " + targetDummyY);
	}

	private void updateDirectionVelocity() {
		if(isCollisionBorderX) {
			dirX -= 180;
			isCollisionBorderX = false;
		}
		if(isCollisionBorderY) {
			dirY -= 180;
			isCollisionBorderY = false;
		}
		
	}

	private void checkCollisionTargetDummy() {
		if (Math.abs(this.x - targetDummyX) <= velocity * 7 || Math.abs(this.y - targetDummyY) <= velocity * 7) {
			isCollisionTargetDummyXY = true;
//			velocity = 0;
			System.out.println("True");
		}
		
	}

	private void checkCollisionBorder() {
		if(isCollisionTargetDummyXY) {
			if (this.x < 0 || this.x + this.image.getWidth() > ShipBumpGame.GAME_WIDTH) {
				isCollisionBorderX = true;
				System.out.println("BorderX");
			}
			if (this.y < 0 || this.y + this.image.getHeight() > ShipBumpGame.GAME_HEIGHT) {
				isCollisionBorderY = true;
				System.out.println("BorderY");
			}	
		}
	}

	private void updatePosition() {
//		initDirXY(); // for BlackHole 5555
		this.x -= velocity * Math.sin(-dirX);
		this.y -= velocity * Math.sin(-dirY);
//		System.out.println("x = " + x + " y = " + y + " dirX = " + dirX + " dirY = " + dirY + " DummyX = " + targetDummyX + " DummyY = " + targetDummyY);
	}

	private void em_setAngleRotation() {
		this.image.setRotation(angle);
		angle += 10;
	}
}
