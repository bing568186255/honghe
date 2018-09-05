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
/*	public void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	}*/

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
	
	public void lvxmXMKFQK(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String id = (String) request.getParameter("xmid");
		String xmrq = (String) request.getParameter("xmrq");
		HashMap hm = new HashMap();
		if(id!=null) {
			hm.put("id", id);
		}
		if(xmrq!=null) {
			hm.put("xmrq", xmrq);
		}
		System.out.println(id);
		List<HashMap> listXMKFQK= DBOperate.getXMKFQK(hm);
		hmRet.put("status", 200);
		hmRet.put("rows", listXMKFQK);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}
	
	public void lvxmXCTBYDW(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String xmid = (String) request.getParameter("xmid");
		String dw = (String) request.getParameter("dw");
		HashMap hm = new HashMap();
		if(xmid!=null) {
			hm.put("xmid", xmid);
		}
		if(dw!=null) {
			hm.put("dw", dw);
		}
		List<HashMap> listXCTBYDW = DBOperate.getXCTBYDW(hm);
		hmRet.put("status", 200);
		hmRet.put("rows", listXCTBYDW);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
		
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

	/**
	 * 市县旅游项目地图显示接口
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public void lvxmXR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String jszt = (String) request.getParameter("jszt");
		String xmsx = (String) request.getParameter("xmsx");
		String xmyt = (String) request.getParameter("xmyt");
		String xmxz = (String) request.getParameter("xmxz");
		HashMap hm = new HashMap();
		List<String> jsztList = new ArrayList<>();
		if (StringUtils.isNotBlank(jszt)) {
			jsztList = buildQueryList(URLDecoder.decode(jszt));
			hm.put("jszt", jsztList);
		}
		if (StringUtils.isNotBlank(xmsx))
			hm.put("xmsx", buildQueryList(URLDecoder.decode(xmsx)));
		if (StringUtils.isNotBlank(xmyt))
			hm.put("xmyt", buildQueryList(URLDecoder.decode(xmyt)));
		if (StringUtils.isNotBlank(xmxz))
			hm.put("xmxz", buildQueryList(URLDecoder.decode(xmxz)));
		List<HashMap> qxList = DBOperate.getQXinfo(hm);
		List<HashMap<String, Object>> positionReturn = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(qxList)) {
			for (HashMap qxmap : qxList) {
				hm.put("qxbm", qxmap.get("BM"));
				List<HashMap> listLYXMTotal = DBOperate.getLYXMTotal(hm);
				HashMap map = new HashMap<>();
				map.put("qxmc", qxmap.get("MC"));
				HashMap item = new HashMap();
				HashMap position = new HashMap();
				try {
					String[] s = GlobalFun.getCoordinate(qxmap.get("MC").toString());
					position.put("longitude", s[0]);
					position.put("latitude", s[1]);
					item.put("position", position);
				} catch (Exception e) {
					e.printStackTrace();
				}
				item.put("title", qxmap.get("MC"));
				item.put("xmsl", 0);
				item.put("qxbm", qxmap.get("BM"));
				// 返回的区县数据
				if (!CollectionUtils.isEmpty(listLYXMTotal)) {
					int index = 1;
					HashMap totalMap = listLYXMTotal.get(0);
					map.putAll(totalMap);
					item.put("xmsl", formatNum(map.get("xmzs")));
					List<HashMap<String, Object>> parentList = new ArrayList<>();
					// 项目总数
					HashMap<String, Object> firstLi = buildMap("id,itemname,itemcount,itemdesign,itemactually",
							index+","+"项目数量"+","+formatNum(map.get("xmzs"))+","+formatNum(map.get("xmztz"))+","+formatNum(map.get("sjljtz")));
					parentList.add(firstLi);
					HashMap<String, Object> secondeLi = new HashMap<>();
					if (!CollectionUtils.isEmpty(jsztList) && !jsztList.contains("已完成")) {
						secondeLi = buildMap("completename,completeNum,completedesign,completeactually,jszt",
								"完工数"+","+0+","+0+","+0+","+ "已完成");
					} else {
						hm.put("jszt", "已完成");
						HashMap wgs = DBOperate.getLYXMWGS(hm);
						secondeLi = buildMap("completename,completeNum,completedesign,completeactually,jszt",
								"完工数"+","+(wgs == null ? 0 : formatNum(wgs.get("xmzs")))+","
						+(wgs == null ? 0 : formatNum(wgs.get("xmztz")))+","+(wgs == null ? 0 : formatNum(wgs.get("sjljtz")))+","+ "已完成");
					}
					parentList.add(secondeLi);
					HashMap<String, Object> thirdLi = new HashMap<>();
					if (!CollectionUtils.isEmpty(jsztList) && !jsztList.contains("建设中")) {
						thirdLi = buildMap("bulidname,bulidNum,buliddesign,bulidactually,jszt",
								"建设数量"+","+0+","+0+","+0+","+ "建设中");
					} else {
						hm.put("jszt", "建设中");
						HashMap wwc = DBOperate.getLYXMWGS(hm);
						thirdLi = buildMap("bulidname,bulidNum,buliddesign,bulidactually,jszt",
								"建设数量"+","+(wwc == null ? 0 : formatNum(wwc.get("xmzs")))+","
						+(wwc == null ? 0 : formatNum(wwc.get("xmztz")))+","+(wwc == null ? 0 : formatNum(wwc.get("sjljtz")))+","+ "建设中");
					}
					parentList.add(thirdLi);
					HashMap<String, Object> fourLi = new HashMap<>();
					if (!CollectionUtils.isEmpty(jsztList) && !jsztList.contains("计划中")) {
						fourLi = buildMap("planningname,planningNum,buliddesign,bulidactually,jszt",
								"计划数量"+","+0+","+0+","+0+","+ "计划中");
					} else {
						hm.put("jszt", "计划中");
						HashMap jhz = DBOperate.getLYXMWGS(hm);
						fourLi = buildMap("planningname,planningNum,buliddesign,bulidactually,jszt",
								"计划数量"+","+(jhz == null ? 0 : formatNum(jhz.get("xmzs")))
								+","+(jhz == null ? 0 : formatNum(jhz.get("xmztz")))
								+","+(jhz == null ? 0 : formatNum(jhz.get("sjljtz")))+","+ "计划中");
					}
					parentList.add(fourLi);
					item.put("list", parentList);
					index++;
					// 县下面旅游项目集合构建
					hm.remove("jszt");
					if (StringUtils.isNotBlank(jszt)) {
						jsztList = buildQueryList(URLDecoder.decode(jszt));
						hm.put("jszt", jsztList);
					}
					List<HashMap> listLYXM = DBOperate.getLYXMGL(hm);
					if (!CollectionUtils.isEmpty(listLYXM)) {
						item.put("subList", buildSubList(listLYXM));
					}
					//}
				}
				positionReturn.add(item);
			}
			hmRet.remove("jszt");
			hmRet.remove("qxbm");
		}
		hmRet.put("status", 200);
		hmRet.put("rows", positionReturn);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));
	}
	
	/**
	 * 构建具体某个县项目
	 * @param listLYXM
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private List<HashMap<String, Object>> buildSubList (List<HashMap> listLYXM){
		List<HashMap<String, Object>> subList = new ArrayList<>();
		int ii = 1;
		for (HashMap hMap : listLYXM) {
			if (hMap != null) {
				HashMap subItem = new HashMap();
				String[] positionArray = hMap.get("XMZB").toString().split(",");
				HashMap subPosition = new HashMap();
				subPosition.put("longitude", positionArray[0]);
				subPosition.put("latitude", positionArray[1]);
				subItem.put("position", subPosition);
				subItem.put("title", hMap.get("XMM"));
				subItem.put("jszt", hMap.get("JSZT"));
				subItem.put("ztz", hMap.get("ZTZ"));	
				List<HashMap<String, Object>> subTKList = new ArrayList<>();
				HashMap<String, Object> subFirstLi =buildMap("id,itemnature,itemtype,itmeproperty,itemformat,planningland,importantitem,itemstate",
						ii+","+ hMap.get("ZDXMXZJWH")+","+hMap.get("XMLX")+","+hMap.get("XMSX")+","+ hMap.get("XMYT")+","+hMap.get("GHYD")
						+","+hMap.get("XMXZ")+","+hMap.get("JSZT"));
				subTKList.add(subFirstLi);
				subItem.put("list", subTKList);
				subList.add(subItem);
				ii++;
			}
		}
		return subList;
	}
	
	
	/**
	 * 封装map
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap buildMap(String key,String value){
		if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return null;
		}
		String [] keyArr = key.split(",");
		String [] valueArr = value.split(",");
		if(keyArr.length != valueArr.length){
			return null;
		}
		HashMap map = new HashMap();
		for(int i = 0 ;i<keyArr.length; i++){
			map.put(keyArr[i], valueArr[i]);
		}
		return map;
	}

	private int formatNum(Object obj) {
		if (obj == null || "".equals(obj)) {
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}

	@SuppressWarnings("unused")
	private int jisuanNum(Object obj1, Object obj2, Object obj3) {
		return Integer.parseInt(obj1.toString()) - Integer.parseInt(obj2.toString())
				- Integer.parseInt(obj3.toString());
	}

	public static void main(String[] args) throws IOException {
		System.out.println(GlobalFun.getCoordinate("红河县"));
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void xmDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String xmmc = (String) request.getParameter("xmmc");
		if ( null == xmmc || xmmc.isEmpty()) {
			hmRet.put("status", 400);
			hmRet.put("msg", "参数错误！");
			String json = JSONObject.fromObject(hmRet).toString();
			response.getOutputStream().write(json.getBytes("UTF-8"));
			return;
		}
		HashMap hm = new HashMap();
		hm.put("xmmc", URLDecoder.decode(xmmc));
		HashMap xmDetail = DBOperate.xmDetail(hm);
		if(xmDetail != null && xmDetail.size()>0){
			for(Object key: xmDetail.keySet()){
				if(xmDetail.get(key) == null){
					xmDetail.put(key, "");
				}
			}
		}
		hmRet.put("status", 200);
		hmRet.put("rows", xmDetail);
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
