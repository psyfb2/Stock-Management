package model;
import java.awt.Dimension;
import java.awt.Toolkit;

public class BasicParameter {
	private static Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
	private static double jobStageButtonHeight = scrSize.getHeight()/16;
	private static double jobStageButtonWidth = scrSize.getWidth()/15;
	private static double logoWidth = scrSize.getWidth();
	private static double logoHeight = jobStageButtonHeight *5 /4;
	private static double TableWidth = scrSize.getWidth() - jobStageButtonWidth;
	private static double TableHeight = scrSize.getHeight() - logoHeight;

	public static Dimension getScrSize() {
		return scrSize;
	}
	public static void setScrSize(Dimension scrSize) {
		BasicParameter.scrSize = scrSize;
	}
	public static double getJobStageButtonHeight() {
		return jobStageButtonHeight;
	}
	public static void setJobStageButtonHeight(double jobStageButtonHeight) {
		BasicParameter.jobStageButtonHeight = jobStageButtonHeight;
	}
	public static double getJobStageButtonWidth() {
		return jobStageButtonWidth;
	}
	public static void setJobStageButtonWidth(double jobStageButtonWidth) {
		BasicParameter.jobStageButtonWidth = jobStageButtonWidth;
	}
	public static double getLogoWidth() {
		return logoWidth;
	}
	public static void setLogoWidth(double logoWidth) {
		BasicParameter.logoWidth = logoWidth;
	}
	public static double getLogoHeight() {
		return logoHeight;
	}
	public static void setLogoHeight(double logoHeight) {
		BasicParameter.logoHeight = logoHeight;
	}
	public static double getTableWidth() {
		return TableWidth;
	}
	public static void setTableWidth(double tableWidth) {
		TableWidth = tableWidth;
	}
	public static double getTableHeight() {
		return TableHeight;
	}
	public static void setTableHeight(double tableHeight) {
		TableHeight = tableHeight;
	}
}
