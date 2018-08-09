package com.osi.vehicle_access.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.osi.vehicle_access.model.Person;
import com.osi.vehicle_access.model.VehicleLog;

public interface IPlateService {
	
	public Optional<Person> findByNumber(String number,String checkType);
	
	public Map<String, String> managePlateData(Map<String, Object> plateData, String vehiclePosition) throws ParseException;
	
}
