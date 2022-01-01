package hu.webuni.logisticApp.lzsidek.web;

import hu.webuni.logisticApp.lzsidek.dto.DelayDto;
import hu.webuni.logisticApp.lzsidek.dto.TransportPlanDto;
import hu.webuni.logisticApp.lzsidek.mapper.TransportPlanMapper;
import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import hu.webuni.logisticApp.lzsidek.service.IncomeService;
import hu.webuni.logisticApp.lzsidek.service.TransportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @Autowired
    TransportPlanService transportPlanService;

    @Autowired
    TransportPlanMapper transportPlanMapper;

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

        TransportPlan transportPlan = transportPlanService.getTransportPlanIfFound(id, milestoneId);

        transportPlanService.saveRelatedMilestones(id, milestoneId, delayInMinutes);

        transportPlan.setPlannedIncome(incomeService.getReducedIncome(transportPlan.getPlannedIncome(), delayInMinutes));
        return transportPlanMapper.transportPlanToDTO(transportPlanService.saveTransportPlan(transportPlan));
    }
}