package cn.et.shiro.first;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroHello {

	//1.使用shiro.ini作为权限控制的基础文件，进行构造
//	static Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	static Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:m.ini");
		
	//2.获得实例,所有的权限都靠它
	static org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
			
	//3.把它直接设置到工具中去
	static {
		SecurityUtils.setSecurityManager(securityManager);
	}
	
	/**
	 * 登录
	 */
	public static void login() {
		//获取当前用户,当前用户是一个内存中分配的虚拟用户
		Subject currentUser = SecurityUtils.getSubject();
		
		//判断是否登录过,默认是false
		if(! currentUser.isAuthenticated()) {
			//输入令牌，用户名和密码
			UsernamePasswordToken upt = new UsernamePasswordToken("znl","666666");
			
			try {
				//用当前用户和输入的令牌做比较
				currentUser.login(upt);
				
				//获取当前用户的session并设值
				Session session = currentUser.getSession();
				session.setAttribute("lostedMoney", 520);
				
				System.out.println("登录成功!");
			} catch (UnknownAccountException uae) {
				System.out.println("账号输入错误!");
			} catch (IncorrectCredentialsException ice) {
				System.out.println("密码输入错误!");
			} catch (LockedAccountException lae) {
				System.out.println("账号被锁定!");
			} catch (AuthenticationException ae) {
				System.out.println("未知认证异常!");
			}
		}
	}
	
	/**
	 * 测试用户是否拥有某个权限
	 */
	public static void testRole() {
		//获取当前用户,当前用户是一个内存中分配的虚拟用户
		Subject currentUser = SecurityUtils.getSubject();
		
		//判断是否登录过
		if(currentUser.isAuthenticated()) {
			//判断是否有这个权限
			boolean hasRole = currentUser.hasRole("student");
			if(hasRole) {
				System.out.println("拥有学生的权限。。。");
			}
			
			//判断是否有借书的权限
			boolean permitted = currentUser.isPermitted("book:borrow");
			if(permitted) {
				System.out.println("拥有借书的权限。。。");
			}
			
			//判断是否拥有管理书籍的权限
			boolean bookmanager = currentUser.isPermitted("book:manager");
			if(bookmanager) {
				System.out.println("拥有管理书籍的权限");
			}
			
			System.out.println(currentUser.getSession().getAttribute("lostedMoney"));
		}
	}
	
	
	public static void main(String[] args) {
	
		login(); //登录
	
		testRole(); //判断权限
	
	}

}

