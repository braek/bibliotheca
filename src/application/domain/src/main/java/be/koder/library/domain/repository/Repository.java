package be.koder.library.domain.repository;

import java.util.Optional;

// Abstraction of domain repository
public interface Repository<ID, AGGREGATE_ROOT> {

    void save(AGGREGATE_ROOT aggregateRoot);

    Optional<AGGREGATE_ROOT> get(ID id);

    void delete(ID id);
}