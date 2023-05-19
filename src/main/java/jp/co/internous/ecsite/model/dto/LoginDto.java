/*
 * DTO（Data Transfer Object）は、ビジネスロジックの結果としてアプリケーション内でデータを転送するために使用されるオブジェクトです。 
 * 
 * DTOは、通常、データベースから取得されたデータを保持し、アプリケーション内の異なる層（例えば、コントローラやビュー）間でデータを転送するために使用されます。 
 * 
 * DTOは、ビジネスロジックとは関係がなく、純粋なデータ保持オブジェクトです。
 */
package jp.co.internous.ecsite.model.dto;

import jp.co.internous.ecsite.model.entity.User;

public class LoginDto {
	
	private long id;
	private String userName;
	private String password;
	private String fullName;
	
	public LoginDto() {
		
	}
	
	public LoginDto(User user) {
		this.id = user.getId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.fullName = user.getFullName();
	}
	
	public LoginDto(long id, String userName, String password, String fullName) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
	}
 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
