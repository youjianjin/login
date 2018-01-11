package cn.desayele.care.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
@Repository
public class BaseDAO extends HibernateTemplate {
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession(){
//		if(sessionFactory.getCurrentSession()==null){
//			return sessionFactory.openSession();//不要用这个方法
//		}else{
			return sessionFactory.getCurrentSession();
//		}
	}
}
