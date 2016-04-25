package com.hgsoft.cards;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.apache.http.util.EncodingUtils;


public class CardInfo_GuoBiao extends CardInfo implements CardInfoOpInterface, Serializable {

	/**
	 * 国标卡信息
	 */
	private static final long serialVersionUID = 1226109757187660188L;
	// 0015文件
	private String issuerPro = ""; // 发卡省份
	public int issuer = 0;// 发卡方标识 8 BCD
	public int type = 0;// 卡类型标识 1 BCD (22/23是国标)
	public int versionNo = 0;// 卡片版本号 1 BCD
	public int networkNo = 0;// 卡片网络编号 2 BCD`
	private String cardID = "";// 物理卡号
	public String cardNo = "";// 卡片内部编号 8 BCD (卡序列号)
	public byte[] cardNoBuf = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
	public String startTime = "";// 启用时间 4 BCD
	public String endTime = "";// 到期时间 4 BCD
	public String vehPlane = ""; // 车牌号码 12 ASC
	public int userType = 0;// 用户类型 BCD
	public int vehPlaneColor = 0;// 车牌照颜色 BCD
	public int vehPlaneType = 0;// 车牌照颜色 BCD
	// 0008

	// 0016
	public int owerId = -1;
	public int systemOwerId = -1;
	public String owerName = "";
	public String owerSfz = "";
	public int owerSfzType = -1;
	
	// 0012

	// 0002
	private long balance = 0;// 金额

	
	
	@Override
	public void read0002(byte[] buf) {
		if (buf!=null&&buf.length>2) {
			byte[] balanceByte = Arrays.copyOf(buf, buf.length - 2);
			balance= BaseUtil.hexToTen(BaseUtil.bytesToHexString(balanceByte));
		}
	}
	
	@Override
	public void read0008(byte[] buf) {

	}

	@Override
	public void read0016(byte[] buf) {
		String s = BaseUtil.bytesToHexString(buf);
		byte[] tempBuf = null;
		if (s.endsWith("9000")) {
			s=s.substring(0, s.length()-4);
			tempBuf=BaseUtil.hexStringToBytes(s);
		}else {
			tempBuf=buf;
		}
		owerId =(int) BaseUtil.hexToTen(s.substring(0, 2));
		systemOwerId = (int) BaseUtil.hexToTen(s.substring(2, 4));
		owerName =EncodingUtils.getString(tempBuf, 2,20, "GB2312").trim();
		owerSfz = new String(BaseLib.hexToBin(s.substring(44, 108)));
		owerSfzType =(int) BaseUtil.hexToTen(s.substring(108, 110));
	}

	@Override
	public void read0012(byte[] buf) {

	}

	@Override
	public void read0015(byte[] buf) {
		String s = BaseUtil.bytesToHexString(buf);
		issuer = BaseUtil.strToInt(s.substring(0, 16), 0);
		if (buf.length > 4) {
			byte[] srcPos = new byte[4];
			System.arraycopy(buf, 0, srcPos, 0, 4);
	        try {
				issuerPro = new String(srcPos,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		type = (int) BaseUtil.hexToTen(s.substring(16, 18));
		versionNo = BaseUtil.strToInt(s.substring(18, 20), 0);
		networkNo = BaseUtil.strToInt(s.substring(20, 24), 0);
		if (issuerPro.equals("贵州")) {
			cardNo = networkNo+s.substring(24, 40);
		}else if (issuerPro.equals("江西")) {
			networkNo=3601;
			cardNo = networkNo+s.substring(24, 40);
		}else {
			cardNo = s.substring(24, 40);
		}
		startTime = s.substring(40, 48);
		endTime = s.substring(48, 56);
        try {
        	byte[] srcPos = new byte[12];
    		System.arraycopy(buf, 28, srcPos, 0, 12);
        	vehPlane = new String(srcPos,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		userType = BaseUtil.strToInt(s.substring(80, 82), 0);
		vehPlaneColor = BaseUtil.strToInt(s.substring(82, 84), 0);
		vehPlaneType= BaseUtil.strToInt(s.substring(84, 86), 0);
	}

	@Override
	public void read0019(byte[] buf) {

	}

	public int getIssuer() {
		return issuer;
	}

	public void setIssuer(int issuer) {
		this.issuer = issuer;
	}

	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public int getNetworkNo() {
		return networkNo;
	}

	public void setNetworkNo(int networkNo) {
		this.networkNo = networkNo;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getVehPlane() {
		return vehPlane;
	}

	public void setVehPlane(String vehPlane) {
		this.vehPlane = vehPlane;
	}

	public int getVehPlaneType() {
		return vehPlaneType;
	}

	public void setVehPlaneType(int vehPlaneType) {
		this.vehPlaneType = vehPlaneType;
	}

	public int getOwerId() {
		return owerId;
	}

	public void setOwerId(int owerId) {
		this.owerId = owerId;
	}

	public int getSystemOwerId() {
		return systemOwerId;
	}

	public void setSystemOwerId(int systemOwerId) {
		this.systemOwerId = systemOwerId;
	}

	public String getOwerName() {
		return owerName;
	}

	public void setOwerName(String owerName) {
		this.owerName = owerName;
	}

	public String getOwerSfz() {
		return owerSfz;
	}

	public void setOwerSfz(String owerSfz) {
		this.owerSfz = owerSfz;
	}

	public int getOwerSfzType() {
		return owerSfzType;
	}

	public void setOwerSfzType(int owerSfzType) {
		this.owerSfzType = owerSfzType;
	}

	public int getVehPlaneColor() {
		return vehPlaneColor;
	}

	public void setVehPlaneColor(int vehPlaneColor) {
		this.vehPlaneColor = vehPlaneColor;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getIssuerPro() {
		return issuerPro;
	}

	public void setIssuerPro(String issuerPro) {
		this.issuerPro = issuerPro;
	}


    @Override
	public String toString() {
		return "CardInfo_GuoBiao [issuerPro=" + issuerPro + ", issuer=" + issuer + ", type=" + type + ", versionNo=" + versionNo + ", networkNo=" + networkNo + ", cardID=" + cardID + ", cardNo="
				+ cardNo + ", cardNoBuf=" + Arrays.toString(cardNoBuf) + ", startTime=" + startTime + ", endTime=" + endTime + ", vehPlane=" + vehPlane + ", userType=" + userType + ", vehPlaneColor="
				+ vehPlaneColor + ", vehPlaneType=" + vehPlaneType + ", owerId=" + owerId + ", systemOwerId=" + systemOwerId + ", owerName=" + owerName + ", owerSfz=" + owerSfz + ", owerSfzType="
				+ owerSfzType + ", balance=" + balance + "]";
	}

	public String getShowInfo(){
    	return "issuerPro=" + issuerPro + ", type=" + type + ", cardID=" + cardID + ", cardNo=" + cardNo + ", endTime=" + endTime + ", balance=" + balance + "";
	}
	
	
}
