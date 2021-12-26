package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import hu.webuni.logisticApp.lzsidek.repository.TransportPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransportPlanService {

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Transactional
    public TransportPlan saveTransportPlan(TransportPlan transportPlan) {
        return transportPlanRepository.save(transportPlan);
    }

    public Optional<TransportPlan> getTransportPlanById(Long id) {
        return transportPlanRepository.findById(id);
    }

}