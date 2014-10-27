package shipbump;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class PlaySound {
	
	private ArrayList<Music> musics = new ArrayList<Music>();
	private Sound useItemBaria;
	private Sound useItemDY;
	private Sound useItemManyTarget;
	private Sound useItemNuclear;
	private Sound useItemTimePause;
	private Sound useRandomItem;
	
	private Sound collideBorder;
	private Sound collideBullet;
	private Sound pickItem;
	
	private Sound death_Alien;
	private Sound death_Gengar;
	private Sound death_KeepoloPink;
	private Sound death_KeepoloRed;
	
	private Sound gameOver;
	private Sound gunShot;
	
	private Music music;
	
	public PlaySound() throws SlickException {
		
		this.musics.add(new Music("res/Sound/BGM_BrightEyes.wav"));
		this.musics.add(new Music("res/Sound/BGM_Final.wav"));
		this.musics.add(new Music("res/Sound/BGM_HeartBreak.wav"));
		this.musics.add(new Music("res/Sound/BGM_Love.wav"));
		this.musics.add(new Music("res/Sound/BGM_MI.wav"));
		this.musics.add(new Music("res/Sound/BGM_PoisonMedicine.wav"));
		this.musics.add(new Music("res/Sound/BGM_WarZone.wav"));
		
		this.useItemBaria = new Sound("res/Sound/UseItemBaria.wav");
		this.useItemDY = new Sound("res/Sound/UseItemDY.wav");
		this.useItemManyTarget = new Sound("res/Sound/UseItemManyTarget.wav");
		this.useItemNuclear = new Sound("res/Sound/UseItemNuclear.wav");
		this.useItemTimePause = new Sound("res/Sound/UseItemTimePause.wav");
		this.useRandomItem = new Sound("res/Sound/UseRandomItem.wav");
		
		this.collideBorder = new Sound("res/Sound/CollideBorder.wav");
		this.collideBullet = new Sound("res/Sound/CollideBullet.wav");
		this.pickItem = new Sound("res/Sound/PickItem.wav");
		
		this.gameOver = new Sound("res/Sound/GameOver.wav");
		this.gunShot = new Sound("res/Sound/GunShot.wav");
		
		this.death_Alien = new Sound("res/Sound/Death_Alien.wav");
		this.death_Gengar = new Sound("res/Sound/Death_Gengar.wav");
		this.death_KeepoloPink = new Sound("res/Sound/Death_KeepoloPink.wav");
		this.death_KeepoloRed = new Sound("res/Sound/Death_KeepoloRed.wav");
		
		randomMusicFile();
	}

	private void randomMusicFile() {
		Random random = new Random();
		this.music = this.musics.get(random.nextInt(this.musics.size()));
	}

	public void playSound_Musics() {
		if (!this.music.playing()) {
			randomMusicFile();
		}
		this.music.loop();
	}

	public void playSound_UseItemBaria() {
		this.useItemBaria.play();
	}

	public void playSound_UseItemDY() {
		this.useItemDY.play();
	}

	public void playSound_UseItemManyTarget() {
		this.useItemManyTarget.play();
	}

	public void playSound_UseItemNuclear() {
		this.useItemNuclear.play();
	}

	public void playSound_UseItemTimePause() {
		this.useItemTimePause.loop();
	}

	public void playSound_UseRandomItem() {
		this.useRandomItem.play();
	}

	public void playSound_CollideBorder() {
		this.collideBorder.play();
	}

	public void playSound_CollideBullet() {
		this.collideBullet.play();
	}

	public void playSound_PickItem() {
		this.pickItem.play();
	}

	public void playSound_Death_Alien() {
		this.death_Alien.play();
	}

	public void playSound_Death_Gengar() {
		this.death_Gengar.play();
	}

	public void playSound_Death_KeepoloPink() {
		this.death_KeepoloPink.play();
	}

	public void playSound_Death_KeepoloRed() {
		this.death_KeepoloRed.play();
	}

	public void playSound_GameOver() {
		this.gameOver.play();
	}

	public void playSound_GunShot() {
		this.gunShot.play();
	}

	public void stopEverthing() {
		this.music.stop();
		this.useItemBaria.stop();
		this.useItemDY.stop();
		this.useItemManyTarget.stop();
		this.useItemNuclear.stop();
		this.useItemTimePause.stop();
		this.useRandomItem.stop();
		
		this.collideBorder.stop();
		this.collideBullet.stop();
		this.pickItem.stop();
		
		this.death_Alien.stop();
		this.death_Gengar.stop();
		this.death_KeepoloPink.stop();
		this.death_KeepoloRed.stop();
		this.gunShot.stop();
	}

	public void stopTimePause() {
		this.useItemTimePause.stop();
	}
}
