package com.infinite.dmj_data.service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.infinite.dmj_data.dao.SunshineMapper;
import com.infinite.dmj_data.entity.SunshineEntity;

@Service
public class SunshineService {
	
	@Autowired
	private SunshineMapper sunshineMapper;
	
	@Scheduled(cron ="0 0 17 * * ?")
	@Transactional
	public void getData() throws ClientProtocolException, IOException{

		RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(5000).build();
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		HttpPost post = new HttpPost("http://ygrszx.sinosig.com/riskgift/company/report");
		post.addHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		post.setHeader("Accept", "application/x-www-form-urlencoded");
		post.setHeader("encoding", "UTF-8");
		List<BasicNameValuePair> params = new ArrayList<>();
		String third = "EDM_SUNSHINE";
		String cret = "8a02bc1543f3596b67d6f778fab2a851";
		params.add(new BasicNameValuePair("companyCode", third));
		params.add(new BasicNameValuePair("companySign", cret));
		params.add(new BasicNameValuePair("qtype", "channel"));
		params.add(new BasicNameValuePair("qtype", "prov"));
		params.add(new BasicNameValuePair("qtype", "total"));
		params.add(new BasicNameValuePair("qtype", "dup"));
		HttpEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");
		post.setEntity(formEntity);
		HttpResponse response = httpClient.execute(post);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			String resContent = EntityUtils.toString(entity, "UTF-8");
			System.out.println("resContent=" + resContent);

			JSONObject jsonObject = JSONObject.parseObject(resContent);
			JSONArray channelArray = jsonObject.getJSONArray("channel");
			JSONArray dupArray = jsonObject.getJSONArray("dup");
			JSONArray totalArray = jsonObject.getJSONArray("total");
			JSONArray provArray = jsonObject.getJSONArray("prov");
			
			this.sunshineMapper.deleteAll();
			System.out.println("-----------删除完所有数据---------------");
			
			for(int index=0; index<channelArray.size(); index++){
				System.out.println("--------channel第"+index+"条数据-------------");
				JSONObject channelObject = channelArray.getJSONObject(index);
				SunshineEntity sunshineEntity = new SunshineEntity();
				sunshineEntity.setChannel(channelObject.getString("channel"));
				sunshineEntity.setChannelCallNum(channelObject.getInteger("channel_call_num"));
				sunshineEntity.setChannelInsNum(channelObject.getInteger("channel_ins_num"));
				sunshineEntity.setChannelMth(channelObject.getString("channel_mth"));
				sunshineEntity.setChannelPerPrem(channelObject.getString("channel_per_prem"));
				sunshineEntity.setTheme(channelObject.getString("theme"));
				sunshineEntity.setCreateTime(new Date());
				sunshineEntity.setUpdateTime(new Date());
				this.sunshineMapper.insert(sunshineEntity);
			}
			
			for(int index=0; index<dupArray.size(); index++){
				System.out.println("--------dup第"+index+"条数据-------------");
				JSONObject dupObject = dupArray.getJSONObject(index);
				SunshineEntity sunshineEntity = new SunshineEntity();
				sunshineEntity.setDupAmt(dupObject.getInteger("dup_amt"));
				sunshineEntity.setDupChannel(dupObject.getString("dup_channel"));
				sunshineEntity.setDupIssued(dupObject.getString("dup_issued"));
				sunshineEntity.setDupListDetailName(dupObject.getString("dup_list_detail_name"));
				sunshineEntity.setDupMth(dupObject.getString("dup_mth"));
				sunshineEntity.setDupSendtime(dupObject.getString("dup_sendtime"));
				sunshineEntity.setCreateTime(new Date());
				sunshineEntity.setUpdateTime(new Date());
				this.sunshineMapper.insert(sunshineEntity);
			}
			
			for(int index=0; index<provArray.size(); index++){
				System.out.println("--------prov第"+index+"条数据-------------");
				JSONObject provObject = provArray.getJSONObject(index);
				SunshineEntity sunshineEntity = new SunshineEntity();
				sunshineEntity.setMobProv(provObject.getString("mob_prov"));
				sunshineEntity.setProvCallNum(provObject.getInteger("prov_call_num"));
				sunshineEntity.setProvInsureNum(provObject.getInteger("prov_insure_num"));
				sunshineEntity.setProvList(provObject.getString("prov_list"));
				sunshineEntity.setProvPerPrem(provObject.getString("prov_per_prem"));
				sunshineEntity.setProvSumPrem(provObject.getString("prov_sum_prem"));
				sunshineEntity.setCreateTime(new Date());
				sunshineEntity.setUpdateTime(new Date());
				this.sunshineMapper.insert(sunshineEntity);
			}
			
			for(int index=0; index<totalArray.size(); index++){
				System.out.println("--------total第"+index+"条数据-------------");
				JSONObject totalObject = totalArray.getJSONObject(index);
				SunshineEntity sunshineEntity = new SunshineEntity();
				sunshineEntity.setTotalCallNum(totalObject.getInteger("total_call_num"));
				sunshineEntity.setTotalInsNum(totalObject.getInteger("total_ins_num"));
				sunshineEntity.setTotalList(totalObject.getString("total_list"));
				sunshineEntity.setTotalMth(totalObject.getString("total_mth"));
				sunshineEntity.setTotalPerPrem(totalObject.getString("total_per_prem"));
				sunshineEntity.setProvSumPrem(totalObject.getString("total_sum_prem"));
				sunshineEntity.setTotalSup(totalObject.getString("total_sup"));
				sunshineEntity.setCreateTime(new Date());
				sunshineEntity.setUpdateTime(new Date());
				this.sunshineMapper.insert(sunshineEntity);
			}	
		}
	}
}
