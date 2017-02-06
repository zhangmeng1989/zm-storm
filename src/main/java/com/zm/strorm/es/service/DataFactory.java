package com.zm.strorm.es.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.zm.strorm.es.entity.Medicine;

public class DataFactory {
	public static DataFactory dataFactory = new DataFactory();

	private DataFactory() {
	}

	public DataFactory getInstance() {
		return dataFactory;
	}

	public static List<String> getInitJsonData() {
		List<String> list = new ArrayList<String>();
		String data1 = JSON.toJSONString(new Medicine(1, "感冒 胶囊", 25.1, "功能主治：银花感冒颗粒 ，头痛,清热，解表，利咽。"));
		String data2 = JSON.toJSONString(new Medicine(2, "感冒  止咳糖浆", 31.2, "功能主治：感冒止咳糖浆,解表清热，止咳化痰。"));
		String data3 = JSON.toJSONString(new Medicine(3, "感冒灵颗粒", 28.2, "功能主治：解热镇痛。头痛 ,清热。"));
		String data4 = JSON.toJSONString(new Medicine(4, "感冒  灵胶囊", 19.8, "功能主治：银花感冒颗粒 ，头痛,清热，解表，利咽。"));
		String data5 = JSON.toJSONString(new Medicine(5, "仁和 感冒 颗粒", 32.1, "功能主治：疏风清热，宣肺止咳,解表清热，止咳化痰。"));
		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		list.add(data5);
		return list;
	}

	public static void main(String[] args) {
		System.out.println(getInitJsonData().toString());
	}
}
