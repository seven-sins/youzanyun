package com.hiya3d.conf.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.hiya3d.po.User;

public abstract class BaseMapperImpl<T> extends SqlSessionDaoSupport implements BaseMapper<T> {
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	private String ns; // 命名空间

	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public List<T> find(T entity) {
		return this.getSqlSession().selectList(ns + ".find", entity);
	}

	public T get(Serializable id) {
		return this.getSqlSession().selectOne(ns + ".get", id);
	}
	
	public int count(User entity) {
		return this.getSqlSession().selectOne(ns + ".count", entity);
	}

	public void insert(T entity) {
		this.getSqlSession().insert(ns + ".insert", entity);
	}

	public void update(T entity) {
		this.getSqlSession().update(ns + ".update", entity);
	}

	public void deleteById(Serializable id) {
		this.getSqlSession().delete(ns + ".deleteById", id);
	}

	public void delete(Serializable[] ids) {
		this.getSqlSession().delete(ns + ".delete", ids);
	}
}