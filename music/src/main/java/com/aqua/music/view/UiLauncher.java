package com.aqua.music.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.aqua.music.api.PlayApi;
import com.aqua.music.model.cyclicset.CyclicFrequencySet;
import com.aqua.music.model.cyclicset.SymmetricalSet;
import com.aqua.music.model.puzzles.QuizController;
import com.aqua.music.model.puzzles.QuizLevel;
import com.aqua.music.view.components.RehearsePanel;
import com.aqua.music.view.components.UiTabs;

public class UiLauncher {
	private static final String frameTitle = "Music";
	private static final Dimension preferredSizeForMainPane = new Dimension(450, 450);

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// UIManager.put("swing.boldMetal", Boolean.FALSE);
				new UiLauncher().createAndShowUi();
			}
		});
	}

	public JFrame createAndShowUi() {
		final JFrame frame = new JFrame(frameTitle);
		frame.setLocationRelativeTo(null);

		frame.add(new UiTabbedPanel(), BorderLayout.CENTER);

		// frame.setUndecorated( true );
		// AWTUtilities.setWindowOpacity(frame, 0.7f);
		frame.pack();
		frame.setVisible(true);

		frame.getContentPane().setPreferredSize(preferredSizeForMainPane);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		return frame;
	}

	static class UiTabbedPanel extends JPanel {
		private static final QuizLevel<CyclicFrequencySet> firstQuizLevel = (QuizLevel<CyclicFrequencySet>) QuizController.FrequencySetQuiz
				.quizLevels().iterator().next();
		private static final SymmetricalSet firstThaat = SymmetricalSet.THAAT_KAFI;

		public UiTabbedPanel() {
			super(new GridLayout(1, 1));
			add(tabbedPanel());
		}

		private JTabbedPane tabbedPanel() {
			JTabbedPane tabbedPane1 = new JTabbedPane();
			UiTabs uiTabs = new UiTabs(tabbedPane1);
			uiTabs.addPlaneThaat(new RehearsePanel(PlayApi.getAllPlainThaat()).getPanel());
			uiTabs.addSong(new RehearsePanel(PlayApi.getAllSongs()).getPanel());
			uiTabs.addPatternedTab(firstThaat, false);
			uiTabs.addQuiz(firstQuizLevel, false);
			tabbedPane1.setOpaque(true);
			return tabbedPane1;
		}
	}
}