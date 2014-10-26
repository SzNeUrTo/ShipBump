package shipbump;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ShowPickItem implements Entity {

	private float x;
	private float y;
	private Image image;
	private String imagePath;
	private String pickTypeItem;
	
	public ShowPickItem() throws SlickException {
		this.x = ShipBumpGame.GAME_WIDTH - 70 - 10;
		this.y = 0 + 10;
		this.imagePath = "res/Item/Null_Item.png";
		this.image = new Image(this.imagePath);
	}

	@Override
	public void render(Graphics g) {
		this.image.draw(this.x, this.y);
	}

	@Override
	public void update(GameContainer container, int delta) {
		try {
			this.image = new Image(this.imagePath);
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setPickTypeItem(String pickTypeItem) {
		this.pickTypeItem = pickTypeItem;
	}
}
