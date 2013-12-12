package com.aqua.music.view.components;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import open.music.api.PlayApi;
import open.music.api.PlayApi.AudioPlayerNextStatus;
import open.music.api.Playable;

import com.aqua.music.model.core.FrequencySet;
import com.aqua.music.model.cyclicset.CyclicFrequencySet.PermuatationsGenerator;
import com.aqua.music.view.components.UiDropdown.ThaatAndPatternDropdownActionListener;

class MusicPanelForPractice extends MusicPanel {
	private final Collection<Playable> intialItemsList;
	private JPanel specificComponentPanel;
	private final String pickTitle;

	/**
	 * Used for plain rehearsing - of thaat and songs
	 * @param pickTitle TODO
	 */
	public MusicPanelForPractice(TopPanelBuilder topPanelBuilder,Collection<Playable> itemsList, String pickTitle) {
		super(topPanelBuilder,false);
		this.pickTitle=pickTitle;
		this.intialItemsList = itemsList;
	}

	/**
	 * Used for patterned rehearse of thaat.
	 * 
	 * @param frequencySet
	 * @param patternItemsCount
	 * @param pickTitle TODO
	 */
	public MusicPanelForPractice(TopPanelBuilder topPanelBuilder,FrequencySet frequencySet, PermuatationsGenerator patternItemsCount, String pickTitle) {
		super(topPanelBuilder,true);
		this.pickTitle=pickTitle;
		this.intialItemsList = PlayApi.getAllPatternedThaat(frequencySet, patternItemsCount);

		final ThaatAndPatternDropdownActionListener thaatPatternListener = new ThaatAndPatternDropdownActionListener(this, frequencySet,
				patternItemsCount);

		final JComboBox thaatDropdown = UiDropdown.thaatDropDown(frequencySet);
		thaatDropdown.addActionListener(thaatPatternListener);

		final JComboBox patternDropdown = UiDropdown.patternThaatDropDown();
		patternDropdown.addActionListener(thaatPatternListener);

		addExtraTopControl(thaatDropdown);
		addExtraTopControl(patternDropdown);
	}

	@Override
	public void repaint() {
		specificComponentPanel.repaint();
	}

	@Override
	protected JPanel createMiddlePanel(final Object selectedObject) {
		this.specificComponentPanel = UiJPanelBuilder.BOX_VERTICAL.createPanel();
		specificComponentPanel.setOpaque(true);
		specificComponentPanel.add(Box.createVerticalStrut(50));

		
		JPanel labelPanel = UiJPanelBuilder.LEFT_FLOWLAYOUT.createPanel();
		labelPanel.add(new JLabel(pickTitle));
		labelPanel.setSize(new Dimension(10,40));
		
		JPanel playAreaPanel = UiJPanelBuilder.BOX_HORIZONTAL.createPanel();
		
		Collection<Playable> itemsList = (Collection<Playable>) selectedObject;
		if (itemsList == null) {
			itemsList = this.intialItemsList;
		}

		final TextArea consoleArea = createConsoleArea();
		
		final JButton pauseButton = pauseButton();
		final Playable[] allPlayableItems = itemsList.toArray(new Playable[itemsList.size()]);
		JList playItemsList = new JList(allPlayableItems);
		playItemsList.addListSelectionListener(new PlaySingleItemActionListener(playItemsList, consoleArea, allPlayableItems, pauseButton));
		playAreaPanel.add(new UiScrollPane().createScrollPane(playItemsList));
		
		playAreaPanel.add(consoleArea);
		
		JButton playAllButton = UiButtons.MusicButtons.PLAYER_FOR_ALL.createStaticNamedButton();
		playAllButton.addActionListener(new PlayAllItemsActionListener(consoleArea, allPlayableItems, pauseButton));
		
		specificComponentPanel.add(labelPanel);
		specificComponentPanel.add(playAreaPanel);
		specificComponentPanel.add(Box.createVerticalGlue());
		
		
		JPanel playAllPanel = UiJPanelBuilder.LEFT_FLOWLAYOUT.createPanel();
		playAllPanel.add(playAllButton);
		specificComponentPanel.add(playAllPanel);
		return specificComponentPanel;
	}

	private TextArea createConsoleArea() {
		TextArea textArea = new TextArea("Played notes will be displayed here in indian scale....");
		textArea.setEditable(false);
		textArea.setVisible(true);
		return textArea;
	}

	class PlayAllItemsActionListener implements ActionListener {
		private final TextArea textArea;
		private final Playable[] playableItems;
		private final JButton pauseButton;

		public PlayAllItemsActionListener(final TextArea textArea, final Playable[] playableItems, JButton pauseButton) {
			this.textArea = textArea;
			this.playableItems = playableItems;
			this.pauseButton = pauseButton;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			this.pauseButton.setText(AudioPlayerNextStatus.PAUSE.toString());
			PlayApi.playAllItemsWithInteractiveDisplayInTextArea(playableItems, textArea, 5);
		}
	}

	class PlaySingleItemActionListener implements ListSelectionListener {
		private final TextArea consoleArea;
		private final Playable[] allPlayableItems;
		private final JButton pauseButton;
		private JList jlist;

		public PlaySingleItemActionListener(final JList jlist, final TextArea consoleArea, Playable[] singlePlayableItem,
				JButton pauseButton) {
			this.jlist = jlist;
			this.consoleArea = consoleArea;
			this.allPlayableItems = singlePlayableItem;
			this.pauseButton = pauseButton;
		}

		private void displayOnConsole(final String name) {
			String displayText = "\n\n Playing::" + name;
			logger.info(displayText);
			consoleArea.setText(displayText);
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {
				int selectedIndex = jlist.getSelectedIndex();
				if (selectedIndex != -1) {
					this.pauseButton.setText(AudioPlayerNextStatus.PAUSE.toString());
					PlayApi.playInLoop(allPlayableItems[selectedIndex]);
					displayOnConsole(allPlayableItems[selectedIndex].name() + "===>" + "\n" + allPlayableItems[selectedIndex].asText());
				}
			}
		}
	}
}