package cn.et.shiro.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

public class HashUtils {

	/**
	 * md5�洢�����ݿ��е�����
	 */
	public static void main(String[] args) {
		Md5Hash m5 = new Md5Hash("4576");
		//ת��md5
		System.out.println(m5.toString()); //f89394c979b34a25cc4ff8e11234fbfb
		
		//������ת��md5
		String encode = Base64.encodeToString("123456".getBytes());
		System.out.println(encode); //MTIzNDU2
		
		//��md5ת������
		String decode = Base64.decodeToString(encode);
		System.out.println(decode); //123456
	}
	
}
