package com.discoperi.module;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Created by limpid on 5/23/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UnifiedMessage< T > {


	private String status = "OK";

	private T data;

	private Integer code = 200;


	public static < T > UnifiedMessage response( T entity ) {
		UnifiedMessage< T > message = new UnifiedMessage<>( );
		message.setData( entity );
		return message;
	}

	public static < T > UnifiedMessage error( String status, int code, T entity ) {
		UnifiedMessage< T > message = new UnifiedMessage< T >( );
		message.setCode( code );
		message.setStatus( status );
		message.setData( entity );
		return message;
	}

}
