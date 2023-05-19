/*
 * コントローラークラスは、MVC（Model-View-Controller）パターンの一部であり、
 * ユーザーからの入力を受け取り、それに対応するアクションを実行して、ビューにデータを提供し、レスポンスを生成します。
 */
package jp.co.internous.ecsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.GoodsForm;
import jp.co.internous.ecsite.model.form.LoginForm;

// "AdminController"は、Webアプリケーションやウェブサイトなどの管理者画面を担当するコントローラーのことです。
@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {
	
	/*
	 * コンストラクタに@Autowiredアノテーションを付けることで、Springコンテナが自動的にUserRepositoryオブジェクトを生成し、引数に渡します。
	 * つまり、UserRepository userRepository = new UserRepository() のように自分でインスタンスを生成する必要がなくなります。
	 * また、他のクラスでUserServiceを利用する場合にも、UserRepositoryオブジェクトを生成して渡す必要がなくなります。
	 */
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private GoodsRepository goodsRepos;
	
	/*
	 * @RequestMapping アノテーションが付けられた index() メソッドです。
	 * このメソッドは、アプリケーションのルート（"/"）に対するGETリクエストを処理し、"adminindex"というビュー名を返します。
	 */
	@RequestMapping("/")
	public String index() {
		return "adminindex";
	}
	
	// "/welcome"エンドポイントに対してPOSTリクエストが送信された場合に、呼び出されるメソッドを定義しています。
	@PostMapping("/welcome")
	public String welcome(LoginForm form, Model m) {
		
		// UserRepositoryを使用して、ログインフォームのユーザ名とパスワードに対応するユーザを検索します。
		List<User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		
		// その検索結果がnullでなく、かつ1件以上存在する場合は、ユーザが管理者権限を持っているかどうかを判定します。
		if (users != null && users.size() > 0) {
			/*
			 * Userオブジェクトのリストから最初のユーザを取得し、そのisAdminフィールドが0でない場合にisAdmin変数をtrueに設定します。
			 * ここで、isAdmin変数はブール型で、ユーザが管理者権限を持っているかどうかを表します。
			 * つまり、isAdmin変数は、usersリストの最初の要素が管理者である場合にtrueに設定されます。
			 * そのため、"isAdmin"は"Is Administrator"の略で、ユーザが管理者であるかどうかを表します。
			 */
			boolean isAdmin = users.get(0).getIsAdmin() != 0;
			/*
			 * ユーザが管理者である場合は、GoodsRepositoryを使用して、全ての商品情報を取得し、Modelオブジェクトに追加します。
			 * そして、welcome.htmlというテンプレートをレンダリングするために、"welcome"という文字列を返します。
			 * この際、Modelオブジェクトに追加されたデータは、テンプレート内で表示されます。
			 */
			if (isAdmin) {
				List<Goods> goods = goodsRepos.findAll();
				/*
				 * Modelは、ViewとControllerの間でデータを受け渡すためのコンテナとして機能します。
				 * Modelオブジェクトをメソッドの引数に指定して、welcome.htmlビューに表示するためのデータを設定しています。
				 */
				m.addAttribute("userName", users.get(0).getUserName());
				m.addAttribute("password", users.get(0).getPassword());
				m.addAttribute("goods", goods);
 			}
		}
			
		return "welcome";
	}
	
	/*
	 * @RequestMappingアノテーションは、HTTPリクエストと対応するメソッドをマッピングするために使用されます。
	 * "/goodsMst"パスに対してHTTP GETリクエストが送信された場合、"goodsMst"という名前のビューを返すようにマッピングされます。
	 */
	@RequestMapping("/goodsMst")
	/*
	 * メソッドの引数として、LoginFormオブジェクトとModelオブジェクトが定義されています。
	 * LoginFormオブジェクトは、ログインフォームに入力されたユーザー名とパスワードを表すために使用されます。
	 * Modelオブジェクトは、ビューにデータを渡すために使用されます。
	 */
	public String goodsMst(LoginForm form, Model m) {
		/*
		 * m.addAttribute()メソッドは、Modelオブジェクトに属性を追加します。
		 * ログインフォームから受け取ったユーザー名とパスワードを、それぞれ"userName"と"password"という名前でモデルに追加しています。
		 */
		m.addAttribute("userName", form.getUserName());
		m.addAttribute("password", form.getPassword());
		
		/*
		 * 最後に、"goodsMst"という名前のビューを返しています。
		 */
		return "goodsMst";
	}
	
	@RequestMapping("/addGoods")
	/*
	 * addGoods()メソッドの引数には、GoodsFormとLoginForm、そしてModelがあります。
	 * GoodsFormは、Webページ上のフォームから受信した商品の情報を格納するために使用されます。
	 * LoginFormは、ログインフォームから送信されたユーザー名とパスワードを格納するために使用されます。
	 * Modelは、ビューにデータを渡すために使用されます。
	 */
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		// m.addAttribute()メソッドを使用して、モデルにユーザー名とパスワードの情報を追加しています。
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password", loginForm.getPassword());
		
		/*
		 * 新しいGoodsオブジェクトを作成し、商品名と価格をGoodsFormから取得した情報で設定しています。
		 * そして、goodsRepos.saveAndFlush(goods)を使用して、データベースに新しい商品を追加します。
		 */
		Goods goods = new Goods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		goodsRepos.saveAndFlush(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	/*
	 * @RequestBody GoodsForm f は、HTTPリクエストのボディから GoodsForm オブジェクトを取得し、f という変数に割り当てます。
	 * Model m は、このメソッドがビューにデータを渡すために使用できるオブジェクトですが、このメソッドでは使用されていません。
	 */
	public String deleteApi(@RequestBody GoodsForm f, Model m) {
		/*
		 * try ブロック内の goodsRepos.deleteById(f.getId()) は、 
		 * GoodsRepository インターフェースのメソッドを使用して、 f オブジェクトのIDに対応するデータベース内の商品データを削除します。
		 * catch ブロックは、 IllegalArgumentException がスローされた場合に -1 を返します。
		 * 
		 * 最後に、このメソッドは "1" を返します。つまり、商品の削除に成功した場合は文字列 "1"、失敗した場合は文字列 "-1" を返します。
		 */
		try {
			goodsRepos.deleteById(f.getId());
		} catch (IllegalArgumentException e) {
			return "-1";
		}
		
		return "1";
		
		/*
		 * try ブロックと catch ブロックは、Javaで例外処理を行うために使用されます。
		 * 
		 * try ブロックは、例外が発生する可能性があるコードを含むブロックです。例外が発生すると、Javaは try ブロック内のコードの実行を中止し、該当する catch ブロックを探します。
		 * 
		 * catch ブロックは、指定された例外をキャッチし、適切な処理を行います。例えば、上記のコードでは、 IllegalArgumentException が発生した場合に -1 を返しています.
		 * 
		 * 例外が発生しなかった場合、 try ブロックの残りのコードが実行されます。例外が発生した場合でも、 catch ブロックの処理が完了した後には、 try ブロックの残りのコードは実行されません。
		 */
	}
}
