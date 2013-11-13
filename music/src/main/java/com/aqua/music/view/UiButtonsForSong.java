package com.aqua.music.view;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqua.music.bo.audio.manager.AudioPlayConfig;
import com.aqua.music.controller.songs.Song;

enum UiButtonsForSong {
	SONG_PLAYER("Play $$", "Click this to play $$", 200) {
		@Override
		JButton createInstanceWith(final TextArea textArea, final Song song) {
			final String buttonTitle = song.name();
			JButton button = configurableNamedButton(this, buttonTitle);

			ActionListener actionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					song.play(AudioPlayConfig.ASYNCHRONOUS_DYNAMIC_PLAYER);
					setText(textArea, buttonTitle + "===>" + "\n" + buttonTitle);
				}
			};

			button.addActionListener(actionListener);

			return button;
		}
	},
	QUIT("Quit", "Click this to quit!", 400) {
		@Override
		JButton createInstanceWith(TextArea Object, Song newParam) {
			JButton button = fixedNameButton(this);

			ActionListener actionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			};

			button.addActionListener(actionListener);
			return button;
		}
	};
	private static final Logger logger = LoggerFactory.getLogger(UiButtonsForSong.class);
	static final int X_COORIDNATE = 30;
	private static final int BUTTON_HEIGHT = 30;
	private final int displayWidth;
	private final String text;
	private final String tooltip;

	private UiButtonsForSong(String text, String tooltip, int buttonWidth) {
		this.text = text;
		this.tooltip = tooltip;
		this.displayWidth = buttonWidth;
	}

	static final int HEIGHT() {
		return BUTTON_HEIGHT;
	}

	private static JButton configurableNamedButton(UiButtonsForSong itemType, String replaceName) {
		JButton resultButton = new JButton(itemType.text.replace("$$", replaceName));
		resultButton.setToolTipText(itemType.tooltip.replace("$$", replaceName));
		return resultButton;
	}

	private static JButton fixedNameButton(UiButtonsForSong itemType) {
		JButton resultButton = new JButton(itemType.text);
		resultButton.setToolTipText(itemType.tooltip);
		return resultButton;
	}

	private static void setText(final TextArea textArea, final String name) {
		String displayText = "\n\n Playing::" + name;
		logger.info(displayText);
		textArea.setText(displayText);
	}

	public JButton createButton(TextArea textArea, int yCoordinate, Song song) {
		JButton buttonItem = createInstanceWith(textArea, song);
		buttonItem.setOpaque(true);
		buttonItem.setBounds(X_COORIDNATE, yCoordinate, displayWidth, BUTTON_HEIGHT);
		return buttonItem;
	}

	abstract JButton createInstanceWith(TextArea textArea, Song song);
}