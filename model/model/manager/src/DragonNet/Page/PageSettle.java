package DragonNet.Page;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.io.*;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import net.sf.json.*;
import DragonNet.Global.*;
import DragonNet.Dao.*;

public class PageSettle extends HttpServlet {
	private static final long serialVersionUID = 2L;
	
	protected void doRequest(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		String strAction = request.getParameter("action");
		if (null == strAction || strAction.isEmpty())
			strAction = "error";
		try{
			Class[] paraClass = new Class[]{HttpServletRequest.class,HttpServletResponse.class};
			Method method = this.getClass().getMethod(strAction, paraClass);
			if (null == method){
				strAction = "error";
				method = this.getClass().getMethod(strAction, paraClass);
			}
			if (method != null){
				method.invoke(this,new Object[]{request,response});
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
			request.getRequestDispatcher("error.jsp").forward(request, response);
		}
		return;
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doRequest(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doRequest(request,response);
	}
	
	//错误页面
	public void error(HttpServletRequest request,HttpServletResponse response)
		throws ServletException,IOException{
		String errorMsg = (String)request.getAttribute("errorMsg");
		if (null == errorMsg)
			request.setAttribute("errorMsg", "请登录后访问！");
		request.getRequestDispatcher("error.jsp").forward(request, response);
		return;
	}
	
	//设置位置或多边形区域
	public void setptpoligon(HttpServletRequest request,HttpServletResponse response)
		throws ServletException,IOException{
		String table = (String)request.getParameter("table");
		String field = (String)request.getParameter("field");
		String id = (String)request.getParameter("id");
		String power = (String)request.getParameter("power");
		int editType = 0;		//只选择位置
		boolean error = false;
		if (null == table || table.isEmpty() || null == id || id.isEmpty()){
			error = true;
		}else{
			table = table.toUpperCase();
			if(!table.equals("HHLY_LYXM")){
				if (null == field || field.isEmpty()){
					error = true;
				}else{
					field = field.toUpperCase();
					if (field.indexOf("ZB")<0 && field.indexOf("GPS")<0){
						//防止字段名称搞错或恶意攻击
						error = true;
					}
				}
			}else{
				editType = 1;		//位置和范围
			}
		}
		if (error){
			request.setAttribute("errorMsg", "参数错误");
			error(request,response);
			return;
		}
		if (null == request.getSession().getAttribute(GlobalConst.SESS_ATTR_SYSUSER)){
			if (SysUser.checkLogin()){
				request.getSession().setAttribute(GlobalConst.SESS_ATTR_SYSUSER,new SysUser());
			}else{
				request.setAttribute("errorMsg", "当前没有登录，请登录后访问！");
				error(request,response);
				return;
			}
		}
		if (null != power && power.equals("edit")){
			request.setAttribute("edit", true);
		}
		HashMap hm = new HashMap();
		hm.put("table",table);
		hm.put("field",field);
		hm.put("id",id);
		//用随机数保存参数，避免在页面提交更新是直接用表名和字段名，提高安全性
		String ramdom = GlobalFun.GetRandString(32);
		request.getSession().setAttribute(ramdom, hm);
		request.setAttribute("editInfo", ramdom);
		request.setAttribute("editType", editType);
		request.getRequestDispatcher("setptpoligon.jsp").forward(request, response);
		return;
	}
}
