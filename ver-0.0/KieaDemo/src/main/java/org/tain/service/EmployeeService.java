package org.tain.service;

import java.util.List;

import org.tain.domain.Employee;

public interface EmployeeService {
	void clear();
	List<Employee> list();
	Employee get(Long id);
	Employee save(Employee employee);
	Employee update(Employee employee);
	void delete(Long id);
}
