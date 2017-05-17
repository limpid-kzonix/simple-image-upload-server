package com.discoperi.model.service.utils.enumeration;


import lombok.Getter;


/**
 * Created by limpid on 5/17/17.
 */
public enum ImageExtension {
	JPEG("image/jpeg", ".jpg", "jpg"),
	PNG("image/png", ".png", "png"),
	GIF("image/gif", ".gif", "gif");


	@Getter private String contentType;
	@Getter private String extension;
	@Getter private String name;

	ImageExtension(String contentType, String extension, String name){
		this.contentType = contentType;
		this.extension = extension;
		this.name = name;
	}

}
