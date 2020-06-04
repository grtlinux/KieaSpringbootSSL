package org.tain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.tain.domain.Stmt;

@RepositoryRestResource
public interface StmtRepository extends JpaRepository<Stmt, Long>{

	Stmt findBySeqNo(Integer seqNo);
}
