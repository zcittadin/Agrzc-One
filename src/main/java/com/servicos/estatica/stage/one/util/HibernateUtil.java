package com.servicos.estatica.stage.one.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.servicos.estatica.stage.one.model.Formula;
import com.servicos.estatica.stage.one.model.Materia;
import com.servicos.estatica.stage.one.model.Quantidade;

public class HibernateUtil {

	private final static SessionFactory sessionFactory;
	private final static ServiceRegistry serviceRegistry;
	private static final Configuration configuration = new Configuration();

	static {
		getConfiguration();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}

	public static void closeSessionFactory() throws Exception {
		sessionFactory.close();
	}

	private static void getConfiguration() {
		configuration.addPackage("com.servicos.estatica.stage.one.model");
		configuration.addAnnotatedClass(Materia.class);
		configuration.addAnnotatedClass(Formula.class);
		configuration.addAnnotatedClass(Quantidade.class);
		configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/agrozacca?useTimezone=true&serverTimezone=UTC");
		configuration.setProperty("hibernate.connection.username", "root");
		configuration.setProperty("hibernate.connection.password", "root");
		configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		// configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.format_sql", "true");
	}

}
