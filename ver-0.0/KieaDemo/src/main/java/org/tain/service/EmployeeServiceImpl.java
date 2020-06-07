package org.tain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tain.domain.Employee;
import org.tain.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repo;
	
	@Override
	public void clear() {
		this.repo.deleteAll();
	}
	
	@Override
	public List<Employee> list() {
		return this.repo.findAll();
	}

	@Override
	public Employee get(Long id) {
		return this.repo.findById(id).get();
	}

	@Override
	public Employee save(Employee employee) {
		return this.repo.save(employee);
	}

	@Override
	public Employee update(Employee employee) {
		return this.repo.saveAndFlush(employee);
	}

	@Override
	public void delete(Long id) {
		this.repo.deleteById(id);
	}
}
