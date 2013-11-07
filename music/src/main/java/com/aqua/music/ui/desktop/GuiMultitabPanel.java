package com.aqua.music.ui.desktop;

import static com.aqua.music.ui.desktop.UiComponents.preferredSizeForThaatPanel;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.aqua.music.items.PatternGenerator;
import com.aqua.music.model.FrequencySet;
import com.aqua.music.model.FrequencySet.SymmetricalSet;

class GuiMultitabPanel extends JPanel {
	private final UiComponents uiComponents = new UiComponents();

	public GuiMultitabPanel() {
		super(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();

		final JPanel plainTab = createPlainTab();
		final JPanel patternTab = createPatternsTab();

		tabbedPane.addTab("Rehearse plain items", plainTab);
		tabbedPane.addTab("Rehearse kafi with pattern", patternTab);

		tabbedPane.setOpaque(true);
		add(tabbedPane);
	}

	private void addQuitButton(JPanel... panels) {
		for (JPanel each : panels) {
			each.add(uiComponents.buttonInstance(GuiButtons.QUIT, null));
		}
	}

	private JPanel createPlainTab() {
		uiComponents.reset();
		final JPanel plainTab = createTab();

		// add individual frequency-set buttons
		for (FrequencySet eachFrequencySet : SymmetricalSet.values()) {
			plainTab.add(uiComponents.buttonInstance(GuiButtons.PLAYABLE, new Object[] { eachFrequencySet }));
		}
		// add play all button
		plainTab.add(uiComponents.buttonInstance(GuiButtons.PLAY_ALL_TO_INFINITY, SymmetricalSet.values()));

		addQuitButton(plainTab);
		
		plainTab.setOpaque(true);
		return plainTab;
	}

	private JPanel createPatternsTab() {
		uiComponents.reset();
		JPanel patternTab = createTab();

		FrequencySet frequencySet = SymmetricalSet.THAAT_KAFI;
		List<int[]> pairPaterrns = PatternGenerator.PAIR.generatePatterns(frequencySet.ascendNotes());

		// add individual pattern button for each set
		for (int[] eachPattern : pairPaterrns) {
			patternTab.add(uiComponents.buttonInstance(GuiButtons.PLAYABLE_PATTERN, new Object[] { frequencySet, eachPattern }));
		}
		
		addQuitButton(patternTab);
		return patternTab;
	}

	private JPanel createTab() {
		JPanel playablePanel = new JPanel();
		playablePanel.setLayout(null);
		playablePanel.setPreferredSize(preferredSizeForThaatPanel);
		return playablePanel;
	}
}
