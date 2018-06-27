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
	 * ��򵥵�һ����¼Ȩ�޴���
	 */
	public static void main(String[] args) {
		
		//1.ʹ��shiro.ini��ΪȨ�޿��ƵĻ����ļ������й���
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	
		//2.���ʵ��,���е�Ȩ�޶�����
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		
		//3.����ֱ�����õ�������ȥ
		SecurityUtils.setSecurityManager(securityManager);
		
		//��ȡ��ǰ�û�,��ǰ�û���һ���ڴ��з���������û�
		Subject currentUser = SecurityUtils.getSubject();
		
		//�ж��Ƿ��¼��,Ĭ����false
		if(! currentUser.isAuthenticated()) {
			//�������ƣ��û���������
			UsernamePasswordToken upt = new UsernamePasswordToken("zyx","123456");
			
			try {
				//�õ�ǰ�û���������������Ƚ�
				currentUser.login(upt);
			} catch (UnknownAccountException uae) {
				System.out.println("�˺��������!");
			} catch (IncorrectCredentialsException ice) {
				System.out.println("�����������!");
			} catch (LockedAccountException lae) {
				System.out.println("�˺ű�����!");
			} catch (AuthenticationException ae) {
				System.out.println("δ֪��֤�쳣!");
			}
		}
	}
}
