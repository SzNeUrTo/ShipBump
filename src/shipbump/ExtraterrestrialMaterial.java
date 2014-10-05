package shipbump;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class ExtraterrestrialMaterial implements Entity {

	private Image image;
	private float angle=0;
	private float x;
	private float y;
	private float targetDummyX;
	private float targetDummyY;
	private float velocity;
	private float deltaX;
	private float deltaY;
	private float distance;
	private float dirX;
	private float dirY;
	private Random random = new Random();
	private boolean isCollisionTargetDummyXY = false;
	private boolean isCollisionBorderX = false;
	private boolean isCollisionBorderY = false;
	private Shape shape;
	public static final float SIZE_IMAGE_EM = 70; 
	private float HP;
	private int point;
	
	public ExtraterrestrialMaterial() throws SlickException {		
		this.image = new Image("res/obtacle.png");
		initPosition();
		initTargetDummyXY();
		initDirXY();
		this.shape = new Circle(this.x + this.image.getWidth() / 2, this.y + this.image.getHeight() / 2, SIZE_IMAGE_EM * 0.7f);
		this.HP = 100;
		this.point = 10;
		this.velocity = 8;

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
		isInBoxGame();
		checkCollisionBorder();
		updateDirectionVelocity();
		updatePosition();	
		updateShape();
		gameOverStopMotion();
	}

	private void gameOverStopMotion() {
		if (ShipBumpGame.IS_GAME_OVER) {
			velocity = 0;
		}
	}

	private void updateShape() {
		shape.setCenterX(this.x + this.image.getWidth() / 2);
		shape.setCenterY(this.y + this.image.getHeight() / 2);
	}

	private void updateDirectionVelocity() {
		if(isCollisionBorderX) {
			dirX -= 190;
			isCollisionBorderX = false;
		}
		if(isCollisionBorderY) {
			dirY -= 190;
			isCollisionBorderY = false;
		}
		
	}

	private void isInBoxGame() {
		if (this.x > this.image.getWidth() && this.y > this.image.getHeight()) {
			if (this.x + this.image.getWidth() < ShipBumpGame.GAME_WIDTH) {
				if (this.y + this.image.getHeight() < ShipBumpGame.GAME_HEIGHT) {
					isCollisionTargetDummyXY = true;
				}
			}	
		}
	}

	private void checkCollisionBorder() {
		if(isCollisionTargetDummyXY) {	
			isCollisionBorderX = CollisionDetector.isEMCollideBorderX(this.x, this.image.getWidth());
			isCollisionBorderY = CollisionDetector.isEMCollideBorderY(this.y, this.image.getHeight());
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

	@Override
	public boolean isDeletable() {
		return false;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public float getHP() {
		return HP;
	}

	public void setHP(float hP) {
		HP = hP;
	}

	public void decreaseHP() {
		HP -= 20;
		if (HP < 0) {
			HP = 0;
		}
		
	}

	public float getDirX() {
		return dirX;
	}

	public float getDirY() {
		return dirY;
	}

	public void setDirY(float dirY) {
		this.dirY = dirY;	
	}

	public void setDirX(float dirX) {
		this.dirX = dirX;
	}

	public int getPoint() {
		return point ;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}

}
