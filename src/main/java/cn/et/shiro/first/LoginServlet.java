package cn.et.shiro.first;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = -3059382916943961240L;

	public LoginServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		//获取当前用户,当前用户是一个内存中分配的虚拟用户
		Subject currentUser = SecurityUtils.getSubject();
				
		//判断是否登录过,默认是false
		if(! currentUser.isAuthenticated()) {
			//输入令牌，用户名和密码
			UsernamePasswordToken upt = new UsernamePasswordToken(userName,password);
					
			try {
				//用当前用户和输入的令牌做比较
				currentUser.login(upt);
						
				//获取当前用户的session并设值
				Session session = currentUser.getSession();
				session.setAttribute("lostedMoney", 520);
				request.getRequestDispatcher("/suc.jsp").forward(request, response);	
			} catch (UnknownAccountException uae) {
				System.out.println("账号输入错误!");
				request.setAttribute("errorText", "账号输入错误！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (IncorrectCredentialsException ice) {
				System.out.println("密码输入错误!");
				request.setAttribute("errorText", "密码输入错误！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (LockedAccountException lae) {
				System.out.println("账号被锁定!");
				request.setAttribute("errorText", "账号被锁定！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (AuthenticationException ae) {
				System.out.println("未知认证异常!");
				request.setAttribute("errorText", "未知认证异常！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
