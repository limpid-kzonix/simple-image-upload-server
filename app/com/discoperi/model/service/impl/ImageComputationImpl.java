package com.discoperi.model.service.impl;


import com.discoperi.model.mongo.entities.Image;
import com.discoperi.model.mongo.entities.ImageSource;
import com.discoperi.model.service.ImageComputationService;
import com.discoperi.model.service.ImageSourceService;
import com.discoperi.model.service.utils.enumeration.ImageExtension;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.Logger;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by limpid on 5/8/17.
 */
@Singleton
public class ImageComputationImpl implements ImageComputationService {

	private SecureRandom random = new SecureRandom( );

	@Inject HttpExecutionContext executionContext;

	@Inject ImageSourceService sourceService;

	@Override public CompletableFuture< Image > imageComputation( Http.MultipartFormData.FilePart< File > picture )
			throws IOException, ExecutionException, InterruptedException {
		String file = picture.getFilename( );
		String contentType = picture.getContentType( );
		File temporaryFile = picture.getFile( );

		BufferedImage bufferedImage = ImageIO.read( temporaryFile );
		bufferedImage = crop( bufferedImage, getImageDimension( bufferedImage ).get( ) ).get( );

		List< ImageSource > imageSourceList = sourceService.generateSources( bufferedImage ).get( );


		return CompletableFuture.supplyAsync( ( ) -> {
			Image image = new Image( );
			image.setName( new BigInteger( 130, random ).toString( 32 ) );
			image.setImageSources( imageSourceList );
			return image;
		}, executionContext.current( ) );
	}

	@Override public CompletableFuture< Optional<File> > fromImage( Image image, String type ) {
		return CompletableFuture.supplyAsync( () -> {
			List<ImageSource> sources =  image.getImageSources();
			for ( ImageSource source : sources ) {
				if(source.getType().equals( type )){
					Logger.info( "From request [" + Http.Context.current().request() + "] - Image: ' " + image.getId() +
					             " ' with name/type : [" + image.getName() + "/" + type +"] - FOUND"  );
					return source.getImageSource();

				}
			}
			Logger.info( "From request [" + Http.Context.current().request() + "] - Image: ' " + image.getId() +
					             " ' with name/type : [" + image.getName() + "/DEFAULT] - FOUND"  );
			return sources.get( 0 ).getImageSource( );
		}, executionContext.current() ).thenApply( bytes -> {
			File file = null;
			try {
				file = File.createTempFile( UUID.randomUUID().toString(),"." + ImageExtension.PNG.getExtension() );
				InputStream in = new ByteArrayInputStream( bytes );
				BufferedImage imageFromSource = ImageIO.read( in );

				ImageIO.write( imageFromSource, ImageExtension.PNG.getExtension(), file );
				return  Optional.of(file);
			} catch ( IOException ignored ) {
				ignored.printStackTrace( );
			}
			return Optional.empty( );
		} );
	}

	@Override public CompletableFuture< File > fromImageSource( ImageSource imageSource, String type ) {
		return null;
	}

	@Override public CompletableFuture< BufferedImage > crop( BufferedImage image, Rectangle2D rectangle2D ) {
		return CompletableFuture.supplyAsync(
				( ) -> image
						.getSubimage( ( int ) rectangle2D.getX( ), ( int ) rectangle2D.getY( ), ( int ) rectangle2D.getWidth( ),
						              ( int ) rectangle2D.getHeight( )
						            )
		                                    ).thenApply( f -> {
			f.coerceData( true );
			return f;
		} );
	}

	@Override public CompletableFuture< Rectangle2D > getImageDimension( BufferedImage originalImage ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			int width = originalImage.getWidth( );
			int height = originalImage.getHeight( );

			int hCenter = ( int ) width / 2;
			int vCenter = ( int ) height / 2;

			int dimension = originalImage.getWidth( ) > originalImage.getHeight
					( ) ?
					originalImage.getHeight( ) :
					originalImage.getWidth( );

			return new Rectangle( hCenter - dimension / 2, vCenter - dimension / 2, dimension, dimension );


		} ).thenApply( Rectangle::getBounds2D );
	}


}
