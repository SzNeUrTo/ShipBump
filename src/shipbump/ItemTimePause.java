package shipbump;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ItemTimePause extends ItemDY {

	private String imagePath;
	
	public ItemTimePause() throws SlickException {
		super();
	}

	@Override
	protected void initImage() throws SlickException {
		this.imagePath = "res/Item/Item_TimePause.png";
		this.image = new Image(this.imagePath);
	}

	@Override
	protected void initItemValue() {
		this.pointPlus = 0;
		this.pointMinus = 1000;
		this.velocity = 4;
		this.hp = 1;
		this.typeItem = "TimePause";
	}
	
//	@Override
//	public String getTypeItem() {
////		System.out.println(this.typeItem);
////		return "TimePause";
//	}
}
