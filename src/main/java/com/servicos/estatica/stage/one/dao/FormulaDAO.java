package com.servicos.estatica.stage.one.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.util.HibernateUtil;

public class FormulaDAO {

	public void saveFormula(Formula formula) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.save(formula);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Formula> findFormulas() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT f FROM Formula f ORDER BY id DESC");
		query.setMaxResults(30);
		List<Formula> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}
}
