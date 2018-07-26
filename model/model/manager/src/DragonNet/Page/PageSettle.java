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
	
	//测试页面
	public void test(HttpServletRequest request,HttpServletResponse response)
		throws ServletException,IOException{
		String objType = (String)request.getParameter("type");
		String objID = (String)request.getParameter("id");
		boolean error = false;
		int selType = 0;			//select point or polygon
		if (null == objType || objType.isEmpty() || null == objID || objID.isEmpty()){
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
		request.setAttribute("objType", objType);
		request.setAttribute("objID", objID);
		request.getRequestDispatcher("test.jsp").forward(request, response);
		return;
	}
}

