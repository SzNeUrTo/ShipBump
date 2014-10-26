package shipbump;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ItemManyTarget extends ItemDY {

	private String imagePath;
	
	public ItemManyTarget() throws SlickException {
		super();
	}

	
	//Hard Random 
	@Override
	protected void initImage() throws SlickException {
		this.imagePath = "res/Item/Item_ManyTarget.png";
		this.image = new Image(this.imagePath);
	}

	@Override
	protected void initItemValue() {
		this.pointPlus = 0;
		this.pointMinus = 1000;
		this.velocity = 4;
		this.hp = 1;
		this.typeItem = "ManyTarget";
	}
	
//	@Override
//	public String getTypeItem() {
//		return "ManyTarget";
//	}
}
