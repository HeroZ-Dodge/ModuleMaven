package com.hgsoft.cards;

import java.util.Date;


/**
 * 获取卡操作命令
 * 
 * @ClassName: GCardOperator
 * @Description: TODO
 * @author weiliu
 * @date 2014-12-19 下午2:33:24
 */
public class CardCommond {

	/**
	 * 选择1001目录文件
	 * 
	 * @return
	 */
	public static String select1001Dir() {
		byte sendbuf[] = { 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x10, 0x01 };
		return BaseUtil.bytesToHexString(sendbuf);
	}

	/**
	 * 选择MF00目录文件
	 * 
	 * @return
	 */
	public static String selectMF00Dir() {
		byte sendbuf[] = { 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x3f, 0x00 };
		return BaseUtil.bytesToHexString(sendbuf);
	}

	/**
	 * 读取MF00目录下的0015文件
	 * 
	 * @return
	 */
	public static String readMF0015() {
		byte sendbuf[] = new byte[] { 0x00, (byte) 0xB0, (byte) 0x95, 0x00, 0x2d };
		return BaseUtil.bytesToHexString(sendbuf);
	}

	/**
	 * 读取卡内二进制文件
	 * 
	 * @param fileno
	 * @param le
	 *            长度
	 * @return
	 */
	private static String readBinary(byte fileno, byte le) {
		byte np = (byte) (fileno | 0x80);
		byte[] commandBytes = new byte[] { 0x00, (byte) 0xB0, np, 0x00, le };
		return BaseUtil.bytesToHexString(commandBytes);
	}

	/**
	 * 读取0012文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String read0012() {
		byte cmd = 0x12;
		return readBinary(cmd, (byte) 0x00);
	}

	/**
	 * 读取0015文件
	 * 
	 * @return
	 */
	public static String read0015() {
		byte[] commandBytes = new byte[] { 0x00, (byte) 0xB0, (byte) 0x95, 0x00, 0x00 };
		return BaseUtil.bytesToHexString(commandBytes);
	}

	/**
	 * 读取私有文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String readfilePrivate(byte fileno) {
		return readBinary(fileno, (byte) 0x00);
	}

	/**
	 * 读取0008文件
	 * 
	 * @return
	 */
	public static String read0008() throws Exception {
		byte cmd = 0x08;
		return readBinary(cmd, (byte) 0x00);
	}

	/**
	 * 读取0009文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String read0009() throws Exception {
		byte cmd = 0x09;
		return readBinary(cmd, (byte) 0x00);
	}

	/**
	 * 读取0019文件
	 * 
	 * @param block
	 *            0x01为第一块 ,0x02为第二块
	 * @return
	 * @throws Exception
	 */
	public static String read0019(byte block) throws Exception {
		byte[] commandBytes = new byte[] { 0x00, (byte) 0xb2, block, (byte) 0xcc, 0x00 };
		return BaseUtil.bytesToHexString(commandBytes);
	}

	/**
	 * 读取卡内余额
	 * 
	 * @return (返回结果 可先转成16进制 再转成10进制即可得到10进制的以分为单位的金额)
	 * @throws Exception
	 */
	public static String readBalance() {
		byte queryMoneySend[] = { (byte) 0x80, 0x5c, 0x00, 0x02, 0x04 };
		return BaseUtil.bytesToHexString(queryMoneySend);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String readTradeRecord(byte filerecord) {
		byte sendbuf[] = { 0x00, (byte) 0xb2, filerecord, (byte) 0xc4, 0x00 };
		return BaseUtil.bytesToHexString(sendbuf);
	}
	/**
	 * 获取校验个人识别码命令
	 * @param pin
	 * @return 
	 * @throws Exception
	 */
	public static String verityPIN(byte[] pin) throws Exception  {
        String result = null;
        try {
            if (pin != null && pin.length >= 3) {
                byte[] veritySend = {(byte) 0x00, (byte) 0x20, (byte) 0x00,
                        (byte) 0x00};
                byte[] pinLen = BaseUtil.hexStringToBytes(Integer.toHexString(pin.length));

                byte sendbuf[] = new byte[veritySend.length + pinLen.length + pin.length];
                System.arraycopy(veritySend, 0, sendbuf, 0, veritySend.length);
                System.arraycopy(pinLen, 0, sendbuf, veritySend.length, pinLen.length);
                System.arraycopy(pin, 0, sendbuf, veritySend.length + pinLen.length, pin.length);
                result = BaseUtil.bytesToHexString(sendbuf);
            }
        } catch (Exception e) {
        	throw new Exception("获取校验个人识别码命令失败");
        }
        return result;
	}

	/**
	 * 获取初始化圈存命令
	 * 
	 * @param psamNo
	 *            终端机编号
	 * @param payFee
	 *            金额
	 * @return
	 * @throws Exception
	 */
	public static String initRecharge(byte[] psamNo, int payFee) throws Exception {
		String result = null;
		try {
			if (psamNo != null && psamNo.length == 6) {
				byte payFeeBuf[] = new byte[4];
				BaseLib.intToByteArray(payFee, payFeeBuf, 0, true);
				byte sendbuf[] = new byte[6 + 4 + psamNo.length + 1];
				System.arraycopy(new byte[] { (byte) 0x80, 0x50, 0x00, 0x02, 0x0B, 0x01 }, 0, sendbuf, 0, 6);// 前5个字节
																												// 放指令+1个字节的密钥索引号
				System.arraycopy(payFeeBuf, 0, sendbuf, 6, 4);// 接着4个字节放消费金额
				System.arraycopy(psamNo, 0, sendbuf, 10, psamNo.length);// 接着放6个字节的终端号
				sendbuf[sendbuf.length - 1] = 0x10;
				result = BaseUtil.bytesToHexString(sendbuf);
			}
		} catch (Exception e) {
			throw new Exception("获取对卡进行初始化圈存命令失败");
		}
		return result;
	}

	/**
	 * 获取对卡进行圈存操作命令
	 * 
	 * @param mac2
	 * @param PayTime
	 * @return
	 * @throws Exception
	 */
	public static String tradeRecharge(byte[] mac2, Date PayTime) throws Exception {
		String result = null;
		try {
			if (mac2 != null && PayTime != null && mac2.length == 4) {
				byte[] PayTimeBuf = BaseLib.hexToBin(BaseLib.dateToStr(PayTime, "yyyyMMddHHmmss"));
				byte[] sendbuf = new byte[] { (byte) 0x80, 0x52, 0x00, 0x00, 0x0B,// 指令命令
						PayTimeBuf[0], PayTimeBuf[1], PayTimeBuf[2], PayTimeBuf[3],// 交易日期
																					// 4个字节
						PayTimeBuf[4], PayTimeBuf[5], PayTimeBuf[6],// 交易时间 3个字节
						mac2[0], mac2[1], mac2[2], mac2[3],// MAC2// 4个字节
						0x00 };// 后一个字节放00
				result = BaseUtil.bytesToHexString(sendbuf);
			}
		} catch (Exception e) {
			throw new Exception("获取对卡进行圈存命令失败");
		}
		return result;
	}

	/**
	 * 获取初始化消费命令
	 * 
	 * @param psamNo 终端号
	 * @param payFee 消费金额
	 * @return
	 * @throws Exception
	 */
	public static String initTradePay(byte[] psamNo, int payFee) throws Exception {
		String result = null;
		try {
			byte payFeeBuf[] = new byte[4];
			BaseLib.intToByteArray(payFee, payFeeBuf, 0, true);
			byte sendbuf[] = new byte[6 + 4 + psamNo.length];
			System.arraycopy(new byte[] { (byte) 0x80, 0x50, 0x01, 0x02, 0x0B, 0x01 }, 0, sendbuf, 0, 6);// 前5个字节
																											// 放指令+1个字节的密钥索引号
			System.arraycopy(payFeeBuf, 0, sendbuf, 6, 4);// 接着4个字节放消费金额
			System.arraycopy(psamNo, 0, sendbuf, 10, psamNo.length);// 接着放6个字节的终端号
			result = BaseUtil.bytesToHexString(sendbuf);
		} catch (Exception e) {
			throw new Exception("获取对卡进行初始化消费命令失败");
		}
		return result;
	}

	/**
	 * 对卡进行消费操作
	 * TODO
	 * @Title:tradePay
	 * @param zdReply 终端交易序号4个字节
	 * @param mac1 4个字节
	 * @param PayTime
	 * @return
	 * @throws Exception 
	 *
	 * @author weiliu
	 */
	public static String tradePay(byte[] zdReply, byte[] mac1, Date PayTime) throws Exception {
		String result = null;
		try {
			if (mac1 != null && zdReply != null && mac1.length == 4 && zdReply.length == 4) {
				byte[] PayTimeBuf = BaseLib.hexToBin(BaseLib.dateToStr(PayTime, "yyyyMMddHHmmss"));
				byte[] sendbuf = new byte[] { (byte) 0x80, 0x54, 0x01, 0x00, 0x0F,// 指令命令
						zdReply[0], zdReply[1], zdReply[2], zdReply[3], // 终端交易序号
																		// 4个字节
						PayTimeBuf[0], PayTimeBuf[1], PayTimeBuf[2], PayTimeBuf[3],// 交易日期4个字节
						PayTimeBuf[4], PayTimeBuf[5], PayTimeBuf[6],// 交易时间 3个字节
						mac1[0], mac1[1], mac1[2], mac1[3],// MAC1 4个字节
						0x08 };// 后一个字节放08
				result = BaseUtil.bytesToHexString(sendbuf);
			} else {
				throw new Exception("获取对卡进行消费命令失败");
			}
		} catch (Exception e) {
			throw new Exception("获取对卡进行消费命令失败");
		}
		return result;
	}
}
