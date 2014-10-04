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
	
	private LinkedList<Entity> entities;
	
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
		entities = new LinkedList<Entity>();
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.drawString(mouse_position, GAME_WIDTH*3/5, 10);
		graphics.drawString(score_str, GAME_WIDTH*4/5, GAME_HEIGHT - 30);
		for (Entity entity : entities) {
		      entity.render(graphics);
		}	
//		for (ExtraterrestrialMaterial item : extra_items) {
		for (int i = 0; i < extra_items.size(); i++) {
//		      item.render(graphics);
			extra_items.get(i).render(graphics);
		}	
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(graphics);
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		score_str = "";
		score = 0;
		ship = new Ship(GAME_WIDTH / 2, GAME_HEIGHT / 2);
		entities.add(ship); 		
		for (int i = 0; i < 7; i++) {
			extra_items.add(new ExtraterrestrialMaterial());
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		mouse_position = "Mouse Position X : " + Mouse.getX() + "\nMouse Position Y : " + Mouse.getY();
	    for (Entity entity : entities) {
	    	entity.update(container, delta);
	    }
		Input input = container.getInput();
		if (input.isMousePressed(0)) {
			try {
				bullets.add(new Bullet(ship.shipCenterX(), ship.shipCenterY(), ship.getAngleRotation()));
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("================== TEST Mouse Click ====================");
//			System.out.println("ShipCenterX = " + ship.shipCenterX());
//			System.out.println("ShipCenterY = " + ship.shipCenterY());
//			System.out.println("ShipAngleRotation = " + ship.getAngleRotation());
		}
//	    for (ExtraterrestrialMaterial item : extra_items) {
	    for (int i = 0; i < extra_items.size(); i++) {
//		      item.update(container, delta);
	    	extra_items.get(i).update(container, delta);
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(container, delta);
		}
	    score_str = "Score : " + score;
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
