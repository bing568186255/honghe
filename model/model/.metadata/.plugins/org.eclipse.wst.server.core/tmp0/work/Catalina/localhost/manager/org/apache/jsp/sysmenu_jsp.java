/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.29
 * Generated at: 2016-12-28 08:45:10 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class sysmenu_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"leftmenu\" id=\"leftmenu\">\r\n");
      out.write("\t<div id=\"accordion\">\r\n");
      out.write("\t    <div class=\"menugroup active\" name=\"setup\">\r\n");
      out.write("\t        <img class=\"icon\" src=\"images/setup.png\" />\r\n");
      out.write("\t        <span class=\"text\">街道设置</span>\r\n");
      out.write("\t        <span class=\"arrow\"></span>\r\n");
      out.write("\t    </div>\r\n");
      out.write("\t\t<div class=\"leftmenuitem\" id=\"areamanage\" group=\"setup\" style=\"padding-top: 20px;\">边界设置</div>\r\n");
      out.write("\t\t<div class=\"leftmenuitem\" group=\"setup\" id=\"community\">社区管理</div>\r\n");
      out.write("\t\t<div class=\"leftmenuitem\" group=\"setup\" id=\"communityarea\">社区边界</div>\r\n");
      out.write("\t    <div class=\"menugroup\" name=\"department\">\r\n");
      out.write("\t        <img class=\"icon\" src=\"images/department.png\" />\r\n");
      out.write("\t        <span class=\"text\">部门信息</span>\r\n");
      out.write("\t        <span class=\"arrow\"></span>\r\n");
      out.write("\t    </div>\r\n");
      out.write("\t\t<div class=\"leftmenuitem\" group=\"department\" id=\"threedef\" style=\"display:none;padding-top: 20px;\">三防信息</div>\r\n");
      out.write("\t    <div class=\"menugroup\" name=\"system\">\r\n");
      out.write("\t        <img class=\"icon\" src=\"images/system.png\" />\r\n");
      out.write("\t        <span class=\"text\">系统管理</span>\r\n");
      out.write("\t        <span class=\"arrow\"></span>\r\n");
      out.write("\t    </div>\r\n");
      out.write("\t\t<div class=\"leftmenuitem\" group=\"system\" id=\"usermanage\" style=\"display:none;padding-top: 20px;\">用户管理</div>\r\n");
      out.write("\t\t<div class=\"leftmenuitem\" group=\"system\" id=\"systemlog\" style=\"display:none;\">系统日志</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("$(function(){\r\n");
      out.write("\t$('#leftmenu div.menugroup').click(function () {\r\n");
      out.write("\t    var $this = $(this);\r\n");
      out.write("\t    var name = $this.attr('name');\r\n");
      out.write("\t    var active = $this.hasClass('active');\r\n");
      out.write("\t    $('#leftmenu div.menugroup').removeClass('active');\r\n");
      out.write("\t    $('#leftmenu div.leftmenuitem').hide();\r\n");
      out.write("\t    if (!active) {\r\n");
      out.write("\t        $this.addClass('active');\r\n");
      out.write("\t        $('#leftmenu div.leftmenuitem[group=\"' + name + '\"]').show();\r\n");
      out.write("\t    }\r\n");
      out.write("\t});\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
