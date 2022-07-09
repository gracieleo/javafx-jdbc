package model.service;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {
	
	public List<Department> findAll(){
		List<Department> lst = new ArrayList<>();
		lst.add(new Department(1, "Um"));
		lst.add(new Department(2, "Dois"));
		return lst;
	}

}
