package com.hgsoft.cards;

public class TradeBean {

	private String CardID;// 卡ID
	private int GBFlag;// 国标标志־
	private String CardNo;// 卡表面号(包括网络编号)
	private String OpTime;// 卡操作时间
	private String TermCode ;// PSAM卡卡号
	private int TermTradeNo;// 终端交易序列号
	private int CardTradeNo;// 卡片交易序列号
	private int Tac;// TAC码
	
	
	public String getCardID() {
		return CardID;
	}
	public void setCardID(String cardID) {
		CardID = cardID;
	}
	
	public int getGBFlag() {
		return GBFlag;
	}
	public void setGBFlag(int gBFlag) {
		GBFlag = gBFlag;
	}
	public String getCardNo() {
		return CardNo;
	}
	public void setCardNo(String cardNo) {
		CardNo = cardNo;
	}
	public String getOpTime() {
		return OpTime;
	}
	public void setOpTime(String opTime) {
		OpTime = opTime;
	}
	
	
	public String getTermCode() {
		return TermCode;
	}
	public void setTermCode(String termCode) {
		TermCode = termCode;
	}
	public int getTermTradeNo() {
		return TermTradeNo;
	}
	public void setTermTradeNo(int termTradeNo) {
		TermTradeNo = termTradeNo;
	}
	public int getCardTradeNo() {
		return CardTradeNo;
	}
	public void setCardTradeNo(int cardTradeNo) {
		CardTradeNo = cardTradeNo;
	}
	public int getTac() {
		return Tac;
	}
	public void setTac(int tac) {
		Tac = tac;
	}
	
}
