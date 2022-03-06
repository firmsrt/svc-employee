package th.co.itv.svcemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import th.co.itv.svcemployee.model.Employee;
import th.co.itv.svcemployee.service.EmployeeService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {
    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;


    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void getAllEmployeesTest() throws Exception {
        List<Employee> dataList = new ArrayList<>();
        dataList.add(new Employee(1, "tony", "stark"));
        dataList.add(new Employee(2, "steve", "rogers"));

        when(employeeService.getAllEmployee()).thenReturn(dataList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {
        Employee data = new Employee(1, "tony", "stark");

        when(employeeService.getEmployeeById(1)).thenReturn(data);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("tony")));
    }

    @Test
    public void addEmployeeTest() throws Exception {
        Employee data = new Employee(1, "tony", "stark");

        when(employeeService.addEmployee(data)).thenReturn(data);

        String content  = objectWriter.writeValueAsString(data);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("tony")));
    }

    @Test
    public void updateEmployeeTest() throws Exception {
        Employee dataUpdated = new Employee(1, "tonyyyy", "starkkkk");

        when(employeeService.updateEmployee(1, dataUpdated)).thenReturn(dataUpdated);

        String content  = objectWriter.writeValueAsString(dataUpdated);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", is("tonyyyy")));
    }

    @Test
    public void daleteEmployeeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
