package filter_service_criteria;

import model.Service;

import java.util.ArrayList;
import java.util.List;

public class CriteriaDistance implements ServiceCriteria{

    Integer distance;

    public CriteriaDistance(int distance){

        this.distance = distance;
    }

    @Override
    public List<Service> meetCriteria(List<Service> services) {

        List<Service> list  = new ArrayList();

        for(Service ser : services){

            if(ser.getDistance() <= distance){

                list.add(ser);
            }
        }

        return list;
    }
}
