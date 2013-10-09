package com.octopus.music.play;

import java.util.HashSet;

import com.octopus.music.play.SequencePlayer;
import com.octopus.music.play.AudioLibrary;
import com.octopus.music.play.SequencePlayer.AllThaat;

public class AscendDescendSequencePuzzles {
	public AscendDescendSequencePuzzles() {
		AudioLibrary.initializeWithGivenSeconds(1);
	}

	void playThats() {
		int repeatCount = 2;
		System.out.println("Repeating each item[" + repeatCount + "] \n");
		for (int i = 0; i < 3; i++) {
			//puzzleBuilder.playArohiAvrohi(repeatCount, SecondYearRaag.values());
			playAscendAndDescend(repeatCount, SequencePlayer
					.AllThaat.values());
			System.out.println("round[" + i + "] done \n");
		}
	}

	private void playAscendAndDescend(int count, SequencePlayer... raags) {
		AllThaat.BILAWAL.playAscendAndDescend();
		System.out.println("\n Played [BILAWAL]");
		HashSet<SequencePlayer> hasheddata = randomize(raags);
		for (SequencePlayer each : hasheddata) {
			for (int i = 0; i < count; i++) {
				each.playAscendAndDescend();
				System.out.println("\nPlayed [" + each.name() + "] ." + i);
				System.out.println("\n");
			}
		}
	}

	private HashSet<SequencePlayer> randomize(SequencePlayer... raag) {
		HashSet<SequencePlayer> hasheddata = new HashSet<SequencePlayer>();
		for (SequencePlayer each : raag) {
			hasheddata.add(each);
		}
		return hasheddata;
	}
}
