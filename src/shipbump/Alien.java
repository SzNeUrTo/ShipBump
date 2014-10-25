package shipbump;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Alien extends ExtraterrestrialMaterial {

	protected SpriteSheet spriteAlien;
	protected Animation animationAlien;
	public Alien() throws SlickException {
		super();
		System.out.println("Create Alien");
		initAlienValue();
		initAnimation();
	}
	
	protected void initAnimation() throws SlickException {
		spriteAlien = new SpriteSheet("res/Alien.png", 120, 120);
		animationAlien = new Animation(spriteAlien, 120);
	}
	
	protected void initAlienValue() {
		this.pointPlus = 1500;
		this.velocity = 4;
		this.hp = 500;
	}

	@Override
	public void render(Graphics graphics) {
		this.animationAlien.draw(this.x, this.y);
	}
}
