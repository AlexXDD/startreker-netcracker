package edu.netcracker.backend.service;

import edu.netcracker.backend.message.request.ServiceCreateForm;
import edu.netcracker.backend.message.response.ServiceDTO;
import edu.netcracker.backend.model.ServiceDescr;

import java.util.List;

public interface ServiceService {

    List<ServiceDTO> getServicesOfCarrier();

    List<ServiceDTO> getPaginServicesOfCarrier(Integer from, Integer amount);

    List<ServiceDTO> findByStatus(Integer status, Integer from, Integer number);

    ServiceDTO addService(ServiceCreateForm serviceCreateForm);

    ServiceDTO updateService(ServiceDTO serviceDTO);

    ServiceDTO deleteService(Long serviceId);
}
