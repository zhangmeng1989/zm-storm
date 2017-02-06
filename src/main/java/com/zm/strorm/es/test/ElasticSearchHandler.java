package com.zm.strorm.es.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ElasticSearchHandler {

	private TransportClient client;

	public ElasticSearchHandler() {
		// 使用本机做为节点
		this("127.0.0.1");

	}

	public ElasticSearchHandler(String ipAddress) {
		try {
			// 在elasticSearch 5.X中新建一个客户端的代码
			Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();// 设置ES实例的名称
			client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipAddress), 9200));
			// 在elasticsearch2.X版本中创建客户端的代码
			// client = TransportClient.builder().build()
			// .addTransportAddress(new
			// InetSocketTransportAddress(InetAddress.getByName(ipAddress),
			// 9300));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立索引,索引建立好之后,会在elasticsearch-rtf-master\data\elasticsearch\nodes\0创建所以你看
	 *
	 * @param index
	 *            为索引库名，一个es集群中可以有多个索引库。 名称必须为小写
	 * @param type
	 *            Type为索引类型，是用来区分同索引库下不同类型的数据的，一个索引库下可以有多个索引类型。
	 * @param jsondata
	 *            json格式的数据集合
	 *
	 * @return
	 */
	public void createIndexResponse(String index, String type, List<String> jsondata) {
		// 创建索引库
		List<IndexRequest> requests = new ArrayList<IndexRequest>();
		for (int i = 0; i < jsondata.size(); i++) {
			IndexRequest request = client.prepareIndex(index, type).setSource(jsondata.get(i)).request();
			requests.add(request);
		}
		// 批量创建索引
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println("批量创建索引错误！");
		}
	}

	/**
	 * 创建索引
	 *
	 * @param client
	 * @param jsondata
	 * @return
	 */
	public IndexResponse createIndexResponse(String indexname, String type, String jsondata) {
		IndexResponse response = client.prepareIndex(indexname, type).setSource(jsondata).execute().actionGet();
		return response;
	}

	/**
	 * 执行搜索
	 *
	 * @param queryBuilder
	 * @param indexname
	 * @param type
	 * @return
	 */
	public List<myFormat> searcher(SearchRequestBuilder searchRequestBuilder, String indexName, String type) {
		List<myFormat> list = new ArrayList<myFormat>();
		// 可以通过setQuery设置查询使用的字符串
		// SearchResponse searchResponse =
		// searchRequestBuilder.setIndices(indexName).setTypes(type).setQuery(QueryBuilders.queryStringQuery("功能主治")).execute().actionGet();
		SearchResponse searchResponse = searchRequestBuilder.setIndices(indexName).setTypes(type).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		System.out.println("查询到记录数=" + hits.getTotalHits());
		SearchHit[] searchHists = hits.getHits();
		for (SearchHit hit : searchHists) {
			Integer id = (Integer) hit.getSource().get("id");
			String name = (String) hit.getSource().get("name");
			String function = (String) hit.getSource().get("function");
			list.add(new myFormat(id, name, function));
		}
		return list;
	}

	// 获取mapping
	public static String getMapping(String indexname, String typename) {
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();// 设置ES实例的名称
		String mapping = "";
		try {
			TransportClient client = new PreBuiltTransportClient(settings)
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
			// TransportClient client =
			// TransportClient.builder().settings(settings).build()
			// .addTransportAddress(new
			// InetSocketTransportAddress(InetAddress.getByName(serverIP),
			// 9300));
			ImmutableOpenMap<String, MappingMetaData> mappings = client.admin().cluster().prepareState().execute()
					.actionGet().getState().getMetaData().getIndices().get(indexname).getMappings();
			mapping = mappings.get(typename).source().toString();

			client.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return mapping;
	}

	// 高亮显示的测试 在进行高亮显示前要设置mapping
	public List<myFormat> highLightSearch() {
		// 设置高亮显示
		List<myFormat> list = new ArrayList<myFormat>();
		QueryBuilder matchQuery = QueryBuilders.matchQuery("title", "编程");
		HighlightBuilder hiBuilder = new HighlightBuilder();
		hiBuilder.postTags("<h2>");
		hiBuilder.postTags("</h2>");
		hiBuilder.field("title");
		SearchResponse searchResponse = client.prepareSearch("blog").setQuery(matchQuery).highlighter(hiBuilder)
				.execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHists = hits.getHits();
		System.out.println("共搜到:" + searchHists.length + "条结果!");
		for (SearchHit hit : searchHists) {
			System.out.println("String方式打印文档搜索内容:");
			System.out.println(hit.getSourceAsString());
			System.out.println("Map方式打印高亮内容");
			System.out.println(hit.getHighlightFields());

			Text[] text = hit.getHighlightFields().get("title").getFragments();
			for (Text str : text) {
				System.out.println(str.string());
			}
		}
		return list;
	}

	// 将带有deleteString字段的文档全部删除
	public void mydelete(SearchRequestBuilder searchRequestBuilder, String indexName, String type,
			String deleteString) {
		SearchResponse searchResponse = searchRequestBuilder.setIndices(indexName).setTypes(type)
				.setQuery(QueryBuilders.queryStringQuery(deleteString)).execute().actionGet();
		SearchHits hits = searchResponse.getHits();
		System.out.println("查询到记录数=" + hits.getTotalHits());
		SearchHit[] searchHists = hits.getHits();
		for (SearchHit hit : searchHists) {
			// 参数包括 indexName type 文档的ID 此ID通过hit.getId()获取，循环删除
			DeleteResponse dResponse = client.prepareDelete(indexName, type, hit.getId()).execute().actionGet();
			if (dResponse.status() == RestStatus.NOT_FOUND) {
				System.out.println("删除成功");
			}
		}
	}

	// 删除indexName索引库
	public void mydeleteIndex(String indexName) {
		DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(indexName).execute().actionGet();
		if (dResponse.isAcknowledged()) {
			System.out.println("delete index " + indexName + "  successfully!");
		} else {
			System.out.println("Fail to delete index " + indexName);
		}
	}

	// 判断给定索引库是否存在
	public void judgeIndexExist(String indexName) {
		boolean flag = false;
		IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(indexName);
		IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();
		if (inExistsResponse.isExists()) {
			flag = true;
		} else {
			flag = false;
		}
	}

	// 关闭索引
	public void closeIndex(String indexName) {
		CloseIndexResponse cIndexResponse = client.admin().indices().prepareClose(indexName)// 这里可以关闭多个索引
																							// index1,index2,...
				.execute().actionGet();
		if (cIndexResponse.isAcknowledged()) {
			System.out.println("关闭索引成功");
		}
	}

	// 打开索引
	public void openIndex(String indexName) {
		OpenIndexResponse oIndexResponse = client.admin().indices().prepareOpen(indexName)// 这里可以打开多个索引
																							// index1,index2,...
				.execute().actionGet();
		System.out.println(oIndexResponse.isAcknowledged());
	}

	public static void main(String[] args) {
		ElasticSearchHandler esHandler = new ElasticSearchHandler();
		List<String> jsondata = DataFactory.getInitJsonData();
		String indexName = "blog";
		String typeName = "1227";
		// esHandler.query();
		// 创建索引，追加方式
		esHandler.createIndexResponse(indexName, typeName, jsondata);
		// 查询条件
		esHandler.highLightSearch();
		SearchRequestBuilder searchRequestBuilder = esHandler.client.prepareSearch(indexName);
		// 设置查询关键词
		// searchRequestBuilder.setQuery(QueryBuilders.queryStringQuery("ConvertToBase64")).
		List<myFormat> result = esHandler.searcher(searchRequestBuilder, indexName, typeName);
		for (int i = 0; i < result.size(); i++) {
			myFormat formatedData = result.get(i);
			System.out.println("(" + formatedData.getId() + ")药品名称:" + formatedData.getName() + "\t" + "\t"
					+ formatedData.getFunction());
		}
		// QueryBuilder builder = QueryBuilders.typeQuery(typeName);//查询整个type
		// DeleteByQueryAction.INSTANCE.newRequestBuilder(esHandler.client).source(indexName).filter(builder).execute().actionGet();
		// esHandler.mydelete(searchRequestBuilder,indexName,typeName,"SCSI");
	}
}