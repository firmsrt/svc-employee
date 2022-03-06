package th.co.itv.svcemployee.repository;

import org.springframework.data.repository.CrudRepository;
import th.co.itv.svcemployee.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
