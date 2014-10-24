package shipbump;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class ItemHeart extends ExtraterrestrialMaterial {

	public ItemHeart() throws SlickException {
		super();
		System.out.println("Create Heart");
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
