package shipbump;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Drawable;

public class ShipBumpGame extends BasicGame {

	private ArrayList<ExtraterrestrialMaterial> extra_items = new ArrayList<ExtraterrestrialMaterial>();
	private ArrayList<ItemDY> items = new ArrayList<ItemDY>();
	private ArrayList<Alien> aliens = new ArrayList<Alien>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private String mouse_position = "";
	private String score_str;
	private static int score;
	public static final int GAME_WIDTH = 1440;
	public static final int GAME_HEIGHT = 900;
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
	private int countTime_Nuclear;
	private int sumSizeObject;
	private static final float MAX_TIME_NUCLEAR = 2000f;
	private int alphaNuclear;
	private float numberTarget;
	private int countTime_ManyTarget;
	private int countTime_TimePause;
	private int countTimeAddAlien;
	private static final float MAX_TIME_BARIA = 4000f;
	private static final float MAX_TIME_MANYTARGET = 5500f;
	private static final float MAX_TIME_TIMEPAUSE = 6000f;
	private static final int MAX_COUNT_TIME_ADD_ITEM = 10000;
	private static final int MAX_COUNT_TIME_ADD_ALIEN = 4000;
	private PlaySound audioRun = new PlaySound();
	private boolean neverPlaySoundGameOver;
	
	public ShipBumpGame(String title) throws SlickException {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics graphics)
			throws SlickException {
		if (!this.isNuclear) {
			ship.render(graphics);
			renderWordString(graphics);
			renderEM(graphics);
			renderBullet(graphics);
			renderItem(graphics);
			this.showPickItem.render(graphics);
		}
		else {
			renderNuclear(graphics);
		}
	}

	private void renderNuclear(Graphics graphics) {
		Rectangle rectangle = new Rectangle(0, 0, GAME_WIDTH, GAME_HEIGHT);
		graphics.setColor(new Color(255, 255, 255, this.alphaNuclear - this.countTime_Nuclear / MAX_TIME_NUCLEAR));
		graphics.fill(rectangle);
		graphics.setColor(new Color(255, 255, 255));
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
		graphics.drawString(mouse_position, GAME_WIDTH - 100 - 70 - 100 - 20, 10);
		graphics.drawString(score_str, GAME_WIDTH  -100 - (score + "").length() * 7, GAME_HEIGHT - 30);
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
		this.sumSizeObject = 0;
		this.alphaNuclear = 1;
		this.isManyTarget = false;
		this.numberTarget = 0;
		this.countTimeAddAlien = 0;
		this.audioRun.stopEverthing();
		this.audioRun.playSound_Musics(); // TEST Sound
		this.neverPlaySoundGameOver = true;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		this.time += delta;
		playSoundGameOver();
		ship.update(container, delta);
		clickMouseShooting(container);
		clickMouseUseItem(container, delta);
		updateBullet(container, delta);
		updateWordString();
		if (!this.isNuclear && !this.isTimePause) {
			addExtraterrestrialMaterial();
			addItem(container, delta);
			addAlien(container, delta);
		}
		reStartGame(container);
		afterEffectItem(delta);
		try {
			updateEM(container, delta);
			updateAlien(container, delta);
			updateItem(container, delta);
			this.showPickItem.update(container, delta);
		} catch (Exception e) {
			System.out.println("exception increaseScore()" + e);
		}
	}

	private void playSoundGameOver() {
		if (IS_GAME_OVER && !this.isNuclear) {
			this.audioRun.stopEverthing();
			if (this.neverPlaySoundGameOver) {
				this.audioRun.playSound_GameOver();
			}
			this.neverPlaySoundGameOver = false;
		}
	}

	private void afterEffectItem(int delta) {
		checkBaria(delta);	
		checkNuclear(delta);
		checkManyTarget(delta);
		checkTimePause(delta);
	}

	protected void checkTimePause(int delta) {
		if (this.isTimePause) {
			this.countTime_TimePause += delta;
			if (this.countTime_TimePause > MAX_TIME_TIMEPAUSE || IS_GAME_OVER) {
				this.audioRun.stopTimePause();
				this.isTimePause = false;
				this.countTime_TimePause = 0;
			}
		}
	}

	protected void checkManyTarget(int delta) {
		if (this.isManyTarget) {
			this.countTime_ManyTarget += delta;
			if (this.countTime_ManyTarget > MAX_TIME_MANYTARGET) {
				this.isManyTarget = false;
				this.countTime_ManyTarget = 0;
				this.numberTarget = 0;
			}
		}
	}

	protected void checkNuclear(int delta) {
		if (this.isNuclear) {
			this.countTime_Nuclear += delta;
			if (this.countTime_Nuclear > MAX_TIME_NUCLEAR) {
				clearObjectAndSumPoint();
				this.isNuclear = false;
				IS_GAME_OVER = false;
				this.countTime_Nuclear = 0;
			}
		}
	}

	protected void checkBaria(int delta) {
		if (this.isBaria) {
			this.countTime_Baria += delta;
			this.ship.setBariaOn();
			if (this.countTime_Baria > MAX_TIME_BARIA) {
				this.isBaria = false;
				this.ship.setBariaOff();
				this.countTime_Baria = 0;
			}
		}
	}

	private void clearObjectAndSumPoint() {
		int sumPoint = 0;
		sumPoint = sumPoint_All_In_Screen(sumPoint);
		increaseScore(sumPoint);
		clearObject();
	}

	protected int sumPoint_All_In_Screen(int sumPoint) {
		sumPoint = sumPoint_Nuclear_EM(sumPoint);
		sumPoint = sumPoint_Nuclear_Alien(sumPoint);
		sumPoint = sumPoint_Nuclear_Item(sumPoint);
		return sumPoint;
	}

	protected int sumPoint_Nuclear_Item(int sumPoint) {
		for (int i = 0; i < items.size(); i++) {
			sumPoint -= items.get(i).getPointMinus();
		}
		return sumPoint;
	}

	protected int sumPoint_Nuclear_Alien(int sumPoint) {
		for (int i = 0; i < aliens.size(); i++) {
			sumPoint += aliens.get(i).getPointPlus();
		}
		return sumPoint;
	}

	protected int sumPoint_Nuclear_EM(int sumPoint) {
		for (int i = 0; i < extra_items.size(); i++) {
			sumPoint += extra_items.get(i).getPointPlus();
		}
		return sumPoint;
	}

	private void clickMouseUseItem(GameContainer container, int delta) {
		Input input = container.getInput();
		if (input.isMousePressed(1) && !IS_GAME_OVER) {
			try {				
				effectItem(this.showPickItem.getPickTypeItem());
			} 
			catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void effectItem(String pickTypeItem) {
		if (!IS_GAME_OVER) {
			if (pickTypeItem == "DY") {
				increaseScore(ItemDY.dyPoint);
				this.audioRun.playSound_UseItemDY();
			} 
			else if (pickTypeItem == "TimePause") {
				this.isTimePause = true;
				this.countTime_TimePause = 0;
				this.audioRun.playSound_UseItemTimePause();
			} 
			else if (pickTypeItem == "Baria") {
				this.countTime_Baria = 0;
				this.isBaria = true;
				this.audioRun.playSound_UseItemBaria();
			} 
			else if (pickTypeItem == "Nuclear") {
				this.countTime_Nuclear = 0;
				this.isNuclear = true;
				this.audioRun.playSound_UseItemNuclear();
			}
			else if (pickTypeItem == "ManyTarget") {
				this.countTime_ManyTarget = 0;
				this.isManyTarget = true;
				this.audioRun.playSound_UseItemManyTarget();
			}
			this.showPickItem.setImagePath("NULL");
			this.showPickItem.setPickTypeItem("NULL");
			if (pickTypeItem == "Random") {
				changeItemRandomToItem();
				this.audioRun.playSound_UseRandomItem();
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
			if (!this.isTimePause) {
				aliens.get(i).update(container, delta);
			}
			checkAlienCollideShip(i);
			checkAlienCollideBullet(i);
		}
	}

	private void checkAlienCollideShip(int i) {
		if (CollisionDetector.isEMCollideShip(aliens.get(i).getShape(),
				ship.getShapeShip())
				&& aliens.get(i).getAlpha() == 1) {
			this.IS_GAME_OVER = true && (!this.isBaria);
		}
	}

	private void checkAlienCollideBullet(int i) {
		for (int j = 0; j < bullets.size(); j++) {
			hitAlien(i, j);
			removeAlienHP_Zero(i);
		}
	}

	protected void hitAlien(int i, int j) {
		if (CollisionDetector.isEMCollideBullet(aliens.get(i).getShape(), bullets.get(j).getShape())
			&& aliens.size() > 0) {				
			aliens.get(i).decreaseHP(bullets.get(j).getDamage());
			this.audioRun.playSound_CollideBullet();
			bullets.remove(j);
		}
	}

	protected void removeAlienHP_Zero(int i) {
		if (aliens.get(i).getHP() == 0) {
			playSoundDie(aliens.get(i).getTypeAlien());
			aliens.remove(i);
		}
	}

	private void playSoundDie(String typeAlien) {
		if (typeAlien == "Alien") {
			this.audioRun.playSound_Death_Alien();
		} else if (typeAlien == "Gengar") {
			this.audioRun.playSound_Death_Gengar();
			System.out.println("Gengar Die");
		} else if (typeAlien == "KeepoPink") {
			this.audioRun.playSound_Death_KeepoloPink();
		} else if (typeAlien == "KeepoRed") {
			this.audioRun.playSound_Death_KeepoloRed();
		} else {
			System.out.println("NO TYPE");
		}
		createManyAlienAfterDie(typeAlien);
		System.out.println("_____PlaySound Die : Type = " + typeAlien);
	}

	protected void createManyAlienAfterDie(String typeAlien) {
		try {
			if (typeAlien == "KeepoRed") {
				aliens.add(new AlienKeepoloPink());
				aliens.add(new AlienKeepoloPink());
				aliens.add(new AlienKeepoloPink());
				aliens.add(new AlienKeepoloPink());
			}
			else if (typeAlien == "KeepoPink") {
				aliens.add(new AlienGengar());
				aliens.add(new AlienGengar());
				aliens.add(new AlienGengar());
			}
			else if (typeAlien == "Gengar") {
				aliens.add(new Alien());
				aliens.add(new Alien());
			}

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void addAlien(GameContainer container, int delta) throws SlickException {
		Random random = new Random();
		if (aliens.size() == 0) {
			this.countTimeAddAlien += delta;
			if (this.countTimeAddAlien > MAX_COUNT_TIME_ADD_ALIEN) {
				this.countTimeAddAlien = 0;
				
				switch (random.nextInt(4)) {
				case 0:
					aliens.add(new Alien());
					break;
				case 1:
					aliens.add(new AlienGengar());
					break;
				case 2:
					aliens.add(new AlienKeepoloPink());
					break;
				case 3:
					aliens.add(new AlienKeepoloRed());
					break;
				default:
					break;
				}	
			}
		}
	}

	private void updateItem(GameContainer container, int delta) {
		for (int i = 0; i < items.size(); i++) {
			if (!this.isTimePause) {
				items.get(i).update(container, delta);
			}
			checkItemCollideShip(i);
			checkItemCollideBullet(i);
		}
	}

	protected void checkItemCollideShip(int i) {
		if (CollisionDetector.isEMCollideShip(items.get(i).getShape(), ship.getShapeShip())) {
			pickItem(items.get(i).getTypeItem(), i);
			this.audioRun.playSound_PickItem();
			items.remove(i);
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
			randomNTarget();
		}
	}

	private void randomNTarget() {
		Random random = new Random();
		int numberTarget = random.nextInt(9) + 2;
		this.showPickItem.setPickTypeItem("ManyTarget");
		this.showPickItem.setImagePath("/res/Item/Item_ManyTarget" + numberTarget +".png");
		this.numberTarget = numberTarget;
	}

	protected void checkItemCollideBullet(int i) {
		for (int j = 0; j < bullets.size(); j++) {
			if (CollisionDetector.isEMCollideBullet(items.get(i).getShape(), bullets.get(j).getShape())
				&& items.size() > 0) {				
				System.out.println("BulletCollide Item Law Na");
				decreaseScore(items.get(i).getPointMinus());
				items.remove(i);
			}
		}
	}

	private void addItem(GameContainer container, int delta) throws SlickException {
		Random random = new Random();
		if (items.size() == 0) {
			this.countTimeAddItem += delta;
			if (this.countTimeAddItem > MAX_COUNT_TIME_ADD_ITEM) {
				randomAddItem(random);
			}
		} else {
			this.countTimeAddItem = 0;
		}
	}

	protected void randomAddItem(Random random) throws SlickException {
		switch (random.nextInt(20)) {
		case 0:
			items.add(new ItemDY());
			break;
		case 1: case 2: case 18:
			items.add(new ItemTimePause());
			break;
		case 3: case 4: case 19: case 20:
			items.add(new ItemNuclear());
			break;
		case 5: case 15: case 16: case 17:  
			items.add(new ItemManyTarget());
			break;
		 case 6: case 7: case 8: case 9: case 10:
			items.add(new ItemRandom());
			break;
		 case 11: case 12: case 13: case 14: 
			items.add(new ItemBaria());
			break;
		default:
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
		int n = this.time / 4000;
		for (int i = 0; i < n - extra_items.size(); i++) {
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
			if (!this.isTimePause) {
				extra_items.get(i).update(container, delta);
			}
			checkEMCollideShip(i);
			checkEMCollideBullet(i);
			removeEMOutSideBox(i);
		}
	}

	private void checkEMCollideShip(int i) {
		checkCollideWithOutBaria(i);
		checkCollideWithBaria(i);
	}

	protected void checkCollideWithBaria(int i) {
		if (CollisionDetector.isEMCollideShip(extra_items.get(i).getShape(), 
				ship.getShapeBaria())) {
			if (this.isBaria) {
				extra_items.remove(i);
				this.IS_GAME_OVER = false;
			}
		}
	}

	protected void checkCollideWithOutBaria(int i) {
		if (CollisionDetector.isEMCollideShip(extra_items.get(i).getShape(),
				ship.getShapeShip())
				&& extra_items.get(i).getIsInBox()
				&& extra_items.get(i).getAlpha() == 1) {
			this.IS_GAME_OVER = true && !(this.isBaria);
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
			removeBulletOutSideBox(i);
		}
	}

	private void removeBulletOutSideBox(int i) {
		if (CollisionDetector.isBulletColideBorder(bullets.get(i).getShape())) {
			bullets.remove(i);
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
		if (input.isMousePressed(0) && !IS_GAME_OVER) { 
			try {
				audioRun.playSound_GunShot();
				bullets.add(new Bullet(ship.shipCenterX(), ship.shipCenterY(),
					ship.getDirX(), ship.getDirY()));
				createManyTargetBullet();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	protected void createManyTargetBullet() {
		if (this.isManyTarget) {
			for (int i = 1; i < this.numberTarget; i++) {
				bullets.add(new Bullet(ship.shipCenterX(), ship.shipCenterY(),
					(float) (ship.getDirX() + (2 * Math.PI * i / this.numberTarget)),(float) (ship.getDirY() + (2 * Math.PI * i / this.numberTarget))));
			}
		}
	}

	public static void main(String[] args) {
		try {
			ShipBumpGame game = new ShipBumpGame("Ship Bump Game");
			AppGameContainer container = new AppGameContainer(game);
			container.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, true);
			container.setMinimumLogicUpdateInterval(1600 / 60);
			container.setTargetFrameRate(FPS);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}