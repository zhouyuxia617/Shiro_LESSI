package cn.et.shiro.first;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroHello {

	/**
	 * 最简单的一个登录权限代码
	 */
	public static void main(String[] args) {
		
		//1.使用shiro.ini作为权限控制的基础文件，进行构造
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	
		//2.获得实例,所有的权限都靠它
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		
		//3.把它直接设置到工具中去
		SecurityUtils.setSecurityManager(securityManager);
		
		//获取当前用户,当前用户是一个内存中分配的虚拟用户
		Subject currentUser = SecurityUtils.getSubject();
		
		//判断是否登录过,默认是false
		if(! currentUser.isAuthenticated()) {
			//输入令牌，用户名和密码
			UsernamePasswordToken upt = new UsernamePasswordToken("zyx","123456");
			
			try {
				//用当前用户和输入的令牌做比较
				currentUser.login(upt);
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
}
