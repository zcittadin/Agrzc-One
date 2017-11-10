package com.servicos.estatica.stage.one.dao;

import org.hibernate.Session;

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
}
