package cn.et.shiro.first;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

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

	String userName = "zyx";
	String password = "123456";
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
		
		//�ж��û����������Ƿ���ͬ
	/*	if(inputUserName.equals(userName) && inputPassword.equals(password)) {
			return new SimpleAccount(userName,password,REALM_NAME);
		}
		
		return null;
		*/
		
		//�û�����ͬ
		if(!inputUserName.equals(userName)) {
			throw new UnknownAccountException();
		}
		
		//���벻ͬ
		if(!inputPassword.equals(password)) {
			throw new IncorrectCredentialsException();
		}
		
		return new SimpleAccount(userName,password,REALM_NAME);
	}

}
