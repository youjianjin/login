package cn.desayele.care.service;

import cn.desayele.care.dao.BaseDAO;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
@Transactional
public class BaseServiceImpl implements BaseService {
	@Resource(name="baseDAO")
	private BaseDAO baseDAO;
	//使用HQL查询所有
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<?> findbyhql(String hql) {
		return baseDAO.find(hql);
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<?> findbyobj(Object obj) {
		return baseDAO.findByExample(obj);
	}
	//使用原生SQL查询所有
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<?> queryList(String sql){
		return baseDAO.getSession().createSQLQuery(sql).list();
	}
	//使用原生SQL查询实体
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Object queryObj(String sql){
		return baseDAO.getSession().createSQLQuery(sql).uniqueResult();
	}
	//使用原生SQL查询元素(String)
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String queryStringS(String sql){
		return (String)baseDAO.getSession().createSQLQuery(sql).uniqueResult();
	}
	//使用原生SQL查询元素(int)
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public String queryStringI(String sql){
		Object obj= baseDAO.getSession().createSQLQuery(sql).uniqueResult();
		if(obj!=null){
			return obj.toString();
		}else{
			return "0";
		}
	}
	//使用原生SQL执行增，删，改
	@Transactional(propagation=Propagation.REQUIRED)
	public void executeupdate(String sql){
		baseDAO.getSession().createSQLQuery(sql).executeUpdate();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void add(Object obj) {
		baseDAO.getSession().persist(obj);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void delete(Object obj) {
		baseDAO.getSession().delete(obj);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void update(Object obj) {
		baseDAO.getSession().update(obj);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveOrupdateSQL(Object obj) {
		baseDAO.getSession().saveOrUpdate(obj);
	}
	//原生SQL映射实体方式
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public  <T> List<T> queryEntityL(String sql,Class<T> clz){
		List<T> tmpList =(List<T>)baseDAO.getSession().createSQLQuery(sql)
		                 .addEntity(clz)
		                 .list();
		return tmpList ;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public  <T> T queryEntity(String sql,Class<T> clz){
		T t =(T)baseDAO.getSession().createSQLQuery(sql)
		                 .addEntity(clz).uniqueResult();
		return t;
	}
	//原生SQL对应POJO
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public  <T> List<T> queryPojoL(String sql,Class<T> clz){
		List<T> tmpList =(List<T>)baseDAO.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clz)).list();
		return tmpList ;
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public  <T> T queryPojo(String sql,Class<T> clz){
		T t =(T)baseDAO.getSession().createSQLQuery(sql)
		                 .setResultTransformer(Transformers.aliasToBean(clz)).uniqueResult();
		return t;
	}

}
