package com.abc.framework.jpa.config;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbcOnDemandRepository<T, P extends Serializable> extends
		JpaRepository<T, P> {

}
