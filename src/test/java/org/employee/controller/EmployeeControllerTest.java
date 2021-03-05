package org.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.employee.exception.ControllerHandlerException;
import org.employee.exception.ResourceNotFoundException;
import org.employee.exception.dto.ErrorResult;
import org.employee.model.Employee;
import org.employee.repository.EmployeeRepository;
import org.hibernate.hql.internal.ast.ErrorReporter;
import org.json.JSONObject;
import org.json.JSONString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

/*@SpringBootTest
@AutoConfigureMockMvc*/
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    public void getEmployeeForAnIdWithSuccessHttpResponseTest() throws Exception {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Tony");
        employee.setLastName("Stark");
        employee.setEmailId("tstarK@gmail.com");
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));
        this.mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andReturn();
        Mockito.verify(employeeRepository,Mockito.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void getEmployeeForAnIdNotFoundHttpResponseTest() throws Exception{
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenThrow(new ResourceNotFoundException("Employee with id"+Mockito.anyLong()+"doesn't exist"));
        this.mockMvc.perform(get("/api/v1/employees/2"))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    public void creatingEmployeeForInvalidRequestAndReturnBadResponseStatusTest() throws Exception{
        //Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenThrow(new ResourceNotFoundException("Employee with id"+Mockito.anyLong()+"doesn't exist"));
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Tony");
        employee.setLastName("Stark");
        employee.setEmailId("tstarK.com");

        ObjectMapper mapper = new ObjectMapper();
        String employeeJSONString = mapper.writeValueAsString(employee);

        MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJSONString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        ErrorResult expectedErrorResponse = new ErrorResult("emailId", "Please enter valid email");
        String exceptedResponse = mapper.writeValueAsString(expectedErrorResponse);

        Assertions.assertEquals(actualResponse,exceptedResponse);

    }


    @Test
    public void creatingEmployeeForInvalidIPAddressAndReturnBadResponseTest() throws Exception{
        //Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenThrow(new ResourceNotFoundException("Employee with id"+Mockito.anyLong()+"doesn't exist"));
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("Tony");
        employee.setLastName("Stark");
        employee.setEmailId("tstarK@stark.com");
        employee.setIpAddress("493.12.2.1");

        ObjectMapper mapper = new ObjectMapper();
        String employeeJSONString = mapper.writeValueAsString(employee);

        MvcResult mvcResult = this.mockMvc.perform(post("/api/v1/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJSONString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        ErrorResult expectedErrorResponse = new ErrorResult("ipAddress", "ip address is invalid");
        String exceptedResponse = mapper.writeValueAsString(expectedErrorResponse);

        Assertions.assertEquals(actualResponse,exceptedResponse);

    }



}
