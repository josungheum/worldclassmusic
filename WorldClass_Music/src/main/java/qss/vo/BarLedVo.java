package qss.vo;

import java.math.BigInteger;
import java.util.List;

public class BarLedVo extends CommonVo {

	private static final long serialVersionUID = -8265625004946938407L;

	/*
	 * marquee main table
	 */
	private BigInteger mqIdx;
	private String mqText;
	private String mqFont;
	private String mqSize;
	private String mqMotion;
	private String mqTextColor;
	private String mqBgColor;
	private String mqType;
	
	/*
	 * marquee schedule target table
	 */
	private BigInteger deviceIdx;
	private String distributeYn;
	
	/*
	 * desc
	 */
	private String searchDvType;
	private String searchDvRes;
	private String searchDvSite;
	private List<BigInteger> deviceList;	
	private String[] checkboxArr;
	
	public BigInteger getMqIdx() {
		return mqIdx;
	}
	public void setMqIdx(BigInteger mqIdx) {
		this.mqIdx = mqIdx;
	}
	public String getMqText() {
		return mqText;
	}
	public void setMqText(String mqText) {
		this.mqText = mqText;
	}
	public String getMqFont() {
		return mqFont;
	}
	public void setMqFont(String mqFont) {
		this.mqFont = mqFont;
	}
	public String getMqSize() {
		return mqSize;
	}
	public void setMqSize(String mqSize) {
		this.mqSize = mqSize;
	}
	public String getMqMotion() {
		return mqMotion;
	}
	public void setMqMotion(String mqMotion) {
		this.mqMotion = mqMotion;
	}
	public String getMqTextColor() {
		return mqTextColor;
	}
	public void setMqTextColor(String mqTextColor) {
		this.mqTextColor = mqTextColor;
	}
	public String getMqBgColor() {
		return mqBgColor;
	}
	public void setMqBgColor(String mqBgColor) {
		this.mqBgColor = mqBgColor;
	}
	public BigInteger getDeviceIdx() {
		return deviceIdx;
	}
	public void setDeviceIdx(BigInteger deviceIdx) {
		this.deviceIdx = deviceIdx;
	}
	public String getDistributeYn() {
		return distributeYn;
	}
	public void setDistributeYn(String distributeYn) {
		this.distributeYn = distributeYn;
	}
	public String getSearchDvType() {
		return searchDvType;
	}
	public void setSearchDvType(String searchDvType) {
		this.searchDvType = searchDvType;
	}
	public String getSearchDvRes() {
		return searchDvRes;
	}
	public void setSearchDvRes(String searchDvRes) {
		this.searchDvRes = searchDvRes;
	}
	public String getSearchDvSite() {
		return searchDvSite;
	}
	public void setSearchDvSite(String searchDvSite) {
		this.searchDvSite = searchDvSite;
	}
	public List<BigInteger> getDeviceList() {
		return deviceList;
	}
	public void setDeviceList(List<BigInteger> deviceList) {
		this.deviceList = deviceList;
	}
	public String[] getCheckboxArr() {
		return checkboxArr;
	}
	public void setCheckboxArr(String[] checkboxArr) {
		this.checkboxArr = checkboxArr;
	}
	public String getMqType() {
		return mqType;
	}
	public void setMqType(String mqType) {
		this.mqType = mqType;
	}
}