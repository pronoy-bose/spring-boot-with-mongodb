package com.osi.vehicle_access.service.Impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osi.vehicle_access.model.Building;
import com.osi.vehicle_access.model.Person;
import com.osi.vehicle_access.model.VehicleLog;
import com.osi.vehicle_access.model.VehicleNumbers;
import com.osi.vehicle_access.repository.BuildingRepository;
import com.osi.vehicle_access.repository.PersonsRepository;
import com.osi.vehicle_access.repository.VehicleLogRepository;
import com.osi.vehicle_access.service.IPlateService;

@Component
public class PlateServiceImpl implements IPlateService {

	@Autowired
	private PersonsRepository personsRepository;
	@Autowired
	private VehicleLogRepository vehicleLogRepository;
	@Autowired
	private BuildingRepository buildingRepository;

	@Override
	public Map<String, String> managePlateData(Map<String, Object> plateData, String vehiclePosition) {
		// TODO To Be Implemented
		ArrayList arrayList = new ArrayList();
		ArrayList candidatesList = new ArrayList();
		Map<String, String> authCheck = new HashMap<String, String>();
		Optional<Person> person = Optional.empty();
		arrayList = (ArrayList) plateData.get("results");
		if (arrayList.size() > 0) {
			Map<String, Object> resultMap = (Map<String, Object>) arrayList.get(0);
			candidatesList = (ArrayList) resultMap.get("candidates");
			String plateNumber = resultMap.get("plate").toString();
			Boolean personFound = false;
			for (Object candidate : candidatesList) {
				Map<String, Object> plateDataMap = (Map<String, Object>) candidate;
				person = personsRepository.findByNumber((String) plateDataMap.get("plate"));
				if (person.isPresent()) {
					personFound = true;
					addVehicleLogs(person.get(), (String) plateDataMap.get("plate"), vehiclePosition);
					break;
				}
			}
			if (personFound) {
				authCheck.put("person", "authorized");
				return authCheck;
			} else {
				authCheck.put("person", "unauthorized");
				return authCheck;
			}

		}
		authCheck.put("person", "noresults");
		return authCheck;
	}

	public void addVehicleLogs(Person person, String number, String vehiclePosition) {
		if (vehiclePosition.equalsIgnoreCase("out")) {
			updateOutTimeForLog(person.getId(), number);
		} else if (vehiclePosition.equalsIgnoreCase("in")) {

			VehicleLog vehicleLog = new VehicleLog();
			vehicleLog.setPersonId(person.getId());
			vehicleLog.setVehicleNumber(number);
			for (VehicleNumbers vehicleNumbers : person.getVehicleNumbers()) {
				if (vehicleNumbers.getNumber().equals(number)) {
					vehicleLog.setVehicleType(vehicleNumbers.getType());
				}
			}

			// Setting old vehicle logs to out
			List<VehicleLog> oldVehicleLogs = vehicleLogRepository
					.findAllByPersonIdAndVehicleNumberAndStatus(person.getId(), number, "IN");
			for (VehicleLog oldVehicleLog : oldVehicleLogs) {
				// if (oldVehicleLog.getOutTime().toString()
				// .equals(getDate("E MMM dd HH:mm:ss Z yyyy", "Fri Jan 01
				// 12:00:00 UTC 1971").toString())) {
				oldVehicleLog.setOutTime(new Date());
				oldVehicleLog.setStatus("OUT");
				vehicleLogRepository.save(oldVehicleLog);
				updateParkingSlot(vehicleLog.getVehicleType(), "OUT");
				// }
			}

			String buildingId = updateParkingSlot(vehicleLog.getVehicleType(), "IN");
			vehicleLog.setBuildingId(buildingId);
			vehicleLog.setInTime(new Date());
			vehicleLog.setOutTime(getDate("yyyy-MM-dd HH:mm:ss", "1971-01-01 12:00:00"));
			vehicleLog.setStatus("IN");
			vehicleLogRepository.save(vehicleLog);
		}

	}

	public void updateOutTimeForLog(String personId, String number) {

		List<VehicleLog> vehicleLogs = vehicleLogRepository.findAllByPersonIdAndVehicleNumberAndStatus(personId, number,
				"IN");
		for (VehicleLog vehicleLog : vehicleLogs) {
			// if (vehicleLog.getOutTime().toString()
			// .equals(getDate("E MMM dd HH:mm:ss Z yyyy", "Fri Jan 01 12:00:00
			// UTC 1971").toString())) {
			vehicleLog.setOutTime(new Date());
			vehicleLog.setStatus("OUT");
			vehicleLogRepository.save(vehicleLog);
			// }
		}
	}

	@Override
	public Optional<Person> findByNumber(String number, String checkType) {
		
		number = "^"+number+"$";
		Optional<Person> person = personsRepository.findByNumber(number);
		if (person.isPresent() && checkType.equals("lookup")) {
			addVehicleLogs(person.get(), number, "in");
		}
		return person;
	}

	public String updateParkingSlot(String vehicleType, String direction) {
		List<Building> buildingList = buildingRepository.findAll();
		String buildingId = "";
		for (Building building : buildingList) {
			if (vehicleType.equalsIgnoreCase("car")) {
				if (building.getRemainingCarSlots() > 0
						&& building.getRemainingCarSlots() <= building.getTotalCarSlots()
						&& direction.equalsIgnoreCase("IN")) {

					building.setRemainingCarSlots(building.getRemainingCarSlots() - 1);
					buildingRepository.save(building);
					buildingId = building.getId();
					return buildingId;
				} else if (building.getRemainingCarSlots() < building.getTotalCarSlots()
						&& direction.equalsIgnoreCase("OUT")) {

					building.setRemainingCarSlots(building.getRemainingCarSlots() + 1);
					buildingRepository.save(building);
					buildingId = building.getId();
					return buildingId;
				}
			} else if (vehicleType.equalsIgnoreCase("bike")) {
				if (building.getRemainingBikeSlots() > 0
						&& building.getRemainingBikeSlots() <= building.getTotalBikeSlots()
						&& direction.equalsIgnoreCase("IN")) {

					building.setRemainingBikeSlots(building.getRemainingBikeSlots() - 1);
					buildingRepository.save(building);
					buildingId = building.getId();
					return buildingId;
				} else if (building.getRemainingBikeSlots() < building.getTotalBikeSlots()
						&& direction.equalsIgnoreCase("OUT")) {

					building.setRemainingBikeSlots(building.getRemainingBikeSlots() + 1);
					buildingRepository.save(building);
					buildingId = building.getId();
					return buildingId;
				}

			}
		}
		return buildingId;
	}

	public Date getDate(String pattern, String dateValue) {

		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Date dateVal = null;
		DateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setTimeZone(timeZone);
		try {
			dateVal = formatter.parse(dateValue);
		} catch (ParseException e) {
			System.out.println("Error");
		}
		return dateVal;
	}

}
