package com.hgsoft.cards;

import java.util.List;

import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;


/**
 * 读卡器回调接口
 * 
 */
public interface CardReaderListener {
	/**
	 * 读到卡片的回调函数
	 * 
	 * @param cardId
	 *            卡片物理卡号
	 */
	public void OnOpenCard(final IsoDep dev,final String cardId);

	public void OnReadCard(final IsoDep dev,
			final CardInfoOpInterface cardInfoOpInterface);
	
	public void OnReadCardRecords(IsoDep dev, List<TradeRecord> records);
	
	public void OnException(final int exceptionType,final Exception e);

	public void OnCardConnected(boolean connected);

}