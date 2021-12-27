package hu.webuni.logisticApp.lzsidek.web;

import hu.webuni.logisticApp.lzsidek.dto.MilestoneDto;
import hu.webuni.logisticApp.lzsidek.mapper.MilestoneMapper;
import hu.webuni.logisticApp.lzsidek.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/milestones")
public class MilestoneController {

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    MilestoneMapper milestoneMapper;

    @GetMapping
    public List<MilestoneDto> getMilestones() {
        return milestoneMapper.milestonesToDTOs(milestoneService.getMilestones());
    }

}