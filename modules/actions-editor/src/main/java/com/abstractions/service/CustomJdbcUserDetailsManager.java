package com.abstractions.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.util.Assert;

import com.abstractions.web.CustomUser;
import com.abstractions.web.EmailExistsException;
import com.abstractions.web.UsernameExistsException;

/**
 * 
 * @author Guido J. Celada (celadaguido@gmail.com)
 *
 */
public class CustomJdbcUserDetailsManager extends JdbcUserDetailsManager {
	 // UserDetailsManager SQL
    public static final String DEF_CREATE_USER_SQL =
            "insert into users (username, password, enabled, confirmed, email, full_name, creation_date) values (?,?,?,?,?,?,?)";
    public static final String DEF_INSERT_AUTHORITY_SQL =
            "insert into authorities (username, authority) values (?,?)";
    public static final String DEF_CONFIRM_SQL =
            "update users set confirmed = 1 where username = ?";
    public static final String DEF_ENABLE_SQL =
            "update users set enabled = 1 where username = ?";
    public static final String DEF_USERS_BY_USERNAME_QUERY =
            "select username, password, enabled, confirmed, email, full_name, creation_date " +
            "from users " +
            "where username = ?";
    public static final String DEF_USER_EXISTS_SQL =
            "select username from users where username = ?";
    public static final String DEF_EMAIL_EXISTS_SQL =
            "select username from users where email = ?";
	
    
    private String createUserSql = DEF_CREATE_USER_SQL;
    private String createAuthoritySql = DEF_INSERT_AUTHORITY_SQL;
    private String confirmSql = DEF_CONFIRM_SQL;
    private String enableSql = DEF_ENABLE_SQL;
    private String usersByUsernameQuery = DEF_USERS_BY_USERNAME_QUERY;
    private String userExistsSql = DEF_USER_EXISTS_SQL;
    private String emailExistsSql = DEF_EMAIL_EXISTS_SQL;
    
    @Value("${register.salt}")
	static String salt;
        
	public CustomJdbcUserDetailsManager(){
        super();
    }
	
    public void createUser(final UserDetails user) throws UsernameExistsException, EmailExistsException {
        final CustomUser customUser = validateUserDetails(user);
        
        if (userExists(customUser.getUsername()))
        	throw new UsernameExistsException();
        if (emailExists(customUser.getEmail()))
        	throw new EmailExistsException();
        
        getJdbcTemplate().update(createUserSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, customUser.getUsername());
                ps.setString(2, customUser.getPassword());
                ps.setBoolean(3, customUser.isEnabled());
                ps.setBoolean(4, customUser.isConfirmed());
                ps.setString(5, customUser.getEmail());
                ps.setString(6, customUser.getFullName());
                ps.setTimestamp(7, new Timestamp(customUser.getCreationDate().getTime()));
            }
        });

        insertUserAuthorities(user);
    }

    private void insertUserAuthorities(UserDetails user) {
        for (GrantedAuthority auth : user.getAuthorities()) {
            getJdbcTemplate().update(createAuthoritySql, user.getUsername(), auth.getAuthority());
        }
    }
    
    private CustomUser validateUserDetails(UserDetails user) {
    	CustomUser customUser = (CustomUser) user;
    	Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
        return customUser;
    }
    
    private void validateAuthorities(Collection<GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }
    
    public boolean userExists(String username) {
        List<String> users = getJdbcTemplate().queryForList(userExistsSql, new String[] {username}, String.class);

        if (users.size() > 1) {
            throw new IncorrectResultSizeDataAccessException("More than one user found with name '" + username + "'", 1);
        }

        return users.size() == 1;
    }
    
    public boolean emailExists(String username) {
        List<String> users = getJdbcTemplate().queryForList(emailExistsSql, new String[] {username}, String.class);

        if (users.size() > 1) {
            throw new IncorrectResultSizeDataAccessException("More than one user found with name '" + username + "'", 1);
        }

        return users.size() == 1;
    }

    public void confirmUser(String username, String token) throws Exception {
		CustomUser user = loadUserByUsernameWithoutAuths(username);
		if (generateConfirmationToken(user).equals(token))
			getJdbcTemplate().update(confirmSql, username);		
		else
			throw new Exception("Invalid confirmation token");
	}
	
    public void enableUser(String username) {
		getJdbcTemplate().update(enableSql, username);		
	}
	
    private CustomUser loadUserByUsernameWithoutAuths(String username) {
        List<CustomUser> users = getJdbcTemplate().query(usersByUsernameQuery, new String[] {username}, new RowMapper<CustomUser>() {
            public CustomUser mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                boolean confirmed = rs.getBoolean(4);
                String email = rs.getString(5);
                String fullName = rs.getString(6);
                Date creationDate = new Date(rs.getTimestamp(7).getTime());
                return new CustomUser(username, password, email, fullName, creationDate, enabled, confirmed, AuthorityUtils.NO_AUTHORITIES);
            }
        });
        if (!users.isEmpty()) 
        	return users.get(0);
        else 
        	return null;
    }
	
    public static String generateConfirmationToken(CustomUser user) {
		return DigestUtils.shaHex(user.getUsername() + user.getCreationDate() + salt);
	}
}
