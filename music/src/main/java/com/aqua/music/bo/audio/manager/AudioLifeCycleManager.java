package com.aqua.music.bo.audio.manager;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqua.music.model.Frequency;

public interface AudioLifeCycleManager {
	Logger logger = LoggerFactory.getLogger(AudioLifeCycleManager.class);
	final AudioLifeCycleManager instance = new AudioLifeCycleManagerImpl();

	void play(final Collection<Frequency> frequencyList, AudioPlayConfig audioModeAndPlayerCombinations);

	<T> void execute(AudioTask<T> audioTask);
}