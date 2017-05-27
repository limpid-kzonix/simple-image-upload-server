package com.discoperi.module.utils.http.filter.logging;


import akka.util.ByteString;
import com.discoperi.module.utils.http.error.custom.UnifiedError;
import com.google.inject.Inject;
import play.Logger;
import play.libs.streams.Accumulator;
import play.mvc.EssentialAction;
import play.mvc.EssentialFilter;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.Executor;


/**
 * Created by limpid on 5/27/17.
 */
public class KzonixUploaderLogging extends EssentialFilter {

	private final Executor executor;

	@Inject
	public KzonixUploaderLogging( Executor executor ) {
		super();
		this.executor = executor;
	}

	@Override
	public EssentialAction apply( EssentialAction next ) {
		return EssentialAction.of(request -> {
			Accumulator<ByteString, Result > accumulator = next.apply( request );
			return accumulator.map(result -> {
				String contenType = "DEFAULT";
				try {
					contenType = request.contentType().orElseThrow( UnifiedError::new );
				} catch ( UnifiedError unifiedError ) {
					unifiedError.printStackTrace( );
				}
				if(contenType.equals( Http.MimeTypes.FORM )){
					Logger.info( "{} {} took {}  and returned {}",
					             request.method(), request.uri(), contenType, result.status() );

				}

				return result.withHeader("Kzonix-Type", "FINISHED");
			}, executor);
		});
	}
}
