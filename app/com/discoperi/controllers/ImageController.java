package com.discoperi.controllers;


import com.discoperi.model.mongo.entities.Image;
import com.discoperi.model.service.ImageComputationService;
import com.discoperi.model.service.ImageService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


/**
 * Created by Harmeet Singh(Taara) on 27/12/16.
 */
@Singleton
public class ImageController extends Controller {

	private ImageService imageService;

	private ImageComputationService imageComputationService;

	@Inject
	public ImageController( ImageService imageService, ImageComputationService imageComputationService) {
		this.imageService = imageService;
		this.imageComputationService = imageComputationService;
	}


	public Result uploadImage() throws InterruptedException, ExecutionException, IOException {
		Http.MultipartFormData< File > body = request( ).body( ).asMultipartFormData( );
		Http.MultipartFormData.FilePart< File > picture = body.getFile( "picture" );
		if ( picture != null ) {
			Image image =  imageComputationService.imageComputation( picture ).get();
			imageService.saveImage( image );
			return ok( Json.toJson( image ));
		}
		return ok( Json.toJson( "IMAGE" ));
	}

	public Result deleteImage (String objectId){
		return ok();
	}

	public Result getImageSource(String objectId){
		return ok();
	}

	public Result getImageSource(String objectId, String sourceType){
		return ok();
	}
}
