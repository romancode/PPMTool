package com.krosstek.ppmtool.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.krosstek.ppmtool.domain.BackLog;

@Repository
public interface BacklogRepository extends CrudRepository<BackLog, Long> {
}
