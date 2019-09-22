package com.abc.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.data.repository.Repository;

import com.abc.framework.jpa.config.AbcOnDemandRepository;
import com.abc.service.BaseService;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * Rover Framework Base Service implementation class
 *
 * @param <T>
 * @param <E>
 * @param <PK>
 */
public class BaseServiceImpl<T, E, PK extends Serializable> implements BaseService<T, E, PK> {

	/**
	 * instance of the mapperFacade 
	 */
	private static MapperFacade mapper;

	static {
		final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		
		mapper = mapperFactory.getMapperFacade();
	}

	/**
	 * dto class generic instance.
	 */
	protected Class<T> dtoClass;

	/**
	 * entity class generic instance.
	 */
	protected Class<T> entityClass;

	/**
	 * repository class generic instance.
	 */
	AbcOnDemandRepository<E, PK> repository;

	/** Parameterized constructor.
	 * @param repository
	 */
	@SuppressWarnings("unchecked")
	public BaseServiceImpl(AbcOnDemandRepository<E, PK> repository) {
		this.repository = repository;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.dtoClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1];

	}

	@Override
	/** Method to find the record from the table using primary key.
	 * @param repository
	 */
	public T find(PK primaryKey) {
		return mapper.map(repository.findById(primaryKey), dtoClass);

	}
	@Override
	/** method to find all the elements.
	 * @param repository
	 */
	public List<T> findAll() {
		List<T> result = new ArrayList<>();
		for (E e : repository.findAll()) {
			result.add(mapper.map(e, dtoClass));
		}
		return result;
	}
	@Override
	/** Method to get all the distinct records.
	 * @param repository
	 */
	public List<T> getAllDistinct() {
		List<T> result = new ArrayList<>();

		Collection<E> resultList = new LinkedHashSet<>(repository.findAll());
		for (E e : resultList) {
			result.add(mapper.map(e, dtoClass));
		}
		return result;

	}

	@Override
	/** Method to check the object existance in the database using primary key.
	 * @param repository
	 */
	public boolean exists(final PK id) {
		return repository.existsById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	/** Method save the object in the database.
	 * @param dto
	 */
	public T save(T dto) {
		
		return mapper.map(repository.save((E) mapper.map(dto, entityClass)), dtoClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	/** Method to remove the object from the persistance context.
	 * @param dto
	 */
	public void remove(T dto) {
		repository.delete((E) mapper.map(dto, entityClass));
	}
	@Override
	/** Method to remove the object from the database using primary key.
	 * @param id
	 */
	public void remove(PK id) {
		repository.deleteById(id);
	}
	
	/** Method is used to get instance of the repository.
	 * @return instance of the repository
	 */
	public Repository<E, PK> getRepository() {

		return repository;
	}

	/** Method to get the instance of the MapperFacade.
	 * @return
	 */
	public MapperFacade getMapper() {
		return mapper;
	}
}
