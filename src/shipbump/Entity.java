package shipbump;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface Entity {
	void render(Graphics g);
	void update(GameContainer container, int delta);
}
