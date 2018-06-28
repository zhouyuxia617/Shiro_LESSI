package cn.et.shiro.first;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class MyFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest sr,ServletResponse sr2,Object obj) throws Exception{
		return true;
	}
}
