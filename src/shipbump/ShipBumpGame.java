package shipbump;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.lwjgl.input.Mouse;


public class ShipBumpGame extends BasicGame{

	private String mouse_position = "";
	public static final int GAME_WIDTH = 640;
	public static final int GAME_HEIGHT = 480;
	private Ship ship;

	public ShipBumpGame(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.drawString(mouse_position, GAME_WIDTH*3/5, 10);
		ship.draw();	
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		ship = new Ship(GAME_WIDTH/2, GAME_HEIGHT/2);
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		ship.movement(Mouse.getX(), Mouse.getY());
		mouse_position = "Mouse Position X : " + Mouse.getX() + "\nMouse Position Y : " + Mouse.getY();

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
