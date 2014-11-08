package shipbump;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class ItemDY extends ExtraterrestrialMaterial {

	private Circle boxCircle;
	protected float sizeImage = 70;
	public static final int dyPoint = 10000;
	
	public ItemDY() throws SlickException {
		super();
		initItemValue();
		System.out.println("CreateItem : " + this.typeItem);
	}
	
	@Override
	protected void initImage() throws SlickException {
		this.imagePath = "res/Item/Item_Dy.png";
		this.image = new Image(this.imagePath);
	}
	
	protected void initItemValue() {
		this.pointPlus = 10000;
		this.pointMinus = 2000;
		this.velocity = 4;
		this.hp = 1;
		this.typeItem = "DY";
	}
	
	@Override
	protected void em_setAngleRotation() {
		this.image.setRotation(angle);
	}

	public int getPointMinus() {
		return this.pointMinus;
	}
	
	@Override
	public void render(Graphics graphics) {
		boxCircleDraw(graphics);
		this.image.draw(this.x, this.y);
	}
	
	protected void boxCircleDraw(Graphics graphics) {
		this.boxCircle = new Circle(emCenterX(), emCenterY(), 60);
		graphics.setColor(new Color(255, 255, 255, 0.15f));
		graphics.fill(this.boxCircle);
		graphics.setColor(new Color(255, 255, 255));
	}
	
	@Override
	protected void decreaseHP(float damage) {
		this.hp -= damage;
		if (this.hp <= 0) {
			this.hp = 0;
			ShipBumpGame.increaseScore(getPointMinus());
		}
	}

}
