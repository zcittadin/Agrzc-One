package com.servicos.estatica.stage.one.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.util.HibernateUtil;

public class MateriaDAO {

	public void saveMateria(Materia materia) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.save(materia);
		session.getTransaction().commit();
		session.close();
	}

	public void updateMateria(Materia materia) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.update(materia);
		session.getTransaction().commit();
		session.close();
	}

	public void removeMateria(Materia materia) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		session.remove(materia);
		session.getTransaction().commit();
		session.close();

	}

	@SuppressWarnings("unchecked")
	public List<Materia> findMaterias() {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT m FROM Materia m ORDER BY id DESC");
		query.setMaxResults(30);
		List<Materia> list = new ArrayList<>();
		list = query.getResultList();
		session.close();
		return list;
	}

	public Materia findById(Long id) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT m FROM Materia m WHERE id = :id");
		query.setParameter("id", id);
		Materia m = (Materia) query.getResultList().get(0);
		session.close();
		return m;
	}

	public Materia findByName(String name) {
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		Query query = session.createQuery("SELECT m FROM Materia m WHERE nomeMateria LIKE :name");
		query.setParameter("name", name);
		Materia m = (Materia) query.getResultList().get(0);
		session.close();
		return m;
	}
}
