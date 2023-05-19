/*
 * 2.DAO:
 * DAO（Data Access Object）
 * 、
 * DAOはエンティティクラスを利用して、データベースとの間でデータのマッピングを行います。
 * 
 * DAOの役割は、データベースへのアクセスを抽象化し、データ操作を行うためのインターフェイスを提供することです。
 */
package jp.co.internous.ecsite.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.internous.ecsite.model.entity.User;

/*
 * UserRepositoryは、JpaRepositoryインターフェースを拡張しており、このインターフェースはSpring Data JPAによって提供されています。
 * JpaRepositoryは、データベースのCRUD（Create, Read, Update, Delete）操作に対応するために、一連の共通メソッドを提供します。
 * この例では、Userエンティティとその主キーの型であるLongを指定しています。
 */
public interface UserRepository extends JpaRepository<User, Long> {
	
	/*
	 * findByUserNameAndPasswordメソッドは、Spring Data JPAによって自動的に生成されるクエリを使用して、
	 * userNameとpasswordの条件に一致するUserオブジェクトのリストを取得するために使用されます。
	 * 
	 * したがって、このコードは、Spring Data JPAを使用して、Userエンティティを永続化するためのDAOを宣言していることがわかります。
	 */
	List<User> findByUserNameAndPassword(String userName, String password);
}
