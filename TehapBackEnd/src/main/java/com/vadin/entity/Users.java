package com.vadin.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.vadin.annotation.UniqueUsername;

@Entity
public class Users implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull(message = "{tehap.constraint.username.NotNull.message}")
	@Size(min = 4, max = 255)
	@UniqueUsername
	private String username;

	@NotNull
	@Size(min = 4, max = 255)
	private String displayName;

	@NotNull
	@Size(min = 8, max = 255)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{tehap.constraint.password.Pattern.message}")
	private String password;

	private String image;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Activity> activities;

	
	private String email;
	
	private String usersurname;
	
	private String university;
	
	private String branch;
	
	
	
	
	public Users() {
		super();
	}

	
	public Users(long id,
			@NotNull(message = "{tehap.constraint.username.NotNull.message}") @Size(min = 4, max = 255) String username,
			@NotNull @Size(min = 4, max = 255) String displayName,
			@NotNull @Size(min = 8, max = 255) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{tehap.constraint.password.Pattern.message}") String password,
			String image, List<Activity> activities, String email, String usersurname, String university,
			String branch) {
		super();
		this.id = id;
		this.username = username;
		this.displayName = displayName;
		this.password = password;
		this.image = image;
		this.activities = activities;
		this.email = email;
		this.usersurname = usersurname;
		this.university = university;
		this.branch = branch;
	}


	/*public Users(long id,
			@NotNull(message = "{tehap.constraint.username.NotNull.message}") @Size(min = 4, max = 255) String username,
			@NotNull @Size(min = 4, max = 255) String displayName,
			@NotNull @Size(min = 8, max = 255) @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{tehap.constraint.password.Pattern.message}") 
			String password,
			String image, 
			List<Activity> activities) {
		super();
		this.id = id;
		this.username = username;
		this.displayName = displayName;
		this.password = password;
		this.image = image;
		this.activities = activities;
	}*/

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getUsersurname() {
		return usersurname;
	}


	public void setUsersurname(String usersurname) {
		this.usersurname = usersurname;
	}


	public String getUniversity() {
		return university;
	}


	public void setUniversity(String university) {
		this.university = university;
	}


	public String getBranch() {
		return branch;
	}


	public void setBranch(String branch) {
		this.branch = branch;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", displayName=" + displayName + ", password=" + password
				+ ", image=" + image + "]";
	}

}
