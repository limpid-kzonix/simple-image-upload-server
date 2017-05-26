package com.discoperi.module.filter.logging;


import com.google.inject.Inject;
import play.filters.gzip.GzipFilter;
import play.http.DefaultHttpFilters;
import play.mvc.EssentialFilter;


/**
 * Created by limpid on 5/26/17.
 */
public class KzonixFilter extends DefaultHttpFilters {

	private EssentialFilter[] filters;

	@Inject
	public KzonixFilter( GzipFilter gzipFilter ) {
		filters = new EssentialFilter[]{
				gzipFilter.asJava()
		};
	}

	public EssentialFilter[] filters() {
		return filters;
	}
}
