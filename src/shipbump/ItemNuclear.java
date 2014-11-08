package shipbump;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ItemNuclear extends ItemDY {
	
	public ItemNuclear() throws SlickException {
		super();
	}

	@Override
	protected void initImage() throws SlickException {
		this.imagePath = "res/Item/Item_Nuclear.png";
		this.image = new Image(this.imagePath);
	}

	@Override
	protected void initItemValue() {
		this.pointPlus = 0;
		this.pointMinus = 2000;
		this.velocity = 4;
		this.hp = 1;
		this.typeItem = "Nuclear";
	}
	
//	@Override
//	public String getTypeItem() {
//		return "Nuclear";
//	}
}
