package DragonNet.Page;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DragonNet.Dao.DBOperate;
import DragonNet.Dao.HotelDBOperate;
import DragonNet.Global.GlobalConst;
import DragonNet.Global.GlobalFun;
import DragonNet.Global.SysUser;
import net.sf.json.JSONObject;

public class HotelPage  extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String strAction = req.getParameter(GlobalConst.HOTEL_PARAM_NAME);
		HashMap hm = new HashMap();
		if (strAction == null || strAction.isEmpty()) {
			GlobalFun.WarningOut("strAction is empty in ajaxpage");
			hm.put("status", 400);
			hm.put("msg", "parameter is wrong!");
			String json = JSONObject.fromObject(hm).toString();
			resp.getOutputStream().write(json.getBytes("UTF-8"));
			return;
		}
		resp.setContentType(GlobalConst.RESP_CONTENT_TYPE);
		resp.setCharacterEncoding(GlobalConst.RESP_CHAR_ENCODING);
		try {
			Method method = this.getClass().getMethod(strAction,
					new Class[] { HttpServletRequest.class, HttpServletResponse.class });
			if (null == method) {
				hm.put("status", 400);
				hm.put("msg", "no action method!");
			} else {
				method.invoke(this, new Object[] { req, resp });
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			hm.put("status", 400);
			hm.put("msg", e.getMessage());
		}
		String json = JSONObject.fromObject(hm).toString();
		resp.getOutputStream().write(json.getBytes("UTF-8"));
		return;
	
	}
	
	public void lvxmEchart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String type = (String) request.getParameter("type");
		HashMap hm = new HashMap();
		if (null != type) {
			hm.put("type", type);
			List<HashMap> listLYXM = HotelDBOperate.getChartData(hm, type);
			hmRet.put("status", 200);
			hmRet.put("rows", listLYXM);
			String json = JSONObject.fromObject(hmRet).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));
		} else {
			hmRet.put("status", 400);
			hmRet.put("rows", null);
			String json = JSONObject.fromObject(hmRet).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));
		}

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
