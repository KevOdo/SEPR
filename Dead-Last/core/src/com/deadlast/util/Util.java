package com.deadlast.util;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.deadlast.assets.Scoreboard;

/**
 * 
 * @author Xzytl
 *
 */
public final class Util {
	
	public static Scoreboard parseScoreFile() throws IOException, IllegalArgumentException {
		FileHandle fileHandle = Gdx.files.internal("data/scores.csv");
		if (!fileHandle.exists()) {
			System.out.println("File does not exist!");
		}
		Scoreboard scoreboard = new Scoreboard();
		// Try with resources ensures file I/O is closed regardless of errors
		try(Stream<String> lines = Files.lines(fileHandle.file().toPath())) {
			lines.skip(1).forEach(line -> {
				scoreboard.addEntry(scoreboard.new Entry(line));
			});
		}
		return scoreboard;
	}

}
