package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Section;
import hu.webuni.logisticApp.lzsidek.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    @Autowired
    SectionRepository sectionRepository;

    public Optional<Section> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }

    public List<Section> findByTransportPlanId(Long id) {
        return sectionRepository.findByTransportPlanId(id);
    }

    public Optional<Section> findNextByTransportPlanIdAndNumber(Long transportPlanId, Integer number) {
        return sectionRepository.findNextByTransportPlanIdAndNumber(transportPlanId, number);
    }

}