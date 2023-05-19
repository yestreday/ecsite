/*
 * Model.dao（Data Access Object、DAO）は、アプリケーションとデータストレージ（例: データベース）間のデータアクセスを担当するオブジェクトです。
 * DAOの役割は、データの取得、保存、更新、削除などの操作を行うことです。
 * 
 * 簡単に言えば、DAOはアプリケーションとデータストレージの「仲介役」です。
 * アプリケーションの他の部分（ビジネスロジックやUI）がデータストレージの詳細を知らなくても、DAOを通じてデータ操作ができるように設計されています。
 * これにより、コードの整理や保守性が向上し、機能の追加や変更も容易に行えるようになります。
 */
package jp.co.internous.ecsite.model.dao;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.internous.ecsite.model.entity.Purchase;

/*
 * PurchaseRepositoryインターフェースは、購入情報をデータベースとやり取りするためのインターフェースです。
 * このインターフェースは、Spring Data JPAを使用しており、JpaRepository<Purchase, Long>を継承しています。
 * JpaRepositoryは、エンティティの基本的なCRUD操作を提供します。
 * Purchaseエンティティを使用して、この場合、主キーのタイプはLongです。
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	
	// @Queryアノテーションを使用して、生のSQLクエリを記述しています。
	// pは、purchaseテーブルのエイリアス(別名、仮名)で、クエリの他の部分で参照できます。
	@Query(value="SELECT * FROM purchase p " + 
			"WHERE created_at = (" +
				 // created_atカラムの最大値（最も新しい日時）を取得します。
				 /*
				  * `:userId`はユーザーIDを動的にクエリにバインドするために使用される変数のプレースホルダーです。
				  * つまり、クエリが実行されるときに、:userId の値が実際のユーザーIDに置き換えられます。
				 */
			"SELECT MAX(created_at) FROM purchase p WHERE p.user_id = :userId)",
			nativeQuery=true)
	
	/*
	 *このfindHistoryメソッドの役割は、指定されたユーザーIDに関連する購入履歴をデータベースから取得し、List<Purchase>型のリストとして返すことです。
	 *このメソッドを使用することで、アプリケーションの他の部分でユーザーの購入履歴にアクセスできます。
	 */
	List<Purchase> findHistory(@Param("userId") long userId);
	
	/* 
	 * クエリのVALUES句の後にある各?1、?2、?3、?4、?5は、PreparedStatementのプレースホルダーとして使用され、
	 * クエリを実行する前に、これらのプレースホルダーに引数の値をバインドする必要があります。
	 * 
	 * また、now()は現在の日時を取得するMySQLの関数です。
	 * この関数を使用することで、引数の値ではなく、データベースサーバーの現在時刻を使用してcreated_atカラムに値を設定することができます。
	 * 
	 *nativeQuery=true：
	 *この部分は、@Queryアノテーションの属性で、記述されたクエリが生のSQLクエリであることを示しています。
	 *これにより、Spring Data JPAは、このクエリをデータベースに対して直接実行します。
	 */
	@Query(value="INSERT INTO purchase (user_id, goods_id, goods_name, item_count, total, created_at) " + 
			"VALUES (?1, ?2, ?3, ?4, ?5, now())", nativeQuery=true)
	
	
	@Transactional
	// @Modifyingアノテーションは、このクエリがデータベースの更新操作（INSERT, UPDATE, DELETE）を行うことを示しています。
	@Modifying
	/*
	 * このリポジトリメソッドの実装では、下記のパラメーターを使用して、データベースのpurchaseテーブルに新しい購入レコードを追加します。
	 * 具体的には、上のようなSQL INSERT文を実行します。
	 */
	void persist(@Param("userId") long userId,
			 	 @Param("goodsId") long productId,
			 	 @Param("goodsName") String goodsName,
			 	 @Param("itemCount") long itemCount,
			 	 @Param("total") long total);
}