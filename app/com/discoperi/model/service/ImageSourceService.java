package com.discoperi.model.service;


import com.discoperi.model.mongo.entities.ImageSource;
import com.discoperi.model.service.utils.enumeration.ImageDimension;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * Created by limpid on 5/8/17.
 */
public interface ImageSourceService {


	CompletableFuture< ImageSource > generateImageSource( BufferedImage image, ImageDimension dimension );

	CompletableFuture< List< ImageSource > > generateSources( BufferedImage image );

	CompletableFuture< File > reverseGenerate( Image image );

	CompletableFuture< File > reverseGenerate( ImageSource imageSource );

	CompletableFuture< BufferedImage > resize( BufferedImage originalImage, Dimension dimension );

	CompletableFuture< byte[] > generateByteArray( CompletableFuture<BufferedImage> image );
}
