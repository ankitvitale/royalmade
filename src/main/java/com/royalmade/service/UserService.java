package com.royalmade.service;



import com.royalmade.dto.*;
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

//    public AppUser appUserRegister(AppUser appUser) {
//			Role role=roleDao.findById("AppUser").get();
//			Set<Role> appUserRole=new HashSet<>();
//		    appUserRole.add(role);
//		    appUser.setRole(appUserRole);
//			appUser.setPassword(getEncodedPassword(appUser.getPassword()));
//			appUser.setUserType(UserType.AppUser);
//			appUserRepository.save(appUser);
//		    return appUser;
//
//    }

	public AppUserDtos appUserRegister(AppUserDtos appUserDto) {
		Role role = roleDao.findById("AppUser").get();
		Set<Role> appUserRole = new HashSet<>();
		appUserRole.add(role);

		AppUser appUser = new AppUser();
		appUser.setUsername(appUserDto.getUsername());
		appUser.setName(appUserDto.getName());
		appUser.setEmail(appUserDto.getEmail());
		appUser.setPassword(getEncodedPassword(appUserDto.getPassword()));
		appUser.setUserType(UserType.AppUser);
		appUser.setRole(appUserRole);

		AppUser savedUser = appUserRepository.save(appUser);

		return new AppUserDtos(savedUser.getUsername(), savedUser.getName(), savedUser.getEmail(), null);
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

	public List<AppUserDTO> getAllSupervisors() {
		List<AppUser> users = appUserRepository.findAll();
		return users.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	private AppUserDTO convertToDTO(AppUser user) {
		List<MaterialDTO> materials = user.getMaterials().stream().map(material ->
				new MaterialDTO(
						material.getId(),
						material.getName(),
						material.getType(),
						material.getQuantity(),
						material.getPrice(),
						material.getAddedOn(),
						material.getPayments().stream().map(payment ->
								new PaymentDTO(
										payment.getId(),
										payment.getPayDate(),
										payment.getAmount(),
										payment.getRemark(),
										payment.getPaymentStatus()
								)
						).collect(Collectors.toList())
				)
		).collect(Collectors.toList());

		return new AppUserDTO(
				user.getId(),
				user.getUsername(),
				user.getName(),
				user.getEmail(),
				user.getUserType().toString(),
				materials
		);
	}


	public List<Employee> getAllEmployees() {
		return employeeRepository.findAllActiveEmployees();
	}

	public boolean deleteEmployee(Long id) {
		return employeeRepository.findById(id)
				.map(employee -> {
					employee.setDeleted(true);
					employeeRepository.save(employee);
					return true;
				}).orElse(false);
	}


}