package shipbump;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.lwjgl.input.Mouse;



public class ShipBumpGame extends BasicGame{
	
	private ArrayList<ExtraterrestrialMaterial> extra_items = new ArrayList<ExtraterrestrialMaterial>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private String mouse_position = "";
	private String score_str;
	private int score;
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	private Ship ship;


	public ShipBumpGame(String title) throws SlickException {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		ship.render(graphics);
		renderWordString(graphics);
		renderEM(graphics);
		renderBullet(graphics);
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
	}

	private void renderWordString(Graphics graphics) {
		graphics.drawString(mouse_position, GAME_WIDTH*3/5, 10);
		graphics.drawString(score_str, GAME_WIDTH*4/5, GAME_HEIGHT - 30);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		score_str = "";
		score = 0;
		ship = new Ship(GAME_WIDTH / 2, GAME_HEIGHT / 2);		
		for (int i = 0; i < 7; i++) {
			extra_items.add(new ExtraterrestrialMaterial());
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		ship.update(container, delta);
		clickMouseShooting(container);
	    updateEM(container, delta);
		updateBullet(container, delta);
		updateWordString();
		increaseScore();
	}

	private void increaseScore() {
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < extra_items.size(); j++) {
				if (CollisionDetector.isEMCollideBullet(extra_items.get(j).getShape(), bullets.get(i).getShape())) {
					extra_items.get(j).decreaseHP();
					if (extra_items.get(j).getHP() == 0) {
						extra_items.remove(j);
						score++;
					}
				}
			}
		}
		
	}

	private void updateWordString() {
		mouse_position = "Mouse Position X : " + Mouse.getX() + "\nMouse Position Y : " + Mouse.getY();
	    score_str = "Score : " + score;
	}

	private void updateBullet(GameContainer container, int delta) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(container, delta);
		}
	}

	private void updateEM(GameContainer container, int delta) {
		for (int i = 0; i < extra_items.size(); i++) {
	    	extra_items.get(i).update(container, delta);
	    	if (CollisionDetector.isEMCollideShip(extra_items.get(i).getShape(), ship.getShape())) {
	    		System.out.println("EM collide Ship");
	    	}
		}
	}

	private void clickMouseShooting(GameContainer container) {
		Input input = container.getInput();
		if (input.isMousePressed(0)) {
			try {
				bullets.add(new Bullet(ship.shipCenterX(), ship.shipCenterY(), ship.getAngleRotation()));
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
			container.start();
	    } catch (SlickException e) {
	    	e.printStackTrace();
	    }
	}

}
