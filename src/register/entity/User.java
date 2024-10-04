package register.entity;

public class User {
	private String fullname;
	private String username;
	private String password;
	private String email;
	private String path;
	private Role role;
	
	
	public User() {
	}

	public User(String fullname, String username, String password, String email, String role,String path) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.path = path;
		this.role = Role.valueOf(role);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return String.valueOf(role);
	}

	public void setRole(String role) {
		this.role = Role.valueOf(role);
	}
	
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
	
	public enum Role {
		ADMIN,USER
	}
}
