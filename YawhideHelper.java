package scripts.AIOSlayer;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@ScriptManifest(authors={"Platinum Force Scripts"}, name="Yawhide Helper", category="Tools")
public class YawhideHelper extends Script {

	public String Locations[] = { "Varrock", "Falador", "Camelot", "Ardougne", "Lumbridge" };
	public Map<String, String[]> TASK = new LinkedHashMap<String, String[]>();
	public Map<String, Integer> TABS = new LinkedHashMap<String, Integer>();
	
	//TODO Change task to any task named below
	public String currTask = "dogs";
	
	int VTAB = 8007;
	int FTAB = 8009;
	int CTAB = 8010;
	int LTAB = 8008;
	int ATAB = 8011;
	
	@Override
	public void run(){
		//Add tasks by location
		TASK.put("Varrock", new String[] { "dwarves", "minotaurs", "rats", "skeletons", "zombies" });
		TASK.put("Falador", new String[] { "bats", "cows", "ghosts", "icefiends", "monkeys" });
		TASK.put("Camelot", new String[] { "cave_crawlers", "wolves" });
		TASK.put("Ardougne", new String[] { "bears", "birds", "dogs" });
		TASK.put("Lumbridge", new String[] { "cave_slimes", "cave_bugs", "goblins", "spiders" });

		//Add tabs by location
		TABS.put("Varrock", VTAB);
		TABS.put("Falador", FTAB);
		TABS.put("Camelot", CTAB);
		TABS.put("Lumbridge", LTAB);
		TABS.put("Ardougne", ATAB);
		
		println(hasReqTab());
	}

	/* Checks if the player has the required tab for a specific task */
	
	public boolean hasReqTab(){
		for(String loc : Locations){
			if(Arrays.asList(TASK.get(loc)).contains(currTask)){
				RSItem[] tabs = Inventory.find(TABS.get(loc));
				if(tabs.length > 0)
					return true;
				break;
			}
		}
		return false;
	}
	
}