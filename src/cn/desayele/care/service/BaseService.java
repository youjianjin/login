package cn.desayele.care.service;

import java.util.List;

public interface BaseService {
	void add(Object obj);
	void delete(Object obj);
	void update(Object obj);
	List<?> findbyhql(String hql);
	List<?> findbyobj(Object obj);
}
