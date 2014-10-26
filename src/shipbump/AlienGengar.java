package shipbump;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class AlienGengar extends Alien {

	public AlienGengar() throws SlickException {
		super();
	}

	protected void initAlienValue() {
		setSizeImage(120);
		this.pointPlus = 2000;
		this.velocity = 5;
		this.maxHP = 80;
		this.hp = this.maxHP;
		this.shape = new Circle(this.x + getSizeImage() / 2, this.y + this.image.getHeight() / 2, getSizeImage() * 0.55f);
		this.valueShiftGate = 8;
		this.imagePath = "res/Alien/SpriteGengar_Left.png";
		this.duration = 120;
	}
}
