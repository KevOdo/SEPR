package com.deadlast.assets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds a list of {@link Entry}'s corresponding to high scores.
 * @author Xzytl
 *
 */
public class Scoreboard {
	
	/**
	 * The list of scoreboard entries.
	 */
	List<Entry> entries;
	
	public Scoreboard() {
		this.entries = new ArrayList<>();
	}
	
	/**
	 * A scoreboard entry. Holds the name, score, and datetime of the entry.
	 * @author Xzytl
	 *
	 */
	public class Entry implements Comparable<Entry> {
		
		/**
		 * The name of the player.
		 */
		String name;
		/**
		 * The integer score the player achieved.
		 */
		int score;
		/**
		 * The date the game was won by the player.
		 */
		String dateTime;
		
		/**
		 * Default class constructor.
		 * @param name		the name of the player
		 * @param score		the score achieved
		 * @param dateTime	the time the score was achieved
		 */
		public Entry(String name, int score, String dateTime) {
			this.name = name;
			this.score = score;
			this.dateTime = dateTime;
		}
		
		/**
		 * Class constructor which parses line from CSV file.
		 * @param line		CSV line with <code>name</code>, <code>score</code> 
		 * 					and <code>dateTime</code>
		 * @throws IllegalArgumentException	invalid CSV format
		 */
		public Entry(String line) throws IllegalArgumentException {
			String[] vars = line.split(",");
			if (vars.length != 3) {
				throw new IllegalArgumentException("Bad argument length");
			}
			this.name = vars[0];
			try {
				this.score = Integer.parseInt(vars[1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Bad score parameter");
			}
			this.dateTime = vars[2];
		}
		
		public String getName() {
			return name;
		}
		
		public int getScore() {
			return score;
		}
		
		/**
		 * Return score as a <code>String</code>.
		 * @return	a string representation of the integer score
		 */
		public String getScoreString() {
			return Integer.toString(score);
		}
		
		public String getDate() {
			return dateTime;
		}
		
		@Override
		public String toString() {
			return this.name + "," + this.score + "," + this.dateTime;
		}

		@Override
		public int compareTo(Entry entry) {
			return entry.getScore() - this.score;
		}
		
	}
	
	public void addEntry(Entry entry) {
		entries.add(entry);
	}
	
	/**
	 * Returns the contained entries, sorted by score.
	 * @return	<code>List&lt;Entry&gt;</code> a sorted list of all entries
	 */
	public List<Entry> getSortedEntries() {
		Collections.sort(entries);
		return entries;
	}

}
