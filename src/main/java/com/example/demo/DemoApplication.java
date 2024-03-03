package com.example.demo;

import com.example.demo.Annonce.Offre.Offre;
import com.example.demo.Domains.Domain;
import com.example.demo.Domains.DomainRepo;
import com.example.demo.user.appRole;
import com.example.demo.user.appRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

@Autowired
DomainRepo domainRepo;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(appRoleRepo repository) {
		return args -> {
			if (repository.findByRoleName("USER") == null) {
				repository.save(new appRole("USER"));
			}
			if (repository.findByRoleName("ADMIN") == null) {
				repository.save(new appRole("ADMIN"));
			}

			if(domainRepo.findByName("Informatique") == null) {


				Domain d1 = new Domain("Informatique");
				Domain d2 = new Domain("Reseau et Telecom");
				Domain d3 = new Domain("Macanique");
				Domain d17 = new Domain("Dev Mobile");
				Domain d4 = new Domain("Industrie");
				Domain d5 = new Domain("Systemes embarques");
				Domain d6 = new Domain("Genie Energitique");
				Domain d7 = new Domain("Cyber Security");
				Domain d8 = new Domain("DataScience et Analytics");
				Domain d9 = new Domain("Systeme d'informations");
				Domain d16 = new Domain("NetworkSecurity");
				Domain d10 = new Domain("Devops");
				Domain d11 = new Domain("Buisness Intelligence");
				Domain d12 = new Domain("Routing");
				Domain d13 = new Domain("Secops");
				Domain d14 = new Domain("ERP");
				Domain d15 = new Domain("Bloc chain");
				domainRepo.save(d1);
				domainRepo.save(d2);
				domainRepo.save(d3);
				domainRepo.save(d4);
				domainRepo.save(d5);
				domainRepo.save(d6);
				domainRepo.save(d7);
				domainRepo.save(d8);
				domainRepo.save(d9);
				domainRepo.save(d10);
				domainRepo.save(d11);
				domainRepo.save(d12);
				domainRepo.save(d13);
				domainRepo.save(d14);
				domainRepo.save(d16);
				domainRepo.save(d15);
				domainRepo.save(d17);

			}
		};
	}

}
