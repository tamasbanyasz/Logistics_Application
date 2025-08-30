package hu.tomi.logistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Application Overview

	This application is a transportation/logistics management system that allows the management of transport plans (TransportPlan), their sections (Section), 
	and milestones (Milestone). The main functionalities are:

		1. Transport Plan Management
	
			* Create, update, and delete transport plans.
			
			* Each plan has an expected income (expectedIncome) and associated sections.
	
		2. Sections and Milestones Management
	
			* Each transport plan consists of multiple sections, with each section having a start and end milestone.
			
			* Each milestone has a planned time (plannedTime) and address details (Address).
	
		3. Delay Handling and Income Adjustment
	
			* When a milestone is delayed, the planned times of the affected section and the subsequent section’s milestones are automatically updated.
			
			* The expected income of the transport plan decreases based on the delay thresholds configured:
	
				- Delay ≥ 30 minutes → 5% reduction
				
				- Delay ≥ 60 minutes → 10% reduction
				
				- Delay ≥ 120 minutes → 15% reduction
	
		4. Security and Authorization
	
			* JWT-based authentication with role-based access (TransportManager, AddressManager, User).
			
			* Only users with the appropriate roles can create or modify plans, addresses, or register delays.
	
		5. Database and Persistence
	
			* Stores transport plans, sections, milestones, and addresses in a 'in-memory H2' database.
			
			* All updates are transactional, ensuring consistency when delays are applied and expected income is updated.
 */

@SpringBootApplication
public class LogisticsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsAppApplication.class, args);
    }
}
