package com.servicos.estatica.stage.one.dao;

import org.hibernate.Session;

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
}
