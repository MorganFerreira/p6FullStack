package com.p6FullStack.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")	
	private int id;

	@Column(name = "email")
	private String email;
	
	@Column(name = "name")
	private String name;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password")
	private String password;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscriptions",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "theme_id"))
    private List<Themes> listThemes = new ArrayList<>();

	public Users(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.listThemes = new ArrayList<>();
	}
}
