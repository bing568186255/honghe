package DragonNet.Page;

import java.io.IOException;
import java.lang.reflect.Method;
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
import DragonNet.Dao.HotelDBOperate;
import DragonNet.Global.GPSenums;
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
	
	/**
	 * 酒店餐饮旅行社地图接口  1旅行社、2酒店、3餐饮
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void lvxmHotel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HashMap hmRet = new HashMap();
		checkUser(request, response);
		String type = (String) request.getParameter("type");
		HashMap hm = new HashMap();
		hm.put("type", buildQueryList(type));
		List<HashMap> qxList = DBOperate.getQXinfo(hm);
		List<HashMap<String, Object>> positionReturn = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(qxList)) {
			for (HashMap qxmap : qxList) {
				hm.put("qxbm", qxmap.get("BM"));
				HashMap map = new HashMap<>();
				map.put("qxmc", qxmap.get("MC"));
				map.put("qxbm", qxmap.get("BM"));
				HashMap position = new HashMap();
				try {
					String[] s = GlobalFun.getCoordinate(qxmap.get("MC").toString());
					position.put("longitude", s[0]);
					position.put("latitude", s[1]);
					map.put("position", position);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//该县下面的1旅行社、2酒店、3餐饮信息
				List<HashMap> hotelList = HotelDBOperate.getHotelInfo(hm);
				if(!CollectionUtils.isEmpty(hotelList)){
					for(HashMap mm : hotelList){
						mm.put("qxmc", qxmap.get("MC"));
					}
					map.put("list", hotelList);
				}
				buildSumInfo(qxmap.get("BM").toString(),qxmap.get("MC").toString(),type,map);
				positionReturn.add(map);
			}
		}
		hmRet.put("status", 200);
		hmRet.put("rows", positionReturn);
		String json = JSONObject.fromObject(hmRet).toString();
		response.getOutputStream().write(json.getBytes("UTF-8"));

	}
	
	/**
	 * 根据类型构建汇总信息 1旅行社、2酒店、3餐饮信息
	 * @param bm
	 * @param type
	 * @param map
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void buildSumInfo(String bm,String mc,String type,HashMap map) throws IOException{
		System.out.println(mc);
		HashMap hset = new HashMap<>();
		hset.put("qxbm", bm);
		if(StringUtils.isEmpty(type)){
			//旅行社
			HashMap lxyMap = new HashMap<>();
			hset.put("type", 1);
			List<HashMap> lxsList = HotelDBOperate.sumHotel(hset);
			if(!CollectionUtils.isEmpty(lxsList)){
				lxyMap.put("position", GPSenums.getMap(mc, "1"));
				lxyMap.put("lxsList", lxsList);
				map.put("lxyMap", lxyMap);
			}
			HashMap jdMap = new HashMap<>();
			hset.put("type", 2);
			List<HashMap> jdList = HotelDBOperate.sumHotel(hset);
			if(!CollectionUtils.isEmpty(jdList)){
				jdMap.put("position", GPSenums.getMap(mc, "2"));
				jdMap.put("jdList", jdList);
				map.put("jdMap", jdMap);
			}
			HashMap cyMap = new HashMap<>();
			hset.put("type", 3);
			List<HashMap> cyList = HotelDBOperate.sumHotel(hset);
			if(!CollectionUtils.isEmpty(cyList)){
				cyMap.put("position", GPSenums.getMap(mc, "3"));
				cyMap.put("cyList", cyList);
				map.put("cyMap", cyMap);
			}
			
		}
		if("1".equals(type)){
			HashMap lxyMap = new HashMap<>();
			hset.put("type", 1);
			List<HashMap> lxsList = HotelDBOperate.sumHotel(hset);
			if(!CollectionUtils.isEmpty(lxsList)){
				lxyMap.put("position",  GPSenums.getMap(mc, "1"));
				lxyMap.put("lxsList", lxsList);
				map.put("lxyMap", lxyMap);
			}
		}
		if("2".equals(type)){
			HashMap jdMap = new HashMap<>();
			hset.put("type", 2);
			List<HashMap> jdList = HotelDBOperate.sumHotel(hset);
			if(!CollectionUtils.isEmpty(jdList)){
				jdMap.put("position", GPSenums.getMap(mc, "2"));
				jdMap.put("jdList", jdList);
				map.put("jdMap", jdMap);
			}
		}
		if("3".equals(type)){
			HashMap cyMap = new HashMap<>();
			hset.put("type", 3);
			List<HashMap> cyList = HotelDBOperate.sumHotel(hset);
			if(!CollectionUtils.isEmpty(cyList)){
				cyMap.put("position", GPSenums.getMap(mc, "3"));
				cyMap.put("cyList", cyList);
				map.put("cyMap", cyMap);
			}
		}
	}
	
	
	private List<String> buildQueryList(String obj) {
		List<String> list = new ArrayList<>();
		if(StringUtils.isEmpty(obj)){
			list.add("1");
			list.add("2");
			list.add("3");
			return list;
		}
		String[] sArray = obj.split(",");
	    list = Arrays.asList(sArray);
		return list;
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
