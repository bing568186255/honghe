package DragonNet.Dao;

import java.util.HashMap;
import java.util.List;

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
			} catch (Exception e) {
				e.printStackTrace();
			}
			return hmList;
		}
}
