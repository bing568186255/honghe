package DragonNet.Global;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;

import DragonNet.Dao.DBOperate;

public class SysUser {
	private String name;
	private String password;
	private int userType;
	private String realName;
	private String power;
	private Date createTime;
	private Date loginTime;
	private String remark;
	
	public String getName(){return name;}
	public void setName(String val){name = val;}
	public int getUserType(){return userType;}
	public void setUserType(int type){userType = type;}
	public void setPassword(String val){password = val;}
	public String getRealName(){return realName;}
	public void setRealName(String val){realName = val;}
	public String getPower(){return power;}
	public void setPower(String strPower){power = strPower;}
	public String getCreateTime(){
		return (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(createTime);			
	};
	public String getLoginTime(){
		return (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(loginTime);			
	};
	public String getRemark(){return remark;}
	public void setRemark(String rem){remark = rem;} 
	
	public static boolean checkLogin(){
		return true;
	}	
}
