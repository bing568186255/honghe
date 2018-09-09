package DragonNet.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import DragonNet.Dao.DBOperate;
import DragonNet.Dao.JQDBOperate;
import DragonNet.Global.GlobalConst;
import DragonNet.Global.GlobalFun;
import DragonNet.Global.SysUser;
import net.sf.json.JSONObject;

public class JQPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String strAction = req.getParameter(GlobalConst.JINGDIAN_PARAM_NAME);
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


	/**
	 * 旅游项目景区展示数据
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void lvxmJQ(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		HashMap hm = new HashMap();
		List<HashMap> qxList = DBOperate.getQXinfo(hm);
		List<HashMap<String, Object>> resp = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(qxList)) {
			for (HashMap qxmap : qxList) {
				HashMap<String, Object> item = new HashMap<>();
				item.put("qxmc", qxmap.get("MC"));
				item.put("qxbm",qxmap.get("BM"));
				HashMap hm0 = new HashMap();
				hm0.put("qxbm", qxmap.get("BM"));
				List<HashMap<String, Object>> QuXianRs = JQDBOperate.getQXJQRS(hm0);
				item.put("qxrs", QuXianRs);
				
				HashMap hm1 = new HashMap();
				hm1.put("qxbm", qxmap.get("BM"));
				//根据获取某县中景区 
				List<HashMap<String, Object>> QuXianJQs = JQDBOperate.getLVXMJQ(hm1);
				if(QuXianJQs==null) {
					item.put("jqsl", 0);
				}else {
					item.put("jqsl", QuXianJQs.size());
					for (HashMap<String, Object> qxJq : QuXianJQs) {
						String jqid = qxJq.get("ID").toString();
						String jqzb = qxJq.get("JQZB").toString();
						String [] strArr = jqzb.split(",");
						HashMap position = new HashMap();
						position.put("longitude", strArr[0]);
						position.put("latitude", strArr[1]);
						qxJq.put("JQZB", position);
						if(StringUtils.isNotBlank(jqid)) {
							HashMap hm2 = new HashMap();
							hm2.put("jqid", jqid);
							List<HashMap<String, Object>> jqRs = JQDBOperate.getJQRS(hm2);
							qxJq.put("jqrs", jqRs);
						}
					}
					
				}
				item.put("jqs", QuXianJQs);
				HashMap position = new HashMap();
				try {
					String[] s = GlobalFun.getCoordinate(qxmap.get("MC").toString());
					position.put("longitude", s[0]);
					position.put("latitude", s[1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				item.put("position", position);
				resp.add(item);
			}
			
		}
		hmRet.put("status", 200);
		hmRet.put("rows", resp);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}

	private void checkUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap hmRet = new HashMap();
		SysUser user = (SysUser) request.getSession().getAttribute(GlobalConst.SESS_ATTR_SYSUSER);
		/*
		 * if (null == user){ hmRet.put("status", 400); hmRet.put("msg", "请登录后操作！");
		 * String json = JSONObject.fromObject(hmRet).toString();
		 * response.getOutputStream().write(json.getBytes("UTF-8")); return; }
		 */
	}
	
	

}
