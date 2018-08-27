package DragonNet.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import DragonNet.Dao.DBOperate;
import DragonNet.Global.GlobalConst;
import DragonNet.Global.GlobalFun;
import DragonNet.Global.SysUser;
import net.sf.json.JSONObject;

public class AJAXzwwPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBOperate dbOperate = null;

	protected void doRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strAction = request.getParameter(GlobalConst.AJAX_PARAM_NAME);
		HashMap hm = new HashMap();
		if (strAction == null || strAction.isEmpty()) {
			GlobalFun.WarningOut("strAction is empty in ajaxpage");
			hm.put("status", 400);
			hm.put("msg", "parameter is wrong!");
			String json = JSONObject.fromObject(hm).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));
			return;
		}
		response.setContentType(GlobalConst.RESP_CONTENT_TYPE);
		response.setCharacterEncoding(GlobalConst.RESP_CHAR_ENCODING);
		try {
			Method method = this.getClass().getMethod(strAction,
					new Class[] { HttpServletRequest.class, HttpServletResponse.class });
			if (null == method) {
				hm.put("status", 400);
				hm.put("msg", "no action method!");
			} else {
				method.invoke(this, new Object[] { request, response });
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("status", 400);
			hm.put("msg", e.getMessage());
		}
		String json = JSONObject.fromObject(hm).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
		return;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	// ajaxTest
	public void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String type = (String) request.getParameter("type");
		String id = (String) request.getParameter("id");
		if (null == type || type.isEmpty() || null == id || id.isEmpty()) {
			hmRet.put("status", 400);
			hmRet.put("msg", "参数错误！");
			String json = JSONObject.fromObject(hmRet).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));
			return;
		}
		HashMap hm = new HashMap();
		if (null != type)
			hm.put("type", type);
		if (null != id)
			hm.put("id", id);
		//List<HashMap> listLYXM = DBOperate.getLYXM(hm);
		hmRet.put("status", 200);
		//hmRet.put("rows", listLYXM);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}

	private void checkUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap hmRet = new HashMap();
		SysUser user = (SysUser) request.getSession().getAttribute(GlobalConst.SESS_ATTR_SYSUSER);
		/*
		 * if (null == user){ hmRet.put("status", 400); hmRet.put("msg",
		 * "请登录后操作！"); String json = JSONObject.fromObject(hmRet).toString();
		 * response.getOutputStream().write(json.getBytes("UTF-8")); return; }
		 */
	}
}
