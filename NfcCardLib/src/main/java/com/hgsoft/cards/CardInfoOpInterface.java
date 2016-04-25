package com.hgsoft.cards;

public interface CardInfoOpInterface {

	/**
	 * 解析0002文件
	 * 
	 * @param buf
	 */
	public void read0002(byte[] buf);

	/**
	 * 解析0008文件
	 * 
	 * @param buf
	 */
	public void read0008(byte[] buf);

	/**
	 * 解析0012文件
	 * 
	 * @param buf
	 */
	public void read0012(byte[] buf);

	/**
	 * 解析0016文件
	 * 
	 * @param buf
	 */
	public void read0016(byte[] buf);

	/**
	 * 解析0015文件
	 * 
	 * @param buf
	 */
	public void read0015(byte[] buf);

	/**
	 * 解析0019文件
	 * 
	 * @param buf
	 */
	public void read0019(byte[] buf);
}
