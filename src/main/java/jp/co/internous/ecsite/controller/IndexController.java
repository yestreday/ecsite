package jp.co.internous.ecsite.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import jp.co.internous.ecsite.model.dao.GoodsRepository;
import jp.co.internous.ecsite.model.dao.PurchaseRepository;
import jp.co.internous.ecsite.model.dao.UserRepository;
import jp.co.internous.ecsite.model.dto.HistoryDto;
import jp.co.internous.ecsite.model.dto.LoginDto;
import jp.co.internous.ecsite.model.entity.Goods;
import jp.co.internous.ecsite.model.entity.Purchase;
import jp.co.internous.ecsite.model.entity.User;
import jp.co.internous.ecsite.model.form.CartForm;
import jp.co.internous.ecsite.model.form.HistoryForm;
import jp.co.internous.ecsite.model.form.LoginForm;

@Controller
@RequestMapping("/ecsite")
public class IndexController {
	
	/*
	 * @Autowiredアノテーションは、Spring Frameworkが提供するDI(Dependency Injection)機能を利用するためのアノテーションです。
	 * 
	 * GoodsRepositoryは、Spring Data JPAが提供するリポジトリーインターフェイスであり、データベースから商品情報を取得するために使用されます。このリポジトリーインターフェイスを利用するために、GoodsRepositoryのインスタンスを取得する必要があります。
	 * 
	 * そこで、@Autowiredアノテーションが用いられます。このアノテーションをフィールドの前につけることで、Spring Frameworkは自動的に適切なクラスのインスタンスを生成し、フィールドに注入します。つまり、GoodsRepositoryのインスタンスを作成する必要がなく、コントローラークラスのフィールドとして直接参照することができます。
	 * 
	 * 以上のように、@Autowiredアノテーションによって、コントローラークラスで使用するオブジェクトのインスタンス生成や注入を自動的に行うことができ、コードの記述量を削減することができます。
	 */
	@Autowired
	private GoodsRepository goodsRepos;
	@Autowired
	private UserRepository userRepos;
	@Autowired
	private PurchaseRepository purchaseRepos;
	
	private Gson gson = new Gson();
	
	/*
	 * m.addAttribute("goods", goods) は、Spring MVCアプリケーションのコントローラーで、ビューにモデル属性を渡すために使用されます。
	 * 
	 * mは、Modelオブジェクトのインスタンスです。このオブジェクトは、ビューに渡すためのオブジェクトを保存するために使用されます。
	 * 
	 * addAttribute() メソッドは、Modelオブジェクトに属性を追加するために使用されます。
	 * このメソッドには、属性名と属性値が渡されます。この例では、属性名は "goods" であり、属性値は goods という名前の商品情報のリストです。
	 * 
	 * このようにして、 m.addAttribute("goods", goods) によって、商品情報のリストが "goods"という名前の属性として、ビューに渡されることになります。
	 * この属性は、Thymeleafテンプレートエンジンを使用してビューに表示することができます。例えば、ビュー内で ${goods} のように書くことで、商品情報のリストが表示されます。
	 */
	@RequestMapping("/")
	public String index(Model m) {
		List<Goods> goods = goodsRepos.findAll();
		m.addAttribute("goods", goods);
		
		return "index";
	}
	
	// @ResponseBodyは、このメソッドがHTTPレスポンスの本文を返すことを示します。
	@ResponseBody
	
	@PostMapping("/api/login")
	/*
	 * @RequestBodyアノテーションは、HTTPリクエストボディからJSONフォーマットのデータをJavaオブジェクトに変換するために使用されます。
	 * LoginFormは、HTTPリクエストボディのJSONデータをJavaオブジェクトに変換するために使用されます。
	 */
	public String loginApi(@RequestBody LoginForm form) {
		/*
		 * findByUserNameAndPasswordメソッドは、ユーザー名とパスワードに基づいてデータを検索するために使用されます。
		 * form.getUserName()とform.getPassword()は、リクエストボディから受信したLoginFormオブジェクトのプロパティを参照しています。
		 */
		List<User> users = userRepos.findByUserNameAndPassword(form.getUserName(), form.getPassword());
		
		/*
		 * LoginDto(User user) コンストラクタ:
		 * このコンストラクタは、Userエンティティを引数に取り、その情報を使用して新しいLoginDtoオブジェクトを作成します。
		 * 現在のloginApi()メソッド内で、このコンストラクタが使われていました。
		 */
		LoginDto dto = new LoginDto(0, null, null ,"ゲスト");
		/*
		 * users.size() > 0 は、usersというリストが空でない場合に真となります。
		 * つまり、データベースから取得されたユーザー情報が存在する場合に真となります。	
		 */
		if (users.size() > 0) {
			/*
			 *このコードでは、データベースから取得されたユーザーリストの先頭のユーザーが使用されています。`users.get(0)`
			 *これは、ユーザー名とパスワードが一致するユーザーが複数いる場合でも、最初に見つかったユーザーの情報を使用することを意味します。 
			*/
			dto = new LoginDto(users.get(0));
		}
		return gson.toJson(dto);
	}
	
	
	@ResponseBody
	@PostMapping("/api/purchase")
	public String purchaseApi(@RequestBody CartForm f) {
		
		// この行では、Cartオブジェクトcから価格（getPrice()）と数量（getCount()）を取得し、両者を掛けて合計金額（total）を計算しています。
		f.getCartList().forEach((c) -> {
			long total = c.getPrice() * c.getCount();
			/*
			 * この行では、PurchaseRepositoryのpersistメソッドを呼び出し、購入情報をデータベースに保存しています。
			 * 引数には、ユーザーID、商品ID、商品名、数量、合計金額が渡されます。
			 */
			purchaseRepos.persist(f.getUserId(), c.getId(), c.getGoodsName(), c.getCount(), total);
		});
		
		/*
		 * この行では、CartFormオブジェクトfからショッピングカート内の商品リスト（List<Cart>）を取得するためにgetCartList()メソッドを呼び出しています。
		 * 次に、そのリストに対してsize()メソッドを呼び出すことで、リストに含まれる商品（Cartオブジェクト）の数を取得しています。
		 * 
		 * つまり、size()はショッピングカート内の商品の数を指しており、これを文字列に変換してAPIの呼び出し元に返しています。
		 * これにより、呼び出し元は正常に処理された商品の数を知ることができます。
		 */
		return String.valueOf(f.getCartList().size());
		
		// このpurchaseApiメソッドは、ショッピングカート内の商品を購入し、購入情報をデータベースに保存する役割を果たしています。
	}
	
	@ResponseBody
	@PostMapping("/api/history")
	public String historyApi(@RequestBody HistoryForm form) {
		String userId = form.getUserId();
		List<Purchase> history = purchaseRepos.findHistory(Long.parseLong(userId));
		List<HistoryDto> historyDtoList = new ArrayList<>();
		history.forEach((v) -> {
			HistoryDto dto = new HistoryDto(v);
			historyDtoList.add(dto);
		});
		
		return gson.toJson(historyDtoList);
 	}
	
}
