package com.discoperi.controllers;


import com.discoperi.model.mongo.entities.Image;
import com.discoperi.model.service.ImageComputationService;
import com.discoperi.model.service.ImageService;
import com.discoperi.module.UnifiedMessage;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Logger;
import play.cache.CacheApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageController extends Controller {


	private ImageService imageService;

	private ImageComputationService imageComputationService;

	private CacheApi cacheApi;

	@Inject
	public ImageController( ImageService imageService,
	                        ImageComputationService imageComputationService,
	                        CacheApi cacheApi ) {
		this.imageService = imageService;
		this.imageComputationService = imageComputationService;
		this.cacheApi = cacheApi;
	}


	public Result uploadImage( ) throws InterruptedException, ExecutionException, IOException {
		Http.MultipartFormData< File > body = request( ).body( ).asMultipartFormData( );
		Http.MultipartFormData.FilePart< File > picture = body.getFile( "picture" );
		if ( picture != null ) {
			Image image = imageComputationService.imageComputation( picture ).get( );
			imageService.saveImage( image );
			return ok( Json.toJson( UnifiedMessage.response( image.getId( ) ) ) );
		}
		return ok( Json.toJson(
				UnifiedMessage.error( "ERROR", 400, "Invalid type of input param [multipart/data]" )
		                      )
		         );
	}

	public Result deleteImage( String objectId ) {
		imageService.delete( objectId );
		return ok( Json.toJson( UnifiedMessage.response( "Image with ID[" + objectId + "] deleted." ) ) );
	}

	public Result getImageSource( String objectId, String sourceType ) throws ExecutionException, InterruptedException {

		String cacheKey = String.format( "key.%s-%s", objectId, sourceType );
		Image image = imageService.findImageById( objectId );
		File outSource = cacheApi.getOrElse( cacheKey, ( ) -> {
			Optional< File > file = imageComputationService.fromImage( image, sourceType ).get( );
			File source = file.orElseGet( ( ) -> {
				return null;
			} );
			cacheApi.set( cacheKey, source, 60 * 20 );
			Logger.info( "Image with key [CACHE-KEY]{}",cacheKey );
			return source;
		} );

		return ok( outSource );
	}
}
