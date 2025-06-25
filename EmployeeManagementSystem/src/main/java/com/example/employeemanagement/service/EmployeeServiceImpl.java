package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeDTO;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    private EmployeeDTO convertToDTO(Employee emp) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(emp.getId());
        dto.setName(emp.getName());
        dto.setDepartment(emp.getDepartment());
        dto.setSalary(emp.getSalary());
        return dto;
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setId(dto.getId());
        emp.setName(dto.getName());
        emp.setDepartment(dto.getDepartment());
        emp.setSalary(dto.getSalary());
        return emp;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return repository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return convertToDTO(repository.findById(id).orElseThrow());
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        Employee emp = convertToEntity(dto);
        return convertToDTO(repository.save(emp));
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee emp = repository.findById(id).orElseThrow();
        emp.setName(dto.getName());
        emp.setDepartment(dto.getDepartment());
        emp.setSalary(dto.getSalary());
        return convertToDTO(repository.save(emp));
    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}