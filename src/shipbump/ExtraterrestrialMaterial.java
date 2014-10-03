package shipbump;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ExtraterrestrialMaterial implements Entity {
	
	private Image image;
	float x;
	float y;
	private float angle=0;
	
	public ExtraterrestrialMaterial() throws SlickException {
		image = new Image("res/obtacle.png");
		initPosition();
	}
	private void initPosition() {
		Random random = new Random();
		selectQuadrant();

	}
	private void selectQuadrant() {
		Random random = new Random();
		int quadrant = random.nextInt(4) + 1;
		if (quadrant == 1) {
			this.x = ShipBumpGame.GAME_WIDTH + this.image.getWidth() * 2;
			this.y = random.nextInt(ShipBumpGame.GAME_HEIGHT * 5 / 4) - ShipBumpGame.GAME_HEIGHT / 4; 
		} else if (quadrant == 2) {
			this.x = random.nextInt(ShipBumpGame.GAME_WIDTH * 5 / 4) - ShipBumpGame.GAME_WIDTH / 4; 
			this.y = ShipBumpGame.GAME_HEIGHT + this.image.getHeight() * 2;
		} else if (quadrant == 3) {
			this.x = -this.image.getWidth() * 2; 
			this.y = random.nextInt(ShipBumpGame.GAME_HEIGHT * 5 / 4) - ShipBumpGame.GAME_HEIGHT / 4;
		} else { // quadrant == 4
			this.x = random.nextInt(ShipBumpGame.GAME_WIDTH * 5 / 4) - ShipBumpGame.GAME_WIDTH / 4; 
			this.y = -this.image.getHeight() * 2;
		}
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
