package com.aqua.music.audio.manager;

public abstract class AudioTask<T> {
	public abstract void beforeForLoop();
	public abstract void forLoopBody(T t);
	public abstract T[] forLoopParameter();
}
