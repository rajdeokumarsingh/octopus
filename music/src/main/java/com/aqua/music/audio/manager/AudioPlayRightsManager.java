package com.aqua.music.audio.manager;

public interface AudioPlayRightsManager {
	void acquireRightToPlay() throws InterruptedException;

	boolean stopPlaying();

	void releaseRightToPlay();
}
