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
		this.image = new Image("res/Gear.png");
	}
	
	protected void initHeartValue() {
		this.pointPlus = 100;
		this.pointMinus = 1000;
	}
	
	@Override
	protected void em_setAngleRotation() {
		this.image.setRotation(angle);
//		angle += 10; // Rotation
	}

	public boolean IsDeleteAble() {
		return false;
	}

	public int getPointMinus() {
		return this.pointMinus;
	}
}
