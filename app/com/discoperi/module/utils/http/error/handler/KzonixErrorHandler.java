package com.discoperi.module.utils.http.error.handler;


import com.discoperi.module.utils.http.error.handler.message.ErrorMessage;
import com.google.inject.Inject;
import com.google.inject.Provider;
import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.http.HttpErrorHandler;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


/**
 * Created by limpid on 5/14/17.
 */
public class KzonixErrorHandler  extends DefaultHttpErrorHandler implements HttpErrorHandler {

	@Inject
	public KzonixErrorHandler( Configuration configuration, Environment environment,
	                           OptionalSourceMapper sourceMapper,
	                           Provider< Router > routes ) {

		super( configuration, environment, sourceMapper, routes );
	}

	@Override
	public CompletionStage< Result > onClientError( Http.RequestHeader request, int statusCode, String message ) {

		return CompletableFuture.completedFuture(
				Results.status(
						statusCode,
						Json.toJson( new ErrorMessage(
								statusCode,
								"Error: " + message
						) ) ) );
	}

	@Override
	public CompletionStage< Result > onServerError( Http.RequestHeader request, Throwable exception ) {

		return CompletableFuture
				.completedFuture(
						Results.internalServerError(
								Json.toJson( new ErrorMessage( 500, "Error: " + exception.getMessage( ) ) )
						                           ) );
	}

	@Override
	protected CompletionStage< Result > onProdServerError( Http.RequestHeader request, UsefulException exception ) {

		return CompletableFuture.completedFuture(
				Results.internalServerError(
						Json.toJson(
								new ErrorMessage( 500, "Error: " + exception.getMessage( ) ) )
				                           )
		                                        );
	}

	@Override
	protected CompletionStage< Result > onForbidden( Http.RequestHeader request, String message ) {

		return CompletableFuture.completedFuture(
				Results.forbidden(
						Json.toJson( new ErrorMessage( 403, "Error: " + message ) )
				                 )
		                                        );
	}

	@Override protected CompletionStage< Result > onBadRequest( Http.RequestHeader request, String message ) {

		return CompletableFuture.completedFuture(
				Results.badRequest(
						Json.toJson( new ErrorMessage( 400, "Error: " + message ) )
				                  )
		                                        );
	}

	@Override protected CompletionStage< Result > onNotFound( Http.RequestHeader request, String message ) {

		return CompletableFuture.completedFuture(
				Results.notFound(
						Json.toJson( new ErrorMessage( 404, "Error: " + message ) )
				                )
		                                        );
	}

	@Override protected CompletionStage< Result > onOtherClientError( Http.RequestHeader request, int statusCode,
	                                                                  String message ) {

		return CompletableFuture.completedFuture(
				Results.status( statusCode,
				                Json.toJson( new ErrorMessage( statusCode, "Error: " + message ) )
				              )
		                                        );
	}

	@Override protected void logServerError( Http.RequestHeader request, UsefulException usefulException ) {
		super.logServerError( request, usefulException );
	}

	@Override
	protected CompletionStage< Result > onDevServerError( Http.RequestHeader request, UsefulException exception ) {

		return CompletableFuture.completedFuture(
				Results.internalServerError(
						Json.toJson( new ErrorMessage( 500, "Error: " + exception.getMessage( ) ) )
				                           )
		                                        );
	}
}