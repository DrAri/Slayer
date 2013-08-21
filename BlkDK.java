package scripts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Screen;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.*;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Pausing;
import java.util.HashMap;
import org.tribot.api2007.WebWalking;


@ScriptManifest(authors = { "Yaw hide" }, version = 1.4, category = "Combat", name = "Yawhide's BlkDK", description = "Welcome to my first black dragon script!")
public class BlkDK extends Script implements Painting, Pausing {
	
	private int[] foodIDs = { 333, 329, 379, 361, 7946, 1897 };
	private int foodID;
	
	private int chicken = 2138;
	private int[] dragz = { 642, 640 };
	private int boltsID = 9142;

	private RSTile[] toShrinePath = { new RSTile(2385, 4458, 0), new RSTile(2394, 4459, 0), new RSTile(2403, 4453, 0), 
			new RSTile(2414, 4455, 0), new RSTile(2425, 4458, 0), new RSTile(2436, 4456, 0), new RSTile(2445, 4453, 0),
			new RSTile(2453, 4459, 0), new RSTile(2452, 4467, 0), new RSTile(2453, 4476, 0) };
	
	private RSTile[] toMiningArea = { new RSTile(2471, 4363, 0), new RSTile(2476, 4369, 0), new RSTile(2483, 4372, 0),
			new RSTile(2485, 4374, 0) };

	private RSTile[] toSafeSpotFromMiningArea = { new RSTile(2485, 4375, 0), new RSTile(2476, 4369, 0), new RSTile(2472, 4363, 0) };
	
	private RSTile[] toBank = { new RSTile(2453, 4476, 0), new RSTile(2453, 4463, 0), new RSTile(2448, 4454, 0), 
			new RSTile(2436, 4456, 0), new RSTile(2423, 4458, 0), new RSTile(2413, 4455, 0), 
			new RSTile(2401, 4452, 0), new RSTile(2393, 4456, 0), new RSTile(2387, 4457, 0) };
	
	private RSTile[] toPortalPath = { new RSTile(2463, 4371, 0), new RSTile(2462, 4360, 0), new RSTile(2461, 4356, 0)};
	   
	private int rangepot3 = 169;
	private int rangepot2 = 171;
	private int rangepot1 = 173;
	private int[] rangepots = { rangepot3, 2444, rangepot2, rangepot1 };
	private int[] prayerpots = { 141, 139, 2434, 143 };
	private int[] antipoison = { 2446, 175, 177, 179 };
	
	private int[] junk = { 886, 1539, 9003, 229, 1623, 1355, 440, 7767, 117,
			6963, 554, 556, 829, 1971, 687, 464, 1973, 1917, 808, 1454, 6180,
			6965, 1969, 6183, 6181, 6962 };

	private int[] loot = { 1369, 995, 816, 1747, 536, 9142, 868, 563,
			1615, 1315, 1319, 1373, 1247, 1303, 1249, 1123, 1149, 1197, 1201,
			1186, 1113, 1079, 892, 565, 560, 561, 563, 2361, 2366, 443, 1462,
			985, 987, 2363, 1617, 1619, 574, 11286, /* clue scroll id starts */
			2722, 2723, 2725, 2727, 2729, 2731, 2733, 2725, 2737, 2739, 2741,
			2743, 2745, 2747, 2773, 2774, 2776, 2778, 2780, 2782, 2783, 2785,
			2786, 2788, 2790, 2792, 2793, 2794, 2796, 2797, 2799, 3520, 3522,
			3524, 3525, 3526, 3528, 3530, 3532, 3534, 3536, 3538, 3540, 3542,
			3544, 3546, 3548, 3560, 3562, 3564, 3566, 3568, 3570, 3272, 3573,
			3574, 3575, 3577, 3579, 3580, 7239, 7241, 7243, 7245, 7247, 7248,
			7249, 7250, 7251, 7252, 7253, 7254, 7255, 7256, 7258, 7260, 7262,
			7264, 7266, 7268, 7270, 7272, 10234, 10236, 10238, 10240, 10242,
			10244, 10426, 10428, 40250, 10252, 13010, 13012, 13014, 13016,
			13018, 13020, 13022, 13024, 13026, 13028, 13030, 13032, 13034,
			13036, 13038, 13040, 13041, 13042, 13044, 13046, 13048, 13049 };

	private int[] loot2 = { 1369, 816, 1747, 536, 868, 563, 1615, 1315,
			1319, 1373, 1247, 1303, 1249, 1123, 1149, 1197, 1201, 1186, 1113,
			1079, 892, 565, 560, 561, 563, 2361, 2366, 443, 1462, 985, 987,
			2363, 1617, 1619, 574, 11286, /* clue scroll id starts */
			2722, 2723, 2725, 2727, 2729, 2731, 2733, 2725, 2737, 2739, 2741,
			2743, 2745, 2747, 2773, 2774, 2776, 2778, 2780, 2782, 2783, 2785,
			2786, 2788, 2790, 2792, 2793, 2794, 2796, 2797, 2799, 3520, 3522,
			3524, 3525, 3526, 3528, 3530, 3532, 3534, 3536, 3538, 3540, 3542,
			3544, 3546, 3548, 3560, 3562, 3564, 3566, 3568, 3570, 3272, 3573,
			3574, 3575, 3577, 3579, 3580, 7239, 7241, 7243, 7245, 7247, 7248,
			7249, 7250, 7251, 7252, 7253, 7254, 7255, 7256, 7258, 7260, 7262,
			7264, 7266, 7268, 7270, 7272, 10234, 10236, 10238, 10240, 10242,
			10244, 10426, 10428, 40250, 10252, 13010, 13012, 13014, 13016,
			13018, 13020, 13022, 13024, 13026, 13028, 13030, 13032, 13034,
			13036, 13038, 13040, 13041, 13042, 13044, 13046, 13048, 13049 };

	HashMap<Integer, String> map1 = new HashMap<Integer, String>(151);
	HashMap<Integer, String> map = new HashMap<Integer, String>(loot.length);
	
	private String[] names = { "Mithril battleaxe", "Coins", "Adamant dart(p)",
			"Black dragonhide", "Dragon bones", "Mithril bolts",
			"Rune knife", "Law rune", "Dragonstone",
			"Mithril 2h sword", "Rune 2h sword", "Rune battleaxe",
			"Rune spear", "Rune longsword", "Dragon spear",
			"Adamant platebody", "Dragon med helm", "Mithril kiteshield",
			"Rune kiteshield", "Rune sq shield", "Rune chainbody",
			"Rune platelegs", "Rune arrow", "Blood rune", "Death rune",
			"Nature rune", "Law rune", "Adamantite bar", "Shield left half",
			"Silver ore", "Nature talisman", "Half of a key", "Half of a key",
			"Runite bar", "Uncut diamond", "Uncut ruby", "Air orb",
			"Draconic visage", "Clue scroll" };

	public void putMap() {
		for (int i = 0; i < loot.length; i++) {
			if (i >= names.length) {
				map.put(loot[i], names[names.length - 1]);
			} else
				map.put(loot[i], names[i]);
		}
	}

	// antiban!
	private long antiban = System.currentTimeMillis();
	private int[] evilchicken = { 2465, 2467 };
	private int swarm = 407;

	public State currentState;

	// Boxes

	// TODO
	// need to make the safespot location general (so i can just call these
	// tiles in my safespot method
	// change from south safe spot to east safespot

	// general safe spot variables
	private RSTile nwsafespotf;
	private RSTile sesafespotf;

	// zanaris stuff
	
	
	private RSTile zbanktile = new RSTile(2386, 4458, 0);
	private RSTile southshrine = new RSTile(2452, 4453, 0);
	private RSTile middlearea = new RSTile(2412, 4458, 0);
	private RSTile bankarea = new RSTile(2384, 4456, 0);
	private RSTile portaltile = new RSTile(2461, 4356, 0);
	private int portal = 12260;
	private boolean forcebank = false;
	private RSTile shrinetile = new RSTile(2453, 4476, 0);
	private RSTile shrinetileT = new RSTile(2453, 4477, 0);
	private int shrine = 12093;
	private RSTile miningtile = new RSTile(2484, 4377, 0);
	private RSTile eloottile = new RSTile(2465, 4363, 0); // loot tile for east
															// spot
	private RSTile loottile;

	

	private RSTile nwbankz = new RSTile(2383, 4462, 0);
	private RSTile sebankz = new RSTile(2389, 4455, 0);

	

	private RSTile nwzarea = new RSTile(2381, 4478, 0);
	private RSTile sezarea = new RSTile(2469, 4421, 0);

	// zanaris
	private RSTile nwdragz = new RSTile(2445, 4393, 0);
	private RSTile sedragz = new RSTile(2480, 4356, 0);

	// weird spot you shouldn't be in
	private RSTile nwWeirdSpotZ = new RSTile(2450, 4376, 0);
	private RSTile seWeirdSpotZ = new RSTile(2461, 4357, 0);

	// zanaris safe spot tiles
	private RSTile nwESafeSpotZ = new RSTile(2470, 4364, 0);
	private RSTile seESafeSpotZ = new RSTile(2473, 4363, 0);
	private RSTile nwNSafeSpotZ = new RSTile(2470, 4364, 0);
	private RSTile seNSafeSpotZ = new RSTile(2473, 4363, 0);
	
	private RSTile evilChickenTile = new RSTile(2460, 4397, 0);
	private RSTile[] toSafeSpotPath = { new RSTile(2460, 4387, 0), new RSTile(2460, 4380, 0), new RSTile(2462, 4371, 0), 
			new RSTile(2466, 4363, 0), new RSTile(2472, 4363, 0) };
	
	// TODO add your handling code here:
	// need to add north safespot.

	private RSTile safez;
	private RSTile safeEastZanarisMM = new RSTile(2472, 4363, 0);
	private RSTile safeNorthZanarisMM = new RSTile(2472, 4363, 0);
	// TODO add your handling code here:
	// have to setup still the safe spot location for the north end.

	// general safespot variables
	private RSTile nwSafeSpotZ;
	private RSTile seSafeSpotZ;

	// area for 4674 dragon
	// private RSTile nw4674drag = new RSTile(2451, 4364, 0);
	// private RSTile se4674drag = new RSTile(4356, 4360, 0);

	private boolean fally = false;
	private boolean antipot = false;

	// ALL PAINT STUFF
	private int startXp = Skills.getXP("Constitution") + Skills.getXP("Range");
	private double version;
	private int current_xp;
	private final long start_time = System.currentTimeMillis();
	// private double XpToLV = Skills.getXPToNextLevel("Constitution");
	private double XpToLVrange = Skills.getXPToNextLevel("Range");
	private double XpToLVhp = Skills.getXPToNextLevel("Constitution");
	public int antibancounter = 0;
	boolean usedchicken = false;
	String fightstate;

	// for counting # of items for paint
	private int tmp_lootCount = 0;
	private int dbones = 0;
	private int dhides = 0;
	private int visage = 0;
	private int cluescroll = 0;

	public BlkDK() {
		version = ((ScriptManifest) getClass().getAnnotation(
				ScriptManifest.class)).version();
	}

	@Override
	public void onPaint(Graphics g) {

		long timeRan = System.currentTimeMillis() - start_time;
		int xpGained = current_xp - startXp;
		double multiplier = timeRan / 3600000D;
		int xpPerHour = (int) (xpGained / multiplier);
		double show_xp_to_lv_range = ((XpToLVrange / xpPerHour) * 60);
		double show_xp_to_lv_hp = ((XpToLVhp / xpPerHour) * 60);

		Rectangle hideZone = new Rectangle(337, 312, 555, 454);
		Point p = Mouse.getPos();

		RSObject[] LOWWALL = Objects.findNearest(10, new int[] { lowwall });
		if (LOWWALL.length > 0) {
			drawModel(LOWWALL[0].getModel(), g, false);
		}

		RSObject[] ladder = Objects.findNearest(10, ladderdown);
		if (ladder.length > 0) {
			drawModel(ladder[0].getModel(), g, false);
		}

		RSObject[] SPIKES = Objects.findNearest(10, spike);
		if (SPIKES.length > 0) {
			drawModel(SPIKES[0].getModel(), g, false);
		}

		RSObject[] SHRINE = Objects.findNearest(10, shrine);
		if (SHRINE.length > 0) {
			drawModel(SHRINE[0].getModel(), g, false);
		}

		RSNPC[] drag = NPCs.findNearest(dragz);
		if (drag.length > 0) {
			if (drag[0].isInteractingWithMe()){
				drawTile(drag[0].getPosition(), g, false);
			}
			drawTile(drag[0].getPosition(), g, false);
		}
		

		if (!hideZone.contains(p)) {

			drawTile(safeEastZanarisMM, g, false);
			drawTile(safeNorthZanarisMM, g, false);
			drawTile(shrinetile, g, false);
			drawTile(southshrine, g, false);
			drawTile(middlearea, g, false);
			drawTile(bankarea, g, false);

			g.setColor(new Color(60, 60, 60));
			g.fillRect(340, 310, 225, 180);

			g.setColor(Color.WHITE);
			g.drawString("Yawhide's BlkDK", 345, 325);
			g.drawString("Version :" + version, 345, 345);
			g.drawString("Running for: " + Timing.msToString(timeRan), 345, 365);
			g.drawString("Total XP ganed: " + xpGained + " (" + xpPerHour
					+ "/h)", 345, 385);
			g.drawString(
					"Time till next ranged lv : "
							+ (int) (show_xp_to_lv_range / 60)
							+ " hours "
							+ ((int) show_xp_to_lv_range - ((int) (show_xp_to_lv_range / 60)) * 60)
							+ " mins.", 345, 405);
			g.drawString(
					"Time till next HP lv : "
							+ (int) (show_xp_to_lv_hp / 60)
							+ " hours "
							+ ((int) show_xp_to_lv_hp - ((int) (show_xp_to_lv_hp / 60)) * 60)
							+ " mins.", 345, 425);
			g.drawString("Current State: " + currentState, 345, 445);
			g.drawString("Sub Fight State: " + fightstate, 345, 465);
			g.drawString("Dbones:" + dbones + " Dhide: " + dhides
					+ " visages: " + visage + " clues: " + cluescroll,
					345, 485);
		}
	}

	// gui variables here
	public boolean GUI_COMPLETE = false;
	public String location;
	public String safespot;
	public String foodusing;
	public boolean rangepotting;

	@Override
	public void run() {

		myForm GUI = new myForm();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenW = screenSize.width;
		int screenH = screenSize.height;

		Dimension dim = GUI.getSize();

		GUI.setVisible(true);

		GUI.setLocation((screenW / 2) - (dim.width / 2), (screenH / 2)
				- (dim.height / 2));

		while (!GUI_COMPLETE) {
			sleep(200);
		}
		GUI.setVisible(false);

		println("   ");
		println("GUI CLOSED and COMPLETED!");
		println("Location is: " + location);
		println("Safe spot is: " + safespot);
		println("Food going to be used: " + foodusing);
		println("Using range pots? " + rangepotting);
		println("   ");

		// after gui variable initialization
		if (safespot == "Zanris-east ss") {
			safez = safeEastZanarisMM;
			nwSafeSpotZ = nwESafeSpotZ;
			seSafeSpotZ = seESafeSpotZ;
			loottile = eloottile;
		} else if (safespot == "Zanris-north ss") {
			safez = safeNorthZanarisMM;
		}
		

		figureoutwhattodo(); // check if we are going to kill stuff in
								// zanaris
								// or falador.
		sleep(80, 100);

		println("Welcome to my BlkDK!!!!");
		Mouse.setSpeed(General.random(200, 210));

		putMap();
		sleep(200, 250);

		while (true) {

			current_xp = Skills.getXP("Range") + Skills.getXP("Constitution");
			currentState = getState();
			XpToLVrange = Skills.getXPToNextLevel("Range");
			XpToLVhp = Skills.getXPToNextLevel("Constitution");

			if (getHp() < 30) {

				println("My current position is: " + pos().getX() + " , "
						+ pos().getY());
				println("My current state: " + currentState);
				println("My current substate: " + fightstate);
				sleep(200, 250);
			}

			switch (getState()) {

			case HEAL:
				HEAL();
				break;
			case DROPJUNK:
				DROPJUNK();
				break;
			case LOOT:
				LOOT();
				break;
			case ANTIBAN:
				ANTIBAN();
				break;
			case JUMP_WALL:
				JUMP_WALL();
				break;
			case GOTO_WALL:
				GOTO_WALL();
				break;
			case GOTO_LADDER:
				GOTO_LADDER();
				break;
			case CLIMB_LADDER:
				CLIMB_LADDER();
				break;
			case GOTO_SPIKE:
				GOTO_SPIKE();
				break;
			case JUMP_SPIKE:
				JUMP_SPIKE();
				break;
			case GOTO_DRAG:
				GOTO_DRAG();
				break;
			case GOTO_SHRINE:
				GOTO_SHRINE();
				break;
			case USE_SHRINE:
				USE_SHRINE();
				break;
			case GOTO_SOUTHSHRINE:
				GOTO_SOUTHSHRINE();
				break;
			case GOTO_MIDDLEAREA:
				GOTO_MIDDLEAREA();
				break;
			case GOTO_BANKZ:
				GOTO_BANKZ();
				break;
			case BANKZ:
				BANKZ();
				break;
			case FIGHTZ:
				FIGHTZ();
				break;
			case GOTO_SAFEZ:
				GOTO_SAFEZ();
				break;
			case WALK_PATH_SHRINE:
				WALKING_PATH_SHRINE();
				break;
			case WALKING_PATH_BANK:
				WALKING_PATH_BANK();
				break;
			case GOTO_SAFEZ_FROM_MINING:
				GOTO_SAFEZ_FROM_MINING();
				break;
			case UNKNOWN_POSITION:
				break;
			}
			sleep(50, 100);
		}
	}

	private static enum State {
		ANTIBAN, HEAL, DROPJUNK, LOOT, BANK, USE_SHRINE, UNKNOWN_POSITION, 
		
		GOTO_BANK, JUMP_WALL, GOTO_WALL, GOTO_LADDER, CLIMB_LADDER, GOTO_SPIKE, JUMP_SPIKE, GOTO_DRAG,

		GOTO_SHRINE, GOTO_SOUTHSHRINE, GOTO_MIDDLEAREA, GOTO_BANKZ, BANKZ, FIGHTZ, GOTO_SAFEZ, 
		
		WALK_PATH_SHRINE, WALKING_PATH_BANK, GOTO_SAFEZ_FROM_MINING
	}

	public State getState() {

		
		RSNPC[] evilChicken = NPCs.findNearest(evilchicken);
		RSNPC[] SWARM = NPCs.findNearest(swarm);

		if (evilChicken.length > 0) {
			if (evilChicken[0].getPosition().distanceTo(pos()) <= 3
					|| evilChicken[0].isInteractingWithMe())
				runFromCombat();
		}
		else if (SWARM.length > 0) {
			if (SWARM[0].getPosition().distanceTo(pos()) <= 3
					|| SWARM[0].isInteractingWithMe())
				runFromCombat();
		}
		else if (pos().distanceTo(evilChickenTile) <= 6){
			Walking.walkPath(toSafeSpotPath);
		}
		else if (((pos().distanceTo(portaltile) <= 2 && inCombat()) || inArea(
				nwWeirdSpotZ, seWeirdSpotZ, pos()))
				&& Inventory.getCount(foodID) <= 5
				&& Inventory.getCount(loot) < 3 && forcebank == false)
			return State.GOTO_SAFEZ;

		else if (getHp() <= 70) {
			if (Inventory.getCount(foodID) == 0) {
				if (getHp() <= 50) {

					println("Out of food and low hp, getting the hell out of here");
					forcebank = true;
					if (inDragArea())
						return State.GOTO_BANKZ;
					else if (pos().distanceTo(zbanktile) <= 7)
						return State.BANKZ;
					else
						return State.GOTO_BANKZ;
				}
			} 
			else if (inDragArea()){
				if (!lootIsOnGround() && getHp() <= 50) {
					return State.GOTO_SAFEZ;
				}
				return State.HEAL;
			}
			else
				return State.HEAL;
			
		}

		
			
		else if (isFull()) {
			if (pos().distanceTo(zbanktile) <= 7)
				return State.BANKZ;
			else if (!inDragArea())
				return State.GOTO_BANKZ;
			else if (Inventory.getCount(foodID) == 0 && Inventory.getCount(junk) < 2 
					&& !lootIsOnGround()) {

				println("INV is full");
				return State.GOTO_BANKZ;
			} 
			else {
				return State.DROPJUNK;
			}

		}

		if (inDragArea()) {

			return State.FIGHTZ;
		}
		
		if (doneBanking() && !inDragArea()) {
			
			if (pos().distanceTo(shrinetile) <= 4)
				return State.USE_SHRINE;
			
			return State.WALK_PATH_SHRINE;
		}

		if (pos().distanceTo(miningtile) <= 4)
			return State.GOTO_SAFEZ_FROM_MINING;

		if (!doneBanking()) {

			if (pos().distanceTo(zbanktile) <= 7)
				return State.BANKZ;
			return State.GOTO_BANKZ;
		}
		
		// antiban logic
		if (System.currentTimeMillis() - antiban > (General.random(300000,
				600000)))
			return State.ANTIBAN;

		return State.UNKNOWN_POSITION;

	}


	// .................................................................
	//
	// zanaris stuff

	// ..................................................................
	//TODO 
	// Walking path method
	public void WALKING_PATH_SHRINE(){

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Mouse.setSpeed(General.random(140, 170));
		Walking.walking_timeout = 500L;
		Walking.walkPath(toShrinePath);
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(50, 100);
	}
	
	public void WALKING_PATH_BANK(){

		Mouse.setSpeed(General.random(140, 170));
		Walking.walking_timeout = 500L;
		Walking.walkPath(toBank);
		sleep(50, 100);
	}
	public void GOTO_SAFEZ_FROM_MINING(){

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Mouse.setSpeed(General.random(140, 170));
		Walking.walking_timeout = 500L;
		Walking.walkPath(toSafeSpotFromMiningArea);
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(50, 100);
	}
	
	public void GOTO_SHRINE() {

		// Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walking_timeout = 500L;
		Walking.walkPath(Walking.generateStraightPath(shrinetile));
		// Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(50, 100);
	}
/*
	public void GOTO_PORTAL() {

		RSObject[] Portal = Objects.findNearest(10, portal);

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);

		if (isFull() || forcebank == true) {

			println("inventory is full or I need to go to bank cuz i am out of food and low hp");

			if (Portal.length > 0) {

				Walking.walkPath(Walking.generateStraightPath(portaltile));
				if (Portal[0].isOnScreen()) {
					Mouse.setSpeed(General.random(150, 170));
					println("setting mouse speed to 150-170");
					if (DynamicClicking.clickRSObject(Portal[0], "Use"))
						Timing.waitCondition(new Condition() {
							;
							@Override
							public boolean active() {
								return !inDragArea();
							}
						}, General.random(1200, 1300));
				} else {
					Camera.turnToTile(new RSTile(2462, 4340, 0));
					Camera.setCameraAngle(General.random(95, 100));
					sleep(100, 150);
				}
			} else if (inDragArea()) {
				Walking.walkPath(Walking.generateStraightPath(portaltile));
			}
		} else
			Walking.walkPath(Walking.generateStraightPath(safez));
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}
*/
	public void GOTO_SOUTHSHRINE() {

		// Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walking_timeout = 500L;
		Walking.walkPath(Walking.generateStraightPath(southshrine));
		// Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	public void GOTO_MIDDLEAREA() {

		// Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walking_timeout = 500L;
		Walking.walkPath(Walking.generateStraightPath(middlearea));
		// Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	public void USE_SHRINE() {

		Camera.setCameraRotation(General.random(0, 3));
		RSItem[] chicken = Inventory.find(2138);
		RSObject[] shrine = Objects.getAt(shrinetileT);//.findNearest(5, 12093);

		if (shrine.length > 0) {

			if (shrine[0].isOnScreen()) {
				if (chicken.length > 0) {
					if (chicken[0].click("Use")) {
						sleep(500, 1000);
						// Mouse.setSpeed(General.random(330, 360));
						// println("setting mouse speed to 330-360");
						if (clickOBJ(shrine[0], "Use")) {// DynamicClicking.clickRSObject(shrine[0],
															// "Use")) {
							
							Timing.waitCondition(new Condition() {
								;
								@Override
								public boolean active() {
									return inDragArea();
								}
							}, General.random(2200, 2300));
						}

					}
				}
			}

		}
		sleep(1000, 1200);

	}
	
	public void USE_PORTAL() {
		
		RSObject[] PORTAL = Objects.findNearest(10, "Portal");
		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		
		Camera.turnToTile(portaltile);
		sleep(200,300);
		
		if (PORTAL.length > 0){
			
			if(PORTAL[0].click("Use")){//clickOBJ(PORTAL[0], "Use")){
				Timing.waitCondition(new Condition() {
					;
					@Override
					public boolean active() {
						return !inDragArea();
					}
				}, General.random(2200, 2300));
			}
		}
		
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
	}

	public void GOTO_BANKZ() {

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walking_timeout = 500L;
		//Walking.walkPath(Walking.generateStraightPath(zbanktile));
		if(pos().distanceTo(portaltile) <=2){
			USE_PORTAL();
			sleep(200,300);
		}
		else if(inDragArea()){
			//WebWalking.walkTo(portaltile);
			Walking.walkPath(toPortalPath);
			sleep(200,300);
			USE_PORTAL();
		}
		else
			Walking.walkPath(toBank);
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	public void GOTO_SAFEZ() { // safez

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		
		Walking.walking_timeout = 1500L;
		fightstate = "Going to safe spot";
		int x = Mouse.getSpeed();
		Mouse.setSpeed(General.random(140, 150));
		sleep(500,750);
		
		if (pos().distanceTo(safez) > 3)
			Walking.clickTileMM(safez, 1);
		else if (safez.isOnScreen() && pos().distanceTo(safez) > 2) {
			if (Camera.getCameraAngle() < 60) {
				Camera.setCameraAngle(General.random(60, 70));
			}
			//Walking.walkScreenPath(Walking.generateStraightScreenPath(safez));
			Walking.blindWalkTo(safez);
		} 
		else if (!inSafeSpotZ()){
			Walking.clickTileMM(safez, 1);
		}
		
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
		Mouse.setSpeed(General.random(x-10, x+10));
	}
	
	//TODO banking method
	public void BANKZ() {

		RSNPC[] banker = NPCs.findNearest("Banker");

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);

		if (inArea(nwbankz, sebankz, pos())) {

			Camera.turnToTile(banker[0].getPosition());
			Camera.setCameraAngle(General.random(95, 100));
			sleep(100, 150);
			if (Banking.openBankBanker()){
				sleep(1200, 1250);
			
				if (Banking.isPinScreenOpen()) {

					Banking.inPin();
					sleep(200, 250);
				}
				if (Banking.isBankScreenOpen()) {
					if (Inventory.getAll().length != 0){
						Banking.depositAll();
					}
					
					//sleep(200, 250);
					Mouse.clickBox(471, 78, 478, 84, 1);
					Mouse.setSpeed(General.random(150,170));
					sleep(200, 250);
					if (getHp() <= 85) {

						println("eating to full HP");
						withdraw(((Skills
								.getActualLevel("Constitution") - Skills
								.getCurrentLevel("Constitution")) / 9), foodID);
						/*Banking.withdraw(((Skills
								.getActualLevel("Constitution") - Skills
								.getCurrentLevel("Constitution")) / 9), foodID);*/
						sleep(200, 250);
						Banking.close();
						BANK_SECTION = 0;
						sleep(200, 250);
						do {
							HEAL();
							sleep(200, 250);
						} while (Inventory.getCount(foodID) > 0);
						sleep(200, 250);
						//DynamicClicking.clickRSNPC(banker[0], "Bank Banker");
						if (!Banking.isBankScreenOpen()) {
							if (Banking.openBankBanker()) {
								sleep(200, 250);
							}
						}
						
					}
					if (Banking.isBankScreenOpen()) {

						//Banking.withdraw(5, foodID);
						withdraw(5, foodID);
						sleep(200, 250);
						//Banking.withdraw(1, chicken);
						withdraw(1, chicken);
						sleep(200, 250);
						
						if (Banking.find(rangepots).length > 0
								&& rangepotting == true) {
							//Banking.withdraw(1, rangepots);
							withdraw(1, rangepots);
							sleep(200, 250);
						}
						if (getStackBolts() < 300) {

							println("withdrawing some bolts cuz we have less than 500");
							//Banking.withdraw(200, boltsID);
							withdraw(200, boltsID);
							sleep(200, 250);
						}

						sleep(200, 250);
						Banking.close();
						sleep(200, 250);
						forcebank = false;
						BANK_SECTION = 0;
						println("Done banking for stuff");
						
					}
				}
			}
		} else
			Walking.walkScreenPath(Walking
					.generateStraightScreenPath(zbanktile));
		
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	//TODO
	//loot method
	public void LOOT() {

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		fightstate = "looting";
		if (Mouse.getSpeed() < 300) {
			Mouse.setSpeed(General.random(200, 225));
			println("Mouse speed was slow for some reason, lets speed it up!");
		}

		RSGroundItem[] Nests = GroundItems.findNearest(loot);

		if (Camera.getCameraRotation() > 42 && Camera.getCameraRotation() < 140
				&& pos().getX() > (loottile.getX() + 2))
			Camera.turnToTile(loottile);
		if (Camera.getCameraAngle() > 60
				&& pos().getX() > (loottile.getX() + 2))
			Camera.setCameraAngle(General.random(38, 42));

		if (getHp() <= 50) {
			if (Inventory.getCount(foodID) == 0) {
				forcebank = true;
			} 
			else
				HEAL();
		}
		sleep(80, 100);

		if (Nests.length > 0) {

			if (!Nests[0].isOnScreen()) {

				Camera.turnToTile(loottile);
			}
			String str = map.get(Nests[0].getID());

			tmp_lootCount = lootCount(Nests[0].getID());

			Nests[0].click("Take " + str);

			waitForInv(Nests[0].getID());

			if (str == "Clue scroll")
				cluescroll++;

			tmp_lootCount = 0;
		}

		sleep(80, 100);
		Mouse.setSpeed(General.random(150, 170));
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
	}

	// TODO
	// fight and loot methods

	public void FIGHTZ() {

		RSNPC[] blkdragsz = NPCs.findNearest(dragz);
		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);

		// if there is loot PICK IT UP!
		if (lootIsOnGround() && pos().distanceTo(portaltile) > 1) {

			if (Inventory.getCount(junk) > 0 && Inventory.getAll().length > 24
					&& inSafeSpotZ())
				DROPJUNK();

			if (Inventory.getCount(junk) > 0)
				dropJunk_noEating();

			sleep(80, 100);
			println("lootin time");
			LOOT();
			sleep(80, 100);

		}

		// if I am west of the loot tile, get back to safe spot
		else if (inCombat()	&& gotFood() && getHp() > 60 && !inSafeSpotZ()) {
			println("I am a little too far out from the safespot, lets go back");

			GOTO_SAFEZ();
		}
		
		
		// if in combat,and in safespot and my anim is -1 (not doing anything)
		// and no dragons
		// around and i have some junk... drop the junk
		/*else if (!inCombat() && inSafeSpotZ() && Player.getAnimation() == -1
				&& blkdragsz.length == 0 && Inventory.getCount(junk) > 0) {

			println("waiting for dragons to spawn, gonna drop some junk");
			dropJunk_noEating();
		}*/
	
	
		// basically, pot up if you are rangelv + 2 and below
		else if (Inventory.getCount(rangepots) > 0
				&& inSafeSpotZ()
				&& Skills.getCurrentLevel("Range") < Skills
						.getActualLevel("Range") + 2) {

			println(Skills.getCurrentLevel("Range") + "  "
					+ Skills.getActualLevel("Range"));
			fightstate = "Potting up";
			drinkPotion(rangepots);
			sleep(1000,1200);
			println("Potted up");

		}

		// in safe spot, dragon isn't in cb
		else if (blkdragsz.length > 0 && 
				!blkdragsz[0].isInteractingWithMe() && !isRanging() && blkdragsz[0].getCombatCycle() <= 0//|| !blkdragsz[0].isInCombat())
				&& inSafeSpotZ() && pos().distanceTo(blkdragsz[0].getPosition()) < 15) {

			killDragon(blkdragsz);
		}

		// dragon is in combat and I am not in combat.
		else if (blkdragsz.length > 0 && blkdragsz[0].isInteractingWithMe() && inSafeSpotZ()) {
			if (!isRanging()) {
				killDragon(blkdragsz);
			} 
			//else if (true)
			//	prayerflick(blkdragsz[0]);
			else {
				fightstate = "Doing some antiban";

				if (antibancounter == General.random(120, 140)) {
					ANTIBAN();
					antibancounter = 0;
				} else {
					sleep(1000, 1200);
					antibancounter += 1;
				}
			}
		}

		// basically, i'll just be standing in the danger zone doing
		// nothing....so
		// go back to safespot.
		else if (!inSafeSpotZ()) {

			// Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
			println("going back to safespot, nothing else to do");

			GOTO_SAFEZ();

			// Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		}

		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);

	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	// // ..................................................................
	//
	// MAIN CODE
	//
	// // ..................................................................
	//

	//
	//
	//
	//
	//
	// fally code
	//
	//
	//
	//
	//
	// /
	//

	public void HEAL() {
		fightstate = "Healing up";
		GameTab.open(org.tribot.api2007.GameTab.TABS.INVENTORY);
		if (Inventory.getCount(foodID) > 0) {
			Inventory.find(foodID)[0].click(new String[] { "Eat" });
			sleep(300, 550);
		} else {
			forcebank = true;
			println("forcebank = true");
		}
	}

	//
	//
	//
	//
	//
	//
	// /
	//
	// just some handy methods
	//
	//
	//
	//
	//
	//
	//
	// /
	//
	public boolean DROPJUNK() {

		// hate dropping junk where i loot, bad idea
		fightstate = "Dropping junk/food";
		if (pos().distanceTo(eloottile) < 1) {
			if (Camera.getCameraAngle() >= 38 || Camera.getCameraAngle() <= 42) {
				Camera.setCameraAngle(General.random(80, 90));
			}
			sleep(80, 100);
		}
		GameTab.open(org.tribot.api2007.GameTab.TABS.INVENTORY);
		sleep(150, 200);
		RSItem[] junks = Inventory.find(junk);
		RSItem[] food = Inventory.find(foodID);
		RSItem[] bolts = Inventory.find(9142);
		RSItem[] cake = Inventory.find(1897);
		RSItem[] cake2 = Inventory.find(1899);
		RSItem[] cake3 = Inventory.find(1901);
		RSItem[] coinstack = Inventory.find(995);
		if (bolts.length > 0) {
			bolts[0].click("Wield");
			sleep(200, 250);
		}

		if (cake.length > 0) {
			if (getHp() == 100)
				Inventory.drop(cake[0].getID());
			else{
			cake[0].click("Eat");
			}
			sleep(650, 700);
		}
		if (cake2.length > 0) {
			if (getHp() == 100)
				Inventory.drop(cake2[0].getID());
			else
				cake2[0].click("Eat");
			sleep(650, 700);
		}
		if (cake3.length > 0) {
			if (getHp() == 100)
				Inventory.drop(cake3[0].getID());
			else
				cake3[0].click("Eat");
			sleep(650, 700);
		}
		if (food.length > 0) {
			if (getHp() == 100)
				Inventory.drop(food[0].getID());
			else
				food[0].click("Eat");
			sleep(650, 700);
		}
		sleep(150, 200);
		Inventory.drop(junk);
		sleep(80, 100);
		if (Inventory.getAll().length > 25 && coinstack.length > 0
				&& coinstack[0].getStack() < 1000) {
			Inventory.drop(995);
			sleep(100, 150);
		}

		return junks.length == 0;
	}

	public boolean dropJunk_noEating() {

		fightstate = "Droping junk items ONLY";
		GameTab.open(org.tribot.api2007.GameTab.TABS.INVENTORY);
		sleep(150, 200);
		RSItem[] junks = Inventory.find(junk);
		RSItem[] bolts = Inventory.find(9142);
		RSItem[] coinstack = Inventory.find(995);
		if (bolts.length > 0) {
			bolts[0].click("Wield");
			sleep(200, 250);
		}
		Inventory.drop(junk);
		sleep(80, 100);
		if (Inventory.getAll().length > 25 && coinstack.length > 0
				&& coinstack[0].getStack() < 1000) {
			Inventory.drop(995);
			sleep(100, 150);
		}
		return junks.length == 0;
	}

	public boolean isFull() {
		// Mouse.setSpeed(General.random(150, 160));
		sleep(5, 7);
		return Inventory.getAll().length == 28;
	}

	public void cameraDrag() {
		Camera.setCameraRotation(General.random(88, 92));
	}

	public void cameraPortal() {
		Camera.setCameraRotation(General.random(178, 182));
	}

	public void cameraBank() {
		Camera.setCameraRotation(General.random(0, 2));
	}

	public boolean inCombat() {
		sleep(5, 7);
		return Player.getRSPlayer().isInCombat();
	}

	public int getHp() {
		double x = Skills.getCurrentLevel("Constitution");
		double y = Skills.getActualLevel("Constitution");
		return (int) ((x / y) * 100);
	}

	public RSTile pos() {
		return Player.getPosition();
	}

	public boolean inArea(RSTile nw, RSTile se, RSTile pos) {

		int posX = pos.getX();
		int posY = pos.getY();
		int nwX = nw.getX();
		int nwY = nw.getY();
		int seX = se.getX();
		int seY = se.getY();

		if (posX >= nwX && posX <= seX && posY >= seY && posY <= nwY) {
			return true;
		} else
			return false;
	}

	public void checkXP() {

		GameTab.open(org.tribot.api2007.GameTab.TABS.STATS);
		sleep(80, 100);
		Mouse.moveBox(557, 307, 600, 321);
		sleep(1700, 2200);
		GameTab.open(org.tribot.api2007.GameTab.TABS.INVENTORY);
		sleep(80, 100);

	}

	public void checkFriends() {

		GameTab.open(org.tribot.api2007.GameTab.TABS.FRIENDS);
		sleep(80, 100);
		Mouse.moveBox(616, 240, 664, 261);
		sleep(1700, 2200);
		GameTab.open(org.tribot.api2007.GameTab.TABS.INVENTORY);
		sleep(80, 100);
	}

	private void ANTIBAN() {

		int x = General.random(1, 2);
		if (x == 1)
			checkXP();
		else
			checkFriends();
		antiban = System.currentTimeMillis();
	}

	private boolean clickNPC(RSNPC npc, String option) {
		//Mouse.setSpeed(General.random(100,120));
		RSTile loc = null;
		if (npc != null && npc.isOnScreen()) {
			loc = npc.getPosition();
			Mouse.move(Projection.tileToScreen(loc, 10));
			if (Game.isUptext(option)) {
				Mouse.click(1);
				waitForDrag(npc);
				return true;
			} else {
				Mouse.click(3);
				if (ChooseOption.isOpen()) {
					ChooseOption.select(option);
				}
			}
		}
		return false;
	}

	private boolean doneBanking() {
		if (getHp() >= 85
				&& (Inventory.getCount(foodID) >= 4 && Inventory
						.getCount(foodID) <= 5)
				&& (Inventory.getCount(rangepots) == 1 || rangepotting == false)
				&& Inventory.getCount(chicken) == 1) {
			println("We got everything, lets go to the drags");
			sleep(5, 7);
			return true;
		} 
		else {
			sleep(5, 7);
			return false;
		}
	}

	// TODO
	public boolean lootIsOnGround() {
		// if (GroundItems.find(9142).length != GroundItems.find(loot).length
		// &&
		RSGroundItem[] nest = GroundItems.findNearest(loot2);
		int acc = 0;
		int junky = 0;
		for (int i = 0; i < nest.length; i++) {
			if (nest[i].getPosition().distanceTo(loottile) > 2)
				junky += 1;
			else if (nest[i].getID() == 9142 || nest[i].getID() == 995)
				acc += 1;
		}
		sleep(5, 7);
		if (junky == nest.length)
			return false;
		else if (acc == nest.length)
			return false;
		else
			return true;
	}

	public void drinkPotion(int[] pot) {
		if (Skills.getCurrentLevel("Range") < Skills.getActualLevel("Range") + 2) {
			Inventory.open();
			RSItem[] potion = Inventory.find(pot);
			if (potion.length > 0) {
				potion[0].click("Drink");
				General.sleep(1100, 1300);
			}
		}
	}
	//TODO
	// dragon killing method
	public void killDragon(RSNPC[] dragon) {
		if (dragon[0].isOnScreen()) {

			// so drag is more than 8 tiles, that means I'll be running
			// after i attack
			// so just click back to to the tile right after i attack
			if (dragon[0].getPosition().distanceTo(pos()) > 6) {
				clickNPC(dragon[0], "Attack"); // clickModel(blkdragsz[0].getModel(),
												// "Attack", true);
				println("attacked a dragon that is further than 8 tiles away, run to safespot!");
				// Mouse.setSpeed(General.random(170,200));

				final RSNPC[] tmp_blkdrag = dragon;
				Timing.waitCondition(new Condition() {
					;
					@Override
					public boolean active() {

						return Player.getAnimation() == 4230
								|| inCombat()
								|| (tmp_blkdrag.length > 0 && tmp_blkdrag[0]
										.isInCombat());
					}
				}, General.random(2200, 2400));
				
				
				for (int i = 0; i < 57; i++, sleep(30, 40)) {
					if (!inSafeSpotZ()) {
						GOTO_SAFEZ();
						break;
					}
				}
				
					

			}
			// drag is close enough that i wont run when I attack it.
			else {
				clickNPC(dragon[0], "Attack"); // clickModel(blkdragsz[0].getModel(),
												// "Attack", true);
				println("attacking black dragon at safespot!");
				// Mouse.setSpeed(General.random(170,200));

				final RSNPC[] tmp_blkdrag = dragon;
				Timing.waitCondition(new Condition() {
					;
					@Override
					public boolean active() {

						return Player.getAnimation() == 4230
								|| inCombat()
								|| (tmp_blkdrag.length > 0 && tmp_blkdrag[0]
										.isInCombat());
					}
				}, General.random(1200, 1400));
				for (int i = 0; i < 57; i++, sleep(30, 40)) {
					if (!inSafeSpotZ()) {
						GOTO_SAFEZ();
						break;
					}
				}
			}
		}
		// drag isn't on screen
		else {
			println("Turning to face dragon");
			Camera.turnToTile(dragon[0].getPosition());
			if (Camera.getCameraAngle() < 35 || Camera.getCameraAngle() > 38) {
				Camera.setCameraAngle(General.random(35, 38));
			}
		}
		sleep(1500, 1700);
	}

	//
	//
	//
	//
	//
	//
	// /
	//
	//
	//
	//
	//
	//
	//
	private boolean clickOBJ(RSObject npc, String option) {
		Camera.turnToTile(npc.getPosition());
		// Mouse.setSpeed(170);
		if (npc.isOnScreen()) {
			Point[] points = npc.getModel().getAllVisiblePoints();

			if (points.length > 0) {
				Mouse.move(points[points.length / 2]);
				// Mouse.move(new Point(points[0].x, points[0].y));
				if (Game.isUptext(option)) {
					Mouse.click(1);
					return true;
				} else {
					Mouse.click(3);
					if (ChooseOption.isOpen()) {
						ChooseOption.select(option);
					}
				}
			}
		}
		return false;
	}

	public void drawModel(RSModel model, Graphics g, boolean fill) {
		if (model.getAllVisiblePoints().length != 0) {
			if (fill) {
				// fill triangles
				for (Polygon p : model.getTriangles()) {
					g.fillPolygon(p);
				}
			} else {
				// draw triangles
				for (Polygon p : model.getTriangles()) {
					g.drawPolygon(p);
				}
			}
		}
	}

	/**
	 * Draws our path
	 * 
	 * @param path
	 *            The tile array we are drawing
	 * @param g
	 *            Our graphics render
	 * @param minimap
	 *            Whether or not we are drawing to the minimap
	 */
	public void drawPath(RSTile[] path, Graphics g, boolean minimap) {
		Point[] points = new Point[path.length];
		if (minimap) {
			// Drawing on the other whatever
			for (int i = 0; i < path.length - 1; i++) {
				Point tilePoint = Projection.tileToMinimap(path[i]);
				if (Projection.isInMinimap(tilePoint)) {
					if (i < path.length - 1) {
						Point nextPoint = Projection.tileToMinimap(path[i + 1]);
						if (Projection.isInMinimap(nextPoint)) {
							g.drawLine(tilePoint.x, tilePoint.y, nextPoint.x,
									nextPoint.y);
						}
					}
					g.fillOval(tilePoint.x, tilePoint.y, 3, 3);
				}
			}
		} else {
			for (int i = 0; i < path.length - 1; i++) {
				if (path[i].isOnScreen()) {
					if (i > 0) { // could just start index
						Point currentTile = Projection.tileToScreen(path[i], 0);
						Point lastTile = Projection
								.tileToScreen(path[i - 1], 0);
						g.drawLine(currentTile.x, currentTile.y, lastTile.x,
								lastTile.y);
					}
					//g.setColor("#FF0000");
					drawTile(path[i], g, false);
				}
			}
		}
	}

	/**
	 * Draws a tile onto the screen
	 * 
	 * @param tile
	 *            The tile you want to draw
	 * @param g
	 *            The graphics render we are using
	 * @param color
	 *            The color we want to use as filler
	 * @param fill
	 *            Whether or not we are filling the tile
	 */
	public void drawTile(RSTile tile, Graphics g, boolean fill) {
		if (tile.isOnScreen()) {
			if (fill) {
				g.fillPolygon(Projection.getTileBoundsPoly(tile, 0));
			} else {
				g.drawPolygon(Projection.getTileBoundsPoly(tile, 0));
			}
		}
	}

	public boolean inSafeSpotZ() {
		sleep(5, 7);
		if (inArea(nwSafeSpotZ, seSafeSpotZ, pos()))
			return true;
		else
			return false;
	}

	public boolean isMovin() {

		return Player.isMoving();
	}

	public boolean gotFood() {

		return Inventory.getCount(foodID) > 0;
	}

	// TODO
	// prayer flicking
	public void prayerflick(RSNPC drag) {

		if (GameTab.getOpen() != GameTab.TABS.PRAYERS) {
			GameTab.open(org.tribot.api2007.GameTab.TABS.PRAYERS);
			sleep(80, 100);
		}
		while (drag.isValid() && drag.isInteractingWithMe()) {
			
			if (!PrayerBook.isOpen()) {
				PrayerBook.open();
				sleep(80, 100);
			}
			Timer t = new Timer(1100L);
			do {
				if (!PrayerBook.isOpen()) {
					PrayerBook.open();
					sleep(50, 100);
				}
				if (Player.getAnimation() != 4230){
					sleep(700,750);
					PrayerBook.activate(Prayer.EAGLE_EYE);
				}
				else{
					if (Prayer.EAGLE_EYE.isActive()){
						sleep(50,150);
						Prayer.EAGLE_EYE.click();
						sleep(450,510);
					}
				}
				
			} while(t.getRemaining() > 0);
			
			
				//PrayerBook.flash(Prayer.HAWK_EYE, General.random(190, 210));

		}
		if (!PrayerBook.isOpen()) {
			PrayerBook.open();
			sleep(80, 100);
		}
		if (PrayerBook.activate(Prayer.EAGLE_EYE, PrayerState.DEACTIVATED));
			sleep(80, 100);
		

	}

	//
	//
	//
	//
	//
	//
	//
	//
	//
	// anti random support here:

	// TODO
	// add timer here
	public void runFromCombat() {
		
		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Mouse.setSpeed(General.random(140, 170));
		Walking.walking_timeout = 500L;
		Walking.walkPath(toMiningArea);
		println("waiting about 5 seconds just to make sure the chicken leaves!");
		sleep(4900, 5500);
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(50, 100);
	}

	public int getStackBolts() {
		RSItem[] items = Interfaces.get(387, 28).getItems(); // get all equiped
																// stuff
		for (int i = 0; i < items.length; i++) { // looping threw items equiped
			if (items[i].getID() == boltsID) { // arrows there
				return items[i].getStack();// return number of arrows
			}
		}
		sleep(80, 100);
		return 0;

	}

	public int lootCount(int ID) {

		return Inventory.getCount(ID);
	}

	// improved waiting for loot
	private void waitForInv(int lootID) {
		int k = 0;

		while (lootCount(lootID) == tmp_lootCount && k < 200
				&& Player.isMoving()) {
			sleep(80);
			k++;
		}

		if (lootCount(lootID) > tmp_lootCount) {

			if (lootID == 1747)
				dhides++;
			else if (lootID == 536)
				dbones++;
			else if (lootID == 11286)
				visage++;
		}
	}

	public boolean clickModel(RSModel model, String option, boolean rightClick){

		Point[] points = model.getAllVisiblePoints();
		int length = points.length;
		if (length != 0) {
			Point p = points[General.random(0, length - 1)];
			Mouse.move(p);
			{
				String top = org.tribot.api2007.Game.getUptext();
				if (top.contains(option)) {
					Mouse.click(p, 0);
					return true;
				} 
				else if (rightClick) {
					Mouse.click(3);
					Timer t = new Timer(500);
					while (t.isRunning() && !ChooseOption.isOpen())
						sleep(50, 100);
					if (!ChooseOption.isOpen())
						return false;
					if (ChooseOption.select(option))
						return true;
				}
			}
		}
		return false;
	}

	public void figureoutwhattodo() {

		if (location == "Zanaris" || inDragArea()
				|| inArea(nwzarea, sezarea, pos())) {
			fally = false;
			println("We are going to kill black dragons at Zanaris");
		}
		// else {
		// fally = true;
		// println("We are going to kill black dragons in Tavernly!");
		// }
		if (foodusing == "Trout") {

			foodID = foodIDs[0];
			println("We are going to use trout as our food");
		} else if (foodusing == "Salmon") {

			foodID = foodIDs[1];
			println("We are going to use Salmon as our food");
		} else if (foodusing == "Lobster"){
			foodID = foodIDs[2];
			println("We are going to use Lobster as our food");
			println("Food ID is: " + foodID);
		}
		else if (foodusing == "Tuna") {

			foodID = foodIDs[3];
			println("We are going to use Tuna as our food");
			println("Food ID is: " + foodID);
		} 
		 else if (foodusing == "Monkfish"){
			foodID = foodIDs[4];
			println("We are going to use Monkfish as our food");
			println("Food ID is: " + foodID);
		}
		
		// if (safespot == "Zanaris") {
		// fally = false;
		// println("We are going to kill black dragons at Zanaris");
		// }

	}
	
	public void waitForDrag(RSNPC drag){
		
		while(drag.isInCombat() && inSafeSpotZ())
			sleep(1000,1300);
	}

	public boolean isRanging() {
		for (int i = 0; i < 57; i++, sleep(30, 40)) {
			if (Player.getRSPlayer().getAnimation() == 4230) {
				return true;
			}
		}
		return false;
	}

	public boolean inDragArea() {
		
		if(inArea(nwdragz, sedragz, pos()))
			return true;
		else return false;
	}
	
	@Override
	public void onPause() {
		if (inDragArea() && !inSafeSpotZ()) {
			Walking.walkPath(Walking.generateStraightPath(safez));
			sleep(50, 100);
		}

		sleep(1000, 1200);
	}
	@Override
	public void onResume() {
		sleep(1000, 1200);
	}

	
	

	int BANK_SECTION = 0;
	boolean WITHDRAWAL_FAIL_SAFE = true;

	/** Withdraws any items from bank if found
	* 
	* @param a (The amount to withdraw)
	* @param b (The id as a single integer or an array of integers)
	* 
	* By: Platinum Force Scripts
	* 
	**/
	public void withdraw(int count, int... ids){
		
		int[] countBefore = new int[ids.length];
		int[] stackBefore = new int[ids.length];
		
		for(int i=0; i<ids.length; i++){
			countBefore[i] = Inventory.getCount(ids[i]);
			stackBefore[i] = 0;
			RSItem[] item = Inventory.find(ids[i]);
			if(item.length > 0)
				stackBefore[i] = item[0].getStack();
		}
		
		int section,scrollTo,itemX,itemY;
		int currentItem = 0;
		int currentItemCount = 0;
		
		for(int i=0; i<ids.length; i++){
			
			RSItem[] items = Banking.find(ids[i]);
			
			//If the item was not found, skip to next item
			if(items.length == 0) continue;
			
			if(ids[i] != currentItem){
				currentItem = ids[i];
				currentItemCount = 1;
			}
			else currentItemCount++;
			
			//If you have tried to withdraw the current item 4 times, continue to the next item 
			if(currentItemCount == 4)
				continue;
			
			//"section" is the section of the bank (each section has 6 rows starting from the top)
			//The math is: (item's position in bank) divided by (8 items per row) divided by (6 rows per section)
			//This determines which section of the bank to scroll to to find the item
			section = (int)Math.floor((items[0].getIndex())/8.0/6.0);
			
			//"itemX" is the actual x position of the item after scrolling to it's section in the bank
			itemX = (int)Math.ceil((items[0].getIndex())%8);
			
			//"itemY" is the actual y position of the item after scrolling to it's section in the bank
			itemY = (int)(Math.floor(items[0].getIndex()/8)%6);
			
			if(section > 7){
				section = 8;
				scrollTo = 260;
				itemY = 4+(int)(Math.ceil(items[0].getIndex()/8)-48);
			}
			else {
				//"scrollTo" is the pixel (y offset) to scroll to, or to click (On the banks scroll bar)
				scrollTo = (int)Math.round((section * 23.5) + 86);
			}
			
			//This determines if an item is located in the section of the bank that you already scrolled to.
			//If so, there is no reason to scroll the the same spot again, you're already there!
			//Whenever you close your bank reset "BANK_SECTION" to zero!
			if(section != BANK_SECTION){
				Mouse.moveBox(469, scrollTo, 481, scrollTo);
				Mouse.click(Mouse.getPos(), 1);
				BANK_SECTION = section;
				sleep(150);
			}
			
			//Withdraw 1
			if(count == 1){
				Mouse.move(80+(itemX*47)+General.random(11, 21), 59+(itemY*38)+General.random(15, 23));
				Mouse.click(Mouse.getPos(), 1);
			}
			//Widthdraw 5 or 10
			else if(count == 5 || count == 10){
				for(int k=0; k<4; k++){
					Mouse.move(80+(itemX*47)+General.random(11, 21), 59+(itemY*38)+General.random(15, 23));
					if(selectOption("Withdraw "+count))
						break;
				}
			}
			//Withdraw All
			else if(count == 0){
				for(int k=0; k<4; k++){
					Mouse.move(80+(itemX*47)+General.random(11, 21), 59+(itemY*38)+General.random(15, 23));
					if(selectOption("Withdraw All"))
						break;
				}
			}
			else { 
				//Withdraw X
				boolean withdrawn = false;
				while(bankIsOpen() && !withdrawn){
					for(int k=0; k<4; k++){
						Mouse.move(80+(itemX*47)+General.random(11, 21), 59+(itemY*38)+General.random(15, 23));
						if(selectOption("Withdraw X")){
							int cnt=0;
							while(!enterAmount()){
								if(cnt >= 20) break;
								sleep(100);
								cnt++;
							}
							if(enterAmount()){
								Keyboard.typeString(count+"");
								Keyboard.pressEnter();
								withdrawn = true;
								break;
							}
						}
					}
				}
			}
			
			sleep(300);
			
			//If the inventory amount before has not changed for this item, you have failed to withdraw it.
			//It will try again. And if the item isn't found in the bank, it will skip it.
			
			boolean countChanged = false;
			boolean stackChanged = false;
			
			//Check if inventory item amount has changed within 2 seconds
			for(int j=0; j<20; j++){
				
				int stackAfter = 0;
				RSItem[] item = Inventory.find(ids[i]);
				if(item.length > 0)
					stackAfter = item[0].getStack();
				
				//Check if the amount of items has changed for non-stackable items
				if(Inventory.getCount(ids[i]) > countBefore[i]){
					countChanged = true;
					break;
				}
				//Check if stack size has changed for stackable items
				else if(stackAfter > stackBefore[i]){
					stackChanged = true;
					break;
				}
				sleep(100);
			}
			if(!countChanged && !stackChanged)
				i--;
			else if(ids.length > 1)
				return;
		}
		return;
	}


		/** Make sure an option is valid **/
		
		private boolean selectOption(String option){
			
			Point pos = Mouse.getPos();
			for(int i=0; i<1; i++){				
				Mouse.click(pos, 3);
				sleep(100);
				if(ChooseOption.isOptionValid(option) && ChooseOption.select(option))
					return true;
				else Mouse.move((int)Mouse.getPos().getX()-General.random(10, 20), (int)Mouse.getPos().getY()-General.random(50,60));
			}
			return false;
		}


		/** Check color at image coordinate **/
		
		private boolean enterAmount(){

			BufferedImage asterisk = Screen.getGameImage().getSubimage(259, 429, 1, 1);
			int[] r = new int[] {0,6};
			int[] g = new int[] {0,6};
			int[] b = new int[] {122,134};
			
			Color rgb = new Color(asterisk.getRGB(0,0));
			int red = rgb.getRed();
			int green = rgb.getGreen();
			int blue = rgb.getBlue();
			
			//Check for colors between these values
			if(red >= r[0] && red <= r[1] && green >= g[0] && green <= g[1] && blue >= b[0] && blue <= b[1])
				return true;
			
			return false;
		}

	
		/** Open bank **/
		
		private boolean bankIsOpen(){
			
			//A method to check if the bank is open
			return Banking.isBankScreenOpen();
		} 
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("serial")
	class myForm extends javax.swing.JFrame {

		/**
		 * Creates new form myForm
		 */
		public myForm() {
			initComponents();
		}

		/**
		 * This method is called from within the constructor to initialize the
		 * form. WARNING: Do NOT modify this code. The content of this method is
		 * always regenerated by the Form Editor.
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		// <editor-fold defaultstate="collapsed" desc="Generated Code">
		private void initComponents() {

			jLabel1 = new javax.swing.JLabel();
			jLabel2 = new javax.swing.JLabel();
			jLabel3 = new javax.swing.JLabel();
			jLabel4 = new javax.swing.JLabel();
			jLabel5 = new javax.swing.JLabel();
			DATA_RANGEPOT = new javax.swing.JRadioButton();
			DATA_LOC = new javax.swing.JComboBox();
			DATA_SS = new javax.swing.JComboBox();
			DATA_FOOD = new javax.swing.JComboBox();
			jLabel6 = new javax.swing.JLabel();
			jLabel7 = new javax.swing.JLabel();
			DATA_START_BUTTON = new javax.swing.JButton();

			setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

			jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
			jLabel1.setText("Yaw hide's Dragon Ranger - Black dragon edition");

			jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
			jLabel2.setText("Choose your location!");

			jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
			jLabel3.setText("Choose your safe spot!");

			jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
			jLabel4.setText("Choose your food!");

			jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
			jLabel5.setText("Will you drink ranging potions?");

			DATA_RANGEPOT.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
			DATA_RANGEPOT.setText("Use ranging pots");
			DATA_RANGEPOT
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
								java.awt.event.ActionEvent evt) {
							DATA_RANGEPOTActionPerformed(evt);
						}
					});

			DATA_LOC.setModel(new javax.swing.DefaultComboBoxModel(
					new String[] { "Zanaris"// , "Falador"
					}));
			DATA_LOC.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					DATA_LOCActionPerformed(evt);
				}
			});

			DATA_SS.setModel(new javax.swing.DefaultComboBoxModel(
					new String[] { "Zanris-east ss"// , "Zanris-north ss",
													// "Falador-west ss",
													// "Falador-east ss"
					}));

			DATA_FOOD.setModel(new javax.swing.DefaultComboBoxModel(
					new String[] { "Trout", "Salmon", "Lobster", "Tuna", "Monkfish" }));

			jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
			jLabel6.setText("Please remember to have all the required items in the top left ");

			jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
			jLabel7.setText("corner of your bank!");

			DATA_START_BUTTON.setFont(new java.awt.Font("Times New Roman", 1,
					18)); // NOI18N
			DATA_START_BUTTON.setText("START");
			DATA_START_BUTTON
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(
								java.awt.event.ActionEvent evt) {
							DATA_START_BUTTONActionPerformed(evt);
						}
					});

			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
					getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(
							layout.createSequentialGroup()
									.addGap(51, 51, 51)
									.addGroup(
											layout.createParallelGroup(
													javax.swing.GroupLayout.Alignment.LEADING)
													.addComponent(jLabel5)
													.addComponent(jLabel2)
													.addComponent(jLabel3)
													.addComponent(jLabel4))
									.addGap(63, 63, 63)
									.addGroup(
											layout.createParallelGroup(
													javax.swing.GroupLayout.Alignment.TRAILING)
													.addGroup(
															layout.createSequentialGroup()
																	.addGroup(
																			layout.createParallelGroup(
																					javax.swing.GroupLayout.Alignment.TRAILING,
																					false)
																					.addComponent(
																							DATA_SS,
																							0,
																							159,
																							Short.MAX_VALUE)
																					.addComponent(
																							DATA_LOC,
																							0,
																							javax.swing.GroupLayout.DEFAULT_SIZE,
																							Short.MAX_VALUE))
																	.addContainerGap(
																			59,
																			Short.MAX_VALUE))
													.addGroup(
															javax.swing.GroupLayout.Alignment.LEADING,
															layout.createSequentialGroup()
																	.addGroup(
																			layout.createParallelGroup(
																					javax.swing.GroupLayout.Alignment.TRAILING,
																					false)
																					.addComponent(
																							DATA_RANGEPOT,
																							javax.swing.GroupLayout.Alignment.LEADING,
																							javax.swing.GroupLayout.DEFAULT_SIZE,
																							159,
																							Short.MAX_VALUE)
																					.addComponent(
																							DATA_FOOD,
																							javax.swing.GroupLayout.Alignment.LEADING,
																							0,
																							javax.swing.GroupLayout.DEFAULT_SIZE,
																							Short.MAX_VALUE))
																	.addGap(0,
																			0,
																			Short.MAX_VALUE))))
					.addGroup(
							layout.createSequentialGroup()
									.addGroup(
											layout.createParallelGroup(
													javax.swing.GroupLayout.Alignment.LEADING)
													.addGroup(
															layout.createSequentialGroup()
																	.addGap(13,
																			13,
																			13)
																	.addGroup(
																			layout.createParallelGroup(
																					javax.swing.GroupLayout.Alignment.LEADING)
																					.addComponent(
																							jLabel6,
																							javax.swing.GroupLayout.PREFERRED_SIZE,
																							420,
																							javax.swing.GroupLayout.PREFERRED_SIZE)
																					.addComponent(
																							jLabel1)))
													.addGroup(
															layout.createSequentialGroup()
																	.addContainerGap()
																	.addComponent(
																			jLabel7))
													.addGroup(
															layout.createSequentialGroup()
																	.addGap(183,
																			183,
																			183)
																	.addComponent(
																			DATA_START_BUTTON)))
									.addContainerGap(
											javax.swing.GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE)));
			layout.setVerticalGroup(layout
					.createParallelGroup(
							javax.swing.GroupLayout.Alignment.LEADING)
					.addGroup(
							layout.createSequentialGroup()
									.addContainerGap()
									.addComponent(jLabel1)
									.addGap(18, 18, 18)
									.addGroup(
											layout.createParallelGroup(
													javax.swing.GroupLayout.Alignment.BASELINE)
													.addComponent(jLabel2)
													.addComponent(
															DATA_LOC,
															javax.swing.GroupLayout.PREFERRED_SIZE,
															javax.swing.GroupLayout.DEFAULT_SIZE,
															javax.swing.GroupLayout.PREFERRED_SIZE))
									.addGap(18, 18, 18)
									.addGroup(
											layout.createParallelGroup(
													javax.swing.GroupLayout.Alignment.BASELINE)
													.addComponent(jLabel3)
													.addComponent(
															DATA_SS,
															javax.swing.GroupLayout.PREFERRED_SIZE,
															javax.swing.GroupLayout.DEFAULT_SIZE,
															javax.swing.GroupLayout.PREFERRED_SIZE))
									.addGap(18, 18, 18)
									.addGroup(
											layout.createParallelGroup(
													javax.swing.GroupLayout.Alignment.BASELINE)
													.addComponent(jLabel4)
													.addComponent(
															DATA_FOOD,
															javax.swing.GroupLayout.PREFERRED_SIZE,
															javax.swing.GroupLayout.DEFAULT_SIZE,
															javax.swing.GroupLayout.PREFERRED_SIZE))
									.addGap(18, 18, 18)
									.addGroup(
											layout.createParallelGroup(
													javax.swing.GroupLayout.Alignment.BASELINE)
													.addComponent(jLabel5)
													.addComponent(DATA_RANGEPOT))
									.addGap(18, 18, 18)
									.addComponent(jLabel6)
									.addPreferredGap(
											javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(jLabel7)
									.addPreferredGap(
											javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(DATA_START_BUTTON)
									.addContainerGap(58, Short.MAX_VALUE)));

			pack();
		}// </editor-fold>

		private void DATA_RANGEPOTActionPerformed(java.awt.event.ActionEvent evt) {

		}

		private void DATA_LOCActionPerformed(java.awt.event.ActionEvent evt) {

		}

		private void DATA_START_BUTTONActionPerformed(
				java.awt.event.ActionEvent evt) {

			location = DATA_LOC.getSelectedItem().toString();
			safespot = DATA_SS.getSelectedItem().toString();
			foodusing = DATA_FOOD.getSelectedItem().toString();
			rangepotting = DATA_RANGEPOT.isSelected();
			GUI_COMPLETE = true;
		}

		// Variables declaration - do not modify
		@SuppressWarnings("rawtypes")
		private javax.swing.JComboBox DATA_FOOD;
		@SuppressWarnings("rawtypes")
		private javax.swing.JComboBox DATA_LOC;
		private javax.swing.JRadioButton DATA_RANGEPOT;
		@SuppressWarnings("rawtypes")
		private javax.swing.JComboBox DATA_SS;
		private javax.swing.JButton DATA_START_BUTTON;
		private javax.swing.JLabel jLabel1;
		private javax.swing.JLabel jLabel2;
		private javax.swing.JLabel jLabel3;
		private javax.swing.JLabel jLabel4;
		private javax.swing.JLabel jLabel5;
		private javax.swing.JLabel jLabel6;
		private javax.swing.JLabel jLabel7;
		// End of variables declaration
	}

	// unneeded code

	public void JUMP_WALL() {

		RSObject[] LOWWALL = Objects.findNearest(10, new int[] { lowwall });

		if (LOWWALL[0].isOnScreen()) {

			// Camera.turnToTile(new RSTile(2931, 3355, 0));
			sleep(80, 100);
			// Camera.setCameraAngle(General.random(40, 50));
			// Point p = Projection.tileToScreen(new RSTile(2935, 3355, 0), 0);
			do {
				clickOBJ(LOWWALL[0], "Climb-over");
				// Mouse.click(p, 1);
				sleep(1000, 1250);
			} while (Player.getAnimation() != 840);
			Camera.setCameraRotation(General.random(0, 5));
			Camera.setCameraAngle(General.random(95, 100));
		}
	}

	public void GOTO_WALL() {

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walkPath(Walking.generateStraightPath(lowwalltile));
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	public void GOTO_LADDER() {

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walkPath(Walking.generateStraightPath(ladderdowntile));
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	public void CLIMB_LADDER() {

		RSObject[] ladder = Objects.findNearest(10, ladderdown);
		Camera.setCameraAngle(General.random(95, 100));
		if (ladder[0].isOnScreen()) {

			sleep(2000, 2500);
			DynamicClicking.clickRSObject(ladder[0], "Climb-down");
			// Point p = Projection.tileToScreen(new RSTile(2884, 3396, 0), 0);
			// Mouse.click(p, 1);
			sleep(200, 250);
		}
	}

	public void GOTO_SPIKE() {

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);
		Walking.walkPath(Walking.generateStraightPath(spiketile));
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	public void JUMP_SPIKE() {

		RSObject[] SPIKES = Objects.findNearest(10, spike);

		if (pos().distanceTo(new RSTile(2881, 9813, 0)) > 1)
			Walking.clickTileMM(spiketile1, 1);
		// if (SPIKES[0].isOnScreen()) {

		sleep(200, 250);
		clickOBJ(SPIKES[0], "Jump-over");
		// DynamicClicking.clickRSObject(SPIKES[0], "Jump-over");
		// Point p = Projection.tileToScreen(spiketile, 0);
		// Mouse.click(p, 1);
		sleep(200, 250);
		// while (Player.getAnimation() != 1995 || !Player.isMoving() &&
		// pos().getX() > 2879);
		println("done with spikes");
		// }
		sleep(80, 100);
	}

	public void GOTO_DRAG() {

		Keyboard.pressKey((char) KeyEvent.VK_CONTROL);

		if (pos().getX() > 2871)
			Walking.clickTileMM(new RSTile(2868, 9816, 0), 1);
		else
			Walking.walkPath(Walking.generateStraightPath(new RSTile(2824,
					9827, 0)));
		Keyboard.releaseKey((char) KeyEvent.VK_CONTROL);
		sleep(80, 100);
	}

	private int lowwall = 11844;
	private RSTile lowwalltile = new RSTile(2935, 3355, 0);

	private RSTile spiketile = new RSTile(2883, 9813, 0);
	private RSTile spiketile1 = new RSTile(2879, 9813, 0);
	private int spike = 9294;

	private RSTile ladderdowntile = new RSTile(2884, 3396, 0);
	private int ladderdown = 20987;
	// bank box
	private RSTile nwbank = new RSTile(2943, 3373, 0);
	private RSTile sebank = new RSTile(2947, 3368, 0);

	// after wall
	private RSTile nwawall = new RSTile(2879, 3404, 0);
	private RSTile seawall = new RSTile(2935, 3348, 0);

	// around the ladder
	private RSTile nwladder = new RSTile(2881, 3400, 0);
	private RSTile seladder = new RSTile(2887, 3393, 0);

	// going to spikes
	private RSTile nwspike = new RSTile(2880, 9820, 0);
	private RSTile sespike = new RSTile(2888, 9794, 0);
	private RSTile nwspikea = new RSTile(2880, 9816, 0);
	private RSTile sespikea = new RSTile(2883, 9811, 0);

	// spider area
	private RSTile nwspider = new RSTile(2820, 9830, 0);
	private RSTile sespider = new RSTile(2879, 9800, 0);

	// drag area
	private RSTile nwdrag = new RSTile(2417, 9830, 0);
	private RSTile sedrag = new RSTile(2843, 9823, 0);

	// killing drag area
	private RSTile nwkill = new RSTile(2822, 9830, 0);
	private RSTile sekill = new RSTile(2842, 9818, 0);

	// safe spot area
	private RSTile nwsafe1 = new RSTile(2822, 9828, 0);
	private RSTile sesafe1 = new RSTile(2826, 9826, 0);

	// safe spot area
	private RSTile nwsafe2 = new RSTile(2834, 9820, 0);
	private RSTile sesafe2 = new RSTile(2836, 9818, 0);
	// teleport area
	private RSTile nwtele = new RSTile(2938, 3385, 0);
	private RSTile setele = new RSTile(2978, 3367, 0);

		// low wall area
	private RSTile nwwall = new RSTile(2936, 3358, 0);
	private RSTile sewall = new RSTile(2939, 3352, 0);

		// fail bank area
	private RSTile nwfailbank = new RSTile(2940, 3374, 0);
	private RSTile sefailbank = new RSTile(2952, 3361, 0);
		
		// danger drag area
	private RSTile sedanger = new RSTile(2841, 9822, 0);
	private RSTile nwdanger = new RSTile(2827, 9830, 0);

		
}