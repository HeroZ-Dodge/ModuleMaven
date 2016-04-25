package com.hgsoft.cards;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import android.nfc.tech.IsoDep;
import android.util.Log;

/**
 * 卡操作类
 * 
 * @author weiliu
 * 
 */
public class CardOperator {

	private final static String TAG = "cardop";
	public final static int READ_OK = 1;
	public final static int READ_FAIL = -1;
	public final static int FILE_NOT_FONUD = 0;
	public final static String file0018Splite = "|";// 交易记录文 内,每一条记录分割符
	public final static int PIN_OK = 0;
	public final static int PIN_FAIL = -1;
	public final static int PIN_LOCK = -2;
	private IsoDep dev;
	private byte[] recvbuf1001;
	private byte[] recvbufmf00;
	private byte[] recvbuf0002;
	private byte[] recvbuf0012;
	private byte[] recvbuf0016;
	private byte[] recvbuf0015;
	private byte[] recvbuf0019;
	private byte[] recvbuf_filePrivate;
	private byte[] recvbuf0009;
	private byte[] recvbuf0008;

	private byte[] EDPaySerial; // ED脱机交易序号或EP脱机交易序号 或电子钱包交易序号 2字节
	private byte[] PayRandomNum; // 伪随机数 4个字节
	private byte[] MAC2;// MAC2码

	private byte[] EDRechargeSerial; // ED联机交易序号 2字节
	private byte[] RechargeRandomNum; // 伪随机数 4个字节
	private byte[] RechargeMAC1; // 充值MAC1

	private int PaySerial = 0;// 储值卡交易序列号
	private int ZdPayserial = 0;// 储值卡终端交易序号
	private int Tac = 0;// 储值卡交易TAC码

	private String recvbuf0018;// 交易记录文件,读取50条，每一条记录用file0018Splite分割
	private List<TradeRecord> tRLists = new ArrayList<TradeRecord>();// 交易记录

	private boolean oplog = false;// 操作记录日记
	private boolean syslog = false;// 操作记录结果打印

	public CardOperator(IsoDep dev) {
		this.dev = dev;
		tRLists = new ArrayList<TradeRecord>();// 交易记录
	}

	/**
	 * 选择1001目录文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean select1001Dir() throws Exception {
		boolean flag = true;
		String result = null;
		try {
			byte sendbuf[] = { 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x10, 0x01 };
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("选择1001目录,发送指令:" + BaseUtil.bytesToHexString(sendbuf));
			}
			byte recvbuf[] = dev.transceive(sendbuf);
			result = BaseUtil.bytesToHexString(recvbuf);
			if (!isSuccess(recvbuf)) {
				if (syslog) {
					Log.e(TAG, "选择用户卡1001目录失败");
				}
				flag = false;
				throw new Exception("选择用户卡1001目录失败,Error Code=" + result);
			} else {
				this.recvbuf1001 = recvbuf;
			}
		} catch (Exception e) {
			throw new Exception("选择用户卡1001目录失败,Error Code=" + result);
		}
		return flag;
	}
	/**
	 * 选择1001目录文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean selectMF00Dir() throws Exception {
		boolean flag = true;
		String result = null;
		try {
			byte sendbuf[] = { 0x00, (byte) 0xA4, 0x00, 0x00, 0x02, 0x3f, 0x00 };
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("选择MF00目录,发送指令:" + BaseUtil.bytesToHexString(sendbuf));
			}
			byte recvbuf[] = dev.transceive(sendbuf);
			result = BaseUtil.bytesToHexString(recvbuf);
			if (!isSuccess(recvbuf)) {
				if (syslog) {
					Log.e(TAG, "选择用户卡MF00目录失败");
				}
				flag = false;
				throw new Exception("选择用户卡MF00目录失败,Error Code=" + result);
			} else {
				this.recvbufmf00 = recvbuf;
			}
		} catch (Exception e) {
			throw new Exception("选择用户卡MF00目录失败,Error Code=" + result);
		}
		return flag;
	}
	/**
	 * 读取卡内二进制文件
	 * 
	 * @param fileno
	 * @param le
	 *            长度
	 * @return
	 * @throws Exception
	 */
	private byte[] readBinary(byte fileno, byte le) throws Exception {
		byte np = (byte) (fileno | 0x80);
		String file = BaseUtil.bytesToHexString(new byte[] { fileno });
		String result = null;
		try {
			byte[] commandBytes = new byte[] { 0x00, (byte) 0xB0, np, 0x00, le };
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("读取00" + file + "文件,发送指令:" + BaseUtil.bytesToHexString(commandBytes));
			}
			byte[] readRecv = dev.transceive(commandBytes);
			result = BaseUtil.bytesToHexString(readRecv);
			if (syslog) {
				Log.e(TAG, "读取卡内00" + file + "文件,返回code=" + result);
			}
			if (!isSuccess(readRecv)) {
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("读取00" + file + "文件失败,Error Code=" + result);
				}
				if (result.equalsIgnoreCase("6A82")) {// 卡内文件不存在
					return null;
				} else {
					throw new Exception("读取卡内00" + file + "文件失败,Error Code=" + result);
				}
			} else {
				return readRecv;
			}
		} catch (Exception e) {
			throw new Exception("读取卡内00" + file + "文件失败,Error Code=" + result);
		}
	}

	/**
	 * 读取0012文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public int read0012() throws Exception {
		byte cmd = 0x12;
		this.recvbuf0012 = readBinary(cmd, (byte) 0x00);
		if (recvbuf0012 != null) {
			return READ_OK;
		} else {
			return FILE_NOT_FONUD;
		}
	}

	/**
	 * 读取0016文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public int read0016() throws Exception {
		byte cmd = 0x16;
		this.recvbuf0016 = readBinary(cmd, (byte) 0x00);
		if (recvbuf0016 != null) {
			return READ_OK;
		} else {
			return FILE_NOT_FONUD;
		}
	}

	/**
	 * 读取0015文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean read0015() throws Exception {
		byte cmd = 0x15;
		this.recvbuf0015 = readBinary(cmd, (byte) 0x00);
		if (recvbuf0015 != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 读取私有文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public int readfilePrivate(byte fileno) throws Exception {
		this.recvbuf_filePrivate = readBinary(fileno, (byte) 0x00);
		if (recvbuf_filePrivate != null) {
			return READ_OK;
		} else {
			return FILE_NOT_FONUD;
		}
	}

	/**
	 * 读取0008文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public int read0008() throws Exception {
		byte cmd = 0x08;
		this.recvbuf0008 = readBinary(cmd, (byte) 0x00);
		if (recvbuf0008 != null) {
			return READ_OK;
		} else {
			return FILE_NOT_FONUD;
		}
	}

	/**
	 * 读取0009文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public int read0009() throws Exception {
		byte cmd = 0x09;
		this.recvbuf0009 = readBinary(cmd, (byte) 0xff);
		if (recvbuf0009 != null) {
			return READ_OK;
		} else {
			return FILE_NOT_FONUD;
		}
	}

	/**
	 * 读取0019文件
	 * 
	 * @param block
	 *            0x01为第一块 ,0x02为第二块
	 * @return
	 * @throws Exception
	 */
	public boolean read0019(byte block) throws Exception {
		boolean flag = true;
		String result = null;
		try {
			if (block != 0x01 && block != 0x02) {
				throw new Exception("读取卡内0019文件失败,块指令不是0x01或0x02");
			}
			byte[] commandBytes = new byte[] { 0x00, (byte) 0xb2, block, (byte) 0xcc, 0x00 };
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("读取0019文件,发送指令:" + BaseUtil.bytesToHexString(commandBytes));
			}
			byte[] read0019Recv = dev.transceive(commandBytes);
			result = BaseUtil.bytesToHexString(read0019Recv);
			if (syslog) {
				Log.e(TAG, "读取卡内0019文件,返回code=" + result);
			}
			if (!isSuccess(read0019Recv)) {
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("读取0019文件失败,Error Code=" + result);
				}
				flag = false;
				throw new Exception("读取卡内0019文件失败,Error Code=" + result);
			} else {
				this.recvbuf0019 = read0019Recv;
			}
		} catch (Exception e) {
			throw new Exception("读取卡内0019文件失败,Error Code=" + result);
		}
		return flag;
	}

	/**
	 * 读取卡内余额
	 * 
	 * @return (返回结果 可先转成16进制 再转成10进制即可得到10进制的以分为单位的金额)
	 * @throws Exception
	 */
	public synchronized byte[] readBalance() throws Exception {
		byte[] balanceByte = null;
		String result = null;
		try {
			byte queryMoneySend[] = { (byte) 0x80, 0x5c, 0x00, 0x02, 0x04 };
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("读取卡内余额文件0002,发送指令:" + BaseUtil.bytesToHexString(queryMoneySend));
			}
			byte queryMoneyRecv[] = dev.transceive(queryMoneySend);
			result = BaseUtil.bytesToHexString(queryMoneyRecv);
			if (syslog) {
				Log.e(TAG, "读取卡内余额文件0002,返回:" + result);
			}
			if (!isSuccess(queryMoneyRecv)) {
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("读取卡内余额文件0002,Error Code=" + result);
				}
				throw new Exception("读取卡内余额文件失败,Error Code=" + result);
			} else {
				this.recvbuf0002 = queryMoneyRecv;
				balanceByte = Arrays.copyOf(queryMoneyRecv, queryMoneyRecv.length - 2);
			}
		} catch (Exception e) {
			throw new Exception("读取卡内余额文件失败,Error Code=" + result);
		}
		return balanceByte;
	}

	/**
	 * 读取所有的记录文件 TODO
	 * TODO
	 * @Title:readAllTradeRecords
	 * @param count 
	 * @throws Exception 
	 *
	 * @author weiliu
	 */
	public synchronized void readAllTradeRecords(int count) throws Exception {
		try {
			StringBuffer buffer = new StringBuffer();
			for (int i = 1; i < count; i++) {
				byte tradeRecordRecv[] = readTradeRecord((byte) i);
				if (tradeRecordRecv != null) {
					buffer.append(BaseUtil.bytesToHexString(tradeRecordRecv));
					buffer.append(file0018Splite);
					//System.out.println("卡内记录文件:"+buffer.toString());
					if (!isTradeRecord(tradeRecordRecv)) {
						break;
					} else {
						praseTradeRecord(tradeRecordRecv);
					}
				} else {
					break;
				}
			}
			if (buffer != null) {
				this.recvbuf0018 = buffer.toString();
				buffer = null;
			}
		} catch (Exception e) {
			throw new Exception("读取卡内交易明细文件失败" + e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized byte[] readTradeRecord(byte filerecord) throws Exception {
		byte[] balanceByte = null;
		String result = null;
		try {
			byte sendbuf[] = { 0x00, (byte) 0xb2, filerecord, (byte) 0xc4, 0x00 };
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("读取卡内交易明细文件,发送指令:" + BaseUtil.bytesToHexString(sendbuf));
			}
			byte sReply[] = dev.transceive(sendbuf);
			result = BaseUtil.bytesToHexString(sReply);
			if (syslog) {
				Log.e(TAG, "读取卡内交易明细文件,返回:" + result);
			}
			if (!isSuccess(sReply) && !result.equalsIgnoreCase("6a83")) {
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("读取卡内交易明细文件,Error Code=" + result);
				}
				throw new Exception("读取卡内交易明细文件失败,Error Code=" + result);
			} else {
				if (result.equalsIgnoreCase("6a83")) {// 不存在记录号文件
					balanceByte = null;
				} else {
					balanceByte = sReply;
				}
			}
		} catch (Exception e) {
			throw new Exception("读取卡内交易明细文件失败,Error Code=" + result);
		}
		return balanceByte;
	}

	/**
	 * 校验个人识别码
	 * 
	 * @param pin
	 * @return (-1-失败 ,0-成功 ,-2-卡已经锁定,1表示还可以场次1次)
	 * @throws Exception
	 */
	public int verityPIN(byte[] pin) throws Exception {
		int flag = PIN_OK;
		try {
			// 校验个人识别码
			byte[] sendbuf=BaseUtil.hexStringToBytes(CardCommond.verityPIN(pin));
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("校验个人识别码:" + BaseUtil.bytesToHexString(sendbuf));
			}
			byte sReply[] = dev.transceive(sendbuf);
			String result = BaseUtil.bytesToHexString(sReply);
			if (syslog) {
				Log.e(TAG, "校验个人识别码返回:" + result);
			}
			if (!isSuccess(sReply)) {// 失败
				flag = PIN_FAIL;
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("校验个人识别码返回:" + result);
				}
				if (result.equalsIgnoreCase("6983")) {
					flag = PIN_LOCK;
				} else {
					if (sReply != null && sReply[sReply.length - 2] == (byte) 0x63 && sReply[sReply.length - 1] == 0xc1) {// 这里出于安全考虑，不用c0
						flag = PIN_FAIL;
					} else if (sReply != null && sReply[sReply.length - 2] == (byte) 0x63 && sReply[sReply.length - 1] == 0xc2) {
						flag = 1;
					} else if (sReply != null && sReply[sReply.length - 2] == (byte) 0x63 && sReply[sReply.length - 1] == 0xc3) {
						flag = 2;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("校验个人识别码失败");
		}
		return flag;
	}

	/**
	 * 初始化圈存
	 * 
	 * @param psamNo
	 *            终端机编号
	 * @param payFee
	 *            金额
	 * @return
	 * @throws Exception
	 */
	public byte[] initRecharge(byte[] psamNo, int payFee) throws Exception {
		byte sReply[] = null;
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
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("初始化圈存:" + BaseUtil.bytesToHexString(sendbuf));
				}
				sReply = dev.transceive(sendbuf);// 初始化圈存
				result = BaseUtil.bytesToHexString(sReply);
				if (oplog) {
					Log.e(TAG, "初始化圈存返回:" + result);
				}
				if (!isSuccess(sReply)) {// 执行失败
					if (oplog) {
						FileTxtLog.logInstance().addCardLog("初始化圈存失败,Error Code=" + result);
					}
					throw new Exception("对卡进行初始化圈存失败,Error Code=" + result);
				} else {
					EDRechargeSerial = new byte[] { sReply[4], sReply[5] };
					RechargeRandomNum = new byte[] { sReply[8], sReply[9], sReply[10], sReply[11] };
					RechargeMAC1 = new byte[] { sReply[11], sReply[12], sReply[13], sReply[14] };
				}
			}
		} catch (Exception e) {
			throw new Exception("对卡进行初始化圈存失败");
		}
		return sReply;
	}

	/**
	 * 对卡进行圈存操作
	 * 
	 * @param mac2
	 * @param PayTime
	 * @return
	 * @throws Exception
	 */
	public byte[] tradeRecharge(byte[] mac2, Date PayTime) throws Exception {
		byte sReply[] = null;
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
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("对卡进行圈存:" + BaseUtil.bytesToHexString(sendbuf));
				}
				sReply = dev.transceive(sendbuf);// 充值
				result = BaseUtil.bytesToHexString(sReply);
				if (oplog) {
					Log.e(TAG, "初始化圈存返回:" + result);
				}
				if (!isSuccess(sReply)) {// 执行失败
					if (oplog) {
						FileTxtLog.logInstance().addCardLog("对卡进行圈存失败,Error Code=" + result);
					}
					throw new Exception("对卡进行圈存失败,Error Code=" + result);
				}
			}
		} catch (Exception e) {
			throw new Exception("对卡进行圈存失败");
		}
		return sReply;
	}

	/**
	 * 初始化消费
	 * 
	 * @param psamNo
	 * @param payFee
	 * @return
	 * @throws Exception
	 */
	public byte[] initTradePay(byte[] psamNo, int payFee) throws Exception {
		byte sReply[] = null;
		String result = null;
		try {
			if (psamNo != null && psamNo.length == 6) {
				byte payFeeBuf[] = new byte[4];
				BaseLib.intToByteArray(payFee, payFeeBuf, 0, true);
				byte sendbuf[] = new byte[6 + 4 + psamNo.length];
				System.arraycopy(new byte[] { (byte) 0x80, 0x50, 0x01, 0x02, 0x0B, 0x01 }, 0, sendbuf, 0, 6);// 前5个字节
																												// 放指令+1个字节的密钥索引号
				System.arraycopy(payFeeBuf, 0, sendbuf, 6, 4);// 接着4个字节放消费金额
				System.arraycopy(psamNo, 0, sendbuf, 10, psamNo.length);// 接着放6个字节的终端号
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("消费初始化:" + BaseUtil.bytesToHexString(sendbuf));
				}
				sReply = dev.transceive(sendbuf);// 消费交易初始化
				result = BaseUtil.bytesToHexString(sReply);
				if (oplog) {
					Log.e(TAG, "消费初始化返回:" + result);
				}
				if (!isSuccess(sReply) || sReply.length < 15 + 2) {
					Log.e("cardpay", "初始化消费失败");
					PayRandomNum = null;
					EDPaySerial = null;
					PaySerial = -1;
					if (oplog) {
						FileTxtLog.logInstance().addCardLog("对卡进行初始化消费失败,Error Code=" + result);
					}
					throw new Exception("对卡进行初始化消费失败,Error Code=" + result);
				} else {
					EDPaySerial = new byte[] { sReply[4], sReply[5] };
					PayRandomNum = new byte[] { sReply[11], sReply[12], sReply[13], sReply[14] };
					PaySerial = BaseLib.byteArrayToShort(sReply, 4, true) + 1;
				}
			}
		} catch (Exception e) {
			throw new Exception("对卡进行初始化消费失败,Error Code=" + result);
		}
		return sReply;
	}

	/**
	 * 初始化复合消费
	 * 
	 * @param psamNo
	 * @param payFee
	 * @return 随机数和交易序号
	 * @throws Exception
	 */
	public byte[] initCAPPTradePay(byte[] psamNo, int payFee) throws Exception {
		byte sReply[] = null;
		String result = null;
		try {
			if (psamNo != null && psamNo.length == 6) {
				byte payFeeBuf[] = new byte[4];
				BaseLib.intToByteArray(payFee, payFeeBuf, 0, true);
				byte sendbuf[] = new byte[6 + 4 + psamNo.length];
				System.arraycopy(new byte[] { (byte) 0x80, 0x50, 0x03, 0x02, 0x0B, 0x01 }, 0, sendbuf, 0, 6);// 前5个字节
																												// 放指令+1个字节的密钥索引号
				System.arraycopy(payFeeBuf, 0, sendbuf, 6, 4);// 接着4个字节放消费金额
				System.arraycopy(psamNo, 0, sendbuf, 10, psamNo.length);// 接着放6个字节的终端号
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("复合消费初始化:" + BaseUtil.bytesToHexString(sendbuf));
				}
				sReply = dev.transceive(sendbuf);// 消费交易初始化
				result = BaseUtil.bytesToHexString(sReply);
				if (oplog) {
					Log.e(TAG, "复合消费初始化返回:" + result);
				}
				if (!isSuccess(sReply) || sReply.length < 15 + 2) {
					Log.e("cardpay", "初始化复合消费失败");
					PayRandomNum = null;
					EDPaySerial = null;
					PaySerial = -1;
					if (oplog) {
						FileTxtLog.logInstance().addCardLog("对卡进行初始化复合消费失败,Error Code=" + result);
					}
					throw new Exception("对卡进行初始化复合消费失败,Error Code=" + result);
				} else {
					EDPaySerial = new byte[] { sReply[4], sReply[5] };
					PayRandomNum = new byte[] { sReply[11], sReply[12], sReply[13], sReply[14] };
					PaySerial = BaseLib.byteArrayToShort(sReply, 4, true) + 1;
				}
			}
		} catch (Exception e) {
			throw new Exception("对卡进行初始化复合消费失败,Error Code=" + result);
		}
		return sReply;
	}

	/**
	 * 更新复合消费缓存 0019文件 第一块
	 * 
	 * @param pdata
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public boolean updateCAPPCacheBlock1(byte[] pdata, byte size) throws Exception {
		boolean flag = true;
		String result = null;
		try {
			if ((pdata[0] & 0xFF) != 0xAA || (pdata[1] & 0xFF) != 0x29 || (pdata[2] & 0xFF) != 0x00) {
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("写0019文件时，文件内容非法，前3字节不是: AA 29 00");
					Log.e(TAG, "写0019文件时，文件内容非法，前3字节不是: AA 29 00");
				}
				return false;
			}
			byte[] commandBytes = new byte[] { (byte) 0x80, (byte) 0xdc, (byte) 0xaa, (byte) 0xC8, size };
			byte sendbuf[] = new byte[commandBytes.length + size];
			System.arraycopy(commandBytes, 0, sendbuf, 0, commandBytes.length);// 先放前5个字节命令
			System.arraycopy(pdata, 0, sendbuf, commandBytes.length, sendbuf.length - commandBytes.length);// 接着放数据记录文件
			byte[] read0019Recv = dev.transceive(sendbuf);
			result = BaseUtil.bytesToHexString(read0019Recv);
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("更新复合消费缓存:" + BaseUtil.bytesToHexString(sendbuf));
			}
			if (oplog) {
				Log.e(TAG, "更新0019文件内容=" + BaseUtil.bytesToHexString(sendbuf));
			}
			if (!isSuccess(read0019Recv)) {
				Log.e("cardwrite", "写0019文件失败,code=" + result);
				flag = false;
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("对卡进行更新复合消费缓存失败,Error Code=" + result);
				}
			}
		} catch (Exception e) {
			throw new Exception("对卡进行更新复合消费缓存失败,Error Code=" + result);
		}
		return flag;
	}

	/**
	 * 更新复合消费缓存 0019文件 第二块
	 * 
	 * @param pdata
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public boolean updateCAPPCacheBlock2(byte[] pdata, byte size) throws Exception {
		boolean flag = true;
		String result = null;
		try {
			if ((pdata[0] & 0xFF) != 0xAA && (pdata[0] & 0xFF) != 0x44) {
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("写0019文件时，文件内容非法，首字节不是 AA 或 44");
					Log.e(TAG, "写0019文件时，文件内容非法，首字节不是 AA 或 44");
				}
				return false;
			}
			pdata[1] = (byte) ((size - 2) & 0xFF);
			pdata[0] = 0x00;
			byte[] commandBytes = new byte[] { (byte) 0x80, (byte) 0xdc, (byte) 0xFF, (byte) 0xC8, size };
			byte sendbuf[] = new byte[commandBytes.length + size];
			System.arraycopy(commandBytes, 0, sendbuf, 0, commandBytes.length);// 先放前5个字节命令
			System.arraycopy(pdata, 0, sendbuf, commandBytes.length, sendbuf.length - commandBytes.length);// 接着放数据记录文件
			byte[] read0019Recv = dev.transceive(sendbuf);
			result = BaseUtil.bytesToHexString(read0019Recv);
			if (oplog) {
				FileTxtLog.logInstance().addCardLog("更新复合消费缓存:" + BaseUtil.bytesToHexString(sendbuf));
			}
			if (oplog) {
				Log.e(TAG, "更新0019文件内容=" + BaseUtil.bytesToHexString(sendbuf));
			}
			if (!isSuccess(read0019Recv)) {
				Log.e("cardwrite", "写0019文件失败,code=" + result);
				flag = false;
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("对卡进行更新复合消费缓存失败,Error Code=" + result);
				}
			}
		} catch (Exception e) {
			throw new Exception("对卡进行更新复合消费缓存失败,Error Code=" + result);
		}
		return flag;
	}

	/**
	 * 对卡进行复合消费操作
	 * 
	 * @param zdReply
	 *            终端交易号
	 * @param macReply
	 *            MAC1码
	 * @param PayTime
	 *            消费日期时间
	 * @return
	 * @throws Exception
	 */
	public boolean doCAPPPay(byte[] zdReply, byte[] macReply, Date PayTime) throws Exception {
		boolean flag = true;
		String result = null;
		try {
			if (macReply != null && zdReply != null && macReply.length == 4 && zdReply.length == 4) {
				byte[] PayTimeBuf = BaseLib.hexToBin(BaseLib.dateToStr(PayTime, "yyyyMMddHHmmss"));
				ZdPayserial = BaseLib.byteArrayToInt(macReply, 0, true);
				byte[] sendbuf = new byte[] { (byte) 0x80, 0x54, 0x01, 0x00, 0x0F,// 指令命令
						zdReply[0], zdReply[1], zdReply[2], zdReply[3], // 终端交易序号
																		// 4个字节
						PayTimeBuf[0], PayTimeBuf[1], PayTimeBuf[2], PayTimeBuf[3],// 交易日期
																					// 4个字节
						PayTimeBuf[4], PayTimeBuf[5], PayTimeBuf[6],// 交易时间 3个字节
						macReply[0], macReply[1], macReply[2], macReply[3],// MAC1
																			// 4个字节
						0x08 };// 后一个字节放08
				if (oplog) {
					FileTxtLog.logInstance().addCardLog("复合消费:" + BaseUtil.bytesToHexString(sendbuf));
				}
				byte[] sReply = dev.transceive(sendbuf);// 扣费
				result = BaseUtil.bytesToHexString(sReply);
				if (oplog) {
					Log.e(TAG, "对卡进行复合消费操作返回" + BaseUtil.bytesToHexString(sendbuf));
				}
				if (!isSuccess(sReply) || sReply.length < 8 + 2) {
					Log.e("cardpay", "消费交易失败");
					flag = false;
					Tac = -1;
					if (oplog) {
						FileTxtLog.logInstance().addCardLog("对卡进行复合消费操作交易失败,Error Code=" + result);
					}
					throw new Exception("对卡进行复合消费操作交易失败,Error Code=" + result);
				} else {
					MAC2 = new byte[] { sReply[4], sReply[5], sReply[6], sReply[7] };
					Tac = BaseLib.byteArrayToInt(sReply, 0, true);
				}
			} else {
				flag = false;
			}
		} catch (Exception e) {
			throw new Exception("对卡进行复合消费操作交易失败,Error Code=" + result);
		}
		return flag;
	}

	/**
	 * 解析交易记录 TODO
	 * 
	 * @Title:readTradeRecord
	 * @param buf
	 * 
	 * @author weiliu
	 * @throws Exception 
	 */
	public void praseTradeRecord(byte[] buf) throws Exception {
		try {
			String s = BaseUtil.bytesToHexString(buf);
			TradeRecord tradeRecord = new TradeRecord();
			tradeRecord.tradeNo = BaseUtil.strToInt(s.substring(0, 4), 0);

			tradeRecord.overAmount = BaseUtil.hexToTen(s.substring(4, 10));
			tradeRecord.tradeAmount = BaseUtil.hexToTen(s.substring(10, 18));
			tradeRecord.tradeType = BaseUtil.strToInt(s.substring(18, 20), 0);
			tradeRecord.terminalNo = BaseUtil.strToInt(s.substring(20, 32), 0);
			try {
				if (!s.substring(32, 46).equalsIgnoreCase("fffffffffffff")) {
					tradeRecord.tradeDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(s.substring(32, 46));
				}
			} catch (Exception e) {
				tradeRecord.tradeDate=null;
			}
			
			if (tRLists != null) {
				tRLists.add(tradeRecord);
			}
		} catch (Exception e) {
			//throw new Exception("解析卡交易记录失败");
		}
	}

	/**
	 * 是否操作成功
	 * 
	 * @param sReply
	 * @return
	 */
	public boolean isSuccess(byte[] sReply) {
		if (sReply == null || sReply[sReply.length - 2] != (byte) 0x90 || sReply[sReply.length - 1] != 0x00) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是交易记录文件 TODO
	 * 
	 * @Title:isTradeRecord
	 * @param sReply
	 * @return
	 * 
	 * @author weiliu
	 */
	private boolean isTradeRecord(byte[] sReply) {
		if (sReply==null||sReply.length <= 2) {
			return false;
		} else {
			String s = BaseUtil.bytesToHexString(sReply);// 判断空记录
			if (s.equalsIgnoreCase("ffffffffffffffffffffffffffffffffffffffffffffff9000")) {
				return false;
			}
		}
		return true;
	}

	public byte[] getRecvbuf1001() {
		return recvbuf1001;
	}

	public void setRecvbuf1001(byte[] recvbuf1001) {
		this.recvbuf1001 = recvbuf1001;
	}

	public byte[] getRecvbufmf00() {
		return recvbufmf00;
	}

	public void setRecvbufmf00(byte[] recvbufmf00) {
		this.recvbufmf00 = recvbufmf00;
	}

	public void setRecvbuf0018(String recvbuf0018) {
		this.recvbuf0018 = recvbuf0018;
	}

	public byte[] getRecvbuf0002() {
		return recvbuf0002;
	}

	public void setRecvbuf0002(byte[] recvbuf0002) {
		this.recvbuf0002 = recvbuf0002;
	}

	public byte[] getRecvbuf0012() {
		return recvbuf0012;
	}

	public void setRecvbuf0012(byte[] recvbuf0012) {
		this.recvbuf0012 = recvbuf0012;
	}

	public byte[] getRecvbuf0016() {
		return recvbuf0016;
	}

	public void setRecvbuf0016(byte[] recvbuf0016) {
		this.recvbuf0016 = recvbuf0016;
	}

	public byte[] getRecvbuf0015() {
		return recvbuf0015;
	}

	public void setRecvbuf0015(byte[] recvbuf0015) {
		this.recvbuf0015 = recvbuf0015;
	}

	public byte[] getRecvbuf0019() {
		return recvbuf0019;
	}

	public void setRecvbuf0019(byte[] recvbuf0019) {
		this.recvbuf0019 = recvbuf0019;
	}

	public byte[] getRecvbuf_filePrivate() {
		return recvbuf_filePrivate;
	}

	public void setRecvbuf_filePrivate(byte[] recvbuf_filePrivate) {
		this.recvbuf_filePrivate = recvbuf_filePrivate;
	}

	public byte[] getRecvbuf0009() {
		return recvbuf0009;
	}

	public void setRecvbuf0009(byte[] recvbuf0009) {
		this.recvbuf0009 = recvbuf0009;
	}

	public byte[] getRecvbuf0008() {
		return recvbuf0008;
	}

	public void setRecvbuf0008(byte[] recvbuf0008) {
		this.recvbuf0008 = recvbuf0008;
	}

	/**
	 * 储值卡交易序列号
	 * 
	 * @return
	 */
	public int getPaySerial() {
		return PaySerial;
	}

	/**
	 * 储值卡终端交易序号
	 * 
	 * @return
	 */
	public int getZdPayserial() {
		return ZdPayserial;
	}

	/**
	 * Tac码
	 * 
	 * @return
	 */
	public int getTac() {
		return Tac;
	}

	/**
	 * ED脱机交易序号或EP脱机交易序号 2字节
	 * 
	 * @return
	 */
	public byte[] getEDPaySerial() {
		return EDPaySerial;
	}

	/**
	 * 卡消费产生的MAC2码
	 * 
	 * @return
	 */
	public byte[] getMAC2() {
		return MAC2;
	}

	/**
	 * 消费伪随机数 4个字节
	 * 
	 * @return
	 */
	public byte[] getPayRandomNum() {
		return PayRandomNum;
	}

	/**
	 * 充值联机交易序列号
	 * 
	 * @return
	 */
	public byte[] getEDRechargeSerial() {
		return EDRechargeSerial;
	}

	/**
	 * 充值伪随机数
	 * 
	 * @return
	 */
	public byte[] getRechargeRandomNum() {
		return RechargeRandomNum;
	}

	/**
	 * 充值MAC1
	 * 
	 * @return
	 */
	public byte[] getRechargeMAC1() {
		return RechargeMAC1;
	}

	/**
	 * 交易记录文件 TODO
	 * 
	 * @Title:gettRLists
	 * @return
	 * 
	 * @author weiliu
	 */
	public List<TradeRecord> gettRLists() {
		return tRLists;
	}

	/**
	 * 0018交易记录文件(50条内容) TODO
	 * 
	 * @Title:getRecvbuf0018
	 * @return
	 * 
	 * @author weiliu
	 */
	public String getRecvbuf0018() {
		return recvbuf0018;
	}
}
