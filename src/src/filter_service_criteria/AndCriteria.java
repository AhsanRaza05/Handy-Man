package filter_service_criteria;

import model.Service;

import java.util.ArrayList;
import java.util.List;

public class AndCriteria implements ServiceCriteria{

    private ServiceCriteria[] criterias;

    public AndCriteria(ServiceCriteria... criterias) {
        this.criterias = criterias;
    }

    @Override
    public List<Service> meetCriteria(List<Service> services) {

        List<Service> filteredServices = services;

        for (ServiceCriteria criteria : criterias) {
            filteredServices = criteria.meetCriteria(filteredServices);
            //System.out.println(filteredServices);
        }

        return filteredServices;
    }
}
