package org.employee.controller;


import org.employee.model.Employee;
import org.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee){

        Employee persistedEmp = employeeRepository.save(employee);
        if(persistedEmp == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(employee);

    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee =  employeeRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Employee with id :"+id+" doesn't exist"));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,@RequestBody Employee employeeDetails){
        Employee employee =  employeeRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Employee with id :"+id+" doesn't exist"));
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setIpAddress(employeeDetails.getIpAddress());
        employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployeeById(@PathVariable Long id){
        Employee employee =  employeeRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Employee with id :"+id+" doesn't exist"));
        employeeRepository.delete(employee);
        Map<String,Boolean> result = new HashMap<>();
        result.put("DELETED",true);
        return ResponseEntity.ok(result);
    }
}

