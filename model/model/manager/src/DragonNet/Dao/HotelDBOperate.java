package DragonNet.Dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ibatis.sqlmap.client.SqlMapClient;

public class HotelDBOperate {
	private static SqlMapClient sqlMapClient = SqlMapClientUtil.getSqlMapClient();

	// 根据类型获取旅行社、2酒店、3餐饮数据
	public static List<HashMap> getHotelInfo(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("getHotelInfo", hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmList;
	}

	// 根据类型获取区县旅行社、2酒店、3餐饮数据汇总星级汇总数据
	public static List<HashMap> sumHotel(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("sumHotel", hm);
			if(!CollectionUtils.isEmpty(hmList)){
				for(HashMap map : hmList){
					if(hm.get("lx") != null){
						String lx = hm.get("lx").toString();
						if("1".equals(lx)){
							map.put("pg", map.get("pg")==null?0:map.get("pg").toString()+"分");
						}
						if("5".equals(lx)){
							map.put("pg", map.get("pg")==null?0:map.get("pg").toString()+"星");
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmList;
	}

	// 根据对象查询指数
	public static List<HashMap> getZS(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("getZS", hm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmList;
	}
	
	// 根据对象查询指数
	public static List<HashMap> getHighSumInfo(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("getHighSumInfo", hm);
			if(!CollectionUtils.isEmpty(hmList)){
				for(HashMap map : hmList){
					if(hm.get("lx") != null){
						String lx = hm.get("lx").toString();
						if("1".equals(lx)){
							map.put("pg", map.get("pg").toString()+"分");
						}
						if("5".equals(lx)){
							map.put("pg", map.get("pg").toString()+"星");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmList;
	}
	
	// 根据对象查询指数
	public static List<HashMap> getLowSumInfo(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("getLowSumInfo", hm);
			if(!CollectionUtils.isEmpty(hmList)){
				for(HashMap map : hmList){
					if(hm.get("lx") != null){
						String lx = hm.get("lx").toString();
						if("1".equals(lx)){
							map.put("pg", map.get("pg").toString()+"分");
						}
						if("5".equals(lx)){
							map.put("pg", map.get("pg").toString()+"星");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmList;
	}
}
