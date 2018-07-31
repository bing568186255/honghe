package DragonNet.Page;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;
import java.util.List;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.text.SimpleDateFormat;
import net.sf.json.*;
import DragonNet.Global.*;
import DragonNet.Dao.*;

public class AjaxPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBOperate dbOperate = null;
	
	protected void doRequest(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		String strAction = request.getParameter(GlobalConst.AJAX_PARAM_NAME);
		HashMap hm = new HashMap();
		if (strAction==null || strAction.isEmpty()){
			GlobalFun.WarningOut("strAction is empty in ajaxpage");
			hm.put("status", 400);
			hm.put("msg", "parameter is wrong!");
			String json = JSONObject.fromObject(hm).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));			
			return;
		}
		response.setContentType(GlobalConst.RESP_CONTENT_TYPE);
		response.setCharacterEncoding(GlobalConst.RESP_CHAR_ENCODING);
		try{
			Method method = this.getClass().getMethod(strAction, 
					new Class[]{HttpServletRequest.class,HttpServletResponse.class});
			if (null == method){
				hm.put("status", 400);
				hm.put("msg", "no action method!");				
			}else{
				method.invoke(this,
						new Object[]{request,response});
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			hm.put("status", 400);
			hm.put("msg", e.getMessage());
		}
		String json = JSONObject.fromObject(hm).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));			
		return;
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doRequest(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doRequest(request,response);
	}

	//ajaxTest
	public void test(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		HashMap hmRet = new HashMap();
		checkUser(request,response);
		String type = (String)request.getParameter("type");
		String id = (String)request.getParameter("id");
		if (null == type || type.isEmpty() || null == id || id.isEmpty()){
			hmRet.put("status", 400);
			hmRet.put("msg", "参数错误！");
			String json = JSONObject.fromObject(hmRet).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));			
			return;
		}
		HashMap hm = new HashMap();
		if (null != type)
			hm.put("type",type);
		if (null != id)
			hm.put("id",id);
		List<HashMap> listLYXM = DBOperate.getLYXM(hm);
		hmRet.put("status", 200);
		hmRet.put("rows", listLYXM);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));			
	}
	public void lvxmList(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		HashMap hmRet = new HashMap();
		checkUser(request,response);
		String qxbm = (String)request.getParameter("qxbm");
		String jszt = (String)request.getParameter("jszt");
		String xmsx = (String)request.getParameter("xmsx");
		String xmyt = (String)request.getParameter("xmyt");
		String xmxz = (String)request.getParameter("xmxz");
		HashMap hm = new HashMap();
		if (null != qxbm)
		hm.put("qxbm",qxbm);
		if (null != jszt)
		hm.put("jszt",jszt);
		if (null != xmsx)
		hm.put("xmsx",xmsx);
		if (null != xmyt)
		hm.put("xmyt",xmyt);
		if (null != xmxz)
		hm.put("xmxz",xmxz);
		List<HashMap> listLYXM = DBOperate.getLYXMGL(hm);
		hmRet.put("status", 200);
		hmRet.put("rows", listLYXM);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}
	
	public void lvxmTotal(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		HashMap hmRet = new HashMap();
		checkUser(request,response);
		String qxbm = (String)request.getParameter("qxbm");
		HashMap hm = new HashMap();
		if (null != qxbm)
		hm.put("qxbm",qxbm);
		List<HashMap> listLYXM = DBOperate.getLYXMTotal(hm);
		if(!CollectionUtils.isEmpty(listLYXM)){
			for(HashMap h : listLYXM){
				hmRet.put("qxbm", h.get("qxbm"));
				hmRet.put("jszt","已完成");
				Integer wgs = DBOperate.getLYXMWGS(hmRet);
				h.put("wgs", wgs);
			}
		}
		hmRet.put("status", 200);
		hmRet.put("rows", listLYXM);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}

	private void checkUser (HttpServletRequest request,HttpServletResponse response) throws IOException {
		HashMap hmRet = new HashMap();
		SysUser user = (SysUser)request.getSession().getAttribute(GlobalConst.SESS_ATTR_SYSUSER);
		if (null == user){
			hmRet.put("status", 400);
			hmRet.put("msg", "请登录后操作！");
			String json = JSONObject.fromObject(hmRet).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));
			return;
		}
	}
}
