package DragonNet.Dao;

import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class DBOperate {
	private static SqlMapClient sqlMapClient = null;

	// 读取配置文件
	static {
		try {
			Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	// 读取旅游项目列表
	public static List<HashMap> getLYXM(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("getLYXM", hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hmList;
	}

	// 根据条件查询旅游项目概览
	public static List<HashMap> getLYXMGL(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("getLYXMGL", hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hmList;
	}

	// 1.2 各区县项目概览展示接口(项目数)
	public static List<HashMap> getLYXMTotal(HashMap hm) {
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>) sqlMapClient.queryForList("getLYXMTotal", hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hmList;
	}

	// 1.2 各区县项目完工数
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Integer getLYXMWGS(HashMap hm) {
		Integer wgs = 0;
		try {
			wgs = (Integer) sqlMapClient.queryForObject("getLYXMWGS", hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wgs;
	}
	
	// 1.3查询区县信息
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List<HashMap>  getQXinfo(HashMap hm) {
			List<HashMap> hmList = null;
			try {
				hmList =  sqlMapClient.queryForList("getQXinfo", hm);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return hmList;
		}
}
