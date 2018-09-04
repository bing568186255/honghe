package DragonNet.Dao;

import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapClientUtil {
	
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
	
	public static SqlMapClient getSqlMapClient() {
		return sqlMapClient ;
	}
}
