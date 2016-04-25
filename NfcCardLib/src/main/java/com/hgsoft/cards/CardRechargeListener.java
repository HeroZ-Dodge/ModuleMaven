package com.hgsoft.cards;

import java.util.HashMap;

import android.nfc.tech.IsoDep;

import com.hgsoft.cards.CardInfoOpInterface;
import com.hgsoft.cards.CardReaderListener;

public interface CardRechargeListener extends CardReaderListener {

	public void OnRechargeCardInit(final IsoDep dev, final CardInfoOpInterface cardInfoOpInterface, final HashMap<String, String> resultMap);

	public void OnRechargeCardFinish(final IsoDep dev, final CardInfoOpInterface cardInfoOpInterface, String loadResult, final HashMap<String, String> reInitMap);

	public void OnDoHalfCommondFinish(String respCmds);

	public void OnWriteResult(IsoDep dev, String cardId);

	public void OnPurchaseResult(IsoDep dev, double money);

	public void OnRechargeResult(IsoDep dev, double money);

}
