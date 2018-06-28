package cn.et.shiro.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

public class HashUtils {

	/**
	 * md5存储的数据库中的密码
	 */
	public static void main(String[] args) {
		Md5Hash m5 = new Md5Hash("4576");
		//转成md5
		System.out.println(m5.toString()); //f89394c979b34a25cc4ff8e11234fbfb
		
		//将数字转成md5
		String encode = Base64.encodeToString("123456".getBytes());
		System.out.println(encode); //MTIzNDU2
		
		//将md5转成数字
		String decode = Base64.decodeToString(encode);
		System.out.println(decode); //123456
	}
	
}
