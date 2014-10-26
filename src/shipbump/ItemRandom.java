package shipbump;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ItemRandom extends ItemDY {

	public ItemRandom() throws SlickException {
		super();
	}

	@Override
	protected void initImage() throws SlickException {
		this.image = new Image("res/Item/Item_Random.png");
	}

	@Override
	protected void initItemValue() {
		this.pointPlus = 0;
		this.pointMinus = 1000;
		this.velocity = 4;
		this.hp = 1;
		this.typeItem = "Random";
	}
}
