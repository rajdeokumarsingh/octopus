package com.octopus.music.play;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.octopus.music.play.AudioPlayer.VLCPlayer;

public class AudioLibrary {
	private static final String AUDIO_LIBRARY = "recognition-puzzles/";
	private static final String FOLDER_PREFIX = "note-recognition-";
	private static Map<String, File> allNoteAudios;
	private static AudioPlayer audioPlayer= new VLCPlayer( );

	public static AudioPlayer audioPlayer(){
	    return audioPlayer;
	}
	
	public static void initializeWithGivenSeconds(int seconds) {
		AudioLibrary.allNoteAudios = findAllNotesAudios(seconds);
	}

	static void addFileIfFound(List<File> audioFiles, Playable note) {
		File audioFile = allNoteAudios.get(note.code());
		if (audioFile == null) {
			System.out.println("No audio found for [" + note + "] in the list of files[" + allNoteAudios.keySet() + "]");
		} else {
			audioFiles.add(audioFile);

		}
	}

	private static String directoryName(int duration) {
		return FOLDER_PREFIX + duration + "s";
	}

	private static Map<String, File> findAllNotesAudios(int duration) {
		String dir =AUDIO_LIBRARY +directoryName(duration)+"/";
		String path = Thread.currentThread().getContextClassLoader().getResource(dir).getPath();
		File sourceDirectory=new File(path );
		System.out.println(sourceDirectory.getAbsolutePath());
		Map<String, File> allNoteAudios = new LinkedHashMap<String, File>();
		for (File audioFile : sourceDirectory.listFiles()) {
			String noteName = audioFile.getName().replace(".m4a", "");
			allNoteAudios.put(noteName, audioFile);
		}
		return allNoteAudios;
	}
}