package hu.webuni.logisticApp.lzsidek.web;

import hu.webuni.logisticApp.lzsidek.dto.DelayDto;
import hu.webuni.logisticApp.lzsidek.dto.TransportPlanDto;
import hu.webuni.logisticApp.lzsidek.mapper.TransportPlanMapper;
import hu.webuni.logisticApp.lzsidek.model.Milestone;
import hu.webuni.logisticApp.lzsidek.model.Section;
import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import hu.webuni.logisticApp.lzsidek.service.IncomeService;
import hu.webuni.logisticApp.lzsidek.service.MilestoneService;
import hu.webuni.logisticApp.lzsidek.service.SectionService;
import hu.webuni.logisticApp.lzsidek.service.TransportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @Autowired
    TransportPlanService transportPlanService;

    @Autowired
    TransportPlanMapper transportPlanMapper;

    @Autowired
    SectionService sectionService;

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    IncomeService incomeService;

    @GetMapping("/{id}")
    public TransportPlanDto getTransportPlanById(@PathVariable Long id) {
        return transportPlanMapper.transportPlanToDTO(
                transportPlanService.getTransportPlanById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/{id}/delay")
    public TransportPlanDto registerDelay(@PathVariable Long id, @RequestBody DelayDto delayDto) {
        Long milestoneId = delayDto.getMilestoneId();
        int delayInMinutes = delayDto.getDelayInMinutes();

        TransportPlan transportPlan = transportPlanService.getTransportPlanById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        milestoneService.getMilestoneById(milestoneId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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

        transportPlan.setPlannedIncome(incomeService.getReducedIncome(transportPlan.getPlannedIncome(), delayInMinutes));
        return transportPlanMapper.transportPlanToDTO(transportPlanService.saveTransportPlan(transportPlan));
    }
}