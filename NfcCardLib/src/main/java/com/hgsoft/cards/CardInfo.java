package com.hgsoft.cards;

import java.io.Serializable;

public class CardInfo implements Serializable{

	/**
	 * 卡信息
	 */
	private static final long serialVersionUID = 256913862851574015L;
	private String cardphysicalNo;
	private int GBFlag;
	private String file0002;//电子钱包文件
	private String orifile0015;//原始0015文件
	private String file0015;//选择1001目录后读取的0015文件
	private String file0019;//复合消费专用文件
	private String filePrivate;//私有文件
	private String file0008;//标识站文件
	private String file0009;
	private String file0018;//终端交易记录文件
	private String file0012;//联网收费信息文件
	private String file0016;//持卡人基本数据文件

	public int getGBFlag() {
		return GBFlag;
	}
	public void setGBFlag(int gBFlag) {
		GBFlag = gBFlag;
	}
	public String getCardphysicalNo() {
		return cardphysicalNo;
	}
	public void setCardphysicalNo(String cardphysicalNo) {
		this.cardphysicalNo = cardphysicalNo;
	}
	public String getFile0002() {
		return file0002;
	}
	public void setFile0002(String file0002) {
		this.file0002 = file0002;
	}
	public String getOrifile0015() {
		return orifile0015;
	}
	public void setOrifile0015(String orifile0015) {
		this.orifile0015 = orifile0015;
	}
	public String getFile0015() {
		return file0015;
	}
	public void setFile0015(String file0015) {
		this.file0015 = file0015;
	}
	public String getFile0019() {
		return file0019;
	}
	public void setFile0019(String file0019) {
		this.file0019 = file0019;
	}
	public String getFilePrivate() {
		return filePrivate;
	}
	public void setFilePrivate(String filePrivate) {
		this.filePrivate = filePrivate;
	}
	public String getFile0008() {
		return file0008;
	}
	public void setFile0008(String file0008) {
		this.file0008 = file0008;
	}
	public String getFile0009() {
		return file0009;
	}
	public void setFile0009(String file0009) {
		this.file0009 = file0009;
	}
	public String getFile0018() {
		return file0018;
	}
	public void setFile0018(String file0018) {
		this.file0018 = file0018;
	}
	public String getFile0012() {
		return file0012;
	}
	public void setFile0012(String file0012) {
		this.file0012 = file0012;
	}
	public String getFile0016() {
		return file0016;
	}
	public void setFile0016(String file0016) {
		this.file0016 = file0016;
	}
	
}
