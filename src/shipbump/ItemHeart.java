package shipbump;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class ItemHeart extends ExtraterrestrialMaterial {

	public ItemHeart() throws SlickException {
		super();
		initHeartValue();
	}
	
	@Override
	protected void initImage() throws SlickException {
		this.image = new Image("res/heart.png");
	}
	
	protected void initHeartValue() {
		this.point = -1000;
	}
}
