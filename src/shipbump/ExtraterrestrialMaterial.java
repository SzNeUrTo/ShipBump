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
	public ExtraterrestrialMaterial() throws SlickException {
		image = new Image("res/obtacle.png");
		initPosition();
	}
	private void initPosition() {
		x = 400f;
		y = 200f;

	}
	@Override
	public void render(Graphics graphics) {
		this.image.draw(this.x, this.y);
		
	}
	@Override
	public void update(GameContainer container, int delta) {
		
		
	}

}
