package scripts.Slayer;

import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;

import scripts.AIOCutter.RSArea;

public abstract class Monster extends Script {
	
	public abstract RSArea getArea();
	public abstract void lootItems();
	
}
