package cn.et.shiro.first;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.et.shiro.utils.JdbcUtils;

/**
 * 自定义realm读取数据库
 */
public class MyRealm extends AuthorizingRealm{

	/**
	 * 授权，当前用户拥有哪些角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	
/**	String userName = "zyx";
	String password = "123456";*/
	static String REALM_NAME = "myrealm";
	
	/**
	 * 认证，登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//用户登录输入的用户名和密码
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		
		String inputUserName = upt.getUsername();
		String inputPassword = new String(upt.getPassword());
		
	/**	//判断用户名和密码是否相同   ---1
		if(inputUserName.equals(userName) && inputPassword.equals(password)) {
			return new SimpleAccount(userName,password,REALM_NAME);
		}
		return null;
		*/
		
	/**	//用户名不同    ---2
		if(!inputUserName.equals(userName)) {
			throw new UnknownAccountException();
		}
		//密码不同
		if(!inputPassword.equals(password)) {
			throw new IncorrectCredentialsException();
		}
		
		return new SimpleAccount(userName,password,REALM_NAME);
		*/
		
		// ----3
		String sql = "SELECT * FROM t_user WHERE n_name=? AND password=? ";
		
		JdbcUtils ju = new JdbcUtils();
		
		ResultSet query = ju.Query(sql, inputUserName, inputPassword);
		
		try {
			if (!query.next()) {
				throw new AuthenticationException();
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new SimpleAccount(inputUserName,inputPassword,REALM_NAME);
	}

}
