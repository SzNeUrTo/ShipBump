package shipbump;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;

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
		this.image = new Image("res/Alien.png");
		this.image.setAlpha(0.01f);
		this.spriteAlien = new SpriteSheet(this.image , 120, 120);
		this.animationAlien = new Animation(spriteAlien, 120);
	}
	
	protected void initAlienValue() {
		this.pointPlus = 1500;
		this.velocity = 10;
		this.hp = 500;
		setSizeImage(120);
		this.shape = new Circle(this.x + getSizeImage() / 2, this.y + this.image.getHeight() / 2, getSizeImage() * 0.55f);
	}

	@Override
	public void render(Graphics graphics) {
		this.animationAlien.draw(this.x, this.y);
		
	}
}
