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
		shape = new Circle(x, y, BULLET_SIZE / 2.0f);
//		System.out.println("Create Bullet");
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
		if (this.x < 0 || this.x > ShipBumpGame.GAME_WIDTH || this.y < 0 || this.y > ShipBumpGame.GAME_HEIGHT) {
			isDeleteable = true;
		}
	}

	private void updatePosition() {
		this.x = (float) (this.x + velocity * Math.cos(dir));
		this.y = (float) (this.y + velocity * Math.sin(dir));
		shape.setCenterX(this.x);
		shape.setCenterY(this.y);
	}

	@Override
	public boolean isDeletable() {
		return isDeleteable;
	}
	
//	public Shape getShape() {
//		return shape;
//	}

}
