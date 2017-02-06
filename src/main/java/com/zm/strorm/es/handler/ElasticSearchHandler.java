package com.zm.strorm.es.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticSearchHandler {
	private TransportClient client;

	public ElasticSearchHandler() {
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(address, 9200));

		// on shutdown

		// client.close();
	}

	public static void main(String[] args) {
		ElasticSearchHandler test = new ElasticSearchHandler();
		test.generateIndex();
		// test.getIndex();
		test.searchIndex();
		// test.deleteIndex();
		test.closeClient();
	}

	/**
	 * 创建索引
	 */
	public void generateIndex() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("user", "sf");
		json.put("postDate", new Date());
		json.put("message", "trying out Elastic Search");

		IndexResponse response = client.prepareIndex("feng", "test", "1").setSource(json).execute().actionGet();
	}

	/**
	 * 查询索引
	 */
	public void getIndex() {
		GetResponse response = client.prepareGet("feng", "test", "1").execute().actionGet();
		Map<String, Object> rpMap = response.getSource();
		if (rpMap == null) {
			System.out.println("empty");
			return;
		}
		Iterator<Entry<String, Object>> rpItor = rpMap.entrySet().iterator();
		while (rpItor.hasNext()) {
			Entry<String, Object> rpEnt = rpItor.next();
			System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
		}
	}

	/**
	 * 查询记录
	 */
	public void searchIndex() {

		QueryBuilder qb = QueryBuilders.termQuery("user", "sf");
		// 100|hits|per|shard|will|be|returned|for|each|scroll
		SearchResponse scrollResp = client.prepareSearch("feng").setTypes("test").setSearchType(SearchType.DEFAULT)
				.setScroll(new TimeValue(60000)).setQuery(qb).setSize(100).execute().actionGet();
		// Scroll until no hits are returned
		while (true) {
			scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(600000)).execute()
					.actionGet();
			for (SearchHit hit : scrollResp.getHits()) {
				Iterator<Entry<String, Object>> rpItor = hit.getSource().entrySet().iterator();
				while (rpItor.hasNext()) {
					Entry<String, Object> rpEnt = rpItor.next();
					System.out.println(rpEnt.getKey() + " : " + rpEnt.getValue());
				}
			}
			// Break condition: No hits are returned
			if (scrollResp.getHits().hits().length == 0) {
				break;
			}
		}
	}

	public void deleteIndex() {
		DeleteResponse response = client.prepareDelete("feng", "test", "1").execute().actionGet();
	}

	public void closeClient() {
		client.close();
	}
}
