/*
 *　Entityクラスとは、オブジェクト指向プログラミングにおいて、データベースに保存されるデータを表すクラスのことです。
 *　Entityとは、英語で「実体」という意味があります。
 * 
 * Entityクラスは、データベースのテーブルに対応するオブジェクトであり、テーブルのカラムをフィールドとして持ちます。
 * 例えば、顧客情報を管理するデータベースのテーブルがある場合、このテーブルに対応するEntityクラスは、顧客の名前や住所などの情報をフィールドに持ちます。
 * 
 * Entityクラスは、ORM（Object-Relational Mapping）の機能を利用して、データベースとオブジェクト指向プログラミングの間の橋渡しを行います。
 * ORMは、Entityクラスとデータベースのテーブルのマッピングを自動的に行い、Entityクラスを操作することで、データベースとのやりとりを簡単に行うことができます。
 */

package jp.co.internous.ecsite.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//　1.クラス宣言部を追加します。

// Entityアノテーションを付与すると、Springの機能により当該クラスはEntityとして振る舞うようになります。
@Entity
// Tableアノテーションは、DBにある「どのテーブルの実体なのか」を指定するものです。
@Table(name="user")
public class User {
	
		
	//2.DBテーブル(user)にあるカラムを、フィールドとして宣言します。
	
	// プライマリキーであることを指定します。
	@Id
	// テーブルのどのカラムとマッピングするかを指定します。
	@Column(name="id")
	/*
	 * IDENTITY戦略は、データベースが自動的に主キーを生成するために使用されるデータベース固有の機能を使用します。
	 * MySQLでは、主キーにAUTO_INCREMENT属性を設定することでIDENTITY戦略を使用できます。
	 * 
	 * @Idアノテーションで主キーを指定し、@GeneratedValueアノテーションでIDENTITY戦略を指定しています。
	 * これにより、データベースが自動的に主キーの値を生成し、エンティティクラスのidフィールドに設定されます。
	 */
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="is_admin")
	private int isAdmin;
	
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
		return userName;
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
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
}
