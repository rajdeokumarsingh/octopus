package com.aqua.music.view.components;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import open.music.api.StateDependentUi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for music panels
 * 
 * @author "Shruti Tiwari"
 * 
 */
abstract class MusicPanel {
	protected final Logger logger = LoggerFactory.getLogger(MusicPanel.class);

	private volatile boolean initialized = false;
	private final JPanel mainPanel;
	private JPanel refreshablePanel;
	
	private final JPanel leftPanel = UiJPanelBuilder.LEFT_FLOWLAYOUT.createPanel();

	private JPanel middlePanel;

	protected MusicPanel(StateDependentUi commonPanelComponents,boolean extraPanel) {
		this.mainPanel = UiJPanelBuilder.BOX_VERTICAL.createPanel();
		this.middlePanel = UiJPanelBuilder.BOX_VERTICAL.createPanel();
		mainPanel.add(leftPanel);
		mainPanel.add(middlePanel);
	}

	public void addExtraTopControl(JComponent aComponent) {
		leftPanel.add(aComponent);
	}

	public JPanel getPanel() {
		if (!initialized) {
			configureMiddlePanel();
		}
		return mainPanel;
	}

	public void refreshSpecificComponentPanel(final Object selectedObject) {
		middlePanel.remove(refreshablePanel);
		addMiddlePanel(selectedObject);
		middlePanel.revalidate();
	}

	public abstract void repaint();

	protected abstract JPanel createMiddlePanel(final Object selectedObject);

	private void addMiddlePanel(final Object selectedObject) {
		this.refreshablePanel = createMiddlePanel(selectedObject);
		middlePanel.add(refreshablePanel);
	}

	private synchronized void configureMiddlePanel() {
		if (!initialized) {
			addMiddlePanel(null);
			mainPanel.setOpaque(true);
			initialized = true;
		}
	}

}
