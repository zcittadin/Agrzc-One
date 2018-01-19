package com.servicos.estatica.stage.one.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.Historico;
import com.servicos.estatica.stage.one.util.HibernateUtil;

public class HistoricoDAO {

	public void saveHistorico(Historico historico) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.save(historico);
		session.getTransaction().commit();
		session.close();
	}

	public void removeHistorico(Historico historico) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.remove(historico);
		session.getTransaction().commit();
		session.close();

	}

	@SuppressWarnings("unchecked")
	public List<Historico> findHistorico() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT h FROM Historico h ORDER BY dataHist DESC");
		query.setMaxResults(30);
		List<Historico> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Historico> findByFilter(Formula formula, String inicio, String fim) {
		String sql = "SELECT h FROM Historico h";
		if (formula != null || inicio != null || fim != null) {
			sql = sql.concat(" WHERE ");
		}
		if (formula != null) {
			sql = sql.concat("h.formula = " + formula.getId() + " ");
			if (inicio != null || fim != null) {
				sql = sql.concat("AND ");
			}
		}
		if (inicio != null) {
			sql = sql.concat("DATE(h.data) >= '" + inicio + "'");
			if (fim != null)
				sql = sql.concat(" AND ");
		}
		if (fim != null) {
			sql = sql.concat("DATE(h.data) <= '" + fim + "'");
		}
		sql = sql.concat(" ORDER BY dataHist DESC");
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery(sql);
		List<Historico> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}
}
