package cn.et.shiro.first;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
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
		
		//��ȡ�û���
		String userName = principals.getPrimaryPrincipal().toString();
		
		//���ص�ǰ�û����еĽ�ɫ��Ȩ��
		SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
	 /**sai.addRole("student"); //ѧ��
		sai.addRole("teacher"); //��ʦ
	 */	
		//	sai.addStringPermission("*"); //�κ�
		
		//���ݿ��в�ѯ�����û���ɫ
		String sql = "SELECT r.rolename FROM t_userinfo u INNER JOIN t_user_role ur ON u.userid=ur.userid INNER JOIN t_role r ON ur.roleid=r.roleid WHERE u.username=?";
		
		JdbcUtils ju = new JdbcUtils();
		ResultSet query = ju.Query(sql,userName);
		
		try {
			while (query.next()) {
				sai.addRole(query.getString("rolename"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			query.close();
		}catch(SQLException el) {
			el.printStackTrace();
		}
		
		//���Ȩ�޵�ream��
		sql = "SELECT pm.permstr FROM t_userinfo u INNER JOIN t_user_role ur ON u_userid=ur.userid\r\n"
				+"INNER JOIN t_role r ON ur.roleid=r.roleid \r\n"
				+"INNER JOIN t_role_perm p ON r.roleid=p.roleid \r\n"
				+"INNER JOIN t_perm ON p.permid=pm.permid \r\n"
				+"WHERE u.username=?";
		
		query = ju.Query(sql, userName);
		try {
			while(query.next()) {
				sai.addStringPermission(query.getString("permstr"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	
		return sai;
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
	//	String sql = "SELECT * FROM t_user WHERE n_name=? AND password=? ";
		String sql = "SELECT * FROM t_user WHERE username=? AND password=? ";
		
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
