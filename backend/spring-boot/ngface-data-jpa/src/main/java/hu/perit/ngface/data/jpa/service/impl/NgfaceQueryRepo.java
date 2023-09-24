package hu.perit.ngface.data.jpa.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NgfaceQueryRepo<E> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E>
{
}
