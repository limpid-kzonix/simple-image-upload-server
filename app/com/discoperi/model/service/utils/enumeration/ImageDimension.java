package com.discoperi.model.service.utils.enumeration;

import lombok.Getter;

/**
 * Created by limpid on 5/8/17.
 */
public enum ImageDimension {
	S100( "100x100", 100, 100),
	S200( "200x200", 200, 200 ),
	S256( "200x200", 256, 256 ),
	S300("300x300", 300, 300),
	M400( "400x400", 400, 400),
	M500( "500x500", 500, 500),
	M512( "512x512", 512, 512),
	M600( "600x600", 600, 600),
	L720( "720x720", 720, 720),
	L1024( "1024x1024", 1024, 1024),
	TYPE_16X9("16:9", 1366, 768);


	@Getter
	private String type;

	@Getter
	private Integer width;

	@Getter
	private Integer height;

	ImageDimension( String type, Integer width, Integer height ) {
		this.type = type;
		this.width = width;
		this.height = height;
	}
}
