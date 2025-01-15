package com.royalmade.service;



import com.royalmade.dto.ExpenceProjectDto;
import com.royalmade.entity.*;
import com.royalmade.entity.enumeration.UserType;
import com.royalmade.repo.*;
import com.royalmade.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

	


	    @Autowired
	    private RoleDao roleDao;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
		@Autowired
		private EmployeeRepository employeeRepository;


		@Autowired
		private AdminRepository adminDao;

	     @Autowired
	     private JwtHelper jwtHelper;

		 @Autowired
		 private SupervisorRepository supervisorRepository;

		 @Autowired
		 private AppUserRepository appUserRepository;

		 @Autowired
		 private ProjectRepository projectRepository;

	@Autowired
	private ModelMapper modelMapper; // To map entities to DTOs
	    public void initRoleAndUser() {
			// Create roles
			if (!roleDao.existsById("Admin")) {
				Role adminRole = new Role();
				adminRole.setRoleName("Admin");
				adminRole.setRoleDescription("Admin role");
				roleDao.save(adminRole);
			}

			if (!roleDao.existsById("Employee")) {
				Role employeeRole = new Role();
				employeeRole.setRoleName("Employee");
				employeeRole.setRoleDescription("Employee role");
				roleDao.save(employeeRole);
			}
			if (!roleDao.existsById("Supervisor")) {
				Role supervisorRole = new Role();
				supervisorRole.setRoleName("Supervisor");
				supervisorRole.setRoleDescription("Supervisor role");
				roleDao.save(supervisorRole);
			}
			if (!roleDao.existsById("AppUser")) {
				Role appUserRole = new Role();
				appUserRole.setRoleName("AppUser");
				appUserRole.setRoleDescription("AppUser role");
				roleDao.save(appUserRole);
			}
	    }


	public Admin registerAdmin(Admin admin) {
		Role role = roleDao.findById("Admin").get();
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		admin.setRole(userRoles);
		admin.setPassword(getEncodedPassword(admin.getPassword()));

		adminDao.save(admin);
        return admin;
    }
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
	public Employee employeeregister(Employee employee) {
		Role role = roleDao.findById("Employee").get();
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		employee.setRole(userRoles);
		employee.setPassword(getEncodedPassword(employee.getPassword()));

		employeeRepository.save(employee);
		return employee;
	}

	public Supervisor supervisorRegister(Supervisor supervisor) {

			Role role=roleDao.findById("Supervisor").get();
			Set<Role> supervisorRole=new HashSet<>();
			supervisorRole.add(role);
			supervisor.setRole(supervisorRole);
			supervisor.setPassword(getEncodedPassword(supervisor.getPassword()));
			supervisorRepository.save(supervisor);
			return supervisor;
	}

    public AppUser appUserRegister(AppUser appUser) {
			Role role=roleDao.findById("AppUser").get();
			Set<Role> appUserRole=new HashSet<>();
		    appUserRole.add(role);
		    appUser.setRole(appUserRole);
			appUser.setPassword(getEncodedPassword(appUser.getPassword()));
			appUser.setUserType(UserType.AppUser);
			appUserRepository.save(appUser);
		    return appUser;

    }

	public List<AppUser> getAllUser() {
		return  appUserRepository.findAll();
	}

	public String getAllUserById(Long id) {
			appUserRepository.findById(id);
			return "This Supervisor not found plase register new Supervisor....!";

	}


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public List<ExpenceProjectDto> getAllProjects() {
		// Retrieve all projects from the repository
		List<Project> projects = projectRepository.findAll();

		// Map the entity list to a list of DTOs
		return projects.stream()
				.map(project -> modelMapper.map(project, ExpenceProjectDto.class))
				.collect(Collectors.toList());
	}


	public List<ExpenceProjectDto> getAllowedSitesForUser(String email) {
		AppUser user = appUserRepository.findByEmail(email);

		// Map the allowed sites to DTOs
		return user.getAllowedSite().stream()
				.map(site -> modelMapper.map(site, ExpenceProjectDto.class))
				.collect(Collectors.toList());
	}
}