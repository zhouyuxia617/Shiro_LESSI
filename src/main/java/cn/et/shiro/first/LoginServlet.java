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
		
		//��ȡ��ǰ�û�,��ǰ�û���һ���ڴ��з���������û�
		Subject currentUser = SecurityUtils.getSubject();
				
		//�ж��Ƿ��¼��,Ĭ����false
		if(! currentUser.isAuthenticated()) {
			//�������ƣ��û���������
			UsernamePasswordToken upt = new UsernamePasswordToken(userName,password);
					
			try {
				//�õ�ǰ�û���������������Ƚ�
				currentUser.login(upt);
						
				//��ȡ��ǰ�û���session����ֵ
				Session session = currentUser.getSession();
				session.setAttribute("lostedMoney", 520);
				request.getRequestDispatcher("/suc.jsp").forward(request, response);	
			} catch (UnknownAccountException uae) {
				System.out.println("�˺��������!");
				request.setAttribute("errorText", "�˺��������");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (IncorrectCredentialsException ice) {
				System.out.println("�����������!");
				request.setAttribute("errorText", "�����������");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (LockedAccountException lae) {
				System.out.println("�˺ű�����!");
				request.setAttribute("errorText", "�˺ű�������");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			} catch (AuthenticationException ae) {
				System.out.println("δ֪��֤�쳣!");
				request.setAttribute("errorText", "δ֪��֤�쳣��");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
