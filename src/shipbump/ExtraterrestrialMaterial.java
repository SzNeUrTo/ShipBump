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
	private Random random = new Random();
	
	
	public ExtraterrestrialMaterial() throws SlickException {
		image = new Image("res/obtacle.png");
		initPosition();
		initTargetDummyXY();
	}
	
	//for check collision dummyXY
	private void initTargetDummyXY() {
		this.targetDummyX = random.nextInt(ShipBumpGame.GAME_WIDTH * 4 / 5) + ShipBumpGame.GAME_WIDTH / 5;
		this.targetDummyY = random.nextInt(ShipBumpGame.GAME_HEIGHT * 4 / 5) + ShipBumpGame.GAME_HEIGHT / 5;
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
		this.image.setRotation(angle);
		angle += 10;	
	}
}
