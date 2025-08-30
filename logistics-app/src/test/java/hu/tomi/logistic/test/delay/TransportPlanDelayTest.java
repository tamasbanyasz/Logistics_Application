package hu.tomi.logistic.test.delay;

import hu.tomi.logistic.config.DelayConfig;
import hu.tomi.logistic.dto.DelayRequestDto;
import hu.tomi.logistic.model.Milestone;
import hu.tomi.logistic.model.Section;
import hu.tomi.logistic.model.TransportPlan;
import hu.tomi.logistic.repository.MilestoneRepository;
import hu.tomi.logistic.repository.SectionRepository;
import hu.tomi.logistic.repository.TransportPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureTestDatabase
public class TransportPlanDelayTest {

    @Autowired
    WebTestClient webTestClient;
    // Web client used to send HTTP requests and verify responses for the REST API

    @Autowired
    DelayConfig delayConfig;
    // Configuration bean that holds delay thresholds and penalty percentages for delays

    @Autowired
    TransportPlanRepository transportPlanRepository;
    // Repository to perform CRUD operations on TransportPlan entities

    @Autowired
    MilestoneRepository milestoneRepository;
    // Repository to perform CRUD operations on Milestone entities

    @Autowired
    SectionRepository sectionRepository;
    // Repository to perform CRUD operations on Section entities, representing parts of a transport plan

    TransportPlan transportPlan;
    Milestone fromMilestone;
    Milestone toMilestone;
    // Variables to hold entities created and used in the tests
    
    String jwtToken;

    @BeforeEach
    void init() {
        // Before each test, clear all related database tables to ensure a clean state
        sectionRepository.deleteAll();
        milestoneRepository.deleteAll();
        transportPlanRepository.deleteAll();

        // Create and save a new TransportPlan with a fixed expected income
        transportPlan = new TransportPlan();
        transportPlan.setExpectedIncome(1000);
        transportPlan = transportPlanRepository.save(transportPlan);

        // Create and save a 'from' Milestone with a fixed planned time
        fromMilestone = new Milestone();
        fromMilestone.setPlannedTime(LocalDateTime.of(2025, 8, 3, 12, 0));
        fromMilestone = milestoneRepository.save(fromMilestone);

        // Create and save a 'to' Milestone with a planned time two hours after 'from'
        toMilestone = new Milestone();
        toMilestone.setPlannedTime(LocalDateTime.of(2025, 8, 3, 14, 0));
        toMilestone = milestoneRepository.save(toMilestone);

        // Create and save a Section linking the TransportPlan and the two Milestones
        Section section = new Section();
        section.setNumber(0);
        section.setTransportPlan(transportPlan);
        section.setFromMilestone(fromMilestone);
        section.setToMilestone(toMilestone);
        sectionRepository.save(section);
        
        jwtToken = getToken("transportadmin", "pass2");

        // Print the delay thresholds and percentages from configuration for debugging purposes
        System.out.println("Delay thresholds: " + delayConfig.getThresholds());
        System.out.println("Delay percentages: " + delayConfig.getPercentages());
    }
    
    private String getToken(String username, String password) {
        String responseBody = webTestClient.post()
                .uri("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)  
                .returnResult()
                .getResponseBody();

        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(responseBody);
            return root.get("token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Token extraction failed", e);
        }
    }


    @Test
    void testDelayAffectsMilestonesAndIncome() {
        // Test that applying a delay correctly updates milestone times and reduces expected income

        DelayRequestDto dto = new DelayRequestDto();
        dto.setMilestoneId(fromMilestone.getId());
        dto.setDelayInMinutes(45);
        // Prepare a delay request of 45 minutes for the 'fromMilestone'

        webTestClient.post()
	        .uri("/api/transportPlans/" + transportPlan.getId() + "/delay")
	        .header("Authorization", "Bearer " + jwtToken)
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(dto)
	        .exchange()
	        .expectStatus().isOk();
        // Send POST request to the delay endpoint and expect 200 OK response

        // Retrieve updated entities from the repository to verify changes
        Milestone updatedFrom = milestoneRepository.findById(fromMilestone.getId()).orElseThrow();
        Milestone updatedTo = milestoneRepository.findById(toMilestone.getId()).orElseThrow();
        TransportPlan updatedPlan = transportPlanRepository.findById(transportPlan.getId()).orElseThrow();

        // Assert that the planned times have been pushed forward by the delay duration
        assertEquals(LocalDateTime.of(2025, 8, 3, 12, 45), updatedFrom.getPlannedTime());
        assertEquals(LocalDateTime.of(2025, 8, 3, 14, 45), updatedTo.getPlannedTime());

        // Calculate expected income reduction dynamically based on configured delay thresholds and percentages
        int delayInMinutes = 45;
        int originalIncome = 1000;
        int reducedPercent = getReductionPercent(delayInMinutes);
        int expectedIncome = originalIncome * (100 - reducedPercent) / 100;

        // Assert that the expected income has been reduced correctly according to the penalty rules
        assertEquals(expectedIncome, updatedPlan.getExpectedIncome());
    }

    @Test
    void testInvalidMilestoneIdReturnsNotFound() {
        // Test that providing an invalid milestone ID returns a 404 Not Found status

        DelayRequestDto dto = new DelayRequestDto();
        dto.setMilestoneId(999L); // Non-existent milestone ID
        dto.setDelayInMinutes(30);

        webTestClient.post()
	        .uri("/api/transportPlans/" + transportPlan.getId() + "/delay")
	        .header("Authorization", "Bearer " + jwtToken)
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(dto)
	        .exchange()
	        .expectStatus().isNotFound();

    }

    @Test
    void testMilestoneNotPartOfPlanReturnsBadRequest() {
        // Test that a milestone not belonging to the transport plan returns 400 Bad Request

        Milestone unrelated = new Milestone();
        unrelated.setPlannedTime(LocalDateTime.now());
        unrelated = milestoneRepository.save(unrelated);

        DelayRequestDto dto = new DelayRequestDto();
        dto.setMilestoneId(unrelated.getId()); // Milestone not linked to transportPlan
        dto.setDelayInMinutes(30);

        webTestClient.post()
	        .uri("/api/transportPlans/" + transportPlan.getId() + "/delay")
	        .header("Authorization", "Bearer " + jwtToken)
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(dto)
	        .exchange()
	        .expectStatus().isBadRequest();
    }

    // Helper method to determine the income reduction percentage based on configured thresholds
    private int getReductionPercent(int delayInMinutes) {
        List<Integer> thresholds = delayConfig.getThresholds();
        List<Integer> percentages = delayConfig.getPercentages();

        // Iterate thresholds from highest to lowest to find applicable penalty
        for (int i = thresholds.size() - 1; i >= 0; i--) {
            if (delayInMinutes >= thresholds.get(i)) {
                return percentages.get(i);
            }
        }
        return 0; // No penalty if delay is below all thresholds
    }

    @Test
    void testZeroDelayDoesNotChangeTimesOrIncome() {
        // Verify that a zero-minute delay does not alter milestone times or income

        DelayRequestDto dto = new DelayRequestDto();
        dto.setMilestoneId(fromMilestone.getId());
        dto.setDelayInMinutes(0);

        webTestClient.post()
	        .uri("/api/transportPlans/" + transportPlan.getId() + "/delay")
	        .header("Authorization", "Bearer " + jwtToken)
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(dto)
	        .exchange()
	        .expectStatus().isOk();

        // Reload entities and assert values remain unchanged
        Milestone updatedFrom = milestoneRepository.findById(fromMilestone.getId()).orElseThrow();
        Milestone updatedTo = milestoneRepository.findById(toMilestone.getId()).orElseThrow();
        TransportPlan updatedPlan = transportPlanRepository.findById(transportPlan.getId()).orElseThrow();

        assertEquals(fromMilestone.getPlannedTime(), updatedFrom.getPlannedTime());
        assertEquals(toMilestone.getPlannedTime(), updatedTo.getPlannedTime());
        assertEquals(1000, updatedPlan.getExpectedIncome());
    }

    @Test
    void testVeryLargeDelayAppliesMaximumPenalty() {
        // Test behavior when an extremely large delay is applied, expecting maximum penalty and time shifts

        int veryLargeDelay = 10_000; // 10,000 minutes (~7 days)

        DelayRequestDto dto = new DelayRequestDto();
        dto.setMilestoneId(fromMilestone.getId());
        dto.setDelayInMinutes(veryLargeDelay);

        webTestClient.post()
	        .uri("/api/transportPlans/" + transportPlan.getId() + "/delay")
	        .header("Authorization", "Bearer " + jwtToken)
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(dto)
	        .exchange()
	        .expectStatus().isOk();

        Milestone updatedFrom = milestoneRepository.findById(fromMilestone.getId()).orElseThrow();
        Milestone updatedTo = milestoneRepository.findById(toMilestone.getId()).orElseThrow();
        TransportPlan updatedPlan = transportPlanRepository.findById(transportPlan.getId()).orElseThrow();

        // Verify milestone times are delayed correctly by the large amount
        assertEquals(fromMilestone.getPlannedTime().plusMinutes(veryLargeDelay), updatedFrom.getPlannedTime());
        assertEquals(toMilestone.getPlannedTime().plusMinutes(veryLargeDelay), updatedTo.getPlannedTime());

        // Calculate expected income after applying the maximum configured penalty
        int maxPenalty = delayConfig.getPercentages().stream().max(Integer::compareTo).orElse(100);
        int expectedIncome = 1000 * (100 - maxPenalty) / 100;

        assertEquals(expectedIncome, updatedPlan.getExpectedIncome());
    }
    
    // Test with not correspond user
    @Test
    void testForbiddenWhenUserHasNoPermission() {
        String userToken = getToken("normaluser", "pass1");

        DelayRequestDto dto = new DelayRequestDto();
        dto.setMilestoneId(fromMilestone.getId());
        dto.setDelayInMinutes(15);

        webTestClient.post()
                .uri("/api/transportPlans/" + transportPlan.getId() + "/delay")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isForbidden();
    }

}