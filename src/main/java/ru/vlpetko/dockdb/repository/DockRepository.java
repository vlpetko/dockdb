package ru.vlpetko.dockdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vlpetko.dockdb.model.DockUser;

@Repository
public interface DockRepository extends JpaRepository<DockUser, Long> {
}
