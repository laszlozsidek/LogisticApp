package hu.webuni.logisticApp.lzsidek.web;

import hu.webuni.logisticApp.lzsidek.config.LogisticConfigProperties;
import hu.webuni.logisticApp.lzsidek.dto.DelayDto;
import hu.webuni.logisticApp.lzsidek.model.Section;
import hu.webuni.logisticApp.lzsidek.model.TransportPlan;
import hu.webuni.logisticApp.lzsidek.service.MilestoneService;
import hu.webuni.logisticApp.lzsidek.service.SectionService;
import hu.webuni.logisticApp.lzsidek.service.TransportPlanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransportPlanControllerIT {

    private static final String BASE_URI = "/api/transportPlans/";
    public static final String DELAY = "/delay";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TransportPlanService transportPlanService;

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    SectionService sectionService;

    @Autowired
    LogisticConfigProperties config;

    @Test
    void testThatNotExistingTransportPlanThrows404() {
        long notExistingTransportPlanId = 99L;
        long existingMilestoneId = 3L;
        int delayInMinutes = 30;

        boolean emptyMilestoneList = milestoneService.getMilestones().stream()
                .filter(e -> e.getId().equals(existingMilestoneId)).collect(Collectors.toList()).isEmpty();
        if (emptyMilestoneList) {
            Assertions.fail("Test precondition: Milestone does not exist");
        }
        boolean notEmptyTransportPlanList = !transportPlanService.getTransportList().stream()
                .filter(e -> e.getId().equals(notExistingTransportPlanId)).collect(Collectors.toList()).isEmpty();
        if (notEmptyTransportPlanList) {
            Assertions.fail("Test precondition: Transport plan exists");
        }

        DelayDto delayDto = new DelayDto(existingMilestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + notExistingTransportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testThatNotExistingMilestoneThrows404() {
        long existingTransportPlanId = 2L;
        long notExistingMilestoneId = 99L;
        int delayInMinutes = 30;

        boolean emptyMilestoneList = !milestoneService.getMilestones().stream()
                .filter(e -> e.getId().equals(notExistingMilestoneId)).collect(Collectors.toList()).isEmpty();
        if (emptyMilestoneList) {
            Assertions.fail("Test precondition: Milestone exists");
        }
        boolean notEmptyTransportPlanList = transportPlanService.getTransportList().stream()
                .filter(e -> e.getId().equals(existingTransportPlanId)).collect(Collectors.toList()).isEmpty();
        if (notEmptyTransportPlanList) {
            Assertions.fail("Test precondition: Transport plan does not exist");
        }

        DelayDto delayDto = new DelayDto(notExistingMilestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + existingTransportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testThatMilestoneNotPartOfTransportPlanThrows400() {
        long existingTransportPlanId = 2L;
        long existingMilestoneId = 1L;
        int delayInMinutes = 30;

        boolean emptyMilestoneList = milestoneService.getMilestones().stream()
                .filter(e -> e.getId().equals(existingMilestoneId)).collect(Collectors.toList()).isEmpty();
        if (emptyMilestoneList) {
            Assertions.fail("Test precondition: Milestone does not exist");
        }
        boolean notEmptyTransportPlanList = transportPlanService.getTransportList().stream()
                .filter(e -> e.getId().equals(existingTransportPlanId)).collect(Collectors.toList()).isEmpty();
        if (notEmptyTransportPlanList) {
            Assertions.fail("Test precondition: Transport plan does not exist");
        }
        
        DelayDto delayDto = new DelayDto(existingMilestoneId, delayInMinutes);

        List<TransportPlan> transportPlanList = webTestClient
                .get()
                .uri(BASE_URI + existingTransportPlanId)
                .exchange()
                .expectBodyList(TransportPlan.class)
                .returnResult()
                .getResponseBody();
        Assertions.assertNotNull(transportPlanList);

        Set<Long> milestones = new HashSet<>();
        transportPlanList.stream()
                .map(TransportPlan::getSections)
                .forEach(e -> e.forEach(f -> {
                    milestones.add(f.getToMilestone().getId());
                    milestones.add(f.getFromMilestone().getId());
                }));

        if (milestones.contains(existingMilestoneId)) {
            Assertions.fail("Test precondition: Milestone is part of transport plan");
        } else {
            webTestClient
                    .post()
                    .uri(BASE_URI + existingTransportPlanId + DELAY)
                    .bodyValue(delayDto)
                    .exchange()
                    .expectStatus()
                    .isBadRequest();
        }
    }

    @Test
    void testThatDelayCanBeAddedToPlannedTime() {
        long transportPlanId = 1L;
        long milestoneId = 1L;
        int delayInMinutes = 30;

        LocalDateTime plannedTime = milestoneService.getMilestoneById(milestoneId)
                .orElseGet(() -> Assertions.fail("Milestone with given ID not found"))
                .getPlannedTime();

        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + transportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isOk();

        LocalDateTime modifiedPlannedTime = milestoneService.getMilestoneById(milestoneId)
                .orElseGet(() -> Assertions.fail("Milestone with given ID not found"))
                .getPlannedTime();

        Assertions.assertEquals(plannedTime.plusMinutes(delayInMinutes), modifiedPlannedTime);
    }

    @Test
    void testThatDelayIsAddedToPlannedTimeOfToMilestoneToo() {
        long transportPlanId = 1L;
        long milestoneId = 1L;
        int delayInMinutes = 30;

        Section section = transportPlanService
                .getTransportPlanById(transportPlanId)
                .orElseGet(() -> Assertions.fail("TransportPlan with given ID not found"))
                .getSections().stream().filter(e -> e.getFromMilestone().getId().equals(milestoneId))
                .findFirst().orElseGet(() -> Assertions.fail("Milestone as FromMilestone not found in given transport plan"));

        LocalDateTime plannedTimeOfFrom = section.getFromMilestone().getPlannedTime();
        LocalDateTime plannedTimeOfTo = section.getToMilestone().getPlannedTime();

        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + transportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isOk();

        section = transportPlanService
                .getTransportPlanById(transportPlanId)
                .orElseGet(() -> Assertions.fail("TransportPlan with given ID not found"))
                .getSections().stream().filter(e -> e.getFromMilestone().getId().equals(milestoneId))
                .findFirst().orElseGet(() -> Assertions.fail("Milestone as FromMilestone not found in given transport plan"));
        LocalDateTime modifiedPlannedTimeOfFrom = section.getFromMilestone().getPlannedTime();
        LocalDateTime modifiedPlannedTimeOfTo = section.getToMilestone().getPlannedTime();

        Assertions.assertEquals(plannedTimeOfTo.plusMinutes(delayInMinutes), modifiedPlannedTimeOfTo);
        Assertions.assertEquals(plannedTimeOfFrom.plusMinutes(delayInMinutes), modifiedPlannedTimeOfFrom);
    }

    @Test
    void testThatDelayIsAddedToPlannedTimeOfFromMilestoneToo() {
        long transportPlanId = 1L;
        long milestoneId = 2L;
        int delayInMinutes = 30;

        Section section = transportPlanService
                .getTransportPlanById(transportPlanId)
                .orElseGet(() -> Assertions.fail("TransportPlan with given ID not found"))
                .getSections().stream().filter(e -> e.getToMilestone().getId().equals(milestoneId))
                .findFirst().orElseGet(() -> Assertions.fail("Milestone as ToMilestone not found in given transport plan"));

        int numberOfNextSection = section.getNumber() + 1;
        Section nextByTransportPlanIdAndNumber = sectionService
                .findNextByTransportPlanIdAndNumber(transportPlanId, numberOfNextSection)
                .orElseGet(() -> Assertions.fail("Next section with number or within transport plan not found"));

        LocalDateTime plannedTimeOfTo = section.getToMilestone().getPlannedTime();
        LocalDateTime plannedTimeOfFrom = nextByTransportPlanIdAndNumber.getFromMilestone().getPlannedTime();

        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + transportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isOk();

        section = transportPlanService
                .getTransportPlanById(transportPlanId)
                .orElseGet(() -> Assertions.fail("TransportPlan with given ID not found"))
                .getSections().stream().filter(e -> e.getToMilestone().getId().equals(milestoneId))
                .findFirst().orElseGet(() -> Assertions.fail("Milestone as ToMilestone not found in given transport plan"));

        nextByTransportPlanIdAndNumber = sectionService
                .findNextByTransportPlanIdAndNumber(transportPlanId, numberOfNextSection)
                .orElseGet(() -> Assertions.fail("Next section with number or within transport plan not found"));

        LocalDateTime modifiedPlannedTimeOfTo = section.getToMilestone().getPlannedTime();
        LocalDateTime modifiedPlannedTimeOfFrom = nextByTransportPlanIdAndNumber.getFromMilestone().getPlannedTime();

        Assertions.assertEquals(plannedTimeOfTo.plusMinutes(delayInMinutes), modifiedPlannedTimeOfTo);
        Assertions.assertEquals(plannedTimeOfFrom.plusMinutes(delayInMinutes), modifiedPlannedTimeOfFrom);
    }

    @Test
    void testThatIncomeDecreasedWithProperPercentsLT30() {
        long transportPlanId = 1L;
        long milestoneId = 2L;
        int delayInMinutes = 29;

        double plannedIncome = getPlannedIncome(transportPlanId);

        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + transportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isOk();

        double modifiedPlannedIncome = getPlannedIncome(transportPlanId);

        Assertions.assertEquals(plannedIncome, modifiedPlannedIncome);
    }

    @Test
    void testThatIncomeDecreasedWithProperPercentsGToE30LT60() {
        long transportPlanId = 1L;
        long milestoneId = 2L;
        int delayInMinutes = 59;

        double plannedIncome = getPlannedIncome(transportPlanId);

        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + transportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isOk();

        double modifiedPlannedIncome = getPlannedIncome(transportPlanId);

        Assertions.assertEquals(plannedIncome * (100 - config.getIncome().getPercent1()) / 100, modifiedPlannedIncome);
    }

    @Test
    void testThatIncomeDecreasedWithProperPercentsGToE60LT120() {
        long transportPlanId = 1L;
        long milestoneId = 2L;
        int delayInMinutes = 119;

        double plannedIncome = getPlannedIncome(transportPlanId);

        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + transportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isOk();

        double modifiedPlannedIncome = getPlannedIncome(transportPlanId);

        Assertions.assertEquals(plannedIncome * (100 - config.getIncome().getPercent2()) / 100, modifiedPlannedIncome);
    }

    @Test
    void testThatIncomeDecreasedWithProperPercentsGToE120() {
        long transportPlanId = 1L;
        long milestoneId = 2L;
        int delayInMinutes = 120;

        double plannedIncome = getPlannedIncome(transportPlanId);

        DelayDto delayDto = new DelayDto(milestoneId, delayInMinutes);

        webTestClient
                .post()
                .uri(BASE_URI + transportPlanId + DELAY)
                .bodyValue(delayDto)
                .exchange()
                .expectStatus()
                .isOk();

        double modifiedPlannedIncome = getPlannedIncome(transportPlanId);

        Assertions.assertEquals(plannedIncome * (100 - config.getIncome().getPercent3()) / 100, modifiedPlannedIncome);
    }

    private double getPlannedIncome(long transportPlanId) {
        return transportPlanService
                .getTransportPlanById(transportPlanId)
                .orElseGet(() -> Assertions.fail("TransportPlan with given ID not found"))
                .getPlannedIncome();
    }

}