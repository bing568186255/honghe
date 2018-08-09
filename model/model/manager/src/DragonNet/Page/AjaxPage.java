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

public class AjaxPage extends HttpServlet {
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
		List<HashMap> listLYXM = DBOperate.getLYXM(hm);
		hmRet.put("status", 200);
		hmRet.put("rows", listLYXM);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}

	public void lvxmEchart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String type = (String) request.getParameter("type");
		HashMap hm = new HashMap();
		if (null != type) {
			hm.put("type", type);
			List<HashMap> listLYXM = DBOperate.getChartData(hm, type);
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

	private List<String> buildQueryList(String obj) {
		String[] sArray = obj.split(",");
		List<String> list = Arrays.asList(sArray);
		return list;
	}

	public void lvxmList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String qxbm = (String) request.getParameter("qxbm");
		String jszt = (String) request.getParameter("jszt");
		String xmsx = (String) request.getParameter("xmsx");
		String xmyt = (String) request.getParameter("xmyt");
		String xmxz = (String) request.getParameter("xmxz");
		HashMap hm = new HashMap();
		if (null != qxbm)
			hm.put("qxbm", buildQueryList(qxbm));
		if (null != jszt)
			hm.put("jszt", buildQueryList(jszt));
		if (null != xmsx)
			hm.put("xmsx", buildQueryList(xmsx));
		if (null != xmyt)
			hm.put("xmyt", buildQueryList(xmyt));
		if (null != xmxz)
			hm.put("xmxz", buildQueryList(xmxz));
		List<HashMap> listLYXM = DBOperate.getLYXMGL(hm);
		hmRet.put("status", 200);
		hmRet.put("rows", listLYXM);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}

	public void lvxmTotal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String qxbm = (String) request.getParameter("qxbm");
		HashMap hm = new HashMap();
		if (null != qxbm)
			hm.put("qxbm", qxbm);
		List<HashMap> listLYXM = DBOperate.getLYXMTotal(hm);
		if (!CollectionUtils.isEmpty(listLYXM)) {
			for (HashMap h : listLYXM) {
				hmRet.put("qxbm", h.get("qxbm"));
				hmRet.put("jszt", "已完成");
				HashMap wgs = DBOperate.getLYXMWGS(hmRet);
				h.put("wgs", wgs == null ? 0 : wgs.get("wgs"));
				List<HashMap> qxList = DBOperate.getQXinfo(hmRet);
				if (!CollectionUtils.isEmpty(qxList)) {
					h.put("qxmc", qxList.get(0).get("mc"));
				}
			}
		}
		hmRet.put("status", 200);
		hmRet.put("rows", listLYXM);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}

	public void lvxmXR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String qxbm = (String) request.getParameter("qxbm");
		String jszt = (String) request.getParameter("jszt");
		String xmsx = (String) request.getParameter("xmsx");
		String xmyt = (String) request.getParameter("xmyt");
		String xmxz = (String) request.getParameter("xmxz");
		HashMap hm = new HashMap();
		if (StringUtils.isNotBlank(qxbm))
			hm.put("qxbm", buildQueryList(qxbm));
		if (StringUtils.isNotBlank(jszt))
			hm.put("jszt", buildQueryList(URLDecoder.decode(jszt)));
		if (StringUtils.isNotBlank(xmsx))
			hm.put("xmsx", buildQueryList(URLDecoder.decode(xmsx)));
		if (StringUtils.isNotBlank(xmyt))
			hm.put("xmyt", buildQueryList(URLDecoder.decode(xmyt)));
		if (StringUtils.isNotBlank(xmxz))
			hm.put("xmxz", buildQueryList(URLDecoder.decode(xmxz)));
		List<HashMap> listLYXMTotal = DBOperate.getLYXMTotal(hm);
		// 返回的区县数据
		List<HashMap<String, Object>> positionReturn = new ArrayList<>();
		if (!CollectionUtils.isEmpty(listLYXMTotal)) {
			int index = 1;
			for (HashMap map : listLYXMTotal) {
				// 查询区县名称；
				hm.put("qxbm", map.get("qxbm"));
				List<HashMap> qxList = DBOperate.getQXinfo(hm);
				if (!CollectionUtils.isEmpty(qxList)) {
					map.put("qxmc", qxList.get(0).get("mc"));
				}
				HashMap item = new HashMap();
				HashMap position = new HashMap();
				String[] s = getCoordinate(map.get("qxmc").toString());
				position.put("longitude", s[0]);
				position.put("latitude", s[1]);
				item.put("position", position);
				item.put("title", map.get("qxmc"));
				item.put("xmsl", formatNum(map.get("xmzs")));
				item.put("qxbm", map.get("qxbm"));
				List<HashMap<String, Object>> parentList = new ArrayList<>();
				// 项目总数
				HashMap<String, Object> firstLi = new HashMap<>();
				firstLi.put("id", index);
				firstLi.put("itemname", "项目数量");
				firstLi.put("itemcount", formatNum(map.get("xmzs")));
				firstLi.put("itemdesign", formatNum(map.get("xmztz")));
				firstLi.put("itemactually", formatNum(map.get("sjljtz")));
				firstLi.put("jszt", "");
				parentList.add(firstLi);
				HashMap<String, Object> secondeLi = new HashMap<>();
				hm.put("jszt", "已完成");
				HashMap wgs = DBOperate.getLYXMWGS(hm);
				secondeLi.put("completename", "完工数");
				secondeLi.put("completeNum", wgs == null ? 0 : formatNum(wgs.get("xmzs")));
				secondeLi.put("completedesign", wgs == null ? 0 : formatNum(wgs.get("xmztz")));
				secondeLi.put("completeactually", wgs == null ? 0 : formatNum(wgs.get("sjljtz")));
				secondeLi.put("jszt", "已完成");
				parentList.add(secondeLi);
				HashMap<String, Object> thirdLi = new HashMap<>();
				hm.put("jszt", "建设中");
				HashMap wwc = DBOperate.getLYXMWGS(hm);
				thirdLi.put("bulidname", "建设数量");
				thirdLi.put("bulidNum", wgs == null ? 0 : formatNum(wgs.get("xmzs")));
				thirdLi.put("buliddesign", wgs == null ? 0 : formatNum(wgs.get("xmztz")));
				thirdLi.put("bulidactually", wgs == null ? 0 : formatNum(wgs.get("sjljtz")));
				thirdLi.put("jszt", "建设中");
				parentList.add(thirdLi);
				HashMap<String, Object> fourLi = new HashMap<>();
				fourLi.put("planningname", "计划数量");
				fourLi.put("planningNum",
						jisuanNum(firstLi.get("itemcount"), secondeLi.get("completeNum"), thirdLi.get("bulidNum")));
				fourLi.put("buliddesign", jisuanNum(firstLi.get("itemdesign"), secondeLi.get("completedesign"),
						thirdLi.get("buliddesign")));
				fourLi.put("bulidactually", jisuanNum(firstLi.get("itemactually"), secondeLi.get("completeactually"),
						thirdLi.get("bulidactually")));
				fourLi.put("jszt", "计划中");
				parentList.add(fourLi);
				item.put("list", parentList);
				index++;
				// 县下面旅游项目集合构建
				hm.remove("jszt");
			/*	if (StringUtils.isNotBlank(jszt))
					hm.put("jszt", jszt);*/
				List<HashMap> listLYXM = DBOperate.getLYXMGL(hm);
				if (!CollectionUtils.isEmpty(listLYXM)) {
					List<HashMap<String, Object>> subList = new ArrayList<>();
					int ii = 1;
					for (HashMap hMap : listLYXM) {
						if (hMap != null) {
							HashMap subItem = new HashMap();
							String[] positionArray = hMap.get("xmzb").toString().split(",");
							HashMap subPosition = new HashMap();
							subPosition.put("longitude", positionArray[0]);
							subPosition.put("latitude", positionArray[1]);
							subItem.put("position", subPosition);
							subItem.put("title", hMap.get("xmm"));
							subItem.put("jszt", hMap.get("jszt"));
							subItem.put("ztz", hMap.get("ztz"));
							List<HashMap<String, Object>> subTKList = new ArrayList<>();
							HashMap<String, Object> subFirstLi = new HashMap<>();
							subFirstLi.put("id", ii);
							subFirstLi.put("itemnature", hMap.get("xmgk"));
							subFirstLi.put("itemtype", hMap.get("xmlx"));
							subFirstLi.put("itmeproperty", hMap.get("xmsx"));
							subFirstLi.put("itemformat", hMap.get("xmyt"));
							subFirstLi.put("planningland", hMap.get("ghyd"));
							subFirstLi.put("importantitem", hMap.get("xmxz"));
							subFirstLi.put("itemstate", hMap.get("jszt"));
							subTKList.add(subFirstLi);
							HashMap<String, Object> subSecondeLi = new HashMap<>();
							subSecondeLi.put("completename", "项目类型");
							subSecondeLi.put("completeNum", "");
							subSecondeLi.put("completedesign", "");
							subSecondeLi.put("completeactually", "");
							subTKList.add(subSecondeLi);
							HashMap<String, Object> subThirdLi = new HashMap<>();
							subThirdLi.put("bulidname", "项目业态");
							subThirdLi.put("bulidNum", "");
							subThirdLi.put("buliddesign", "");
							subThirdLi.put("bulidactually", "");
							subTKList.add(subThirdLi);
							HashMap<String, Object> subFourLi = new HashMap<>();
							subFourLi.put("planningname", "项目建设状态");
							subFourLi.put("planningNum", "");
							subFourLi.put("buliddesign", "");
							subFourLi.put("bulidactually", "");
							subTKList.add(subFourLi);
							subItem.put("list", subTKList);
							subList.add(subItem);
							ii++;
						}
					}
					item.put("subList", subList);
				}
				positionReturn.add(item);
			}
		}
		// List<HashMap> listLYXM = DBOperate.getLYXMGL(hm);
		hmRet.remove("jszt");
		hmRet.remove("qxbm");
		hmRet.put("status", 200);
		hmRet.put("rows", positionReturn);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}

	private int formatNum(Object obj) {
		if (obj == null || "".equals(obj)) {
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}

	private int jisuanNum(Object obj1, Object obj2, Object obj3) {
		return Integer.parseInt(obj1.toString()) - Integer.parseInt(obj2.toString())
				- Integer.parseInt(obj3.toString());
	}

	/**
	 * @param addr
	 *            查询的地址
	 * @return
	 * @throws IOException
	 */
	public static String[] getCoordinate(String addr) throws IOException {
		String lng = null;// 经度
		String lat = null;// 纬度
		String address = null;
		try {
			address = java.net.URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// System.out.println(address);
		String url = "http://api.map.baidu.com/geocoder/v2/?output=json&ak=iV88vKWCxcFd0XkPBT6G0xBG8Fa1Geim&address="
				+ address;
		URL myURL = null;

		URLConnection httpsConn = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				while ((data = br.readLine()) != null) {
					JSONObject json = JSONObject.fromObject(data);
					lng = json.getJSONObject("result").getJSONObject("location").getString("lng");
					lat = json.getJSONObject("result").getJSONObject("location").getString("lat");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (insr != null) {
				insr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return new String[] { lng, lat };
	}

	public static void main(String[] args) throws IOException {
		System.out.println(getCoordinate("红河县"));
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
