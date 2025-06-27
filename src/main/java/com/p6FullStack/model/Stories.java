package com.p6FullStack.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "stories")
public class Stories {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
 
    @Column(name = "title")
    private String title;
	
	@Column(name = "content")
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(optional = false, targetEntity = Users.class)
    @JoinColumn(name = "associatedUser", referencedColumnName = "id")
    private Users associatedUser;

    @OneToOne(optional = false, targetEntity = Themes.class)
    @JoinColumn(name = "associatedTheme", referencedColumnName = "id")
    private Themes associatedTheme;

}