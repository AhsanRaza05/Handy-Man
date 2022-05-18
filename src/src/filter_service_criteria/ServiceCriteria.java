package filter_service_criteria;

import model.Service;

import java.util.List;

public interface ServiceCriteria {

    public List<Service> meetCriteria(List<Service> services);
}
