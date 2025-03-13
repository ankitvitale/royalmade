package com.royalmade.dto;

import java.util.List;

public class AppUserDTO {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String userType;
    private List<MaterialDTO> materials;

    public AppUserDTO(Long id, String username, String name, String email, String userType, List<MaterialDTO> materials) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.materials = materials;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUserType() { return userType; }
    public List<MaterialDTO> getMaterials() { return materials; }
}
