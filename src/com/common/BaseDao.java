package com.common;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;

public class BaseDao extends SqlMapClientDaoSupport{
    @Resource
    private SqlMapClient sqlMapClient;
//    @Value("#{configProperties['machine_name']}")
//    protected  String machineName;
	
    @PostConstruct
    public void initSqlMapClient() {  
        super.setSqlMapClient(sqlMapClient);  
    }
}
