package com.discoperi.model.mongo.dao.impl;

import com.discoperi.model.KunderaEntityManageFactory;
import com.discoperi.model.mongo.dao.ImageDao;
import com.discoperi.model.mongo.entities.Image;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageDaoImpl extends GenericDaoImpl< Image > implements ImageDao {

	@Inject
	public ImageDaoImpl(KunderaEntityManageFactory entityManageFactory ) {
		super(entityManageFactory );
	}
}
