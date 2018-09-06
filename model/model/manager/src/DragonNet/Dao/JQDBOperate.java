package DragonNet.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

public class JQDBOperate {
	private static SqlMapClient sqlMapClient = SqlMapClientUtil.getSqlMapClient();
	
	public static List<HashMap<String, Object>> getLVXMJQ(HashMap<String, Object> hm) {
		List<HashMap<String, Object>> li = null ;
		try {
			li  = (List<HashMap<String, Object>>) sqlMapClient.queryForList("getJQ", hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
	
	public static List<HashMap<String, Object>> getJQRS(HashMap<String, Object> hm) {
		List<HashMap<String, Object>> li = null ;
		try {
			li  = (List<HashMap<String, Object>>) sqlMapClient.queryForList("getJQRS", hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
	
	public static List<HashMap<String, Object>> getQXJQRS(HashMap<String, Object> hm) {
		List<HashMap<String, Object>> li = null ;
		try {
			li  = (List<HashMap<String, Object>>) sqlMapClient.queryForList("getQXJQRS", hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
}
