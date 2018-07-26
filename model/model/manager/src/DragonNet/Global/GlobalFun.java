package DragonNet.Global;

import java.io.*;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GlobalFun {
	public static boolean mbIsWindows = false;
	public static int miLoginErrorTimes = 3;
	public static int miLoginLockTime = 10;
	public static String mstrMachineName = "";
	public static long mlStartTime = 0;
	public static String mstrUnitName = "";
	public static boolean mbRegisted = false;
	public static String mstrRegCode = "";
	public static long mlRegistTime = 0;
	public static boolean mbIsTimeLimit = false;
	
	private static String mstrLogFile = null;

	static{
		String strOS = System.getProperty("os.name");
		if (strOS.indexOf("Windows")>=0){
			mbIsWindows = true;
			DebugOut("server is Windows");
		}else
			mbIsWindows = false;		
	}
	
	//获取系统WEB-INF真实目录，后面带/
	public static String getWebInfPath(){
		String classFilePath = GlobalFun.class.getResource("GlobalFun.class").toString();
		if (classFilePath.startsWith("file:/"))
			classFilePath = classFilePath.substring(6);
		try{
			classFilePath = URLDecoder.decode(classFilePath,"utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		int pos = classFilePath.indexOf("/WEB-INF/");
		String strPath = classFilePath.substring(0,pos+9);
		return strPath;
	}
	
	//调试、信息、错误输出
	public static void ErrorOut(String strPrint){
		System.out.println(strPrint);
		return;
	}
	public static void WarningOut(String strPrint){
		System.out.println(strPrint);
		return;
	}
	public static void DebugOut(String strPrint){
		System.out.println(strPrint);
		return;
	}
	
	//写入系统错误文件
	public static synchronized void writeSysError(String strErrorInfo){
		if (mstrLogFile == null){
			mstrLogFile = getWebInfPath();
			mstrLogFile += GlobalConst.SYS_ERROR_FILENAME;
		}
		try{
			FileWriter fw = new FileWriter(mstrLogFile);
			fw.write(strErrorInfo);
			fw.write("\r\n");
			fw.flush();
			fw.close();
		}catch(Exception e){
			GlobalFun.ErrorOut("operate " + mstrLogFile + " file failed");
		}
	}
	
	//在字符串数据组中查找字符串 
	public static int MatchStringArray(String[] strList,String strMatch,boolean bNoCase){
		int iRet = -1;
		if ((null == strMatch) || (strMatch.length()==0))
			return -1;
		if (null == strList || strList.length < 1)
			return -1;
		for(int i=0;i<strList.length;i++){
			if (bNoCase){
				if (strMatch.compareToIgnoreCase(strList[i])==0){
					iRet = i;
					break;
				}
			}else{
				if (strMatch.compareTo(strList[i])==0){
					iRet = i;
					break;
				}				
			}
		}
		return iRet;
	}

	//String 2 Long
	public static long Str2Long(String str){
		long iRet = 0;
		try{
			iRet = Long.valueOf(str);
		}catch(Exception e){
			GlobalFun.WarningOut(e.getMessage());
			iRet =0;
		}
		return iRet;
	}
	
	//String 2 int
	public static int Str2Int(String str){
		int iRet = 0;
		try{
			iRet = Integer.valueOf(str);
		}catch(Exception e){
			GlobalFun.WarningOut(e.getMessage());
			iRet =0;
		}
		return iRet;
	}

	//Strign 2 Float
	public static float Str2Float(String str){
		float fRet = 0;
		try{
			fRet = Float.valueOf(str);
		}catch(Exception e){
			GlobalFun.WarningOut(e.getMessage());
			fRet =0;
		}
		return fRet;
	}
	
	//String数组转换为int数组
	public static int[] StrList2IntList(String[] strList){
		if (null == strList)
			return null;
		if (strList.length < 1)
			return null;
		int[] intList = new int[strList.length];
		for(int i=0;i<strList.length;i++){
			intList[i]= Str2Int(strList[i]);
		}
		return intList;
	}

	//验证IP地址
	public static boolean CheckIPAddress(String strIP){
		if (null == strIP)
			return false;
		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";  
	    if (!strIP.matches(regex)) { 
	    	return false;
	    }  			
	    return true;
	}
	
	//转换整型IP掩码为String
	public static String TrasMaskInt2String(int iMask){
		if (iMask < 1 || iMask>32)
			return "";
		StringBuffer result=new StringBuffer();
		int iSub[]=new int[4];
		int iTemp = iMask;
		for(int i=0;i<4;i++){
			if (iTemp >= 8){
				iSub[i]=8;
				iTemp -= 8;
			}else{
				iSub[i]=iTemp;
				iTemp = 0;
			}
		}
		for(int i=0;i<4;i++){
			if (i>0)
				result.append('.');
			int iField = (int)Math.pow(2,iSub[i]) - 1;
			result.append(iField);
		}
        return result.toString();
	}
	
	//把从1970-1-1零点开始的秒数转为String
	public static String Long2TimeStr(long lTime,String strFormat){
		Date time = new Date();
		time.setTime(lTime*1000);
		SimpleDateFormat df;
		if (null != strFormat && strFormat.length()>0)
			df = new SimpleDateFormat(strFormat);
		else
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(time);			
	}

	//把时间字符串转换为从1970-1-1的秒数
	public static long TimeStr2Long(String strTime,String strFormat){
		char c = (char)160;
		String strNewTime = strTime.replace(c, ' ');
		SimpleDateFormat df = new SimpleDateFormat(strFormat);
		Date date = null;
		try{
			date = df.parse(strNewTime);
			return date.getTime()/1000;
		} catch (ParseException e) {
			GlobalFun.DebugOut(e.getMessage());
			return 0;
		}
	}
	
	//把字符串转换为日期
	public static Date TimeStr2Date(String strTime,String strFormat){
		char c = (char)160;
		String strNewTime = strTime.replace(c, ' ');
		SimpleDateFormat df = new SimpleDateFormat(strFormat);
		Date date = null;
		try{
			date = df.parse(strNewTime);
			return date;
		} catch (ParseException e) {
			GlobalFun.DebugOut(e.getMessage());
			return null;
		}
	}

	//获取当前时间字符串
	public static String getCurTimeString(String strFormat){
		SimpleDateFormat df = new SimpleDateFormat(strFormat);
		Date date = new Date();
		return df.format(date);
	}
	
	//获取访问者的IP地址
	public static String getRequestIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		return ip;
	}
	
	//转换String为HTML，转换特殊字符
	public static String TransStr2Html(String strSource){
		StringBuffer strRet;
		char[] cCharList={' ','&','<','>','"','\''};	
		String[] strTransList={"&nbsp;","&amp;","&lt;","&gt;","&quot;","&apos;"};
		int iCharNum = cCharList.length;
		int iStrNum = strTransList.length;
		if (iCharNum != iStrNum){
			ErrorOut("char num not match string num in TransStr2Html");
			if (iCharNum > iStrNum)
				iCharNum = iStrNum;
		}
		int iSourceLen = strSource.length();
		strRet = new StringBuffer(iSourceLen*2);
		for(int i=0;i<iSourceLen;i++){
			char c = strSource.charAt(i);
			boolean bMatch = false;
			for(int j=0;j<iCharNum;j++){
				if (c == cCharList[j]){
					strRet.append(strTransList[j]);
					bMatch = true;
					break;
				}
			}
			if (!bMatch)
				strRet.append(c);
		}
		return strRet.toString();
	}
	
	//获取UTF8字符串的字符长度
	public static int GetStringLength(String strInput){
		int iRet=0;
		try{
			iRet = strInput.getBytes("utf8").length;
		}catch (Exception e) {
			GlobalFun.ErrorOut(e.getMessage());
		} 
		return iRet;
	}
	
	//验证输入字符串有无非法字符
	//iMaxLen <=0 不验证长度
	public static boolean CheckInputString(String strInput,int iMaxLen){
		if (strInput==null)
			return false;
		if ((iMaxLen>0) && (GetStringLength(strInput)>iMaxLen))
			return false;
		
		char[] cIllegalList={'"','\'','`','%','&','*','(',')',
				'\\','|','<','>','?',
				'\t','\r','\n',',','\0'};
		int iIllegalNum = cIllegalList.length;
		int iInputLen = strInput.length();
		for(int i=0;i<iInputLen;i++){
			char cInput = strInput.charAt(i);
			if ((cInput >= 'a') && (cInput <= 'z'))
				continue;
			if ((cInput >= 'A') && (cInput <= 'Z'))
				continue;
			if ((cInput >= '0') && (cInput <= '9'))
				continue;
			for(int j=0;j<iIllegalNum;j++){
				if (cInput == cIllegalList[j])
					return false;
			}
		}
		return true;
	}
	public static boolean CheckInputString2(String strInput,int iMaxLen){
		if (strInput==null)
			return false;
		if ((iMaxLen>0) && (GetStringLength(strInput)>iMaxLen))
			return false;
		
		char[] cIllegalList={'\'','`','%','&','*','(',')','\\','|','<','>','/','?',
				'\t','\r','\n','\0'};
		int iIllegalNum = cIllegalList.length;
		int iInputLen = strInput.length();
		for(int i=0;i<iInputLen;i++){
			char cInput = strInput.charAt(i);
			if ((cInput >= 'a') && (cInput <= 'z'))
				continue;
			if ((cInput >= 'A') && (cInput <= 'Z'))
				continue;
			if ((cInput >= '0') && (cInput <= '9'))
				continue;
			for(int j=0;j<iIllegalNum;j++){
				if (cInput == cIllegalList[j]){
					GlobalFun.DebugOut("" + cInput);
					return false;
				}
			}
		}
		return true;
	}
	
	//密码复杂度验证
	public static boolean checkComplex(String pwd){
		if (pwd.length()<8)
			return false;
		int i = 0;
		if(pwd.matches("[a-z]+")){
		    i++;  
		}  
		if(pwd.matches("[A-Z]+")){
		    i++;  
		}  
		if(pwd.matches("[0-9]+")){
		    i++;  
		}
		if(pwd.matches("[^a-zA-Z0-9]+")){
		    i++;  
		}
		if (i>1)
			return true;
		return false;
	}
	
	//密码加密
	public static String getSecrit(String orgString){
		//if (orgString.length() < 1)
		//	return orgString;
		return secritMD5(orgString);
		//return orgString;
	}
	
	//MD5加密
	public static String secritMD5(String orgString){
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(orgString.getBytes());
			byte b[] = md.digest();
	
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString().toUpperCase();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//替换html预定义符号
	public static String htmlSpecialChars(String org){
		String strRet ;
		strRet = org.replace("&","&amp;");
		strRet = strRet.replace("\"","&quot;");
		strRet = strRet.replace("\'","&#039;");
		strRet = strRet.replace("<","&lt;");
		strRet = strRet.replace(">","&gt;");
		return strRet;
	}
	
	//为数据库替换引号
	public static String transQuot(String org){
		if(null == org)
			return null;
		String strRet;
		strRet = org.replace("\\", "\\\\");
		strRet = strRet.replace("\'", "\\\'");
		strRet = strRet.replace("\"", "\\\"");
		return strRet;
	}
	
	//WEB下载指定文件
	public static void downloadFile(String filePath,HttpServletResponse response,String fileName)
			throws Exception{
		File file = new File(filePath);
		if (null == fileName){
			fileName = file.getName();
		}
        // 以流的形式下载文件。
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename="+fileName);
        response.addHeader("Content-Length", "" + file.length());
        response.setContentType("application/octet-stream");
        
        InputStream is = new FileInputStream(file);  
        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[2048];
        int len;
        while((len = is.read(buffer))>0){
            os.write(buffer,0,len);
        }
        is.close();
        os.flush();
        os.close();
	}
	
	public static void zip(String zipFileName, String filePath) throws Exception {
		File file = new File(filePath);
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zipHandle(out,bo,file);
        bo.close();
        out.close();
	}
	private static void zipHandle(ZipOutputStream out,OutputStream bo,
			File file)  throws Exception {
		String fileName = file.getName();
		if (file.isDirectory()){
			File[] fl = file.listFiles();  
            if (fl.length == 0) {  
                out.putNextEntry(new ZipEntry(fileName + "/")); // 创建zip压缩进入点base  
            }  
            for (int i = 0; i < fl.length; i++) {  
            	zipHandle(out,bo,fl[i]); //递归遍历子文件夹  
            }  
		}else{
	        out.putNextEntry(new ZipEntry(fileName)); // 创建zip压缩进入点 			
	        FileInputStream in = new FileInputStream(file);  
	        byte[] buffer = new byte[2048];
	        int len;
	        while((len = in.read(buffer))>0){
	            bo.write(buffer,0,len);
	        }
            in.close(); // 输入流关闭  
		}
    }  
	
	//执行外面文件
	public static void runbat(String batName) { 
		try { 
			Process ps = Runtime.getRuntime().exec(batName); 
			/*
			InputStream in = ps.getInputStream(); 
			int c; 
			while ((c = in.read()) != -1) { 
			System.out.print(c);// 如果你不需要看输出，这行可以注销掉 
			} 
			in.close();
			*/ 
			ps.waitFor();
		} catch (Exception ioe) {
		        ioe.printStackTrace();
	    }
	    GlobalFun.DebugOut("runbat done");
	}	
}
