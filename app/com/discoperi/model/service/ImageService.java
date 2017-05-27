package com.discoperi.model.service;

import com.discoperi.model.mongo.entities.Image;
import com.discoperi.module.utils.http.error.custom.UnifiedError;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
public interface ImageService {

	void saveImage( Image image );

	Image findImageById( String id );

	void delete( String id );

	void delete(Image image);

	File getTypedImageAsFile( String objectId, String type )
			throws ExecutionException, InterruptedException, UnifiedError;

	File getOriginalImageAsFile( String objectId );
}
