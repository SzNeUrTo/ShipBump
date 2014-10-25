package shipbump;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class ExtraterrestrialMaterial implements Entity {

	protected Image image;
	protected float angle=0;
	protected float x;
	protected float y;
	protected float targetDummyX;
	protected float targetDummyY;
	protected float velocity;
	protected float deltaX;
	protected float deltaY;
	protected float distance;
	protected float dirX;
	protected float dirY;
	protected Random random = new Random();
	protected boolean isCollisionTargetDummyXY = false;
	protected boolean isCollisionBorderX = false;
	protected boolean isCollisionBorderY = false;
	protected Shape shape;
	public static final float SIZE_IMAGE_EM = 70; 
	protected float hp;
	protected int pointPlus;
	protected int pointMinus;
	protected float alpha;
	protected int colorRed;
	protected int colorGreen;
	protected int colorBlue;
	protected int baseValueColorRandom;
	
	
	public ExtraterrestrialMaterial() throws SlickException {		
		initImage();
		initPosition();
		initTargetDummyXY();
		initDirXY();
		initValue();
		this.baseValueColorRandom = 30;
		this.colorRed = random.nextInt(255 - this.baseValueColorRandom) + this.baseValueColorRandom;
		this.colorGreen = random.nextInt(255 - this.baseValueColorRandom) + this.baseValueColorRandom;
		this.colorBlue = random.nextInt(255 - this.baseValueColorRandom) + this.baseValueColorRandom;
	}

	protected void initImage() throws SlickException {
		this.image = new Image("res/obtacle2.png");
	}

	protected void initValue() {
		this.shape = new Circle(this.x + this.image.getWidth() / 2, this.y + this.image.getHeight() / 2, SIZE_IMAGE_EM * 0.7f);
		this.hp = 100f;
		this.pointPlus = 10;
		this.pointMinus = 0;
		this.velocity = 10;
		this.alpha = 0.0f;
	}
	
	protected void initDirXY() {
		this.deltaX = targetDummyX - this.x;
		this.deltaY = targetDummyY - this.y;
		this.distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		this.dirX = (float) Math.sin(deltaX / distance);
		this.dirY = (float) Math.sin(deltaY / distance);
	}
	
	protected void initTargetDummyXY() {
		this.targetDummyX = random.nextInt(ShipBumpGame.GAME_WIDTH * 3 / 5) + ShipBumpGame.GAME_WIDTH / 5;
		this.targetDummyY = random.nextInt(ShipBumpGame.GAME_HEIGHT * 3 / 5) + ShipBumpGame.GAME_HEIGHT / 5;
	}
	
	protected void initPosition() {
		selectQuadrant();
	}
	
	protected void selectQuadrant() {
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
	
	protected void quadrant_1() {
		this.x = ShipBumpGame.GAME_WIDTH + this.image.getWidth() * 2;
		this.y = random.nextInt(ShipBumpGame.GAME_HEIGHT * 5 / 4) - ShipBumpGame.GAME_HEIGHT / 4;
	}
	
	protected void quadrant_2() {
		this.x = random.nextInt(ShipBumpGame.GAME_WIDTH * 5 / 4) - ShipBumpGame.GAME_WIDTH / 4; 
		this.y = ShipBumpGame.GAME_HEIGHT + this.image.getHeight() * 2;
	}
	
	protected void quadrant_3() {
		this.x = -this.image.getWidth() * 2; 
		this.y = random.nextInt(ShipBumpGame.GAME_HEIGHT * 5 / 4) - ShipBumpGame.GAME_HEIGHT / 4;
	}
	
	protected void quadrant_4() {
		this.x = random.nextInt(ShipBumpGame.GAME_WIDTH * 5 / 4) - ShipBumpGame.GAME_WIDTH / 4; 
		this.y = -this.image.getHeight() * 2;
	}
	
	@Override
	public void render(Graphics graphics) {
		this.image.draw(this.x, this.y, new Color(this.colorRed, this.colorGreen, this.colorBlue));	
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
		updateImageAlpha();
	}

	protected void updateImageAlpha() {
		alpha = alpha + 0.01f;
		if (alpha > 1) {
			alpha = 1.0f;
		}
		this.image.setAlpha(this.alpha);
	}

	protected void gameOverStopMotion() {
		if (ShipBumpGame.IS_GAME_OVER) {
			velocity = 0;
		}
	}

	protected void updateShape() {
		shape.setCenterX(this.x + this.image.getWidth() / 2);
		shape.setCenterY(this.y + this.image.getHeight() / 2);
	}

	protected void updateDirectionVelocity() {
		if(isCollisionBorderX) {
			dirX -= 190;
			isCollisionBorderX = false;
		}
		if(isCollisionBorderY) {
			dirY -= 190;
			isCollisionBorderY = false;
		}
		
	}

	protected void isInBoxGame() {
		if (this.x > this.image.getWidth() && this.y > this.image.getHeight()) {
			if (this.x + this.image.getWidth() < ShipBumpGame.GAME_WIDTH) {
				if (this.y + this.image.getHeight() < ShipBumpGame.GAME_HEIGHT) {
					isCollisionTargetDummyXY = true;
				}
			}	
		}
	}

	protected void checkCollisionBorder() {
		if(isCollisionTargetDummyXY) {	
			isCollisionBorderX = CollisionDetector.isEMCollideBorderX(this.x, this.image.getWidth());
			isCollisionBorderY = CollisionDetector.isEMCollideBorderY(this.y, this.image.getHeight());
		}
	}

	protected void updatePosition() {
//		initDirXY(); // for BlackHole 5555
		this.x -= velocity * Math.sin(-dirX);
		this.y -= velocity * Math.sin(-dirY);
//		System.out.println("x = " + x + " y = " + y + " dirX = " + dirX + " dirY = " + dirY + " DummyX = " + targetDummyX + " DummyY = " + targetDummyY);
	}

	protected void em_setAngleRotation() {
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
		return hp;
	}

	public void setHP(float hP) {
		hp = hP;
	}

	public void decreaseHP() {
		this.hp -= 1000;
		if (this.hp <= 0) {
			this.hp = 0;
			ShipBumpGame.increaseScore(getPointPlus());
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

	public int getPointPlus() {
		return pointPlus ;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public boolean getIsInBox() {
		return isCollisionTargetDummyXY;
	}
	
	public float getAlpha() {
		return this.alpha;
	}
	
	public float emCenterX() {
		return this.x + SIZE_IMAGE_EM / 2;
	}
	
	public float emCenterY() {
		return this.y + SIZE_IMAGE_EM / 2;
	}
}
