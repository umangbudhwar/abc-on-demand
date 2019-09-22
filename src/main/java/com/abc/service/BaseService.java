package com.abc.service;

import java.io.Serializable;
import java.util.List;


/**
 * Skeleton Service for all the business object Service interfaces.
 *
 * @param <T>
 * @param <E>
 * @param <PK>
 */
public interface BaseService<T,E,P extends Serializable> { //NOSONAR
	
	/** Method to find the persistent object using primary key.
	 * @param primaryKey
	 * @return
	 */
	T find(P primaryKey);
	
	/** Method to return all the persistent object.
	 * @return List of Type T.
	 */
	List<T> findAll();
	
	/** Method to return all the distinct persistent object.
	 * @return List of Type T.
	 */
	List<T> getAllDistinct();
	
	/** Method to check the existence of the persistent object using primary key.
	 * @param key
	 * @return
	 */
	boolean exists(P key);
	
	/** Method to save the persistent object.
	 * @param object
	 * @return
	 */
	T save(T object);
	
	/** Method to remove the persistent object.
	 * @param object
	 */
	void remove(T object);
	
	/** Method to remove the persistent object using primary key.
	 * @param key
	 */
	void remove(P key);
	
}

