/*
1.Form:
		コントローラがリクエストを受け取り、Formクラスを使用してユーザーからの入力データを取得します。
		
		Formクラスの役割は、ユーザーの入力データを保持し、バリデーションやエラーメッセージの管理に使用されます。
 */

package jp.co.internous.ecsite.model.form;

import java.io.Serializable;

/*
 * LoginFormというJavaクラスを定義しています。
 * LoginFormクラスはSerializableインターフェースを実装しているため、オブジェクトのシリアル化が可能です。
 */
public class LoginForm implements Serializable{
	/*
	 * serialVersionという名前のlong型の定数を宣言しています。
	 * この定数は、オブジェクトをシリアル化する際に使用されるバージョン番号を表します。
	 * シリアル化されたオブジェクトが読み込まれる際に、この番号が一致しない場合、デシリアル化中にInvalidClassExceptionがスローされます。
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
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
}
