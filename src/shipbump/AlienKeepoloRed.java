package shipbump;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class AlienKeepoloRed extends Alien {

	public AlienKeepoloRed() throws SlickException {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initAlienValue() {
		setSizeImage(120);
		this.pointPlus = 300;
		this.velocity = 5;
		this.maxHP = 120;
		this.hp = this.maxHP;
		this.shape = new Circle(this.x + getSizeImage() / 2, this.y + this.image.getHeight() / 2, getSizeImage() * 0.55f);
		this.valueShiftGate = 8;
		this.imagePath = "res/Alien/SpriteKeepoloRed.png";
		this.duration = 120;
	}

}
