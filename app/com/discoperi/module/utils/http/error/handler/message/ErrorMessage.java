package com.discoperi.module.utils.http.error.handler.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by limpid on 5/15/17.
 */
@Data
@AllArgsConstructor
public class ErrorMessage {

	private Integer code;

	private String message;


}
