package DragonNet.Dao;

import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class DBOperate {
	private static SqlMapClient sqlMapClient = null;
	
	// 读取配置文件
	static{
		try {
			Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//读取旅游项目列表
	public static List<HashMap> getLYXM(HashMap hm){
		List<HashMap> hmList = null;
		try {
			hmList = (List<HashMap>)sqlMapClient.queryForList("getLYXM",hm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hmList;
	}
}
