package com.aqua.music.model.song;

import org.junit.Test;

import com.aqua.music.api.AudioPlayConfig;

public class TaanTest {
	@Test
	public void testJaunpuriTaans1() {
		Song.SONG_JAUNPURI.playTaan(AudioPlayConfig.SYNCHRONOUS_DYNAMIC_PLAYER);
	}
}
