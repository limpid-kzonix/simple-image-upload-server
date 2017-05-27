package com.discoperi.model.service.impl;

import com.discoperi.model.mongo.dao.ImageDao;
import com.discoperi.model.mongo.entities.Image;
import com.discoperi.model.service.ImageComputationService;
import com.discoperi.model.service.ImageService;
import com.discoperi.module.utils.http.error.custom.UnifiedError;
import com.google.inject.Inject;

import javax.inject.Singleton;
import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageServiceImpl implements ImageService {

	@Inject
	private ImageDao imageDao;

	@Inject
	private ImageComputationService computationService;

	@Override public void saveImage( Image image ) {
		imageDao.save( image );
	}

	@Override public Image findImageById( String id ) {
		return imageDao.findById( id );
	}

	@Override public void delete( String id ) {
		imageDao.remove( id );
	}

	@Override public void delete( Image image ) {
		imageDao.remove( image );
	}

	@Override public File getTypedImageAsFile( String objectId, String imageType )
			throws ExecutionException, InterruptedException, UnifiedError {
		Image image = imageDao.findById( objectId );
		if( imageType.isEmpty( ) ){
			throw new UnifiedError( "Invalid type param" );
		}
		computationService.fromImage( image, imageType);
		return null;
	}

	@Override public File getOriginalImageAsFile( String objectId ) {
		return null;
	}
}
