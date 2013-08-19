package scripts.AIOSlayer;

import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterface;

public class SlayerTask {

	private static int[] IDs = null;
	private static String name = null;
	private static boolean specialItems = false;

	// classes (like hitpoints before)

	public SlayerTask(int[] IDs, String name, boolean specialItems) {
		this.IDs = IDs;
		this.name = name;
		this.specialItems = specialItems;
	}

	// now the getters and setters

	public static int[] getMonsterID() {
		return IDs;
	}

	public static String getMonsterName() {
		return name;
	}

	public boolean needsSpecialItems() {
		return specialItems;
	}

	public static int getAmmountLeft() {
		RSInterface textInterface = Interfaces.get(243, 2);
		if (textInterface != null && textInterface.getText().length() > 0)
			return filterNumbers(textInterface.getText());
		return 0;
	}

	public static String getMonsterToKill() {
		RSInterface textInterface = Interfaces.get(243, 2);
		if (textInterface != null && textInterface.getText().length() > 0)
			return filterWords(sliceText(textInterface.getText()));
		return null;
	}

	public static String[] sliceText(String s) {
		return s.split(" ");
	}

	public static int filterNumbers(String s) {
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (isNumber(c))
				sb.append(c);
		}
		String finalString = sb.toString();
		if (finalString.length() > 0)
			return Integer.parseInt(finalString);
		return 0;

	}

	public static String filterWords(String[] string) {
		for (String s : string) {
			if (!isUselessWord(s)) {
				return s.replace(";", "");
			}
		}
		return null;
	}

	static boolean isUselessWord(String s) {
		String useless = "You're currently assigned to kill only more";
		return useless.contains(s);
	}

	static boolean isNumber(char c) {
		String numString = "0123456789";
		return numString.contains(c + "");
	}

}
