let login = (event) => {
		event.preventDefault();
		// まず、左辺には「let.jsonString」という変数があります。これは、JSON形式の文字列に変換されたデータを格納するための変数です。
		
		/*
		右辺には、JavaScriptオブジェクトが定義されています。
		このオブジェクトは、2つのプロパティ「userName」と「password」を持ち、それぞれの値は、input要素のname属性が「userName」と「password」である要素から取得された値です。
		
		.val()は、jQueryのメソッドの1つで、フォーム要素（input、select、textareaなど）の値を取得したり設定したりするために使用されます。
		*/
		let jsonString = {
			'userName': $('input[name=userName]').val(),
			'password': $('input[name=password]').val()
		};
		// $.ajax()関数を使用して、POSTリクエストをサーバーに送信します。
		$.ajax({
			type: 'POST',
			url: '/ecsite/api/login',
			data: JSON.stringify(jsonString),
			contentType: 'application/json',
			datatype: 'json',
			scriptCharset: 'utf-8'
		})
		// then()メソッドを使用して、サーバーからのレスポンスデータを処理します。
		.then((result) => {
			/*
			resultという名前の変数に格納されたJSON形式の文字列を、JavaScriptオブジェクトに変換しています。
			
			JSON.parse()は、JSON形式の文字列をJavaScriptオブジェクトに変換するための組み込み関数です。
			この関数は、サーバーから受け取ったJSON形式のレスポンスをJavaScriptで利用しやすい形に変換するために使用されます。
			
			要するに、let user = JSON.parse(result);の目的は、
			サーバーからのJSONレスポンスをJavaScriptオブジェクトに変換し、その情報を他の処理で利用するためです。
			*/
			let user = JSON.parse(result);
			$('#welcome').text(` -- ようこそ！ ${user.fullName} さん`);
			$('#hiddenUserId').val(user.id);
			// このコードは、ログイン成功後にユーザ名とパスワードの入力欄を空にするためのものです。
			$('input[name=userName]').val('');
			$('input[name=password]').val('');
		}, () => {
			console.error('Error: ajax connnection failed.');
		}) 		
	};

	
let addCart = (event) => {
		let tdList = $(event.target).parent().parent().find('td');
		
		let id = $(tdList[0]).text();
		let goodsName = $(tdList[1]).text();
		let price = $(tdList[2]).text();
		/*
		val()は、jQueryのメソッドで、フォーム要素（例えば<input>, <select>, <textarea>など）の値を取得または設定するために使用されます。
		"value"（値）の略語です。
		*/
		let count = $(tdList[3]).find('input').val();
		
		if (count === '0' || count === '') {
			alert('注文数入れてへんやないかい！')
			return;
		};
		
		// 商品の情報を表すオブジェクト（cart）を作成し、それをショッピングカートを表す配列（carList）に追加することで、複数の商品をカートに格納できます。
		let cart = {
				'id': id,
				'goodsName': goodsName,
				'price': price,
				'count': count
		};
		// push()メソッドは、JavaScriptの配列において、新しい要素を配列の末尾に追加するために使用されるメソッドです。
		cartList.push(cart);
		
		let tbody = $('#cart').find('tbody');
		// tbodyの子要素（行）をすべて削除しています。これにより、カートの表示が初期化されます。
		$(tbody).children().remove();
		// cartList配列の各要素に対して、アロー関数を実行しています。
		// この関数では、各商品の情報を表示するための新しい<tr>要素（行）を作成しています。
		cartList.forEach(function(cart, index) {
			let tr = $('<tr />');
			
			// 作成した<tr>要素に、<td>要素を追加しています。これにより、商品のID、商品名、価格、数量が表示されます。
			$('<td />', {'text': cart.id}).appendTo(tr);
			$('<td />', {'text': cart.goodsName}).appendTo(tr);
			$('<td />', {'text': cart.price}).appendTo(tr);
			$('<td />', {'text': cart.count}).appendTo(tr);
			/*
			削除ボタンの追加:
			新しい<td>要素（tdButton）を作成し、その中に削除ボタンを追加しています。
			削除ボタンには、テキストとして「カート削除」と表示され、removeBtnというクラスが割り当てられます。
			*/
			let tdButton = $('<td />');
			$('<button />', {
				'text': 'カート削除',
				'class': 'removeBtn',
			}).appendTo(tdButton);
			
			$(tdButton).appendTo(tr);
			// 商品情報と削除ボタンを含む<tr>要素をtbodyに追加しています。
			$(tr).appendTo(tbody);
		});
		$('.removeBtn').on('click', removeCart);
	};
	

let buy = (event) => {
	$.ajax({
		type: 'POST',
		url: '/ecsite/api/purchase',
		data: JSON.stringify({
			"userId": $('#hiddenUserId').val(),
			"cartList": cartList
		}),
		contentType: 'application/json',
		datatype: 'json',
		scriptCharset: 'utf-8'
	})
	.then((result) => {
		alert('買うてもうたやないかい！');
	}, () => {
		console.error('Error: ajax connnection failed.');
	});
}


let removeCart = ((event) => {
	const tdList = $(event.target).parent().parent().find('td');
	let id = $(tdList[0]).text();
	cartList = cartList.filter(function(cart) {
		return cart.id !== id;
	});
	$(event.target).parent().parent().remove();
});


let showHistory = () => {
	$.ajax({
		type: 'POST',
		url: '/ecsite/api/history',
		data: JSON.stringify({ "userId": $('#hiddenUserId').val() }),
		contentType: 'application/json',
		datatype: 'json',
		scriptCharset: 'utf-8'
	})
	.then((result)=> {
		let historyList = JSON.parse(result);
		let tbody = $('#historyTable').find('tbody');
		$(tbody).children().remove();
		historyList.forEach((history, index) => {
			let tr = $('<tr />');
			
			$('<td />', { 'text' : history.goodsName }).appendTo(tr);
			$('<td />', { 'text' : history.itemCount }).appendTo(tr);
			$('<td />', { 'text' : history.createdAt }).appendTo(tr);
			
			$(tr).appendTo(tbody);
		});
		$("#history").dialog("open");
	}, () => {
		console.error('Error: ajax connnection failed.');
	});
};








