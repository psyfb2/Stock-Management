package com.g52grp.warehouse.model;
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class BasicParameter {
	private static Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static double button1Width = scrSize.getWidth()/5;
	public static double button1Height = scrSize.getHeight()/3;
	
	public static double button2Width = scrSize.getWidth()/14;
	public static double button2Height = scrSize.getHeight()/40;
	
	public static double button3Width = scrSize.getWidth()/25;
	public static double button3height = scrSize.getWidth()/25;
	
	public static Scene Scene;
	public static Stage theStage;

	public static Dimension getScrSize() {
		return scrSize;
	}
	public static void setScrSize(Dimension scrSize) {
		BasicParameter.scrSize = scrSize;
	}
}
