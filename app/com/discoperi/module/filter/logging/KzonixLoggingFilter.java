package com.discoperi.module.filter.logging;


import akka.util.ByteString;
import com.google.inject.Inject;
import play.Logger;
import play.libs.streams.Accumulator;
import play.mvc.EssentialAction;
import play.mvc.EssentialFilter;
import play.mvc.Result;

import java.util.concurrent.Executor;


/**
 * Created by limpid on 5/27/17.
 */
public class KzonixLoggingFilter extends EssentialFilter {

	private final Executor executor;

	@Inject
	public KzonixLoggingFilter( Executor executor ) {
		super();
		this.executor = executor;
	}

	@Override
	public EssentialAction apply( EssentialAction next ) {
		return EssentialAction.of(request -> {
			long startTime = System.currentTimeMillis();
			Accumulator<ByteString, Result > accumulator = next.apply( request );
			return accumulator.map(result -> {
				long endTime = System.currentTimeMillis();
				long requestTime = endTime - startTime;

				Logger.info( "{} {} took {}ms and returned {}",
				             request.method(), request.uri(), requestTime, result.status() );

				return result.withHeader("Request-Time", "" + requestTime);
			}, executor);
		});
	}
}
