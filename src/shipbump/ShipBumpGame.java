package shipbump;

import java.util.ArrayList;
import java.util.LinkedList;

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
	private ArrayList<ItemHeart> heart_items = new ArrayList<ItemHeart>();
	private ArrayList<Alien> aliens = new ArrayList<Alien>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private String mouse_position = "";
	private String score_str;
	private static int score;
	public static final int GAME_WIDTH = 1000;//1440;
	public static final int GAME_HEIGHT = 800;//900;
	private Ship ship;
	public static boolean IS_GAME_OVER;
	public static final Shape BOX_GAME = new Rectangle(0, 0, GAME_WIDTH,
			GAME_HEIGHT);
	private static int FPS = 60;
	private int time;

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
		renderHeartItem(graphics);
	}

	private void renderHeartItem(Graphics graphics) {
		for (int i = 0; i < heart_items.size(); i++) {
			heart_items.get(i).render(graphics);
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
		for (int i = 0; i < heart_items.size(); i++) {
			heart_items.get(i).render(graphics);
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
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		time += delta;
		ship.update(container, delta);
		clickMouseShooting(container);
		updateEM(container, delta);
		updateBullet(container, delta);
		updateWordString();
//		addExtraterrestrialMaterial();
		addHeartItem(container, delta);
		addAlien(container, delta);
		reStartGame(container);
		try {
			updateAlien(container, delta);
			updateHeartItem(container, delta);
			checkBulletcollideEM();
		} catch (Exception e) {
			System.out.println("exception increaseScore()" + e);
		}
	}

	private void updateAlien(GameContainer container, int delta) {
		
	}

	private void addAlien(GameContainer container, int delta) {
		
	}

	private void updateHeartItem(GameContainer container, int delta) {
		for (int i = 0; i < heart_items.size(); i++) {
			heart_items.get(i).update(container, delta);
			checkItemCollideShip(i);
			checkItemCollideBullet(i);
			
		}
	}

	protected void checkItemCollideShip(int i) {
		if (CollisionDetector.isEMCollideShip(heart_items.get(i).getShape(), ship.getShape()) 
			|| heart_items.get(i).IsDeleteAble()) {
			increaseScore(heart_items.get(i).getPointPlus());
			heart_items.remove(i);
		}
	}

	protected void checkItemCollideBullet(int i) {
		for (int j = 0; j < bullets.size(); j++) {
			if (CollisionDetector.isEMCollideBullet(heart_items.get(i).getShape(), bullets.get(j).getShape())
				&& heart_items.size() > 0) {				
				decreaseScore(heart_items.get(i).getPointMinus());
				heart_items.remove(i);
//					System.out.println("OK");
			}
		}
	}

	private void addHeartItem(GameContainer container, int delta) throws SlickException {
		if (heart_items.size() == 0) {
			heart_items.add(new ItemHeart());	
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
		heart_items.clear();
	}

	private void addExtraterrestrialMaterial() throws SlickException {
		for (int i = 0; i < 15 - extra_items.size(); i++) {
			extra_items.add(new ExtraterrestrialMaterial());
		}
	}

	private void checkBulletcollideEM() {
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < extra_items.size(); j++) {
				isEMCollideBullet(i, j);
			}
			removeEM_OutsideBox(i);
		}
	}

	protected void isEMCollideBullet(int i, int j) {
		if (CollisionDetector.isEMCollideBullet(extra_items.get(j)
				.getShape(), bullets.get(i).getShape())) {
			bullets.remove(i);
			extra_items.get(j).decreaseHP();
			removeEM_HP_zero(j);
		}
	}

	protected void removeEM_HP_zero(int j) {
		if (extra_items.get(j).getHP() == 0) {
//						score += extra_items.get(j).getPoint();
			// increaseScore ต่อออ
			extra_items.remove(j);
		}
	}

	protected void removeEM_OutsideBox(int i) {
		if (CollisionDetector.isBulletColideBorder(bullets.get(i)
				.getShape())) { // Remove OutSideBox
			bullets.remove(i);
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

	private void updateEM(GameContainer container, int delta)
			throws SlickException { // EM CollideShip
		for (int i = 0; i < extra_items.size(); i++) {
			extra_items.get(i).update(container, delta);
			checkEMCollideShip(i);
			removeEMOutSideBox(i);
		}
	}

	private void checkEMCollideShip(int i) {
		if (CollisionDetector.isEMCollideShip(extra_items.get(i).getShape(),
				ship.getShape())
				&& extra_items.get(i).getIsInBox()
				&& extra_items.get(i).getAlpha() == 1) {
			this.IS_GAME_OVER = true;
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
