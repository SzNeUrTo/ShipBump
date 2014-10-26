package shipbump;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ManyTargetBullet extends Bullet {

	private ArrayList<Bullet> bullets;
	
	public ManyTargetBullet(float x, float y, float dirX, float dirY, float n) {
		super(x, y, dirX, dirY);
		
		bullets = new ArrayList<Bullet>();
		for (int i = 0; i < n; i++) {
			bullets.add(new Bullet(x, y, dirX + (360 * i / (n)),(dirY + (90 * i / (n)))));
			System.out.println("ManyTarget Create Bullet (in Class)");
		}
	}
	
	public void render(Graphics graphics) {
		for (Bullet bullet : bullets) {
			bullet.render(graphics);
		}
	}
	 
	public void update(GameContainer container, int delta) {
		for (Bullet bullet : bullets) {
			bullet.update(container, delta);
		}    
	}
}
