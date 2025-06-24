package com.unfried.algasensors.temperature.monitoring.domain.repository;

import com.unfried.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.unfried.algasensors.temperature.monitoring.domain.model.SensorId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorAlertRepository extends JpaRepository<SensorAlert, SensorId> {
}
