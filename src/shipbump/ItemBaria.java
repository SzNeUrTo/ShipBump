package shipbump;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ItemBaria extends ItemDY {

	private String imagePath;
	
	public ItemBaria() throws SlickException {
		super();
	}
	
	@Override
	protected void initImage() throws SlickException {
		this.imagePath = "res/Item/Item_Baria.png";
		this.image = new Image(this.imagePath);
	}

	@Override
	protected void initItemValue() {
		this.pointPlus = 0;
		this.pointMinus = 1000;
		this.velocity = 4;
		this.hp = 1;
		this.typeItem = "Baria";
	}
	
//	@Override
//	public String getTypeItem() {
//		return "Baria";
//	}
}
