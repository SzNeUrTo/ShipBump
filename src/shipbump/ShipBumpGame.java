package shipbump;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.lwjgl.input.Mouse;

public class ShipBumpGame extends BasicGame {

	private ArrayList<ExtraterrestrialMaterial> extra_items = new ArrayList<ExtraterrestrialMaterial>();
	private ArrayList<ItemDY> items = new ArrayList<ItemDY>();
	private ArrayList<Alien> aliens = new ArrayList<Alien>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private String mouse_position = "";
	private String score_str;
	private static int score;
	public static final int GAME_WIDTH = 1000;//1440;
	public static final int GAME_HEIGHT = 800;//900;
	private Ship ship;
	public static boolean IS_GAME_OVER;
	public static final Shape BOX_GAME = new Rectangle(0, 0, GAME_WIDTH, GAME_HEIGHT);
	private static int FPS = 60;
	private int time;
	private int countTimeAddItem;
	private ShowPickItem showPickItem;
	private boolean isTimePause;
	private boolean isManyTarget;
	private boolean isNuclear;
	private boolean isBaria;
	private int countTime_Baria;
	

	public ShipBumpGame(String title) throws SlickException {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics graphics)
			throws SlickException {
		ship.render(graphics);
		renderWordString(graphics);
		renderEM(graphics);
		renderBullet(graphics);
		renderItem(graphics);
		this.showPickItem.render(graphics);
	}

	private void renderItem(Graphics graphics) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).render(graphics);
		}
	}

	private void renderBullet(Graphics graphics) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(graphics);
		}
	}

	private void renderEM(Graphics graphics) {
		for (int i = 0; i < extra_items.size(); i++) {
			extra_items.get(i).render(graphics);
		}
		for (int i = 0; i < items.size(); i++) {
			items.get(i).render(graphics);
		}
		for (int i = 0; i < aliens.size(); i++) {
			aliens.get(i).render(graphics);
		}
	}

	private void renderWordString(Graphics graphics) {
		graphics.drawString(mouse_position, GAME_WIDTH * 3 / 5, 10);
		graphics.drawString(score_str, GAME_WIDTH * 4 / 5, GAME_HEIGHT - 30);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		score_str = "";
		score = 0;
		IS_GAME_OVER = false;
		ship = new Ship(GAME_WIDTH / 2, GAME_HEIGHT / 2);
		time = 0;
		this.showPickItem = new ShowPickItem();
		this.isTimePause = false;
		this.isBaria = false;
		this.isManyTarget = false;
		this.isNuclear = false;
		this.countTime_Baria = 0;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		time += delta;
		ship.update(container, delta);
		clickMouseShooting(container);
		clickMouseUseItem(container, delta);
		updateBullet(container, delta);
		updateWordString();
		addExtraterrestrialMaterial();
		addItem(container, delta);
		addAlien(container, delta);
		reStartGame(container);
		afterEffectItem(delta);
		try {
			updateEM(container, delta);
			updateAlien(container, delta);
			this.showPickItem.update(container, delta);
			updateItem(container, delta);
		} catch (Exception e) {
			System.out.println("exception increaseScore()" + e);
		}
	}

	private void afterEffectItem(int delta) {
		if (isBaria) {
			this.countTime_Baria += delta;
			this.ship.setBariaOn();
			if (this.countTime_Baria > 4000) {
				this.isBaria = false;
				this.ship.setBariaOff();
				this.countTime_Baria = 0;
			}
		}	
	}

	private void clickMouseUseItem(GameContainer container, int delta) {
		Input input = container.getInput();
		if (input.isMousePressed(1) && !IS_GAME_OVER) {
			try {
				System.out.println("Use Item : " + this.showPickItem.getPickTypeItem());
				
				effectItem(this.showPickItem.getPickTypeItem());
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void effectItem(String pickTypeItem) {
		System.out.println("Effect by : " + pickTypeItem);
		if (!IS_GAME_OVER) {
			if (pickTypeItem == "DY") {
				System.out.println("DY Pick + 10000");
				increaseScore(ItemDY.dyPoint);
			} 
			else if (pickTypeItem == "TimePause") {
				isTimePause = true;
			} 
			else if (pickTypeItem == "Baria") {
				isBaria = true;
			} 
			else if (pickTypeItem == "Nuclear") {
				isNuclear = true;
			}
			else if (pickTypeItem == "ManyTarget") {
				isManyTarget = true;
			}
			this.showPickItem.setImagePath("NULL");
			this.showPickItem.setPickTypeItem("NULL");
			if (pickTypeItem == "Random") {
				changeItemRandomToItem();
			}
		}	
	}

	private void changeItemRandomToItem() {
		Random random = new Random();
		switch (random.nextInt(16)) {
		case 0:
			this.showPickItem.setImagePath("res/Item/Item_DY.png");
			this.showPickItem.setPickTypeItem("DY");
			break;
		case 1: case 2:
			this.showPickItem.setImagePath("res/Item/Item_TimePause.png");
			this.showPickItem.setPickTypeItem("TimePause");
			break;
		case 3: case 4:
			this.showPickItem.setImagePath("res/Item/Item_Nuclear.png");
			this.showPickItem.setPickTypeItem("Nuclear");
			break;
		case 5: case 6: case 7: case 8:
			this.showPickItem.setImagePath("res/Item/Item_Baria.png");
			this.showPickItem.setPickTypeItem("Baria");
			break;
		default:
			randomNTarget();
			break;
		}
		
	}

	private void updateAlien(GameContainer container, int delta) {
		for (int i = 0; i < aliens.size(); i++) {
			aliens.get(i).update(container, delta);
			checkAlienCollideShip(i);
			checkAlienCollideBullet(i);
		}
	}

	private void checkAlienCollideShip(int i) {
		if (CollisionDetector.isEMCollideShip(aliens.get(i).getShape(),
				ship.getShapeShip())
				&& aliens.get(i).getAlpha() == 1) {
			this.IS_GAME_OVER = true;
		}
	}

	private void checkAlienCollideBullet(int i) {
		for (int j = 0; j < bullets.size(); j++) {
			if (CollisionDetector.isEMCollideBullet(aliens.get(i).getShape(), bullets.get(j).getShape())
				&& aliens.size() > 0) {				
				aliens.get(i).decreaseHP(bullets.get(j).getDamage());
				bullets.remove(j);
			}
			
			if (aliens.get(i).getHP() == 0) {
				aliens.remove(i);
			}
		}
	}

	private void addAlien(GameContainer container, int delta) throws SlickException {
		if (aliens.size() == 0) {
			aliens.add(new Alien());	
		}
	}

	private void updateItem(GameContainer container, int delta) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).update(container, delta);
			checkItemCollideShip(i);
			checkItemCollideBullet(i);
		}
	}

	protected void checkItemCollideShip(int i) {
		if (CollisionDetector.isEMCollideShip(items.get(i).getShape(), ship.getShapeShip())) {
//			increaseScore(items.get(i).getPointPlus());
			pickItem(items.get(i).getTypeItem(), i);
			items.remove(i);
			//pick = true
		}
	}

	private void pickItem(String typeItem, int i) {
		if (typeItem.equals("DY")) {
			this.showPickItem.setPickTypeItem("DY");
			this.showPickItem.setImagePath("/res/Item/Item_DY.png");
		} 
		else if (typeItem.equals("TimePause")) {
			this.showPickItem.setPickTypeItem("TimePause");
			this.showPickItem.setImagePath("/res/Item/Item_TimePause.png");
		} 
		else if (typeItem.equals("Baria")) {
			this.showPickItem.setPickTypeItem("Baria");
			this.showPickItem.setImagePath("/res/Item/Item_Baria.png");
		} 
		else if (typeItem.equals("Nuclear")) {
			this.showPickItem.setPickTypeItem("Nuclear");
			this.showPickItem.setImagePath("/res/Item/Item_Nuclear.png");
		} 
		else if (typeItem.equals("Random")) {
			this.showPickItem.setPickTypeItem("Random");
			this.showPickItem.setImagePath("/res/Item/Item_Random.png");
		} 
		else if (typeItem.equals("ManyTarget")) {
			//Random n target			
			randomNTarget();
//			this.showPickItem.setPickTypeItem(items.get(i).getTypeItem());
//			this.showPickItem.setImagePath(items.get(i).getImagePath());

		}
	}

	private void randomNTarget() {
		Random random = new Random();
		int number = random.nextInt(10) + 1;
		this.showPickItem.setPickTypeItem("ManyTarget" + number);
		this.showPickItem.setImagePath("/res/Item/Item_ManyTarget" + number +".png");
	}

	protected void checkItemCollideBullet(int i) {
		for (int j = 0; j < bullets.size(); j++) {
			if (CollisionDetector.isEMCollideBullet(items.get(i).getShape(), bullets.get(j).getShape())
				&& items.size() > 0) {				
				decreaseScore(items.get(i).getPointMinus());
				items.remove(i);
			}
		}
	}

	private void addItem(GameContainer container, int delta) throws SlickException {
		Random random = new Random();
		if (items.size() == 0) {
			this.countTimeAddItem += delta;
			if (this.countTimeAddItem > 1000) {
				randomAddItem(random);
			}
		} else {
			this.countTimeAddItem = 0;
		}
	}

	protected void randomAddItem(Random random) throws SlickException {
		switch (random.nextInt(20)) {
		case 1:
			items.add(new ItemDY());
			System.out.println("ItemDY");
			break;
		case 2: case 3:
			items.add(new ItemTimePause());
			System.out.println("ItemTimePause");
			break;
		case 4: case 0 :
			items.add(new ItemNuclear());
			System.out.println("ItemNuclear");
			break;
		case 5: case 15 : case 16 : case 17 : case 18 : case 19 : case 20 :
			items.add(new ItemManyTarget());
			System.out.println("ManyTarget");
			break;
		 case 6 : case 7 : case 8 : case 9 : case 10 :
			items.add(new ItemRandom());
			System.out.println("ItemRandom");
			break;
		 case 11 : case 12 : case 13 : case 14 : 
			items.add(new ItemBaria());
			System.out.println("ItemBaria");
			break;
		default:
			System.out.println("No Item");
			break;
		}
	}

	private void reStartGame(GameContainer container) throws SlickException {
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_ENTER)) {
			clearObject();
			container.reinit();
		}
	}
	
	public static void increaseScore(int point) {
		score += point;
	}
	
	public static void decreaseScore(int point) {
		score -= point;
	}
	
	private void clearObject() {
		extra_items.clear();
		bullets.clear();
		items.clear();
		aliens.clear();
	}

	private void addExtraterrestrialMaterial() throws SlickException {
		for (int i = 0; i < 5 - extra_items.size(); i++) {
			extra_items.add(new ExtraterrestrialMaterial());
		}
	}

	protected void checkEMCollideBullet(int i) {
		for (int j = 0; j < bullets.size(); j++) {
			if (CollisionDetector.isEMCollideBullet(extra_items.get(i)
				.getShape(), bullets.get(j).getShape())) {
				extra_items.get(i).decreaseHP(bullets.get(j).getDamage());
				bullets.remove(j);
			}
			if (extra_items.get(i).getHP() == 0) {
				extra_items.remove(i);
			}
		}
	}

	private void updateEM(GameContainer container, int delta) throws SlickException {
		for (int i = 0; i < extra_items.size(); i++) {
			extra_items.get(i).update(container, delta);
			checkEMCollideShip(i);
			checkEMCollideBullet(i);
			removeEMOutSideBox(i);
		}
	}

	private void checkEMCollideShip(int i) {
		if (CollisionDetector.isEMCollideShip(extra_items.get(i).getShape(),
				ship.getShapeShip())
				&& extra_items.get(i).getIsInBox()
				&& extra_items.get(i).getAlpha() == 1) {
			this.IS_GAME_OVER = true && !(this.isBaria);
		}
		if (CollisionDetector.isEMCollideShip(extra_items.get(i).getShape(), 
				ship.getShapeBaria())) {
			if (this.isBaria) {
				extra_items.remove(i);
				this.IS_GAME_OVER = false;
			}
		}
	}
	
	private void updateWordString() {
		mouse_position = "Mouse Position X : " + Mouse.getX()
				+ "\nMouse Position Y : " + Mouse.getY();
		
		if (IS_GAME_OVER) {
			score_str = "Score : " + score;
		}
		else {
			score_str = "Score : " + (score + time / 1000);
		}
		
	}

	private void updateBullet(GameContainer container, int delta) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(container, delta);
		}
	}

	private void removeEMOutSideBox(int i) throws SlickException {
		if (!BOX_GAME.intersects(extra_items.get(i).getShape())
				&& extra_items.get(i).getAlpha() == 1) {
			extra_items.remove(i);
		}

	}

	private void clickMouseShooting(GameContainer container) {
		Input input = container.getInput();
		if (input.isMousePressed(0) && !IS_GAME_OVER) { // Game Over Not Add
														// Bullet
			try {
//				 for (int i = 0; i < 10; i++) { // StarBullet
//				 bullets.add(new Bullet(ship.shipCenterX(),
//				 ship.shipCenterY(), i * 360 / 10, i * 360 / 10));
//				 }
				bullets.add(new Bullet(ship.shipCenterX(), ship.shipCenterY(),
						ship.getDirX(), ship.getDirY()));
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("================== TEST Mouse Click ====================");
		}
	}

	public static void main(String[] args) {
		try {
			ShipBumpGame game = new ShipBumpGame("Ship Bump Game");
			AppGameContainer container = new AppGameContainer(game);
			container.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, false);
			container.setMinimumLogicUpdateInterval(1600 / 60);
			container.setTargetFrameRate(FPS);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}