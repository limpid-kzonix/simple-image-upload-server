package com.discoperi.model;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by limpid on 4/28/17.
 */
@Singleton
public class KunderaEntityManageFactory {

	@Getter
	private static EntityManagerFactory managerFactory;

	@Inject
	public KunderaEntityManageFactory(){
		managerFactory = Persistence.createEntityManagerFactory( "mongoUnit" );
	}
}
