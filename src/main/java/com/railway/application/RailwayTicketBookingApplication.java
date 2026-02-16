package com.railway.application;

import com.railway.application.entity.Role;
import com.railway.application.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;
import java.util.UUID;

@SpringBootApplication
public class RailwayTicketBookingApplication implements CommandLineRunner {

	public static void main(String[] args)  {
		SpringApplication.run(RailwayTicketBookingApplication.class, args);


	}
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		if(!roleRepository.existsByName("ROLE_ADMIN"))
		{
			Role role=new Role();
			role.setId(UUID.randomUUID().toString());
			role.setName("ROLE_ADMIN");
			roleRepository.save(role);
		}
		if(!roleRepository.existsByName("ROLE_NORMAL"))
		{
			Role userRole=new Role();
			userRole.setId(UUID.randomUUID().toString());
			userRole.setName("ROLE_NORMAL");
			roleRepository.save(userRole);
		}
		System.out.println("Roles Initialized Successfully");
	}
}
