package hu.webuni.logisticApp.lzsidek.web;

import hu.webuni.logisticApp.lzsidek.dto.TransportPlanBodyDto;
import hu.webuni.logisticApp.lzsidek.dto.TransportPlanDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    @PostMapping("/{id}/delay")
    public TransportPlanDto registerDelay(@PathVariable Long id, @RequestBody TransportPlanBodyDto transportPlanBodyDto) {



        return null;
    }
}