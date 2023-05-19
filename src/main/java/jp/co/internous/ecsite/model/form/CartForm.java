package jp.co.internous.ecsite.model.form;

import java.io.Serializable;
import java.util.List;

public class CartForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long userId;
	// cartListはCartオブジェクトのリストを表しています。
	/*
	 * ショッピングカート内の複数の商品を保持するために使用されます。
	 * CartFormクラスのgetCartList()およびsetCartList(List<Cart> cartList)メソッドを使って、Cartオブジェクトのリストにアクセスしたり、値を設定したりできます。
	 * 
	 * 要約すると、Cartクラスは各商品を表し、CartFormクラスはユーザーのショッピングカート全体を表すためにCartオブジェクトのリストを含んでいます。
	 * これにより、アプリケーションでショッピングカート機能を実装する際に、これらのクラスを使用してデータを簡単に操作できます。
	 */
	private List<Cart> cartList;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public List<Cart> getCartList() {
		return cartList;
	}
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

}
