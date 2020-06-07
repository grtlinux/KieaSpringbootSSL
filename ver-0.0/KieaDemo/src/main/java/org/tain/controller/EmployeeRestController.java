package org.tain.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tain.domain.Employee;
import org.tain.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

	@Autowired
	private EmployeeService service;
	
	@GetMapping("/clear")
	public void clear() {
		this.service.clear();
	}
	
	@GetMapping("/list")
	public List<Employee> list() {
		return this.service.list();
	}
	
	@GetMapping("/by/{id}")
	public Employee get(@PathVariable(name = "id") Long id) {
		return this.service.get(id);
	}
	
	@Transactional
	@PostMapping("/save")
	public Employee save(@RequestBody Employee employee) throws Exception {
		employee.setDttm(getTimestamp());
		employee.setCredttm(LocalDateTime.now());
		employee.setUpddttm(LocalDateTime.now());
		return this.service.save(employee);
	}
	
	@Transactional
	@PutMapping("/update")
	public Employee update(@RequestBody Employee employee) {
		employee.setDttm(getTimestamp());
		employee.setUpddttm(LocalDateTime.now());
		return this.service.update(employee);
	}
	
	@Transactional
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable(name = "id") Long id) {
		this.service.delete(id);
	}
	
	/*
	 * getTimestamp
	 */
	private Timestamp getTimestamp() {
		String type = "LocalDateTime";
		switch (type) {
		case "LocalDateTime":
			return Timestamp.valueOf(LocalDateTime.now());
		case "DateToLong":
			return new Timestamp(new Date().getTime());
		}
		return new Timestamp(new Date().getTime());
	}
}
