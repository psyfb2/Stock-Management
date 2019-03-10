package com.g52grp.warehouse.model;
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author psyzh1
 * Parameter to set button size
 *
 */
public class BasicParameter {
	private static Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static double button1Width = scrSize.getWidth()/5;
	private static double button1Height = button1Width/6 * 8;
	private static double button2Width = scrSize.getWidth()/14;
	private static double button2Height = scrSize.getHeight()/40;
	private static double button3Width = scrSize.getWidth()/25;
	private static double button3Height = scrSize.getWidth()/25;

	public static Dimension getScrSize() {
		return scrSize;
	}
	public static double getButton1Width() {
		return button1Width;
	}
	public static double getButton1Height() {
		return button1Height;
	}
	public static double getButton2Width() {
		return button2Width;
	}
	public static double getButton2Height() {
		return button2Height;
	}
	public static double getButton3Width() {
		return button3Width;
	}
	public static double getButton3Height() {
		return button3Height;
	}
}
