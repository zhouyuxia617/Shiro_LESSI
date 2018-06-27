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

	//1.ʹ��shiro.ini��ΪȨ�޿��ƵĻ����ļ������й���
//	static Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	static Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:m.ini");
		
	//2.���ʵ��,���е�Ȩ�޶�����
	static org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
			
	//3.����ֱ�����õ�������ȥ
	static {
		SecurityUtils.setSecurityManager(securityManager);
	}
	
	/**
	 * ��¼
	 */
	public static void login() {
		//��ȡ��ǰ�û�,��ǰ�û���һ���ڴ��з���������û�
		Subject currentUser = SecurityUtils.getSubject();
		
		//�ж��Ƿ��¼��,Ĭ����false
		if(! currentUser.isAuthenticated()) {
			//�������ƣ��û���������
			UsernamePasswordToken upt = new UsernamePasswordToken("znl","666666");
			
			try {
				//�õ�ǰ�û���������������Ƚ�
				currentUser.login(upt);
				
				//��ȡ��ǰ�û���session����ֵ
				Session session = currentUser.getSession();
				session.setAttribute("lostedMoney", 520);
				
				System.out.println("��¼�ɹ�!");
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
	
	/**
	 * �����û��Ƿ�ӵ��ĳ��Ȩ��
	 */
	public static void testRole() {
		//��ȡ��ǰ�û�,��ǰ�û���һ���ڴ��з���������û�
		Subject currentUser = SecurityUtils.getSubject();
		
		//�ж��Ƿ��¼��
		if(currentUser.isAuthenticated()) {
			//�ж��Ƿ������Ȩ��
			boolean hasRole = currentUser.hasRole("student");
			if(hasRole) {
				System.out.println("ӵ��ѧ����Ȩ�ޡ�����");
			}
			
			//�ж��Ƿ��н����Ȩ��
			boolean permitted = currentUser.isPermitted("book:borrow");
			if(permitted) {
				System.out.println("ӵ�н����Ȩ�ޡ�����");
			}
			
			//�ж��Ƿ�ӵ�й����鼮��Ȩ��
			boolean bookmanager = currentUser.isPermitted("book:manager");
			if(bookmanager) {
				System.out.println("ӵ�й����鼮��Ȩ��");
			}
			
			System.out.println(currentUser.getSession().getAttribute("lostedMoney"));
		}
	}
	
	
	public static void main(String[] args) {
	
		login(); //��¼
	
		testRole(); //�ж�Ȩ��
	
	}

}

