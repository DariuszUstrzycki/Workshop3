package pl.coderslab.app;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
	
	public String encode(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean validatePassword(String plainText, String hashedPass) {
		return BCrypt.checkpw(plainText, hashedPass);
	}

}
