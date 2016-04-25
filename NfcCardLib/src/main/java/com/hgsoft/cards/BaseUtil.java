package com.hgsoft.cards;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class BaseUtil {
	
	
	/**
	 * byte[]转十六进制字符串
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            原数组
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	public static String bytesToHexString(byte[] src, int endInx) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < endInx; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 十六进制转十进制整形
	 * 
	 * @param hexString
	 * @return
	 */
	public static long hexToTen(String hexString) {
		return new BigInteger(hexString, 16).longValue();
	}

	/**
	 * int转十六进制的字符串
	 * 
	 * @param value
	 *            整形
	 * @param len
	 *            返回字符串的长度
	 * @return
	 */
	public static String intToHexString(int value, int len) {
		String str = "00000000000000000000000000000000";// 32位
		String hexString = Integer.toHexString(value);
		if (hexString.length() >= len) {
			hexString = hexString.substring(0, len);
		} else {
			hexString = str.substring(0, len - hexString.length()) + hexString;
		}
		return hexString;
	}

	/**
	 * 十六进制字符串转byte[]
	 * 
	 * @param hexString
	 *            十六进制数
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		if (hexString.length() % 2 == 1) {
			hexString = "0" + hexString;
		}
		hexString = hexString.toLowerCase();
		int length = (hexString.length() + 1) / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * char转byep
	 * 
	 * @param c
	 *            字符
	 * @return
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789abcdef".indexOf(c);
	}

	public static int byteArrayToInt(byte[] res, int offset,
			boolean netByteSequence) {
		if (netByteSequence) {// 网络字节顺序(高位在前)
			int targets = (res[offset + 3] & 0xff)
					| ((res[offset + 2] << 8) & 0xff00)
					| ((res[offset + 1] << 24) >>> 8) | (res[offset] << 24);
			return targets;
		} else {// 非网络字节顺序(低位在前)
			int targets = (res[offset] & 0xff)
					| ((res[offset + 1] << 8) & 0xff00)
					| ((res[offset + 2] << 24) >>> 8) | (res[offset + 3] << 24);
			return targets;
		}
	}

	public static String doubleToString(double src, int dotLen) {
		String s = String.valueOf(src);
		int inx = s.lastIndexOf(".");
		String zeros = "00000000000000000000";
		if (inx == -1) {
			return s + "." + zeros.substring(0, dotLen);
		} else if (s.length() - 1 - inx >= dotLen) {
			return s.substring(0, inx + dotLen + 1);
		} else {
			return s + zeros.substring(0, dotLen - (s.length() - 1 - inx));
		}
	}

	/**
	 * 字符串转int
	 * 
	 * @param s
	 *            字符串
	 * @param defValue
	 *            缺省值
	 * @return 转换结果
	 */
	public static int strToInt(String s, int defValue) {
		s = s.trim();
		if (s.length() == 0)
			return defValue;
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defValue;
		}
	}
	
	public static boolean isMoney(String moneyString) {
		boolean result = false;
		if (moneyString.equals("")) {
			return false;
		}
		double temp = 0;
		try {
			temp = Double.valueOf(moneyString);
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			result = false;
		}
		if (result) {
			moneyString = String.valueOf(temp);
			if (moneyString.indexOf(".") != -1 && moneyString.length() 
					- 1 - moneyString.indexOf(".") > 2) {
				result = false;
			}
		}
		return result;
	}
	
	public static String fenToYuanStr(String src) {
		StringBuffer temp = new StringBuffer("");
		if (src.length() <= 2) {
			temp.append(Double.valueOf(src) / 100.0);
		} else {
			temp.append(src.substring(0, src.length() - 2));
			temp.append(".");
			temp.append(src.substring(src.length() - 2, src.length()));
		}
		
		return temp.toString();
	}
	
	/**
	 * 转为unix时间
	 * @param hexStr 16进制的字符串
	 * @return
	 */
	public static Date String2UnixDate(String hexStr){
		long timePoke = hexToTen(hexStr) * 1000l;
		Log.i("test", "timePlke=" + timePoke);
		java.util.Date date = new java.util.Date(timePoke);
		return date;
	}
	
	
	/**
	 * 
	 * 将日期转化为yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String date2String(Date date){
		String dateStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateStr = sdf.format(date);
		return dateStr;
	}
	
	/**
	 * 
	 * 将日期转化为yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static Date string2Date(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 * 计算收费站号
	 * @param hexStr
	 * @return
	 */
	public static int calInStationNo(String hexStr){
		int result = 0;
		//前两个字节转为10进制
		String firstTenBody = hexToTen(hexStr.substring(0, 2)) + ""; 
		//后两个字节转为10进制
		int tenBody = (int)hexToTen(hexStr.substring(2, hexStr.length()));
		//后两个字节转为2进制
		String binBody = Integer.toBinaryString(tenBody);
		if (binBody.length() < 8) {//不足8位
			for (int i = 0; i < 8 - binBody.length(); i++) {
				binBody = "0" + binBody;
			}
		}
		
		//把2进制拆分
		String secondBinBody = "00000" + binBody.substring(0, 3);
		String thirdBinBody = "000" + binBody.substring(3,binBody.length());
		String secondTenBody = String.format("%02d", toTenFromBinaryString(secondBinBody));
		String thirdTenBody = String.format("%02d", toTenFromBinaryString(thirdBinBody));
		String tempResult = firstTenBody + secondTenBody + thirdTenBody;
		result = Integer.valueOf(tempResult);
		return result;
	}
	
	/**
	 * 将i转换为两位数：例如 1转为01,11转为11
	 * @param i
	 * @return
	 */
	public static String numberTo01Format(int i){
		return String.format("%02d", i);
	}
	
	/**
	 * 二进制转为十进制
	 * @param binary
	 * @return
	 */
	public static int toTenFromBinaryString(String binary){
		return  Integer.parseInt(Integer.valueOf(binary,2).toString());
	}
	
	
	/**
	 * 16�����ַ�(����ִ�Сд)ת��Ϊ����������
	 * @param hexString 16�����ַ�(����ִ�Сд)
	 * @return ����������
	 */
	static public byte[] hexToBin(String hexString) 
    {
        int hexStringLength = hexString.length();
        byte[] byteArray = null;
        int count = 0;
        char c;
        int i;

        for (i = 0; i < hexStringLength; i++) 
        {
            c = hexString.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f') 
            {
                count++;
            }
        }

        byteArray = new byte[(count + 1) / 2];
        boolean first = true;
        int len = 0;
        int value;
        for (i = 0; i < hexStringLength; i++) 
        {
            c = hexString.charAt(i);
            if (c >= '0' && c <= '9') 
            {
                value = c - '0';
            } 
            else if (c >= 'A' && c <= 'F') 
            {
                value = c - 'A' + 10;
            } 
            else if (c >= 'a' && c <= 'f') 
            {
                value = c - 'a' + 10;
            } 
            else 
            {
                value = -1;
            }

            if (value >= 0) 
            {
                if (first) 
                {
                    byteArray[len] = (byte) (value << 4);
                }
                else 
                {
                    byteArray[len] |= value;
                    len++;
                }

                first = !first;
            }
        }

        return byteArray;
    }
	
}
