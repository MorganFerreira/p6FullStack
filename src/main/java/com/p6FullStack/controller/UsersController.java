package com.p6FullStack.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.p6FullStack.dto.UsersDto;
import com.p6FullStack.mappers.UsersMapper;
import com.p6FullStack.model.Users;
import com.p6FullStack.repository.UsersRepository;
import com.p6FullStack.service.JWTService;
import com.p6FullStack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UsersController {
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private UsersRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	private JWTService jwtService;
	
	private UsersMapper usersMapper;
	
	public UsersController(UserService userService, JWTService jwtService, UsersMapper usersMapper) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.usersMapper = usersMapper;
	}
	
	/**
	 * Register - Add a new user
	 * @param RegisterRequest An object users
	 * @return Token
	 */
	@Operation(summary = "Register a new user", description = "Vérifie si l'utilisateur est en BDD, crée l'utilisateur et retourne un token JWT")
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest body) {

        if (userRepository.existsByEmail(body.email())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email déjà utilisé!"));
        }
        if (userRepository.existsByName(body.name())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nom déjà utilisé!"));
        }

        Users user = new Users(body.email(), body.name(), passwordEncoder.encode(body.password()));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
    
	/**
	 * Login - Log an user
	 * @param Authentication An object authentication
	 * @return Token
	 */
	@Operation(summary = "Login", description = "Autorise un utilisateur à se connecter et retourne un token JWT")
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest body) {
        
		Users userFound = this.userRepository.findByEmail(body.email());
        if (userFound != null) {
            if (!passwordEncoder.matches(body.password(), userFound.getPassword()))
                return ResponseEntity.badRequest().body(new MessageResponse("Error1"));
            else {
            	return ResponseEntity.ok(new ResponseWithToken(jwtService.generateToken(userFound.getEmail()), userFound.getId(), userFound.getName(), userFound.getEmail()));
            }
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error2"));
        }
    }

	/**
	 * Read - Get one user 
	 * @param id The id of the user
	 * @return An UsersDto object
	 */
	@Operation(summary = "Get user by id", description = "Retourne un utilisateur par son id")
    @GetMapping("/user/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable String id) {
        
		Optional<Users> user = userService.getUserById(id);
        if(user.isPresent()){
            UsersDto userDto = usersMapper.mapToDtoWithOptional(user);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	/**
	 * Post - Subscribe to theme 
	 * @param user_id and theme_id
	 */
	@Operation(summary = "Subscribe to theme", description = "Souscris un abonnement à un thème pour l'utilisateur")
    @PostMapping("/user/{userId}/subscribe/{themeId}")
    public ResponseEntity<MessageResponse> subscribe(@PathVariable("userId") String userId, @PathVariable("themeId") String themeId) {
        
		try {
            String message = this.userService.subscribe(Long.parseLong(userId), Long.parseLong(themeId));
			if (!message.equals("User subscribed to theme successfully")) {
				return ResponseEntity.badRequest().body(new MessageResponse(message));
			} else {
				return ResponseEntity.ok(new MessageResponse(message));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

	/**
	 * Delete - UnSubscribe to theme 
	 * @param user_id and theme_id
	 */
	@Operation(summary = "UnSubscribe to theme", description = "Désabonne un utilisateur à un thème")
    @DeleteMapping("/user/{userId}/unsubscribe/{themeId}")
    public ResponseEntity<MessageResponse> unSubscribe(@PathVariable("userId") String userId, @PathVariable("themeId") String themeId) {
        
		try {
            String message = this.userService.unSubscribe(Long.parseLong(userId), Long.parseLong(themeId));
			if (!message.equals("User unsubscribed from theme successfully")) {
				return ResponseEntity.badRequest().body(new MessageResponse(message));
			} else {
				return ResponseEntity.ok(new MessageResponse(message));
			}
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

	/**
	 * Modify User by Id
	 * @param user_id and userDto
	 * @return UpdateResponse
	 */
	@Operation(summary = "Update user", description = "Modifie les informations d'un utilisateur")
    @PutMapping("/user/{id}")
    public ResponseEntity<UpdateResponse> updateUser(@PathVariable String id, @ModelAttribute UsersDto userDto) {
        
        try {
            userDto.setId(Long.parseLong(id));
            String message = this.userService.updateUser(userDto);
            if (message == "User updated"){
                return ResponseEntity.ok(new UpdateResponse(message));
            } else {
                return ResponseEntity.status(404).build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

	public record UpdateResponse(String response){}
	public record MessageResponse(String message) {}
    public record RegisterRequest(String email, String password, String name) {}
    public record LoginRequest(String email, String password) {}
    public record ResponseWithToken(String token, Long id, String name, String email) {}

}
