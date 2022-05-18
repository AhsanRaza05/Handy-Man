package filter_service_criteria;

import model.Service;

import java.util.ArrayList;
import java.util.List;

public class CriteriaSalary implements ServiceCriteria{

    Integer salary;

    public CriteriaSalary(int salary){

        this.salary = salary;
    }

    @Override
    public List<Service> meetCriteria(List<Service> services) {

        List<Service> list  = new ArrayList();

        for(Service ser : services){

            if( ser.getSalary() <= salary){

                list.add(ser);
            }
        }

        return list;
    }
}
