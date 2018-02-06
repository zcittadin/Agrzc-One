package com.servicos.estatica.stage.one.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.model.Silo;
import com.servicos.estatica.stage.one.util.HibernateUtil;

public class SiloDAO {

	public void updateSilo(Silo silo) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.update(silo);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Silo> getSilosStatus() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT s FROM Silo s");
		List<Silo> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}

	public Silo findBySilo(String silo) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT s FROM Silo s WHERE silo LIKE :silo");
		query.setParameter("silo", silo);
		Silo s = (Silo) query.getResultList().get(0);
		session.close();
		return s;
	}

	@SuppressWarnings("unchecked")
	public Silo findByMateria(Materia materia) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT s FROM Silo s WHERE materia LIKE :materia");
		query.setParameter("materia", materia);
		List<Silo> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		if (list.size() == 0)
			return null;
		Silo s = list.get(0);
		return s;
	}

}
