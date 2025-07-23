package com.p6FullStack.model;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "themes")
public class Themes {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
 
    @Column(name = "title")
    private String title;
	
	@Column(name = "content")
    private String content;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "subscriptions",
               joinColumns = @JoinColumn(name = "theme_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Users> listUsers = new ArrayList<>();

}
