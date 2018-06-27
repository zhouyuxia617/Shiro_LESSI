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
 * �Զ���realm��ȡ���ݿ�
 */
public class MyRealm extends AuthorizingRealm{

	/**
	 * ��Ȩ����ǰ�û�ӵ����Щ��ɫ��Ȩ��
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
	 * ��֤����¼
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//�û���¼������û���������
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		
		String inputUserName = upt.getUsername();
		String inputPassword = new String(upt.getPassword());
		
	/**	//�ж��û����������Ƿ���ͬ   ---1
		if(inputUserName.equals(userName) && inputPassword.equals(password)) {
			return new SimpleAccount(userName,password,REALM_NAME);
		}
		return null;
		*/
		
	/**	//�û�����ͬ    ---2
		if(!inputUserName.equals(userName)) {
			throw new UnknownAccountException();
		}
		//���벻ͬ
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
