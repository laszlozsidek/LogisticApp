package hu.webuni.logisticApp.lzsidek.service;

import hu.webuni.logisticApp.lzsidek.model.Milestone;
import hu.webuni.logisticApp.lzsidek.model.Section;
import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import hu.webuni.logisticApp.lzsidek.repository.TransportPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TransportPlanService {

    @Autowired
    TransportPlanRepository transportPlanRepository;

    @Autowired
    SectionService sectionService;

    @Autowired
    MilestoneService milestoneService;

    @Transactional
    public TransportPlan saveTransportPlan(TransportPlan transportPlan) {
        return transportPlanRepository.save(transportPlan);
    }

    public Optional<TransportPlan> getTransportPlanById(Long id) {
        return transportPlanRepository.findById(id);
    }

    public List<TransportPlan> getTransportList() {
        return transportPlanRepository.findAll();
    }

    public TransportPlan getTransportPlanIfFound(Long id, Long milestoneId) {
        TransportPlan transportPlan = getTransportPlanById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        milestoneService.getMilestoneById(milestoneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return transportPlan;
    }

    public void saveRelatedMilestones(Long id, Long milestoneId, int delayInMinutes) {
        boolean fromMilestoneFound = false;
        boolean toMilestoneFound = false;
        Milestone currentMilestoneToSet = null;
        Milestone nextMilestoneToSet = null;
        for (Section section : sectionService.findByTransportPlanId(id)) {
            if (section.getFromMilestone().getId().equals(milestoneId)) {
                fromMilestoneFound = true;
                currentMilestoneToSet = section.getFromMilestone();
                nextMilestoneToSet = section.getToMilestone();
                break;
            }
            if (section.getToMilestone().getId().equals(milestoneId)) {
                toMilestoneFound = true;
                Optional<Section> nextSection = sectionService.findNextByTransportPlanIdAndNumber(id, section.getNumber() + 1);
                if (nextSection.isPresent()) {
                    nextMilestoneToSet = nextSection.get().getFromMilestone();
                }
                break;
            }
        }
        if (!fromMilestoneFound && !toMilestoneFound) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (fromMilestoneFound) {
            currentMilestoneToSet.setPlannedTime(currentMilestoneToSet.getPlannedTime().plusMinutes(delayInMinutes));
            milestoneService.saveMilestone(currentMilestoneToSet);
        }

        if (nextMilestoneToSet != null) {
            nextMilestoneToSet.setPlannedTime(nextMilestoneToSet.getPlannedTime().plusMinutes(delayInMinutes));
            milestoneService.saveMilestone(nextMilestoneToSet);
        }
    }
}