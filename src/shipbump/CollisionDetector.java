package shipbump;

import org.newdawn.slick.geom.Shape;

public class CollisionDetector {
	
	public static boolean isEMCollideBullet(Shape em, Shape bullet) {
		if (em.intersects(bullet)) {
			return true;
		}
		return false;
	}

	public static boolean isEMCollideBorderX(float emX, float emWidth) {
		if (emX < 0 || emX + emWidth > ShipBumpGame.GAME_WIDTH) {
			return true;
		}
		return false;
	}

	public static boolean isEMCollideBorderY(float emY, float emHeight) {
		if (emY < 0 || emY + emHeight > ShipBumpGame.GAME_HEIGHT) {
			return true;
		}
		return false;
	}

	public static boolean isEMCollideShip(Shape em, Shape ship) {
		if (em.intersects(ship)) {
			return true;
		}
		return false;
	}

	public static boolean isBulletColideBorder(Shape bullet) {
		if (!bullet.intersects(ShipBumpGame.BOX_GAME)) {
			return true;
		}
		return false;
	}
}
