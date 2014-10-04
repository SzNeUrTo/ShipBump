package shipbump;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Bullet implements Entity {
	
	private static final float BULLET_SIZE = 5;
	protected float x;
	protected float y;
	protected float dir;
	private float velocity = 5;
	private boolean isDeleteable = false;
	protected Shape shape;
	
	public Bullet(float x, float y, float dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.shape = new Circle(x + BULLET_SIZE / 2, y + BULLET_SIZE / 2, BULLET_SIZE / 2);
		System.out.println("Create Bullet");
	}

	@Override
	public void render(Graphics g) {
		g.fill(shape);
	}

	@Override
	public void update(GameContainer container, int delta) {
		updatePosition();
		checkCollisionTarget();
		
		
	}

	private void checkCollisionTarget() {
		//Check Check 
		isDeleteable = true;
	}

	private void updatePosition() {
		this.x += velocity * Math.cos(dir);
		this.y += velocity * Math.sin(dir);
	}

	@Override
	public boolean isDeletable() {
		return isDeleteable;
	}
	
//	public Shape getShape() {
//		return shape;
//	}

}
