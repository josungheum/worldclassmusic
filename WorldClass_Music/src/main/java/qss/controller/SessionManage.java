package qss.controller;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * 세션 관리
 * <pre>
 * qss.controller
 *    |_ SessionManage.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:22:47
 * @version :
 * @author : admin
 */
public class SessionManage implements HttpSessionBindingListener{
	public static SessionManage loginManager = null;

	@SuppressWarnings("rawtypes")
	public static Hashtable loginUsers = new Hashtable();

	public SessionManage() {
		super();
	}

	public static synchronized SessionManage getInstance()
	{
	    if(loginManager == null){
	    	loginManager = new SessionManage();
	    }
	    return loginManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueBound(HttpSessionBindingEvent event)
	{
		// TODO Auto-generated method stub
		/*if (this.isUsing(event.getName()) && !"signage".equals(event.getName()))
		{
			this.removeSession(event.getName());
		}*/
		loginUsers.put(event.getSession(), event.getName());
		System.out.println(event.getName() + " login ******************" + getUserCount());
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event)
	{
		// TODO Auto-generated method stub
		loginUsers.remove(event.getSession());
		System.out.println(event.getName() + " logout ******************" + getUserCount());
	}

	@SuppressWarnings("unchecked")
	public void removeSession(String userId)
	{
		Enumeration<HttpSession> e = loginUsers.keys();
		HttpSession session = null;
		while(e.hasMoreElements()){
			session = (HttpSession)e.nextElement();
			if(loginUsers.get(session).equals(userId))
			{
				loginUsers.remove(session);

				session.invalidate();
			}
		}
		//20190520 test를 위한 임시주석
	}

	public boolean isUsing(String userID)
	{
		return loginUsers.containsValue(userID);
	}

	public void setSession(HttpSession session, String userId)
	{
		session.setAttribute(userId, this);
	}

	/**
	 * 세션 ID로 로긴된 ID 구분
	 * @param session
	 * @return
	 */
    public String getUserID(HttpSession session)
    {
    	return (String)loginUsers.get(session);
    }

    /**
     * 현재 접속자수
     * @return
     */
    public int getUserCount()
    {
    	return loginUsers.size();
    }
}