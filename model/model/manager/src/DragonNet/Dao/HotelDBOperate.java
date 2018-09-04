package DragonNet.Dao;

import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

public class HotelDBOperate {
	private static SqlMapClient sqlMapClient = SqlMapClientUtil.getSqlMapClient();
	
	// 根据类型获取各统计chart数据
	public static List<HashMap> getChartData(HashMap hm, String type) {
		List<HashMap> hmList = null;
		try {
			if (type.equals("xms")) {
				hmList = (List<HashMap>) sqlMapClient.queryForList("getXMSTJ", hm);
			} else if (type.equals("xmmj")) {
				hmList = (List<HashMap>) sqlMapClient.queryForList("getXMMJTJ", hm);
			} else if (type.equals("jszt")) {
				hmList = (List<HashMap>) sqlMapClient.queryForList("getJSZTTJ", hm);
			} else {
				hmList = (List<HashMap>) sqlMapClient.queryForList("getXMSXTJ", hm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hmList;
	}
}
