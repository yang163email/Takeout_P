package com.yan.takeout.model.net;

import java.util.List;

public class GoodsTypeInfo {
    int id;//商品类型id
    String name;//商品类型名称
    String info;//特价信息
    List<GoodsInfo> list;//商品列表

	int redCount;	//选中商品数量

	public int getRedCount() {
		return redCount;
	}

	public void setRedCount(int redCount) {
		this.redCount = redCount;
	}

	public GoodsTypeInfo() {
		super();
	}
	public GoodsTypeInfo(int id, String name, String info, List<GoodsInfo> list) {
		super();
		this.id = id;
		this.name = name;
		this.info = info;
		this.list = list;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public List<GoodsInfo> getList() {
		return list;
	}
	public void setList(List<GoodsInfo> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "GoodsTypeInfo [id=" + id + ", name=" + name + ", info=" + info + ", list=" + list + "]";
	}
    
}
