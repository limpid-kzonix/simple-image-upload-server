package com.discoperi.model.service;


import com.discoperi.model.mongo.entities.Image;
import com.discoperi.model.mongo.entities.ImageSource;
import play.mvc.Http;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by limpid on 5/8/17.
 */
public interface ImageComputationService {


	CompletableFuture< Image > imageComputation( Http.MultipartFormData.FilePart< File > picture )
			throws IOException, ExecutionException, InterruptedException;

	CompletableFuture< Optional< File > > fromImage( Image image, String type );

	CompletableFuture< File > fromImageSource( ImageSource imageSource, String type );

	CompletableFuture< BufferedImage > crop( BufferedImage image, Rectangle2D rectangle2D );

	CompletableFuture< Rectangle2D > getImageDimension( BufferedImage originalImage );


}
