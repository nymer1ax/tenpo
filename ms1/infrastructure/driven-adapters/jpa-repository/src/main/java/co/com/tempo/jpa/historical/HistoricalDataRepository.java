package co.com.tempo.jpa.historical;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalDataRepository extends JpaRepository<HistoricalData, Long>, QueryByExampleExecutor<HistoricalData> {
    HistoricalData findFirstByOrderByIdDesc();
}
