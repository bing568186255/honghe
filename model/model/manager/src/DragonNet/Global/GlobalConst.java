package DragonNet.Global;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import org.w3c.dom.*; 

public final class GlobalConst {
	public static final String SESS_ATTR_CHECKIMG = "checkimg";
	public static final String SESS_ATTR_SYSUSER = "sysuser";
	public static final String ACTION_PARAM_NAME = "action";
	public static final String AJAX_PARAM_NAME = "ajax";
	public static final String SYS_ERROR_FILENAME = "error.log";
	public static final String RESP_CONTENT_TYPE = "text/html;charset=utf-8";
	public static final String RESP_CHAR_ENCODING = "utf-8";
	public static final long SYS_TRIAL_DAYS	= 90;	//试用天数
	
	private static boolean initialized = false;
	private static String imagePath = "";
	private static String tempPath = "";
	
	public static String getImagePath(){
		if (!initialized)
			init();
		return imagePath;
	}
	public static String getTempPath(){
		if (!initialized)
			init();
		return tempPath;
	}
	
	private static void init(){
		String xmlPathFile = GlobalFun.getWebInfPath()+"config.xml";
		Document docConfigXML;
		try { 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			docConfigXML = db.parse(xmlPathFile); 
			NodeList nodeRootList = docConfigXML.getChildNodes(); 
			for (int i = 0; i < nodeRootList.getLength(); i++) { 
				Node node1 = nodeRootList.item(i);
				if(node1.getNodeName().equals("config")){
					NodeList node2List = node1.getChildNodes();
					for(int k = 0;k<node2List.getLength();k++){
						Node node2 = node2List.item(k);
						if(node2.getNodeName().equals("imgpath")){
							imagePath = node2.getTextContent();
						}else if(node2.getNodeName().equals("temppath")){
							tempPath = node2.getTextContent();
						}
					}
				}
			}
		} 
		catch (Exception e) {
			GlobalFun.ErrorOut(e.getMessage());
		}
		initialized = true;
	}
}
