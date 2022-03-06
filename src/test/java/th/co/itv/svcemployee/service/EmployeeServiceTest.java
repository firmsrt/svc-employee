package th.co.itv.svcemployee.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import th.co.itv.svcemployee.model.Employee;
import th.co.itv.svcemployee.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllEmployeeTest() {
        List<Employee> dataList = new ArrayList<>();
        dataList.add(new Employee(1, "tony", "stark"));
        dataList.add(new Employee(2, "steve", "rogers"));

        when(employeeRepository.findAll()).thenReturn(dataList);

        List<Employee> employeeList = employeeService.getAllEmployee();
        assertEquals(2, employeeList.size());
        assertEquals("tony", employeeList.get(0).getFirstName());
        assertEquals("rogers", employeeList.get(1).getLastName());
    }

    @Test
    public void getEmployeeByIdTest() {
        Employee data = new Employee(2, "steve", "rogers");

        when(employeeRepository.findById((long) 2)).thenReturn(java.util.Optional.of(data));

        Employee employee = employeeService.getEmployeeById(2);
        assertEquals("steve", employee.getFirstName());
        assertEquals("rogers", employee.getLastName());
    }

    @Test
    public void addEmployeeTest() {
        Employee data = new Employee();
        data.setFirstName("tony")
                .setLastName("stark");

        employeeService.addEmployee(data);

        verify(employeeRepository, times(1)).save(data);
    }

    @Test
    public void updateEmployeeTest() {
        Employee data = new Employee();
        data.setId(2)
                .setFirstName("steve")
                .setLastName("rogers");

        when(employeeRepository.findById((long) 2)).thenReturn(java.util.Optional.of(data));

        Employee updateEmployee = new Employee();
        updateEmployee.setFirstName("steve")
                .setLastName("rogerssssss");
        employeeService.updateEmployee(2, updateEmployee);

        verify(employeeRepository, times(1)).save(data);
    }

    @Test
    public void deleteEmployeeTest() {
        Employee data = new Employee();
        data.setId(2)
                .setFirstName("steve")
                .setLastName("rogerssssss");

        when(employeeRepository.findById((long) 2)).thenReturn(java.util.Optional.of(data));

        employeeService.deleteEmployee(2);

        verify(employeeRepository, times(1)).delete(data);
    }
}
