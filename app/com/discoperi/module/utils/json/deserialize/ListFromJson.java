package com.discoperi.module.utils.json.deserialize;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lonely on 25.08.16.
 * @author : Alexander Balyshyn
 * @version : v1
 */

/**
 * Gson custom class with ParameterizedType implementation for transform Json token (List structure) to
 * List interface
 **/
public class ListFromJson<X> implements ParameterizedType {

	private Class<?> aClass;

	public ListFromJson( Class<X> wrapped ) {
		this.aClass = wrapped;
	}
	@Override
	public Type[] getActualTypeArguments() {
		return new Type[] { aClass };
	}
	@Override
	public Type getRawType() {
		return List.class;
	}
	@Override
	public Type getOwnerType() {
		return null;
	}

}