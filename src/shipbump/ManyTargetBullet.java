package shipbump;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ManyTargetBullet extends Bullet {

	private ArrayList<Bullet> bullets;
	
	public ManyTargetBullet(float x, float y, float dirX, float dirY, float n) {
		super(x, y, dirX, dirY);
		this.typeBullet = "ManyTargetBullet";
		
		bullets = new ArrayList<Bullet>();
		for (int i = 0; i < n; i++) {
			if ((dirX * 180 / Math.PI) + (360 * i / n) > 180) {
				dirX = (float) (dirX - 2 * Math.PI);
			}
			if ((dirY * 180 / Math.PI) + (360 * i / n) > 180) {
				dirY = (float) (dirY - 2 * Math.PI);
			}
			bullets.add(new Bullet(x, y, (float) (dirX + (2 * Math.PI * i / n)), (float) (dirX + (2 * Math.PI * i / n))));
//			System.out.println("ManyTarget Create Bullet (in Class)");
		
		}
//		bullets.add(new Bullet(x,y, dirX % 360, 90 - dirX));
//		bullets.add(new Bullet(x,y, 0 + 120,dirY -120));
//		bullets.add(new Bullet(x,y,0+240,dirY-240));
	}
	
	@Override
	public void render(Graphics graphics) {
		for (Bullet bullet : bullets) {
			bullet.render(graphics);
		}
	}
	
	@Override
	public void update(GameContainer container, int delta) {
		for (Bullet bullet : bullets) {
			bullet.update(container, delta);
		}    
	}
}
