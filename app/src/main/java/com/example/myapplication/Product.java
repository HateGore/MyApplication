package com.example.myapplication;


import java.io.Serializable;

public class Product implements Serializable {
	private int num;// 번호
	private String name;// 이름
	private String maker;// 메이커
	private int price;// 가격
	private String origin;// 원산지
	private String material;// 재료
	private int quantity;// 수량
	private String imgPath;//// 사진경로

	private int category1;// 카테고리1
	private int category2;// 카테고리2
	private int event_num;// 이벤트페이지


	public Product(int num, String name, String maker, int price, String origin, String material, int quantity, String imgPath, int category1, int category2, int event_num) {
		this.num = num;
		this.name = name;
		this.maker = maker;
		this.price = price;
		this.origin = origin;
		this.material = material;
		this.quantity = quantity;
		this.imgPath = imgPath;
		this.category1 = category1;
		this.category2 = category2;
		this.event_num = event_num;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getCategory1() {
		return category1;
	}

	public void setCategory1(int category1) {
		this.category1 = category1;
	}

	public int getCategory2() {
		return category2;
	}

	public void setCategory2(int category2) {
		this.category2 = category2;
	}

	public int getEvent_num() {
		return event_num;
	}

	public void setEvent_num(int event_num) {
		this.event_num = event_num;
	}

	@Override
	public String toString() {
		return "Product{" +
				"num=" + num +
				", name='" + name + '\'' +
				", maker='" + maker + '\'' +
				", price=" + price +
				", origin='" + origin + '\'' +
				", material='" + material + '\'' +
				", quantity=" + quantity +
				", imgPath='" + imgPath + '\'' +
				", category1=" + category1 +
				", category2=" + category2 +
				", event_num=" + event_num +
				'}';
	}
}
