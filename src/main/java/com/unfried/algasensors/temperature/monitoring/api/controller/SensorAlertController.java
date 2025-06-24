package com.unfried.algasensors.temperature.monitoring.api.controller;

import com.unfried.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.unfried.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.unfried.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.unfried.algasensors.temperature.monitoring.domain.model.SensorId;
import com.unfried.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
@RequiredArgsConstructor
public class SensorAlertController {

	private final SensorAlertRepository sensorAlertRepository;

	@GetMapping
	public SensorAlertOutput getDetail(@PathVariable TSID sensorId) {
		SensorAlert sensorAlert = findById(sensorId);

		return convertToModel(sensorAlert);
	}

	@PutMapping
	public SensorAlertOutput createOrUpdate(@PathVariable TSID sensorId, @RequestBody SensorAlertInput input) {
		SensorAlert sensorAlert = findByIdOrDefault(sensorId);
		sensorAlert.setMinTemperature(input.getMinTemperature());
		sensorAlert.setMaxTemperature(input.getMaxTemperature());
		sensorAlertRepository.saveAndFlush(sensorAlert);

		return convertToModel(sensorAlert);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping
	public void delete(@PathVariable TSID sensorId) {
		SensorAlert sensorAlert = findById(sensorId);

		sensorAlertRepository.delete(sensorAlert);
	}

	private SensorAlert findById(TSID sensorId) {
		return sensorAlertRepository.findById(new SensorId(sensorId))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	private SensorAlert findByIdOrDefault(TSID sensorId) {
		return sensorAlertRepository.findById(new SensorId(sensorId))
				.orElse(SensorAlert.builder()
						.id(new SensorId(sensorId))
						.minTemperature(null)
						.maxTemperature(null)
						.build());
	}

	private SensorAlertOutput convertToModel(SensorAlert sensorAlert) {
		return SensorAlertOutput.builder()
				.id(sensorAlert.getId().getValue())
				.maxTemperature(sensorAlert.getMaxTemperature())
				.minTemperature(sensorAlert.getMinTemperature())
				.build();
	}

}
