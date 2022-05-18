package filter_service_criteria;

import model.Service;

import java.util.ArrayList;
import java.util.List;

public class CriteriaServiceName implements ServiceCriteria{

    String name;

    public CriteriaServiceName(String name){

        this.name = name;
    }
    @Override
    public List<Service> meetCriteria(List<Service> services) {

        List<Service> list  = new ArrayList();

        for(Service ser : services){

            if(ser.getName().equals(name)){

                list.add(ser);
            }
        }

        return list;
    }
}
