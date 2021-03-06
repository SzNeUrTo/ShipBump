package shipbump;

import org.lwjgl.opengl.Drawable;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Alien extends ExtraterrestrialMaterial {

	protected SpriteSheet spriteAlien;
	protected Animation animationAlien;
	protected Rectangle hpGate;
	protected Rectangle hpMaxGate;
	protected float maxHP;
	protected float valueShiftGate;
	protected int duration;
	protected String typeAlien;
	protected PlaySound audioDie;
	public Alien() throws SlickException {
		super();
		System.out.println("Create Alien");
		initAlienValue();
		initAnimation();
		this.typeObject = "Alien";
		
	}
	
	protected void initAnimation() throws SlickException {
		this.image = new Image(this.imagePath);
		this.spriteAlien = new SpriteSheet(this.image , 120, 120);
		this.animationAlien = new Animation(this.spriteAlien, this.duration);
	}
	
	protected void initAlienValue() {
		setSizeImage(120);
		this.pointPlus = 1500;
		this.velocity = 5;
		this.maxHP = 50;
		this.hp = this.maxHP;
		this.shape = new Circle(this.x + getSizeImage() / 2, this.y + this.image.getHeight() / 2, getSizeImage() * 0.55f);
		this.valueShiftGate = 8;
		this.imagePath = "res/Alien/Sprite3Eyes.png";
		this.duration = 50;
		this.typeAlien = "Alien";
	}

	@Override
	public void render(Graphics graphics) {
		this.animationAlien.draw(this.x, this.y, new Color(255f, 255f, 255f, this.alpha));
		drawHPGate(graphics);
		this.animationAlien.stop();
	}

	protected void drawHPGate(Graphics graphics) {
		this.hpGate = new Rectangle(this.x + 4, this.y - 15, (getSizeImage() - valueShiftGate) * this.hp / this.maxHP , 5);
		this.hpMaxGate = new Rectangle(this.x + 4, this.y - 15, getSizeImage() - valueShiftGate, 5);
		graphics.setColor(new Color(255, 255, 255));
		graphics.fill(this.hpMaxGate);
		graphics.setColor(new Color(255, 0, 0));
		graphics.fill(this.hpGate);
		graphics.setColor(new Color(255, 255, 255));
	}

	@Override
	protected void decreaseHP(float damage) {
		this.hp -= damage;
		if (this.hp <= 0) {
			this.hp = 0;
			ShipBumpGame.increaseScore(getPointPlus());
		}
	}
	
	@Override
	protected void timePauseStopMotion() {
		this.animationAlien.start();
	}
	
	public String getTypeAlien() {
		return this.typeAlien;
	}
}
