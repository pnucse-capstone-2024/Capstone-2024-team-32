package ddalpi.backend.repository;

import ddalpi.backend.domain.RequestIP;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface RequestIPRepository extends JpaRepository<RequestIP, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from RequestIP r where r.ip = :ip")
    Optional<RequestIP> findByIpForUpdate(String ip);
}
