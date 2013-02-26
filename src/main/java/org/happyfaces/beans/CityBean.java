package org.happyfaces.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.happyfaces.beans.base.BaseBean;
import org.happyfaces.domain.City;
import org.happyfaces.services.ICityService;
import org.happyfaces.services.base.IService;

@ManagedBean(name = "cityBean")
@ViewScoped
public class CityBean extends BaseBean<City> {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{cityService}")
    private ICityService cityService;

    public CityBean() {
        super(City.class);
    }

    @Override
    protected IService<City> getPersistenceService() {
        return cityService;
    }

    public void setCityService(ICityService cityService) {
        this.cityService = cityService;
    }

}
