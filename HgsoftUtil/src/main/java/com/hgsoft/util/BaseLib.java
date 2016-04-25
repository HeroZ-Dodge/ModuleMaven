package com.hgsoft.util;


import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * �����<br>
 * �ṩһЩ���õĹ�������
 * @author LiuWu
 *
 */
public class BaseLib 
{
	/**
	 * ���� boolean[][][] �������ֵ
	 * @param dest Ŀ������
	 * @param value ���õ�ֵ
	 */
	public static void reset(boolean[][][] dest,boolean value)
	{
		for(int index1=0;index1<dest.length;++index1)
		{
			for(int index2=0;index2<dest[index1].length;++index2)
			{
				//Arrays.fill(dest[index1][index2],value);
				for(int index3=0;index3<dest[index1][index2].length;++index3)
				{
					dest[index1][index2][index3]=value;
				}
			}
		}
	}
	
	/**
	 * 字节转成中文
	 * @param src
	 */
	public static String byteToGBKString(byte[] src)
	{
		try {
			return new String(src, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��¡ boolean[][][] ����
	 * @param src ����¡����
	 * @return ���ؿ�¡�������
	 */
	public static boolean[][][] clone(boolean[][][] src)
	{
		boolean[][][] result=new boolean[src.length][][];
		for(int index1=0;index1<src.length;++index1)
		{
			result[index1]=new boolean[src[index1].length][];
			for(int index2=0;index2<src[index1].length;++index2)
			{
				result[index1][index2]=new boolean[src[index1][index2].length];
				//System.arraycopy(src[index1][index2],0,result[index1][index2],0,src[index1][index2].length);
				for(int index3=0;index3<src[index1][index2].length;++index3)
				{
					result[index1][index2][index3]=src[index1][index2][index3];
				}
			}
		}
		return result;
	}
	
	/**
	 * ��¡ boolean[] ����
	 * @param src ����¡����
	 * @return ���ؿ�¡�������
	 */
	public static boolean[] clone(boolean[] src)
	{
		boolean[] result=new boolean[src.length];
		//System.arraycopy(src,0,result,0,src.length);
		for(int index1=0;index1<src.length;++index1)
		{
			result[index1]=src[index1];
			
		}
		return result;
	}
	
	/**
	 * �ַ�תint
	 * @param s �ַ�
	 * @param defValue ȱʡֵ
	 * @return ת�����
	 */
	public static int strToInt(String s,int defValue)
	{
		s=s.trim();
		if(s.length()==0)return defValue;
		try
		{
			return Integer.parseInt(s);
		}
		catch(Exception e)
		{
			return defValue;
		}
	}
	/**
	 * �ַ�תdouble
	 * @param s �ַ�
	 * @param defValue ȱʡֵ
	 * @return ת�����
	 */
	public static double strToDouble(String s,double defValue)
	{
		s=s.trim();
		if(s.length()==0)return 0;
		try
		{
			return Double.parseDouble(s);
		}
		catch(Exception e)
		{
			return defValue;
		}
	}
	/**
	 * intת�ַ�
	 * @param i ����
	 * @return ת�����
	 */
	public static String intToStr(int i)
	{
		return String.format(Locale.US,"%d",i);
	}
	/**
	 * Dateת(yyyy-MM-dd HH:mm:ss)��ʽ�ַ�
	 * @param datetime ��������
	 * @return ת�����
	 */
	public static String dateToStr(Date datetime)
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).format(datetime);
	}
	/**
	 * Dateת�ַ�
	 * @param datetime ��������
	 * @param format ת����ʽ
	 * @return ת�����
	 */
	public static String dateToStr(Date datetime,String format)
	{
		return new SimpleDateFormat(format,Locale.CHINA).format(datetime);
	}
	
	/**
	 * (yyyy-MM-dd HH:mm:ss)��ʽ�ַ�תDate
	 * @param str �ַ�
	 * @return ת�����
	 */
	public static Date strToDate(String str)
	{
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA).parse(str);
		}
		catch(Exception e)
		{
			return new Date();
		}
	}
	/**
	 * Dateת(yyyy-MM-dd)��ʽ�ַ�
	 * @param datetime ��������
	 * @return ת�����
	 */
	public static String dayToStr(Date datetime)
	{
		return new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(datetime);
	}
	
	/**
	 * ����ת�����ֽ�����
	 * @param res ����
	 * @param dest �ֽ�����
	 * @param offset �ֽ�����ƫ��
	 * @param netByteSequence �Ƿ������ֽ�˳��(��λ��ǰ)
	 */
	public static void intToByteArray(int res,byte[] dest,int offset,boolean netByteSequence) 
	{
		if(netByteSequence)
		{//�����ֽ�˳��(��λ��ǰ)
			dest[offset+3]   = (byte) (res & 0xff);// ���λ 
			dest[offset+2] = (byte) ((res >> 8) & 0xff);// �ε�λ 
			dest[offset+1] = (byte) ((res >> 16) & 0xff);// �θ�λ 
			dest[offset] = (byte) (res >>> 24);// ���λ,�޷�����ơ�
		}
		else
		{//�������ֽ�˳��(��λ��ǰ)
			dest[offset]   = (byte) (res & 0xff);// ���λ 
			dest[offset+1] = (byte) ((res >> 8) & 0xff);// �ε�λ 
			dest[offset+2] = (byte) ((res >> 16) & 0xff);// �θ�λ 
			dest[offset+3] = (byte) (res >>> 24);// ���λ,�޷�����ơ�
		}
	}
	
	/**
	 * �ֽ�����ת��Ϊ����
	 * @param res �ֽ�����
	 * @param offset �ֽ�����ƫ��
	 * @param netByteSequence �Ƿ������ֽ�˳��(��λ��ǰ)
	 * @return
	 */
	public static int byteArrayToInt(byte[] res,int offset,boolean netByteSequence) 
	{
		if(netByteSequence)
		{//�����ֽ�˳��(��λ��ǰ)
			int targets = (res[offset+3] & 0xff) | ((res[offset+2] << 8) & 0xff00) 
					| ((res[offset+1] << 24) >>> 8) | (res[offset] << 24); 
			return targets;
		}
		else
		{//�������ֽ�˳��(��λ��ǰ)
			int targets = (res[offset] & 0xff) | ((res[offset+1] << 8) & 0xff00) 
					| ((res[offset+2] << 24) >>> 8) | (res[offset+3] << 24); 
			return targets;
		}
	}
	
	/**
	 * �ֽ�����ת��Ϊ����
	 * @param res �ֽ�����
	 * @param offset �ֽ�����ƫ��
	 * @param netByteSequence �Ƿ������ֽ�˳��(��λ��ǰ)
	 * @return
	 */
	public static int byteArrayToShort(byte[] res,int offset,boolean netByteSequence) 
	{
		if(netByteSequence)
		{//�����ֽ�˳��(��λ��ǰ)
			int targets = (res[offset+1] & 0xff) | ((res[offset] << 8) & 0xff00) ; 
			return targets;
		}
		else
		{//�������ֽ�˳��(��λ��ǰ)
			int targets = (res[offset] & 0xff) | ((res[offset+1] << 8) & 0xff00) ; 
			return targets;
		}
	}
	
	/**
	 * �Ƿ�Ϊ�����ַ�
	 * @param a �ַ�
	 * @return �Ƿ�Ϊ�����ַ� 
	 */
	public static boolean isChinese(char a) 
	{   
		int v = a;   
		return (v >=19968 && v <= 171941);
	}

	/**
	 * ��ݷָ��ַ��ַ�ָ���ַ�����
	 * @param src ���ָ��ַ�
	 * @param split_char �ָ��ַ�
	 * @return �ָ�ɵ��ַ�����
	 */
	public static Vector<String> toWord(String src,char split_char)
    {
		Vector<String> res=new Vector<String>();
		StringBuffer lastStr=new StringBuffer(0);
		for(int i=0;i<src.length();i++)
    	{
    		char c=src.charAt(i);
    		if(split_char=='\n'&&c=='\r')continue;
    		if(c==split_char)
    		{
    			if(lastStr.length()>0)res.add(lastStr.toString());
    			else res.add("");
    			lastStr.setLength(0);
    		}
    		else 
    		{
    			lastStr.append(c);
    		}
    	}
    	if(lastStr.length()>0)res.add(lastStr.toString());
		else res.add("");
    	return res;
    }
	
	/**
	 * ���ַ������в���ĳ�ַ�
	 * @param strArray �ַ����� 
	 * @param str �����ַ�
	 * @return ���ҵ����ַ������������û���ҵ��򷵻�-1
	 */
	public static int find(final String[] strArray,final String str)
	{
		for(int i=0;i<strArray.length;++i)
		{
			if(str.equals(strArray[i]))return i;
		}
		return -1;
	}
    
	/**
	 * ���ĳĿ¼(������Ŀ¼)���ļ�����������ʽ���ļ�����
	 * @param dir Ŀ¼
	 * @param regularMask ������ʽ
	 * @return �ļ�����������ʽ���ļ���
	 */
	public static int getFileCount(String dir,String regularMask)
	{
		int count=0;
		
		File file = new File(dir);    
		File[] files = file.listFiles();
        if(files==null)return count;
		Pattern p = Pattern.compile(regularMask.toLowerCase(Locale.US));
		for(int j=0;j<files.length;++j)
        {
        	if(files[j].isDirectory())
            {
            }
            else
            {
            	Matcher fMatcher = p.matcher(files[j].getName().toLowerCase(Locale.US));    
				if (fMatcher.matches()) 
				{
					++count; 
				}
            }
        }
		return count;
	}
	
	/**
	 * ���ĳĿ¼(������Ŀ¼)���ļ�����������ʽ������һ���ļ����ļ���
	 * @param dir Ŀ¼
	 * @param regularMask ������ʽ
	 * @return �ļ�����������ʽ������һ���ļ����ļ���
	 */
	public static String getFileName(String dir,String regularMask)
	{
		File file = new File(dir);    
		File[] files = file.listFiles();
        if(files==null)return "";
		Pattern p = Pattern.compile(regularMask.toLowerCase(Locale.US));
		for(int j=0;j<files.length;++j)
        {
        	if(files[j].isDirectory())
            {
            }
            else
            {
            	Matcher fMatcher = p.matcher(files[j].getName().toLowerCase(Locale.US));    
				if (fMatcher.matches()) 
				{
					return files[j].getName(); 
				}
            }
        }
		return "";
	}
	
	private static boolean deleteFiles(File file,Pattern p,boolean includeSubDirFile,boolean delDir,boolean delBefore,long timeBefore)
	{
		boolean bIsBlank=true;
		//int count=0;
		if (file.exists())// �ж��ļ��Ƿ���� 
		{
			if (file.isFile())// �ж��Ƿ����ļ� 
			{
				if(delBefore)
				{
					if(file.lastModified()>timeBefore)
					{
						Matcher fMatcher = p.matcher(file.getName().toLowerCase(Locale.US));    
						if (fMatcher.matches()) 
						{
							file.delete();
							//++count;
						}
						else bIsBlank=false;
					}
					else bIsBlank=false;
				}
				else
				{
					Matcher fMatcher = p.matcher(file.getName().toLowerCase(Locale.US));    
					if (fMatcher.matches()) 
					{
						file.delete();
						//++count;
					}
					else bIsBlank=false;
				}
			} 
			else if (file.isDirectory())// �����������һ��Ŀ¼
			{
				if(includeSubDirFile)
				{
					File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
					for (int i = 0; i < files.length; i++) 
					{ 
						// ����Ŀ¼�����е��ļ�
						//count=count+
						bIsBlank=bIsBlank&&deleteFiles(files[i],p,includeSubDirFile,delDir,delBefore,timeBefore); // ��ÿ���ļ� ������������е��
					}
				}
				else bIsBlank=false;
				
				if(delDir&&bIsBlank)
				{
					file.delete();
					//++count;
				}
			}
		}
		//return count;
		return bIsBlank;
	}
	
	/**
	 * ɾ��ĳĿ¼���ļ�����������ʽ���ļ�
	 * @param dir Ŀ¼
	 * @param regularMask ������ʽ
	 * @param includeSubDirFile �Ƿ����Ŀ¼
	 * @param delDir �����Ŀ¼Ϊ�գ��Ƿ�ͬʱɾ����Ŀ¼
	 * @param delBefore �Ƿ�ֻɾ���޸�ʱ����ĳʱ��֮ǰ���ļ�
	 * @param timeBefore �޸�ʱ��
	 */
	public static void deleteFiles(String dir,String regularMask,boolean includeSubDirFile,boolean delDir,boolean delBefore,long timeBefore)
	{
		Pattern p = Pattern.compile(regularMask.toLowerCase(Locale.US));
		File file = new File(dir);
		deleteFiles(file,p,includeSubDirFile,delDir,delBefore,timeBefore);
	}
	
	/**
	 * Vector<String>תString[]
	 * @param r Vector<String>
	 * @return ��Ϊnull��String[]
	 */
	public static String[] toArray(Vector<String> r)
    {
		if(r==null)return new String[0];
    	String[] res=new String[r.size()];
    	for(int i=0;i<r.size();++i)
    	{
    		res[i]=r.get(i);
    	}
		return res;
    }
	
	/**
	 * д��־�ļ�,��־�ļ���XXXXyyyymmdd.txt����־����HH:mm:ss.zzz XXXXXXXXX
	 * @param fileName ��־�ļ���(��Ŀ¼)
	 * @param info ��־��Ϣ
	 */
	public static void writeLog(String fileName,String info)
	{
		Date dt=new Date();
		fileName=fileName+dateToStr(dt,"yyyyMMdd")+".txt";
		info=dateToStr(dt,"HH:mm:ss.")+String.format("%03d ",dt.getTime()%1000)+info+"\r\n";;
		writeFile(fileName,info,true,"gbk");
	}
	
	/**
	 * д�ļ�
	 * @param fileName �ļ���
	 * @param info д������
	 * @param append �Ƿ����
	 * @param encode ����
	 * @return �Ƿ�ɹ�
	 */
	public static boolean writeFile(String fileName, String info,boolean append,String encode) 
	{
		try 
		{
			FileOutputStream fout = new FileOutputStream(fileName,append);
			byte[] bytes = info.getBytes(encode);
			fout.write(bytes);
			fout.close();
			return true;
		} 
		catch (Exception err) 
		{
		}
		return false;
	}
	
	/**
	 * ��֤�����Ե�д�ļ�(�����ļ��Ѵ��ڣ��򸲸�)<br>
	 * �����Ի����ǣ���д����ʱ�ļ�(filename.temp)���ٸ���
	 * @param fileName �ļ���
	 * @param info д������
	 * @param encode ����
	 * @return �Ƿ�ɹ�
	 */
	public static boolean safeWriteFile(String fileName, String info,String encode) 
	{
		if(!writeFile(fileName+".temp",info,false,encode))return false;
		return moveFile(fileName+".temp",fileName,"");
	}
	
	/**
	 * ���ļ�
	 * @param fileName �ļ���
	 * @return �ļ�����
	 */
	public static byte [] readFile(String fileName) 
	{
		try 
		{
			File file = new File(fileName);   
			if(!file.exists())return null;
			if(file.isDirectory())return null;
			
	        FileInputStream fis = new FileInputStream(file);
	        int length = fis.available();
	        byte [] buffer = new byte[length];   
	        fis.read(buffer);
	        fis.close();
	        return buffer;
		} 
		catch (Exception err) 
		{
			return null;
		}
	}
	
	/**
	 * ���ļ�
	 * @param fileName �ļ���
	 * @param encode ����
	 * @return �ļ�����
	 */
	public static String readFile(String fileName,String encode) 
	{
		byte [] buffer = readFile(fileName);
		if(buffer==null||buffer.length==0)return "";
	    return EncodingUtils.getString(buffer, encode);   
	}
	
	/**
	 * �ļ�����
	 * @param from Դ�ļ�
	 * @param to Ŀ���ļ�
	 * @return �����Ƿ�ɹ�
	 */
	public static boolean copyFile(InputStream from,OutputStream to)
	{
		try 
		{
			byte bt[] = new byte[1024];
			int c;
			while ((c = from.read(bt)) > 0) 
			{
				to.write(bt, 0, c);
			}
			from.close();
			to.close();
			return true;

		} 
		catch (Exception ex) 
		{
			return false;
		}
	}
	/**
	 * �ļ�����
	 * @param from Դ�ļ�
	 * @param to Ŀ���ļ�
	 * @return �����Ƿ�ɹ�
	 */
	public static boolean copyFile(File from,File to)
	{
		try 
		{
			return copyFile(new FileInputStream(from),new FileOutputStream(to));

		} 
		catch (Exception ex) 
		{
			return false;
		}
	}
	/**
	 * �ļ�����
	 * @param from Դ�ļ�
	 * @param to Ŀ���ļ�
	 * @return �����Ƿ�ɹ�
	 */
	public static boolean copyFile(String from,String to)
	{
		return copyFile(new File(from),new File(to));
	}
	
	/**
	 * �ƶ��ļ�
	 * @param srcFileName Դ�ļ�
	 * @param destFileName Ŀ���ļ�
	 * @param tempFileName �м���ʱ�ļ�(���û�ã�����"")
	 * @return �Ƿ�ɹ�
	 */
	public static boolean moveFile(String srcFileName,String destFileName,String tempFileName)
	{
		File srcFile=new File(srcFileName);
		if(!srcFile.exists())return false;
		
		File tempFile=new File(tempFileName);
		if(tempFile.exists())
		{
			if(!tempFile.delete())return false;
		}
		
		File destFile=new File(destFileName);
		
		if(tempFileName.length()>0)
		{
			if(!srcFile.renameTo(tempFile))return false;
			
			if(destFile.exists())
			{
				if(!destFile.delete())return false;
			}
			return tempFile.renameTo(destFile);
		}
		else 
		{
			if(destFile.exists())
			{
				if(!destFile.delete())return false;
			}
			return srcFile.renameTo(destFile);
		}
		/*
		File srcFile=new File(srcFileName);   
		File destFile=new File(destFileName);
		File tempFile=new File(tempFileName);
		if(!srcFile.exists())return true;;
		try 
		{
            FileInputStream fosfrom = new FileInputStream(srcFile);
            FileOutputStream fosto;
            if(tempFileName.length()>0)fosto= new FileOutputStream(tempFileName);
            else fosto= new FileOutputStream(destFileName);
            
            byte bt[] = new byte[1024*64];
            int c;
            while ((c = fosfrom.read(bt)) > 0) 
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            
            if(tempFileName.length()>0)tempFile.renameTo(destFile);
            return true;
        } 
		catch (Exception ex) 
        {   
        }
		return false;*/
	}
	
	/**
	 * ��ʾ�Ի���<br>
	 * ע��,���ô˺����һ��Ӧ��������
	 * @param c ������
	 * @param Title ����
	 * @param Msg ����
	 */
	public static void showMessage(Context c,String Title,String Msg) 
	{
		new AlertDialog.Builder(c)
		.setTitle(Title)
		.setMessage(Msg)
		.setCancelable(false)
		.setPositiveButton("ȷ��", null)
		.show();
	}
	
	/**
	 * ��ʾ�Ի���,�������������뽹��<br>
	 * ע��,���ô˺����һ��Ӧ��������
	 * @param c ������
	 * @param Title ����
	 * @param Msg ����
	 * @param focusView ���뽹��
	 */
	public static void showMessage(Context c,String Title,String Msg,View focusView) 
	{
		showMessage(c,Title,Msg);
		if(focusView==null)return;
		focusView.requestFocus();
	}
	
	/**
	 * ��ʾ�Ի��򣬰�ȷ��������Activity<br>
	 * ע��,���ô˺����һ��Ӧ��������
	 * @param a Activity
	 * @param Title ����
	 * @param Msg ����
	 */
	public static void showMessageAndFinish(final Activity a,String Title,String Msg) 
	{
		new AlertDialog.Builder(a)
		.setTitle(Title)
		.setMessage(Msg)
		.setCancelable(false)
		.setPositiveButton
		("ȷ��",
				new DialogInterface.OnClickListener()
				{
					@Override public void onClick(DialogInterface dialoginterface, int i)                   
					{
						a.finish();
					} 
				}
		)
		.show();
	}
	
	/**
	 * ��ʾ�Ի��򣬰�ȷ����������<br>
	 * ע��,���ô˺����һ��Ӧ��������
	 * @param a Activity
	 * @param Title ����
	 * @param Msg ����
	 */
	public static void showMessageAndTerminate(final Activity a,String Title,String Msg) 
	{
		new AlertDialog.Builder(a)
		.setTitle(Title)
		.setMessage(Msg)
		.setCancelable(false)
		.setPositiveButton
		("ȷ��",
			new DialogInterface.OnClickListener()
			{
				@Override public void onClick(DialogInterface dialoginterface, int i)                   
				{
					try
					{
						Application app=a.getApplication();						
						Method mt = app.getClass().getMethod("Terminate");
						mt.invoke(app);
						return;
					}
					catch(Exception e)
					{
					}
		            System.exit(0);
				} 
			}
		)
		.show();
	}
	
	/**
	 * ����ͼƬ��ImageView�ؼ�<br>
	 * Ϊ��ֹ����OutOfMemory�쳣�����ܻ��ݿؼ���С��С�����ص�ͼƬ
	 * @param fileName ͼƬ�ļ���
	 * @param dest ImageView�ؼ�
	 */
	public static void showImage(String fileName,ImageView dest)
	{
		Bitmap bitmap=null;
		try
		{
			BitmapDrawable bitmapDrawable = (BitmapDrawable) dest.getDrawable();
			if(bitmapDrawable!=null)bitmap=bitmapDrawable.getBitmap();
			//���ͼƬ��δ���գ���ǿ�ƻ��ո�ͼƬ
			if( (bitmap!=null) && (!bitmap.isRecycled()) )
			{
				bitmap.recycle();
			}
			
			File file=new File(fileName);
			if(file.exists()&&!file.isDirectory())
			{
				FileInputStream fis = new FileInputStream(file);
				
				int width=dest.getLayoutParams().width;
				int height=dest.getLayoutParams().height;
				int scale = 1;  
			    BitmapFactory.Options options = new BitmapFactory.Options();  
			    if(width>0 || height>0)
			    {  
			        // Decode image size without loading all data into memory  
			        options.inJustDecodeBounds = true;  
			        BitmapFactory.decodeStream(  
			                new BufferedInputStream(fis, 16*512),  
			                null,  
			                options);
			          
			        int w = options.outWidth;  
			        int h = options.outHeight;  
			        while (true) 
			        {  
			            if ((width>0 && w/2 < width)  
			                    || (height>0 && h/2 < height))
			            {  
			                break;  
			            }  
			            w /= 2;  
			            h /= 2;  
			            scale *= 2;  
			        }  
			    }  
			
			    // Decode with inSampleSize option
			    fis.close();
			    fis = new FileInputStream(file);
			    options.inJustDecodeBounds = false;  
			    options.inSampleSize = scale;  
			    options.inPreferredConfig = Config.RGB_565;
			    bitmap = BitmapFactory.decodeStream(new BufferedInputStream(fis, 16*512),null,options);
			    dest.setImageBitmap(bitmap); 
			    fis.close();
			    return;
			}
		}
		catch(Exception e)
		{
		}
		dest.setImageBitmap(null);
	}  
	/**
	 * ����ͼƬ��ImageView�ؼ�
	 * @param fileName ͼƬ�ļ���
	 * @param dest ImageView�ؼ�
	 */
	/*
	public static void showImage(String fileName,ImageView dest)
	{
		try
		{
			dest.setImageBitmap(safeDecodeStream);
			File file=new File(fileName);
			if(file.exists())
			{
				InputStream fis = new FileInputStream(file);
				Bitmap bitmap =BitmapFactory.decodeStream(fis);
				dest.setImageBitmap(bitmap);
			}
			else
			{
				dest.setImageBitmap(null);
			}
		}
		catch(Exception e)
		{
		}
	}*/
	
	/**
	 * ������ת��Ϊ16�����ַ�(��д)
	 * @param src �����ƻ����� 
	 * @param offset ƫ������
	 * @param len ����
	 * @return 16�����ַ�(��д)
	 */
	static public String binToHex(byte[] src,int offset,int len) 
	{
		if (src == null || src.length <= 0) 
		{
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder("");
		char[] buffer = new char[2];
		for (int i = offset; (i<src.length) &&(i < offset+len); i++) 
		{
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			stringBuilder.append(buffer);
		}
		return stringBuilder.toString().toUpperCase(Locale.US);	
	}
	/**
	 * ������ת��Ϊ16�����ַ�(��д)
	 * @param src �����ƻ����� 
	 * @return 16�����ַ�(��д)
	 */
	static public String binToHex(byte[] src) 
	{
		if(src==null)return "";
		return binToHex(src,0,src.length);
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
	
	/**
	 * ���ͼƬ�ļ�����ɫ����
	 * @param fileName ͼƬ�ļ���
	 * @return ��ɫ����
	 */
	public static int[] getPixels(String fileName)
	{
		Bitmap bm = BitmapFactory.decodeFile(fileName);
        int x=0,y=0, width = bm.getWidth(), height = bm.getHeight(), offset = 0, stride = bm.getWidth();
		int[] pixels = new int[stride * height + offset];
		bm.getPixels(pixels, offset, stride, x, y, width, height);
		return pixels;
	}
	
	/**
	 * ��ݷֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����) 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static float dip2px(Context context, float dpValue) 
	{ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return dpValue * scale; 
	} 
	
	/**
	 * ��ݷֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static float px2dip(Context context, float pxValue) 
	{ 
		final float scale = context.getResources().getDisplayMetrics().density; 
		return pxValue / scale; 
	}
	
	private static List<View> getAllChildViews(View view) 
	{
		List<View> allchildren = new ArrayList<View>();
		if (view instanceof ViewGroup) 
		{
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) 
			{
				View viewchild = vp.getChildAt(i);
				allchildren.add(viewchild);
				allchildren.addAll(getAllChildViews(viewchild));
			}
		}
		return allchildren;
	}
	/**
	 * ��ô������е�����View
	 * @param a
	 * @return
	 */
	public static List<View> getAllChildViews(Activity a) {
		View view = a.getWindow().getDecorView();
		return getAllChildViews(view);
	}

	private static void reSize(View view,float xr,float yr)
	{
		ViewGroup.LayoutParams lp=view.getLayoutParams();
		if(lp!=null)
		{
			if(lp.width>0)lp.width=Math.round(lp.width*xr);
			if(lp.height>0)lp.height=Math.round(lp.height*yr);
		}
		
		if(lp instanceof ViewGroup.MarginLayoutParams)
		{
			ViewGroup.MarginLayoutParams lp2=(ViewGroup.MarginLayoutParams)lp;
			if(lp2.leftMargin>0)lp2.leftMargin=Math.round(lp2.leftMargin*xr);
			if(lp2.topMargin>0)lp2.topMargin=Math.round(lp2.topMargin*yr);
			if(lp2.rightMargin>0)lp2.rightMargin=Math.round(lp2.rightMargin*xr);
			if(lp2.bottomMargin>0)lp2.bottomMargin=Math.round(lp2.bottomMargin*yr);
		}
	}
	private static void autoSize(View view,float xr,float yr) 
	{
		float minr=Math.min(xr,yr);
		
		if(view instanceof TextView)
		{
			reSize(view,xr,yr);
			((TextView)view).setTextSize(TypedValue.COMPLEX_UNIT_PX,((TextView)view).getTextSize()*minr-1.0f);
		}
		else if(view instanceof EditText)
		{
			reSize(view,xr,yr);
			((EditText)view).setTextSize(TypedValue.COMPLEX_UNIT_PX,((EditText)view).getTextSize()*minr-1.0f);
		}
		else if(view instanceof Button)
		{
			reSize(view,xr,yr);
			((Button)view).setTextSize(TypedValue.COMPLEX_UNIT_PX,((Button)view).getTextSize()*minr-1.0f);
		}
		else if(view instanceof RadioButton)
		{
			reSize(view,xr,yr);
			((RadioButton)view).setTextSize(TypedValue.COMPLEX_UNIT_PX,((RadioButton)view).getTextSize()*minr-1.0f);
		}
		else if(view instanceof Spinner)
		{
			reSize(view,xr,yr);
		}
		else if(view instanceof GridView)
		{
			reSize(view,xr,yr);
		}
		else if(view instanceof ImageView)
		{
			reSize(view,xr,yr);
		}
		else if(view instanceof LinearLayout)
		{
			reSize(view,xr,yr);
		}
		else
		{
			reSize(view,xr,yr);
		}
		
		if (view instanceof ViewGroup) 
		{
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) 
			{
				View viewchild = vp.getChildAt(i);
				autoSize(viewchild,xr,yr);
			}
		}
	}
	
	/**
	 * �����Ļ��С�Զ��Ŵ���С���ڸ��ؼ�������
	 * @param a
	 * @param original_x_dp ԭʼ����dip���
	 * @param original_y_dp ԭʼ����dip�߶�
	 */
	public static void autoSize(Activity a,float original_x_dp,float original_y_dp)
	{
		float original_x_px=dip2px(a,original_x_dp);
	    float original_y_px=dip2px(a,original_y_dp);
	    
		//DisplayMetrics dm = new DisplayMetrics();
	    //a.getWindowManager().getDefaultDisplay().getMetrics(dm); 
	    //float xr=1.0f*dm.widthPixels/original_x_px;
	    //float yr=1.0f*dm.heightPixels/original_y_px;
	    
	    float xr=1.0f*getScreenWidth(a)/original_x_px;
	    float yr=1.0f*getScreenHeight(a)/original_y_px;
	    
	    autoSize(a.getWindow().getDecorView(),xr,yr);
	}
	
	/**
	 * �����Ļԭʼpix���
	 * @param a
	 * @return ��Ļԭʼpix���
	 */
	public static int getRawScreenWidth(Activity a) 
	{         
	    DisplayMetrics dm = new DisplayMetrics(); 
	    android.view.Display display = a.getWindowManager().getDefaultDisplay(); 
	    display.getMetrics(dm); 
	    
	    int ver = Build.VERSION.SDK_INT;
	    if (ver == 13) 
	    { 
	        try 
	        { 
	            Method mt = display.getClass().getMethod("getRealWidth"); 
	            return (Integer) mt.invoke(display); 
	        } 
	        catch (Exception e) 
	        { 
	            return dm.widthPixels;
	        }  
	    } 
	    else if (ver > 13) 
	    { 
	        try 
	        { 
	            Method mt = display.getClass().getMethod("getRawWidth"); 
	            return (Integer) mt.invoke(display); 
	       
	        } 
	        catch (Exception e) 
	        { 
	        	return dm.widthPixels;
	        } 
	    }
	    else return dm.widthPixels;
	}
	
	/**
	 * �����Ļ��ʾpix���
	 * @param a
	 * @return ��Ļ��ʾpix���
	 */
	public static int getScreenWidth(Activity a) 
	{
		DisplayMetrics dm = new DisplayMetrics(); 
	    android.view.Display display = a.getWindowManager().getDefaultDisplay(); 
	    display.getMetrics(dm); 
	    return dm.widthPixels;
	    //return getRawScreenWidth(a);
	}
	
	/**
	 * �����Ļԭʼpix�߶�
	 * @param a
	 * @return ��Ļԭʼpix�߶�
	 */
	public static int getRawScreenHeight(Activity a) 
	{         
	    DisplayMetrics dm = new DisplayMetrics(); 
	    android.view.Display display = a.getWindowManager().getDefaultDisplay(); 
	    display.getMetrics(dm); 
	     
	    int ver = Build.VERSION.SDK_INT; 
	    if (ver == 13) 
	    { 
	        try 
	        { 
	            Method mt = display.getClass().getMethod("getRealHeight"); 
	            return (Integer) mt.invoke(display); 
	        } 
	        catch (Exception e) 
	        { 
	            return dm.heightPixels;
	        }  
	    } 
	    else if (ver > 13) 
	    { 
	        try 
	        { 
	            Method mt = display.getClass().getMethod("getRawHeight"); 
	            return (Integer) mt.invoke(display); 
	       
	        } 
	        catch (Exception e) 
	        { 
	        	return dm.heightPixels;
	        } 
	    }
	    else return dm.heightPixels;
	}
	
	/**
	 * �����Ļ��ʾpix�߶�
	 * @param a
	 * @return ��Ļ��ʾpix�߶�
	 */
	public static int getScreenHeight(Activity a) 
	{         
	    DisplayMetrics dm = new DisplayMetrics(); 
	    android.view.Display display = a.getWindowManager().getDefaultDisplay(); 
	    display.getMetrics(dm); 
	    return dm.heightPixels;
	}
	
	private static String saveInst(View view) 
	{
		String result="";
		if(view instanceof EditText)
		{
			result="EditText|"+BaseLib.intToStr(((EditText)view).getId())+"|"+((EditText)view).getText().toString()+"\n";
		}
		else if(view instanceof RadioButton)
		{
			result="RadioButton|"+BaseLib.intToStr(((RadioButton)view).getId())+"|"+(((RadioButton)view).isChecked()?"1":"0")+"\n";
		}
		else if(view instanceof Button)
		{
			result="Button|"+BaseLib.intToStr(((Button)view).getId())+"|"+((Button)view).getText()+"\n";
		}
		else if(view instanceof Spinner)
		{
			result="Spinner|"+BaseLib.intToStr(((Spinner)view).getId())+"|"+BaseLib.intToStr(((Spinner)view).getSelectedItemPosition())+"\n";
		}
		
		
		if (view instanceof ViewGroup) 
		{
			ViewGroup vp = (ViewGroup) view;
			for (int i = 0; i < vp.getChildCount(); i++) 
			{
				View viewchild = vp.getChildAt(i);
				result=result+saveInst(viewchild);
			}
		}
		return result;
	}
	/**
	 * ��ô�������EditText��RadioButton��Spinner�ؼ���ǰֵ�ı��洮�л��ַ�
	 * @param a
	 * @return
	 */
	public static String saveInst(Activity a)
	{
		return saveInst(a.getWindow().getDecorView());
	}
	/**
	 * ��ݴ��ڱ���Ĵ��л��ַ����ô���EditText��RadioButton��Spinner�ؼ���ǰֵ
	 * @param a
	 * @param savedStr
	 */
	public static void loadInst(Activity a,String savedStr)
	{
		Vector<String> tempVec=toWord(savedStr,'\n');
		for(String s:tempVec)
		{
			if(s.length()==0)continue;
			Vector<String> tempVec2=toWord(s,'|');
			if(tempVec2.size()<3)continue;
			if(tempVec2.get(0).equals("EditText"))
			{
				int id=strToInt(tempVec2.get(1),0);
				((EditText)a.findViewById(id)).setText(tempVec2.get(2));
			}
			else if(tempVec2.get(0).equals("RadioButton"))
			{
				int id=strToInt(tempVec2.get(1),0);
				((RadioButton)a.findViewById(id)).setChecked(tempVec2.get(2).equalsIgnoreCase("1"));
			}
			else if(tempVec2.get(0).equals("Button"))
			{
				int id=strToInt(tempVec2.get(1),0);
				((Button)a.findViewById(id)).setText(tempVec2.get(2));
			}
			else if(tempVec2.get(0).equals("Spinner"))
			{
				int id=strToInt(tempVec2.get(1),0);
				((Spinner)a.findViewById(id)).setSelection(strToInt(tempVec2.get(2),0));
			}
		}
	}
	
	/**
	 * ����ڴ������Ϣ
	 * @param context
	 * @return
	 */
	public static String getMemStat(Context context)
	{
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		Runtime rt=Runtime.getRuntime();

		String res=String.format(Locale.CHINA,"����ϵͳ  �����ڴ�:%dK �ڴ治��ķ�ֵ:%dK��������ڴ� ��������:%dK,������:%dK,��ǰ����:%dK,��ǰʣ��:%dK",memoryInfo.availMem/1024,memoryInfo.threshold/1024,rt.maxMemory()/1024,rt.totalMemory()/1024,(rt.totalMemory()-rt.freeMemory())/1024,rt.freeMemory()/1024);
		return res;
	}
	
	/**
	  * ��ȡ�汾��
	  * @return ��ǰӦ�õİ汾��
	  */
	public static String getVersion(Context context) 
	{
	    try 
	    {
	        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
	    } 
	    catch (Exception e) 
	    {
	    }
	    return "";
	}
	
	/**
	  * ��ȡ�汾���
	  * @return ��ǰӦ�õİ汾���
	  */
	public static int getVersionCode(Context context) 
	{
	    try 
	    {
	        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
	    } 
	    catch (Exception e) 
	    {
	    }
	    return 0;
	}
	
	/**
	 * ���IPv4��ʽ��IP��ַ
	 * @return
	 */
	public static String getLocalIpv4Address() 
	{  
        try 
        {  
        	for (Enumeration<NetworkInterface> en = NetworkInterface  
                .getNetworkInterfaces(); en.hasMoreElements();) 
        	{  
        		NetworkInterface intf = en.nextElement();  
        		for (Enumeration<InetAddress> enumIpAddr = intf  
                    .getInetAddresses(); enumIpAddr.hasMoreElements();) 
        		{  
        			InetAddress inetAddress = enumIpAddr.nextElement();  
        			if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) 
        			{  
        				String ipaddress=inetAddress.getHostAddress().toString();
        				if(ipaddress.length()>0&&!ipaddress.equals("127.0.0.1"))
        				{
        					return ipaddress;
        				}
        			}  
        		}  
        	}  
        } 
        catch (SocketException ex) 
        {  
        }
        return "127.0.0.1";
	}
	
	/**
     * ��ѹ��һ���ļ�
     * @param zipFile ѹ���ļ�
     * @param folderPath ��ѹ����Ŀ��Ŀ¼
     * @return �Ƿ��ѹ�ɹ�
     */
    private static boolean unZipFile(File zipFile, String folderPath,boolean isOverwrite) 
    {
    	try
    	{
    		if(!zipFile.exists()||!zipFile.isFile())return false;
    		File desDir = new File(folderPath);
            if (!desDir.exists()) 
            {
                desDir.mkdirs();
            }
            ZipFile zf = new ZipFile(zipFile);
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) 
            {
                ZipEntry entry = ((ZipEntry)entries.nextElement());
                InputStream in = zf.getInputStream(entry);
                String str = folderPath + File.separator + entry.getName();
                str = new String(str.getBytes("8859_1"), "GB2312");
                if(str.substring(str.length()-File.separator.length(),str.length()).equals(File.separator))
                {
                	str=str.substring(0,str.length()-File.separator.length());
                }
                File desFile = new File(str);
                if(entry.isDirectory())
                {
                	desFile.mkdirs();
                	in.close();
                	continue;
                }
                if(desFile.isDirectory())
                {
                	in.close();
                	continue;
                }
                
                if(desFile.exists()) 
                {
                	if(!isOverwrite)
                	{
                		in.close();
                		continue;
                	}
                	desFile.createNewFile();
                }
                	
                OutputStream out = new FileOutputStream(desFile);
                byte buffer[] = new byte[1024*1024];
                int realLength;
                while ((realLength = in.read(buffer)) > 0) 
                {
                    out.write(buffer, 0, realLength);
                }
                in.close();
                out.close();
            }
            return true;
    	}
    	catch(Exception e)
    	{
    	}
    	return false;
    }
	/**
     * ��ѹ��һ���ļ�
     * @param fileName ѹ���ļ���
     * @param folderPath ��ѹ����Ŀ��Ŀ¼
     * @return �Ƿ��ѹ�ɹ�
     */
    public static boolean unZipFile(String fileName, String folderPath,boolean isOverwrite) 
    {
    	return unZipFile(new File(fileName),folderPath,isOverwrite);
    }
    
    private static boolean waitForProcess(Process p) 
	{
		boolean isSuccess = false;
		int returnCode;
		try 
		{
			returnCode = p.waitFor();
			switch (returnCode) 
			{
				case 0:
					isSuccess = true;
				    break;
				case 1:
				    break;    
				default:
				    break;
			}
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		 
		return isSuccess;
	}
    /**
     * ���ó����û�ִ��shell����ű�
     * @param cmdStr shell����ű�����������ʱ�û��з����
     * @return ִ�н��
     */
    public static boolean execWithSID(String cmdStr) 
	{
		boolean isSuccess = false;
		Process process = null;
		OutputStream out = null;
		try 
		{
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			 
			dataOutputStream.writeBytes(cmdStr);
			dataOutputStream.flush(); // �ύ����
			dataOutputStream.close(); // �ر�������
			out.close();
			   
			isSuccess = waitForProcess(process);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			 
		return isSuccess;
	}
    
    /**
     * ����豸Ψһ��ʶ����ǰ��MAC��ַ��Ϊ�豸Ψһ��ʶ
     * @param c
     * @return �豸Ψһ��ʶ
     */
    public static String getDeviceID(Context c)
    {
    	String devID = "";
		WifiManager wifi = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		devID = info.getMacAddress();
		return devID;
    }
    
    /**
     * ֱ����ͼƬ���Ͻǵ�������
     * @param dest ���������ֵ�ͼƬ
     * @param signStr ���ӵ�����
     * @param textSize ���ӵ����������С(����),Ϊ0���Զ����ͼ���С���������С
     * @return ���ӽ��
     */
    public static void signToText(Bitmap dest,String signStr,float textSize) 
	{
    	int width=dest.getWidth();
    	int height=dest.getHeight();
    	if(textSize<=0)textSize=Math.min(height,width)/20;
    	
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);//���û���
		textPaint.setTextSize(textSize);//�����С    
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);//����Ĭ�ϵĿ��
		//textPaint.setShadowLayer(3f, 1, 1,this.getResources().getColor(android.R.color.background_dark));//Ӱ��������
		
		Canvas canvas=new Canvas(dest);
		
		float rowHeight=textSize+3;
		Vector<String> tmp=toWord(signStr,'\n');
		for(int row=0;row<tmp.size();++row)
        {
			textPaint.setColor(Color.BLACK);
			canvas.drawText(tmp.get(row), 4-1, 4+rowHeight*(row+1)-1, textPaint);
			canvas.drawText(tmp.get(row), 4-1, 4+rowHeight*(row+1)+1, textPaint);   
			canvas.drawText(tmp.get(row), 4+1, 4+rowHeight*(row+1)-1, textPaint);   
			canvas.drawText(tmp.get(row), 4+1, 4+rowHeight*(row+1)+1, textPaint);   
			textPaint.setColor(Color.WHITE);
			canvas.drawText(tmp.get(row), 4, 4+rowHeight*(row+1), textPaint);
        }
		
		canvas.save(Canvas.ALL_SAVE_FLAG);   
		canvas.restore();
	}
    
    /**
     * ��ԴͼƬ���Ͻǵ������֣�������ͼƬ
     * @param src �������ֵ�ԴͼƬ
     * @param signStr ���ӵ�����
     * @param textSize ���ӵ����������С(����),Ϊ0���Զ����ͼ���С���������С
     * @return ���ӽ��ע��:������÷���ֵ���뼰ʱ����Bitmap��recycle()��������ڴ�
     */
    public static Bitmap signText(final Bitmap src,String signStr,float textSize) 
	{
    	Bitmap dest=src.copy(Bitmap.Config.ARGB_8888,true);
    	if(dest==null)return dest;
    	signToText(dest,signStr,textSize);
    	return dest;
	}
    
    /**
     * ��ͼƬ���Ͻǵ�������,���Ӻ��ͼƬ��ʽ�洢ΪJPEG��ʽ
     * @param fileName ����������ͼƬ�ļ���
     * @param signStr ���ӵ�����
     * @param textSize ���ӵ����������С(����),Ϊ0���Զ����ͼ���С���������С
     * @return ���Ӻ��λͼ��ע��:������÷���ֵ���뼰ʱ����Bitmap��recycle()��������ڴ�
     */
    public static Bitmap signText(String fileName,String signStr,float textSize)
	{
		try
		{
			File file=new File(fileName);
			if(file.exists())
			{
				InputStream fis = new FileInputStream(file);
				Bitmap bitmap =BitmapFactory.decodeStream(fis);
				if(bitmap.isMutable())signToText(bitmap,signStr,textSize);
				else
				{
					Bitmap newbitmap=signText(bitmap,signStr,textSize);
					bitmap.recycle();
					bitmap=newbitmap;
				}

				if(bitmap!=null)
				{
					FileOutputStream out;
					out = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG,80, out); 
					out.flush(); 
					out.close();
				}
				return bitmap;
			}
			else
			{
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}
    
    /**
     * ��ͼƬ���Ͻǵ�������,���Ӻ��ͼƬ��ʽ�洢ΪJPEG��ʽ
     * @param srcFileName ������������ԴͼƬ�ļ���
     * @param destFileName ���������ֺ�ͼƬ�����ļ���
     * @param signStr ���ӵ�����
     * @param textSize ���ӵ����������С(����),Ϊ0���Զ����ͼ���С���������С
     * @return ���Ӻ��λͼ��ע��:������÷���ֵ���뼰ʱ����Bitmap��recycle()��������ڴ�
     */
    public static Bitmap signText(String srcFileName,String destFileName,String signStr,float textSize)
	{
		try
		{
			File file=new File(srcFileName);
			if(file.exists())
			{
				InputStream fis = new FileInputStream(file);
				Bitmap bitmap =BitmapFactory.decodeStream(fis);
				if(bitmap.isMutable())signToText(bitmap,signStr,textSize);
				else
				{
					Bitmap newbitmap=signText(bitmap,signStr,textSize);
					bitmap.recycle();
					bitmap=newbitmap;
				}
				
				if(bitmap!=null)
				{
					FileOutputStream out;
					out = new FileOutputStream(destFileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG,80, out); 
					out.flush(); 
					out.close();
				}
				return bitmap;
			}
			else
			{
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}
    
    /**
     * �Ƿ�װ��ĳ��apk
     * @param context ���ý���
     * @param packageName ���� ��������ʱ��null
     * @param appName Ӧ���� �����Ӧ����ʱ��null
     * @return �Ƿ�װ��ĳ��apk 
     */
    public static boolean isApkInstalled(Context context,String packageName,String appName)
    {
    	List<PackageInfo> list = context.getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);    
    	
    	for (PackageInfo packageInfo : list) 
    	{  
    	     if( (packageName!=null) && !packageName.equals(packageInfo.packageName) )continue;//����
    	     if( (appName!=null) && !appName.equals(packageInfo.applicationInfo.loadLabel(context.getPackageManager())) )continue;//Ӧ����
    	     return true;
    	}
    	return false;
    }
    
    /**
	 * �򵥵��ļ�ѡ����
	 * @author LiuWu
	 *
	 */
    public static class OpenFileView extends LinearLayout implements OnItemClickListener
    {
    	private Context Owner;
		private String CurrentPath;
		private Pattern regMask;
		private Vector<String> FileNames;
		private Vector<String> ShowNames;
		private TextView[] ShowTexts;
		private int SelectedIndex;
		private ListView listView;
		private TextView Title;
		public String FileName;
		
    	public OpenFileView(Context context,String Path,String Name,String regularMask)
		{
			super(context);
			init(context,Path,Name,regularMask);
		}
    	
    	public OpenFileView(Context context,String fileName,String regularMask)
		{
    		super(context);
    		
    		String Path=new File(fileName).getParent();
			if(!Path.equals("/"))Path=Path+"/";
			String Name=new File(fileName).getName();
			
    		init(context,Path,Name,regularMask);
		}
    	
    	private void init(Context context,String Path,String Name,String regularMask)
    	{
    		setOrientation(VERTICAL);
			String fName="";
			try
			{
				Title=new TextView(context);
				listView=new ListView(context);
				this.addView(Title);
				this.addView(listView);
				SelectedIndex=-1;
				Owner=context;
				CurrentPath="/";
				FileNames=new Vector<String>();
				ShowNames=new Vector<String>();
				regMask = Pattern.compile(regularMask.toLowerCase(Locale.US));
				
				File file=new File(Path);
				if(!file.exists())return;
				if(!file.isDirectory())return;
				CurrentPath=Path;
				file=new File(Path+Name);
				if(!file.exists())return;
				if(!file.isFile())return;
				fName=Name;
			}
			finally
			{
				refresh(fName);
				listView.setOnItemClickListener(this);
			}
    	}
    	
    	private class MyAdapter extends ArrayAdapter<String> 
		{
			public MyAdapter(final Context context,
		            final int textViewResourceId, final String[] objects) 
		    {
		        super(context, textViewResourceId, objects);
		    }
			
			public int getCount() 
			{
				// TODO Auto-generated method stub
				return ShowNames.size();
			}
			public String getItem(int arg0) {
				// TODO Auto-generated method stub
				return ShowNames.get(arg0);
			}
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			public View getView(int position, View convertView, ViewGroup parent) 
			{
				if(ShowTexts[position]==null)
				{
					LayoutInflater inflater = LayoutInflater.from(Owner);
					View group = inflater.inflate(
		            		android.R.layout.simple_list_item_1, parent, false);
		            
					ShowTexts[position] = (TextView) group.findViewById(android.R.id.text1);
					if(new File(FileNames.get(position)).isDirectory())
					{
						ShowTexts[position].setTextColor(Color.GRAY);
					}
					ShowTexts[position].setText(ShowNames.get(position));			        
				}
		        
				TextView tv = ShowTexts[position];//new TextView(Owner);//(TextView) group.findViewById(android.R.id.text1);//new TextView(Owner);
				if (position == SelectedIndex) 
		        {
		        	tv.setBackgroundColor(Color.BLUE);
				} 
				else 
				{
					tv.setBackgroundColor(Color.TRANSPARENT);
				}
				
				return tv;
			}
		}
    	private void refresh(String Name)
		{
			SelectedIndex=-1;
			FileName=null;
			FileNames.clear();
			ShowNames.clear();
			File file=new File(CurrentPath);
			if(file.getParent()!=null)
			{
				String str=file.getParent();
				if(str.equals("/"))FileNames.add(str);
				else FileNames.add(str+"/");
				ShowNames.add("��һ��");
			}
			
			File files[] = file.listFiles();
			if(files!=null)
			for (int i = 0; i < files.length; i++) 
			{
				if(files[i].isDirectory())
				{
					FileNames.add(files[i].getPath()+"/");
					ShowNames.add(files[i].getName()+"/");
				}
				else
				{
					Matcher fMatcher = regMask.matcher(file.getName().toLowerCase(Locale.US));    
					if (fMatcher.matches()) 
					{
						FileNames.add(CurrentPath+files[i].getName());
						ShowNames.add(files[i].getName());
						if(Name.equals(files[i].getName()))
						{
							FileName=CurrentPath+files[i].getName();
							SelectedIndex=FileNames.size()-1;
						}
					}
				}
			}
			
			ShowTexts= new TextView[ShowNames.size()];
			String[] items=new String[ShowNames.size()];
			for(int i=0;i<ShowNames.size();++i)
			{
				items[i]=ShowNames.get(i);
			}
			
			listView.setAdapter(new MyAdapter(Owner, android.R.layout.simple_list_item_1,items));
			//listView.setSelector(Color.TRANSPARENT);
			//listView.setBackgroundColor(Color.GRAY);
			//listView.setChoiceMode(CHOICE_MODE_SINGLE);
			if(SelectedIndex>=0)
			{
				listView.setSelection(SelectedIndex);
				//listView.getChildAt(SelectedIndex).setBackgroundColor(Color.BLUE);
			}
			Title.setText(CurrentPath);
		}
		
		@Override
		public void onItemClick(AdapterView<?> parent,View view,int position,long id)
		{
			if(position>=FileNames.size())return;
			if(position<0)return;
			if(new File(FileNames.get(position)).isDirectory())
			{
				CurrentPath=FileNames.get(position);
				refresh("");
			}
			else
			{
				//if((SelectedIndex>=0) && (listView.getChildAt(SelectedIndex)!=null))listView.getChildAt(SelectedIndex).setBackgroundColor(Color.TRANSPARENT);
				if((SelectedIndex>=0) && (ShowTexts[SelectedIndex]!=null))ShowTexts[SelectedIndex].setBackgroundColor(Color.TRANSPARENT);
				SelectedIndex=position;
				FileName=FileNames.get(SelectedIndex);
				
				if(ShowTexts[SelectedIndex]!=null)ShowTexts[SelectedIndex].setBackgroundColor(Color.BLUE);
				//listView.setSelection(SelectedIndex);
				//if(listView.getChildAt(SelectedIndex)!=null)listView.getChildAt(SelectedIndex).setBackgroundColor(Color.BLUE);
			}
		}
    }
}
