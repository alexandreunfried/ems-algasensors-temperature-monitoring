package com.unfried.algasensors.temperature.monitoring.api.model;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SensorAlertOutput {

	private TSID id;
	private Double maxTemperature;
	private Double minTemperature;

}
