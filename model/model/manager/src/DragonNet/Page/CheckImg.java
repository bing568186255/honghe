package DragonNet.Page;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

import DragonNet.Global.GlobalConst;

public class CheckImg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Color getRandColor(int fc,int bc)
	{ 
		Random random = new Random(); 
		if(fc>255) fc=255; 
		if(bc>255) bc=255; 
		int r=fc+random.nextInt(bc-fc); 
		int g=fc+random.nextInt(bc-fc); 
		int b=fc+random.nextInt(bc-fc); 
		return new Color(r,g,b); 
	}
	
	protected void doRequest(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0); 
		int width=60, height=20; 
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		Graphics g = image.getGraphics(); 
		Random random = new Random(); 
		g.setColor(getRandColor(200,250)); 
		g.fillRect(0, 0, width, height); 
		g.setFont(new Font("Times New Roman",Font.PLAIN,18)); 
		g.setColor(getRandColor(160,200)); 
		for (int i=0;i<155;i++) 
		{ 
			int x = random.nextInt(width); 
			int y = random.nextInt(height); 
			int xl = random.nextInt(12); 
			int yl = random.nextInt(12); 
			g.drawLine(x,y,x+xl,y+yl); 
		} 
		String sRand=""; 
		for (int i=0;i<4;i++){
			String strList="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			char cRand =strList.charAt(random.nextInt(36)); 
			sRand+=cRand; 
			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110))); 
			//g.drawString(rand,13*i+6,16); 
		} 
		g.drawString(sRand,6,16);

		// save to SESSION 
		HttpSession session = request.getSession(true);
		session.setAttribute(GlobalConst.SESS_ATTR_CHECKIMG,sRand);

		g.dispose(); 
		ImageIO.write(image, "JPEG", response.getOutputStream()); 
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doRequest(request,response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		doRequest(request,response);
	}
}