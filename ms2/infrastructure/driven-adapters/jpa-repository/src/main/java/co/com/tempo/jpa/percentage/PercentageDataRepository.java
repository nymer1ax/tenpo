package co.com.tempo.jpa.percentage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PercentageDataRepository extends JpaRepository<PercentageData, Long>, QueryByExampleExecutor<PercentageData> {
    Optional<PercentageData> findFirstByOrderByIdDesc();
}
