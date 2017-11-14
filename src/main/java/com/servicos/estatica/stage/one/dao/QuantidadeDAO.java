package com.servicos.estatica.stage.one.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.model.Quantidade;
import com.servicos.estatica.stage.one.util.HibernateUtil;

public class QuantidadeDAO {

	public void saveQuantidade(Quantidade quantidade) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.save(quantidade);
		session.getTransaction().commit();
		session.close();
	}

	public void updateQuantidade(Quantidade quantidade) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.update(quantidade);
		session.getTransaction().commit();
		session.close();
	}

	public void removeQuantidade(Quantidade quantidade) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.remove(quantidade);
		session.getTransaction().commit();
		session.close();
	}
	
	public Quantidade findById(Long id) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT q FROM Quantidade q WHERE id = :id");
		query.setParameter("id", id);
		Quantidade q = (Quantidade) query.getResultList().get(0);
		return q;
	}

	@SuppressWarnings("unchecked")
	public List<Quantidade> findByFormula(Formula formula) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("SELECT q FROM Quantidade q where formulaQuantidade = :idFormula ORDER BY id DESC");
		query.setParameter("idFormula", formula);
		query.setMaxResults(30);
		List<Quantidade> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}

	public Quantidade findByMateriaAndFormula(Materia mat, Formula f) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery(
				"SELECT q FROM Quantidade q WHERE materiaQuantidade = :idMateria AND formulaQuantidade = :idFormula");
		query.setParameter("idMateria", mat);
		query.setParameter("idFormula", f);
		if (query.getResultList().isEmpty()) {
			session.close();
			return null;
		}
		Quantidade q = (Quantidade) query.getResultList().get(0);
		session.close();
		return q;
	}
}
