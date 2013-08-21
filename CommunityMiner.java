package scripts;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Inventory;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MousePainting;
import org.tribot.script.interfaces.MouseSplinePainting;
import org.tribot.script.interfaces.Painting;

@ScriptManifest(authors = { "Everyone"}, category = "Mining", name = "Community Miner")
public class CommunityMiner extends Script implements Painting, MousePainting, MouseSplinePainting{
	
	//Fields
	private RSObject currentRock = null;


	//Abstract method overrides
	@Override
	public void run() {
		if(!onStart())return;
		while(loop() >= 0 && !Thread.interrupted())sleep(10);
	}
	
	@Override
	public void paintMouseSpline(Graphics g, ArrayList<Point> pts) {
		//Let's leave this blank for now since the splines look kind of nasty sometimes.
	}

	@Override
	public void paintMouse(Graphics g, Point pt0, Point pt1) {
		
	}

	@Override
	public void onPaint(Graphics g) {
		
	}
	
	//Loop
	private boolean onStart() {
		return true;
	}
	
	public int loop(){
		
		walkToBank();
		sleep(100);
		/*
		//Hostile NPC handler
		if(Player.getRSPlayer().isInCombat()){
			runaway();
			return 0;
		}
		
		//Check if we need to bank
		if(Inventory.isFull()){
			if(isInBankArea()){
				if(Banking.isBankScreenOpen()){
					deposit();
					return 0;
				}else{
					openBank();
					return 0;
				}
			}else{
				walkToBank();
				return 0;
			}
		}
		
		//Check if we are mining and the current Rock is still there
		if(isRockPresent()){
			if(Player.getAnimation() != -1){
				sleep(100,200);
				return 0;
			}
		}
		
		if(isInMineArea()){
			//We are not mining or the current rock has either been mined or has turned into an exploding rock.
			RSObject nextRock = getNextRock();
			if(nextRock != null){
				mine(nextRock);
			}else{
				//If there is no next rock available, we still need to stop mining in case there is an exploding rock
				stopMining();
			}
		}else{
			walkToMine();
			return 0;
		}*/
		return 0;
	}

	//Question Methods
	private boolean isInMineArea() {
		return true;
	}
	
	private boolean isInBankArea(){
		
		return true;
	}
	
	private boolean isRockPresent() {
		return true;
	}
	
	
	//Action Methods
	private RSObject getNextRock() {
		return null;
	}
	
	private void deposit(){
		
	}
	
	private void openBank() {
		
	}

	RSTile fallyEastBankTile = new RSTile(3012, 3356, 0);
	RSTile dwarvenOutdoorsDoorTile = new RSTile(3061, 3374, 0);
	RSTile dwavenStairsIndoorsTile = new RSTile(3059, 9776, 0);
	RSTile mGuildLadderUpTile = new RSTile(3020, 9739, 0);
	RSTile shortCutTile = new RSTile(3029, 9806, 0);
	
	RSTile[] fallyEastAreaBorders = { new RSTile(3000, 3330, 0), new RSTile(3059, 3330, 0), 
			new RSTile(3059, 3363, 0), new RSTile(3065, 3370, 0), new RSTile(3065, 3384, 0), 
			new RSTile(3059, 3389, 0), new RSTile(3047, 3388, 0), new RSTile(3024, 3387, 0), 
			new RSTile(3009, 3386, 0)};
	
	RSArea fallyEastBankArea = new RSArea(new RSTile(3009, 3358, 0), new RSTile(3018, 3355, 0));
	RSArea fallyEastArea = new RSArea(fallyEastAreaBorders);
	RSArea dwarvenOutdoorsStairArea = new RSArea(new RSTile(3058, 3381, 0), new RSTile(3062, 3375, 0));
	RSArea southDwarvenArea = new RSArea(new RSTile(3032, 9779, 0), new RSTile(3060, 9757, 0));
	RSArea northDwarvenArea = new RSArea(new RSTile(3010, 9840, 0), new RSTile(3060, 9779, 0));
	RSArea mGuildArea = new RSArea(new RSTile(3016, 9756, 0), new RSTile(3040, 9740, 0));
	
	
	
	
	
	RSTile[] longDwarvenToStairsPath = { new RSTile(3024, 9804, 0), new RSTile(3024, 9810, 0), 
			new RSTile(3025, 9816, 0), new RSTile(3025, 9822, 0), new RSTile(3025, 9828, 0), new RSTile(3030, 9832, 0), 
			new RSTile(3037, 9832, 0), new RSTile(3042, 9828, 0), new RSTile(3046, 9825, 0), 
			new RSTile(3049, 9821, 0), new RSTile(3047, 9816, 0), new RSTile(3044, 9813, 0), 
			new RSTile(3044, 9808, 0), new RSTile(3040, 9803, 0), new RSTile(3043, 9796, 0), 
			new RSTile(3044, 9788, 0), new RSTile(3044, 9781, 0), new RSTile(3050, 9778, 0), 
			new RSTile(3054, 9776, 0), new RSTile(3058, 9776, 0) };
	RSTile[] shortDwarvenToStairsPath = { new RSTile(3039, 9771, 0), new RSTile(3039, 9766, 0), 
			new RSTile(3045, 9763, 0), 
			new RSTile(3048, 9767, 0), new RSTile(3051, 9771, 0), new RSTile(3055, 9775, 0), 
			new RSTile(3058, 9776, 0) };
	
	RSTile[] toMGuildLadderUpPath = { new RSTile(3046, 9751, 0), new RSTile(3046, 9746, 0), new RSTile(3047, 9739, 0), 
			new RSTile(3041, 9739, 0), new RSTile(3037, 9737, 0), new RSTile(3033, 9738, 0), 
			new RSTile(3029, 9738, 0), new RSTile(3025, 9738, 0), new RSTile(3021, 9739, 0),
			new RSTile(3017, 9739, 0)};
	
	
	
	
	
	private void walkToBank() {
		RSObject[] dwarvenOutdoorsDoor = Objects.getAt(dwarvenOutdoorsDoorTile);
		RSObject[] dwarvenIndoorStairs = Objects.getAt(dwavenStairsIndoorsTile);
		RSObject[] mGuildLadderUp = Objects.getAt(mGuildLadderUpTile);
		RSObject[] shortCut = Objects.getAt(shortCutTile);		
				
		if(dwarvenOutdoorsStairArea.contains(getPos())){
			// I am in the house where the stairs going down to the dwarven mines are.
			if(dwarvenOutdoorsDoor.length > 0){
				if(dwarvenOutdoorsDoor[0].isOnScreen()){
					if(dwarvenOutdoorsDoor[0].click("Open")){
						waitIsMovin(); // waits for your character to stop moving
					}
				}
				else{
					// sets the camera angle to where the door is
					int r = Projection.angleToTile(dwarvenOutdoorsDoor[0].getAnimablePosition());
	    			Camera.setCameraRotation(r + General.random(0, 80));
	    			sleep(200,300);
				}
			}
			else{
				WebWalking.walkTo(fallyEastBankTile); // go webwalking!
				waitIsMovin();
			}
		}
		else if (fallyEastArea.contains(getPos())){
			// i am in east falador
			WebWalking.walkTo(fallyEastBankTile);
			waitIsMovin();
		}
		else if (getPos().distanceTo(dwavenStairsIndoorsTile) <= 5){
			// I am near the stairs that go up to falador
			if(dwarvenIndoorStairs.length > 0){
				if(dwarvenIndoorStairs[0].isOnScreen()){
					if(dwarvenIndoorStairs[0].click("Climb-up")){
						waitIsMovin(); // waits for your character to stop moving
						sleep(1000,1100);
					}
				}
				else{
					// sets the camera angle to where the door is
					int r = Projection.angleToTile(dwarvenIndoorStairs[0].getAnimablePosition());
	    			Camera.setCameraRotation(r + General.random(0, 80));
	    			sleep(200,300);
				}
			}
		}
		else if (getPos().distanceTo(shortCutTile) <=5){
			// i am near the shortcut, lets go in it!
			if(shortCut.length > 0){
				if(shortCut[0].isOnScreen()){
					if(Camera.getCameraRotation() < 260 || Camera.getCameraRotation() > 280){
						// sets the camera rotation to a better rotation
						Camera.setCameraRotation(General.random(260, 280));
						sleep(200,300);
					}
					if (Camera.getCameraAngle() < 50 || Camera.getCameraAngle() > 60){
						// sets the camera angle to a better angle
						Camera.setCameraAngle(General.random(50, 60));
						sleep(200,300);
					}
					if(shortCut[0].click("Squeeze-through")){
						// set the camera angle and rotation back to a better angle/rotation				
						Camera.setCameraRotation(General.random(350, 359));
						sleep(200,300);
						Camera.setCameraAngle(General.random(100, 110));
						sleep(200,300);
						
						sleep(7000,8100); // sleep through the animation
					}
				}
				else{ // sets the camera angle to where the door is
					int r = Projection.angleToTile(shortCut[0].getAnimablePosition());
	    			Camera.setCameraRotation(r + General.random(0, 80));
	    			sleep(200,300);
				}
			}
		}
		else if (northDwarvenArea.contains(getPos())){
			// i am in the northern part of the dwarven mines
			Walking.walkPath(longDwarvenToStairsPath);
			waitIsMovin();
		}
		else if (southDwarvenArea.contains(getPos())){
			// i am in the southern part of the dwarven mines
			Walking.walkPath(shortDwarvenToStairsPath);
			waitIsMovin();
		}
		else if (getPos().distanceTo(mGuildLadderUpTile) <=5){
			// i am near the mining guild's ladder up to falador
			
			if(mGuildLadderUp.length > 0){
				
				if(mGuildLadderUp[0].isOnScreen()){
					
					if(DynamicClicking.clickRSObject(mGuildLadderUp[0],  "Climb-up")){//.click("Climb-up")){
						waitIsMovin(); // waits for your character to stop moving
						sleep(1000,1100);
						
					}
					println("step2");
				}
				else{ // sets the camera angle to where the door is
					int r = Projection.angleToTile(mGuildLadderUp[0].getAnimablePosition());
	    			Camera.setCameraRotation(r + General.random(0, 80));
	    			sleep(200,300);
				}
			}
		}
		else if (mGuildArea.contains(getPos())){
			// i am in the mining guild, walk to the ladders up
			Walking.walkPath(toMGuildLadderUpPath);
			waitIsMovin();
		}	
	}
	
	private void stopMining() {
		
	}

	private void walkToMine() {
		
	}

	private void runaway() {
		
	}
	
	private void mine(RSObject nextRock) {
		
	}
	
	//Supporting Methods
	private RSTile getPos(){
		// returns your RSTile position
		return Player.getPosition();
	}
	
	private void waitIsMovin(){
		//sleeps while your character is moving
		for(int i = 0; i < 57; i++, sleep(30, 40)){
			if (!Player.isMoving())
				break;
		}
	}
	
}