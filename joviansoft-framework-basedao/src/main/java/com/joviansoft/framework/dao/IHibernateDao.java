/**
* @author gaojie   create date Dec 7, 2006
*/
package com.joviansoft.framework.dao;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.hibernate.Session;

public interface IHibernateDao {
	public List find(String hsql) throws Exception;
	public void saveOrUpdate(Object obj) throws Exception;
	public void save(Object obj) throws Exception;
	public void update(Object obj) throws Exception;
	public void delete(Object obj) throws Exception;
	public List findForPagination(String hsql,int startRecord,int pageSize) throws Exception;
	public int findForRecordCount(String hsql) throws Exception;
	public HibernateTemplate getHt() throws Exception;
    public Session getDaoSession() throws Exception;
    public Object findEntityById(Class entityClass, Serializable id);        
        //hl add 20080423 get a system Sequence
//        public int getSID() throws Exception;
    /**
     * hl add 20100419 保存照片表
     * obj为有照片字段的po
     * zpFile:照片文件
     * zpFieldName:po中照片字段名
     * return 照片po
     */
    public Object saveZp(Object obj, File zpFile, String zpFieldName) throws Exception;
    /**
     * hl add 20100603 保存照片表
     * obj为有照片字段的po
     * zp:Base64编码的照片字符
     * zpFieldName:po中照片字段名
     * return 照片po
     */
    public Object saveBase64Zp(Object obj, String zp, String zpFieldName) throws Exception;
}
