package com.discoperi.module.utils.json.deserialize;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by lonely on 25.08.16.
 * @author : Alexander Balyshyn
 * @version : v1
 */

/**
 * Gson custom class with ParameterizedType implementation for transform Json token (Map structure) to
 * Map interface (HashMap, TreeMap and LinkedHashMap)
 **/
public class MapFromJson<X, Y> implements ParameterizedType {
	private Class<?> aClass;
	private Class<?> bClass;

	public MapFromJson( Class<X> aClass, Class<Y> bClass ) {
		this.aClass = aClass;
		this.bClass = bClass;
	}


	@Override
	public Type[] getActualTypeArguments( ) {
		return new Type[ ]{ aClass, bClass };
	}

	@Override
	public Type getRawType( ) {
		return Map.class;
	}

	@Override
	public Type getOwnerType( ) {
		return null;
	}
}
