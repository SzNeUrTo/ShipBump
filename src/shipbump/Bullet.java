package shipbump;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class Bullet implements Entity {
	
	public static final float BULLET_SIZE = 5;
	protected float x;
	protected float y;
	protected float dirX;
	protected float dirY;
	protected float velocity = 10;
	protected boolean isDeleteable = false;
	protected Shape shape;
	
	public Bullet(float x, float y, float dirX, float dirY) {
		this.x = x;
		this.y = y;
		this.dirX = dirX;
		this.dirY = dirY;
		shape = new Circle(x, y, BULLET_SIZE / 2.0f);
	}

	@Override
	public void render(Graphics g) {
		g.fill(shape);
	}

	@Override
	public void update(GameContainer container, int delta) {
		updatePosition();
		checkCollisionTarget();
		updateShape();
	}
	
	private void updateShape() {
		shape.setCenterX(this.x);
		shape.setCenterY(this.y);
	}

	private void checkCollisionTarget() {
		if (this.x < 0 || this.x > ShipBumpGame.GAME_WIDTH || this.y < 0 || this.y > ShipBumpGame.GAME_HEIGHT) {
			isDeleteable = true;
		}
	}
	private void updatePosition() {
		this.x = (float) (this.x + velocity * Math.cos(dirX));
		this.y = (float) (this.y + velocity * Math.sin(dirY));
	}

	@Override
	public boolean isDeletable() {
		return isDeleteable;
	}
	
	public Shape getShape() {
		return shape;
	}
}
