package shipbump;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class AlienKeepoloPink extends Alien {

	public AlienKeepoloPink() throws SlickException {
		super();
	}

	@Override
	protected void initAlienValue() {
		setSizeImage(120);
		this.pointPlus = 2500;
		this.velocity = 5;
		this.maxHP = 90;
		this.hp = this.maxHP;
		this.shape = new Circle(this.x + getSizeImage() / 2, this.y + this.image.getHeight() / 2, getSizeImage() * 0.55f);
		this.valueShiftGate = 33;
		this.imagePath = "res/Alien/SpriteKeepoloPink.png";
		this.duration = 120;
	}
}
