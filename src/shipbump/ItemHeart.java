package shipbump;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class ItemHeart extends ExtraterrestrialMaterial {

	private Circle boxCircle;
	
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
		this.velocity = 4;
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
	
	@Override
	public void render(Graphics graphics) {
		boxCircleDraw(graphics);
		this.image.draw(this.x, this.y, new Color(this.colorRed, this.colorGreen, this.colorBlue));	
	}
	
	private void boxCircleDraw(Graphics graphics) {
		this.boxCircle = new Circle(emCenterX(), emCenterY(), 50);
		graphics.setColor(new Color(255, 255, 255, 0.15f));
		graphics.fill(this.boxCircle);
		graphics.setColor(new Color(255, 255, 255));
	}
}
