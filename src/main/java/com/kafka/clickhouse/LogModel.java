package com.kafka.clickhouse;

import java.util.Date;

public class LogModel {
	private Date receive_time;
    private String services;
    private String source_host;
    private String remote_addr;
    private long body_bytes_sent;
    private long bytes_sent;
    private double request_time;
    private int status;
    private String request_uri;
    private String request_method;
    private String host;
    private String uri;
    private String hostname;
    private String http_referer;
    private String http_user_agent;
    private double upstream_response_time;
    private String upstream_host;
    private String upstream_cache_status;
    private long request_length;
    private int server_port;
    private String server_protocol;
    private long connection;
    private long connection_requests;
    private String isp;
	public Date getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(Date receive_time) {
		this.receive_time = receive_time;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getSource_host() {
		return source_host;
	}
	public void setSource_host(String source_host) {
		this.source_host = source_host;
	}
	public String getRemote_addr() {
		return remote_addr;
	}
	public void setRemote_addr(String remote_addr) {
		this.remote_addr = remote_addr;
	}
	public long getBody_bytes_sent() {
		return body_bytes_sent;
	}
	public void setBody_bytes_sent(long body_bytes_sent) {
		this.body_bytes_sent = body_bytes_sent;
	}
	public long getBytes_sent() {
		return bytes_sent;
	}
	public void setBytes_sent(long bytes_sent) {
		this.bytes_sent = bytes_sent;
	}
	public double getRequest_time() {
		return request_time;
	}
	public void setRequest_time(double request_time) {
		this.request_time = request_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRequest_uri() {
		return request_uri;
	}
	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}
	public String getRequest_method() {
		return request_method;
	}
	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getHttp_referer() {
		return http_referer;
	}
	public void setHttp_referer(String http_referer) {
		this.http_referer = http_referer;
	}
	public String getHttp_user_agent() {
		return http_user_agent;
	}
	public void setHttp_user_agent(String http_user_agent) {
		this.http_user_agent = http_user_agent;
	}
	public double getUpstream_response_time() {
		return upstream_response_time;
	}
	public void setUpstream_response_time(double upstream_response_time) {
		this.upstream_response_time = upstream_response_time;
	}
	public String getUpstream_host() {
		return upstream_host;
	}
	public void setUpstream_host(String upstream_host) {
		this.upstream_host = upstream_host;
	}
	public String getUpstream_cache_status() {
		return upstream_cache_status;
	}
	public void setUpstream_cache_status(String upstream_cache_status) {
		this.upstream_cache_status = upstream_cache_status;
	}
	public long getRequest_length() {
		return request_length;
	}
	public void setRequest_length(long request_length) {
		this.request_length = request_length;
	}
	public int getServer_port() {
		return server_port;
	}
	public void setServer_port(int server_port) {
		this.server_port = server_port;
	}
	public String getServer_protocol() {
		return server_protocol;
	}
	public void setServer_protocol(String server_protocol) {
		this.server_protocol = server_protocol;
	}
	public long getConnection() {
		return connection;
	}
	public void setConnection(long connection) {
		this.connection = connection;
	}
	public long getConnection_requests() {
		return connection_requests;
	}
	public void setConnection_requests(long connection_requests) {
		this.connection_requests = connection_requests;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
    
    
    
}
