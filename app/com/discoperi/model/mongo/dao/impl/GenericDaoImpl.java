package com.discoperi.model.mongo.dao.impl;

import com.discoperi.model.KunderaEntityManageFactory;
import com.discoperi.model.mongo.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public abstract class GenericDaoImpl< E > implements GenericDao< E > {

	private Class< E > entityClass;

	private KunderaEntityManageFactory entityManageFactory;


	public GenericDaoImpl( KunderaEntityManageFactory entityManageFactory ) {
		EntityManagerFactory managerFactory = entityManageFactory.getManagerFactory( );
		ParameterizedType parameterizedType = ( ParameterizedType ) getClass( ).getGenericSuperclass( );
		entityClass = ( Class< E > ) parameterizedType.getActualTypeArguments( )[ 0 ];
	}

	protected EntityManager getEntityManager( ) {
		return this.entityManageFactory.getManagerFactory( ).createEntityManager( );
	}

	@Override
	public void save( E entity ) {
		getEntityManager( ).persist( entity );
	}

	@Override
	public E findById( String id ) {
		return getEntityManager( )
				.createQuery( "SELECT entity FROM " + entityClass.getSimpleName( ) + " entity WHERE entity.id = :id", entityClass )
				.setParameter( "id", id ).getSingleResult( );
	}

	@Override
	public List< E > findAll( ) {
		return getEntityManager( ).createQuery( "SELECT entity FROM " + entityClass.getSimpleName( ) + " entity", entityClass )
				.getResultList( );
	}

	@Override public void remove( E entity ) {

	}

	@Override public void remove( String id ) {

	}


}
