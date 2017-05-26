package com.discoperi.model.service.impl;


import com.discoperi.model.mongo.entities.ImageSource;
import com.discoperi.model.service.ImageSourceService;
import com.discoperi.model.service.utils.enumeration.ImageDimension;
import com.discoperi.model.service.utils.enumeration.ImageExtension;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.libs.concurrent.HttpExecutionContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by limpid on 5/9/17.
 */
@Singleton
public class ImageSourceImpl implements ImageSourceService {


	@Inject
	private HttpExecutionContext executionContext;

	@Override
	public CompletableFuture< ImageSource > generateImageSource( BufferedImage image, ImageDimension dimension ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			ImageSource source = new ImageSource( );
			source.setType( dimension.getType( ) );
			source.setWidth( dimension.getWidth( ) );
			source.setHeight( dimension.getHeight( ) );
			source.setExtension( ImageExtension.PNG.getExtension() );
			try {
				source.setImageSource(
						generateByteArray( resize( image, new Dimension( dimension.getWidth( ), dimension.getHeight( ) )
						                         ) ).get() );
			} catch ( InterruptedException | ExecutionException e ) {
				e.printStackTrace( );
			}


			return source;
		}, executionContext.current( ) );
	}

	@Override public CompletableFuture< List< ImageSource > > generateSources( BufferedImage image ) {

		return CompletableFuture.supplyAsync( ( ) -> {
			List< ImageSource > sources = new ArrayList< ImageSource >( ImageDimension.values( ).length );
			for ( ImageDimension imageDimension : ImageDimension.values( ) ) {
				try {
					sources.add( generateImageSource( image, imageDimension ).get( ) );
				} catch ( InterruptedException | ExecutionException e ) {
					e.printStackTrace( );
				}
			}
			return sources;
		}, executionContext.current( ) );
	}

	@Override public CompletableFuture< File > reverseGenerate( Image image ) {
		return null;
	}

	@Override public CompletableFuture< File > reverseGenerate( ImageSource imageSource ) {
		return null;
	}

	@Override public CompletableFuture< BufferedImage > resize( BufferedImage originalImage, Dimension dimension ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			int type = originalImage.getType( ) == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType( );
			BufferedImage resizedImage =
					new BufferedImage( ( int ) dimension.getWidth( ), ( int ) dimension.getHeight( ), type );
			Graphics2D g = resizedImage.createGraphics( );
			g.drawImage( originalImage, 0, 0, ( int ) dimension.getWidth( ), ( int ) dimension.getHeight( ), null );
			g.dispose( );
			g.setComposite( AlphaComposite.Src );


			g.setRenderingHint( RenderingHints.KEY_RENDERING,
			                    RenderingHints.VALUE_RENDER_QUALITY );
			g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
			                    RenderingHints.VALUE_ANTIALIAS_ON );
			return resizedImage;
		} ).thenApply( f -> {
			f.coerceData( true );
			return f;
		} );
	}

	@Override public CompletableFuture< byte[] > generateByteArray( CompletableFuture<BufferedImage> image ) {
		return CompletableFuture.supplyAsync( ( ) -> {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream( );

			try {
				ImageIO.write( image.get(), ImageExtension.JPEG.getName(), byteArrayOutputStream );
			} catch ( IOException | InterruptedException | ExecutionException e ) {
				e.printStackTrace( );
			}
			return byteArrayOutputStream;
		} ).thenApply( ByteArrayOutputStream::toByteArray );
	}


}
