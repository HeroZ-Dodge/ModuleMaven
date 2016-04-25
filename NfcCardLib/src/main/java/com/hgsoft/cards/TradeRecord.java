package com.hgsoft.cards;

import java.util.Date;

public class TradeRecord {
    public int tradeNo; // 联机交易序号1-2 2
    public long overAmount; // 透支金额3-5 3
    public long tradeAmount; // 交易金额6-9 4
    public int tradeType; // 交易类型标识10 1 2是充值，6是消费
    public int terminalNo; // 终端机编号11-16 6
    public Date tradeDate; // 交易日期17-23 7

    @Override
    public String toString() {
        return "TradeRecord [tradeNo=" + tradeNo + ", overAmount="
                + overAmount + ", tradeAmount=" + tradeAmount
                + ", tradeType=" + tradeType + ", terminalNo=" + terminalNo
                + ", tradeDate=" + tradeDate.toLocaleString() + "]";
    }
}
