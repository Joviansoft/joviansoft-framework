/**
 * @author gaojie   create date Dec 7, 2006
 */
package com.joviansoft.framework.dao.hibernate;

import com.joviansoft.framework.dao.IHibernateDao;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.lob.SerializableBlob;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;


public class HibernateDao extends HibernateDaoSupport implements IHibernateDao {

    public Session getDaoSession() throws Exception {
        return super.getSession();
    }

    public void delete(Object obj) throws Exception {
        this.getHibernateTemplate().delete(obj);
        //hl add 20100413 保存后直接更新，这样可以做数据校验
        this.getHibernateTemplate().flush();
    }

    public List find(String hsql) throws Exception {
//        return this.getSession().createQuery(hsql).list();
    	return this.getHibernateTemplate().find(hsql);
    }
    
    public Object findEntityById(Class entityClass, Serializable id){
    	return this.getHibernateTemplate().load(entityClass, id);
    }

    public List findForPagination(String hsql, int startRecord, int pageSize) throws
            Exception {
    	
        return this.getSession().createQuery(hsql).setMaxResults(pageSize)
                .setFirstResult(startRecord).list();
    }

    public int findForRecordCount(String hsql) throws Exception {
        return ((Long)this.getHibernateTemplate().find(hsql).iterator()
                .next()).intValue();
    }

    public HibernateTemplate getHt() throws Exception {
        return this.getHibernateTemplate();
    }

    public void save(Object obj) throws Exception {

        this.getHibernateTemplate().save(obj);
        
        //hl add 20100413 保存后直接更新，这样可以做数据校验
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().refresh(obj);
        //如果表中有触发器或DEFAULT字段，则需要增加如下代码：
        //建议通过继承CommonDao实现如下功能
        //getDaoSession().flush();
        //getDaoSession().refresh(obj);
    }

    public void saveOrUpdate(Object obj) throws Exception {
        this.getHibernateTemplate().saveOrUpdate(obj);
        //hl add 20100413 保存后直接更新，这样可以做数据校验
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().refresh(obj);
        
    }

    public void update(Object obj) throws Exception {
    	this.getHibernateTemplate().update(obj);
        //hl add 20100413 保存后直接更新，这样可以做数据校验
        this.getHibernateTemplate().flush();
        this.getHibernateTemplate().refresh(obj);
    }

	public Object saveZp(Object obj, File zpFile, String zpFieldName)
			throws Exception {
		
		
		this.getHibernateTemplate().save(obj);
		this.getHibernateTemplate().flush();
		this.getHibernateTemplate().refresh(obj, LockMode.UPGRADE);
		
		//动态生成getZP方法
		Method method = obj.getClass().getMethod("get" + zpFieldName, new Class[]{});//得到getZP方法
		SerializableBlob bb = (SerializableBlob)method.invoke(obj, new Object[]{});
		
		
//		SerializableBlob bb = (SerializableBlob) ((XX_ZP)obj).getZP(); // 先从java.sql.Blob转SerializableBlob    
		
        java.sql.Blob blob = bb.getWrappedBlob(); //再从通过getWrappedBlob()这个方法又转为java.sql.Blob，这里实在不理解    
        oracle.sql.BLOB ob = (oracle.sql.BLOB)blob;// 从java.sql.Blob转oracle.sql.BLOB    
        OutputStream out = ob.getBinaryOutputStream();    
        
		FileInputStream fin = new FileInputStream(zpFile); 
		byte[] data = new byte[(int)fin.available()]; 
		fin.read(data); 
		out.write(data); 
		fin.close(); 
		out.close(); 
		this.getHibernateTemplate().flush(); 
				
		return obj;
	}
	
    public Object saveBase64Zp(Object obj, String zp, String zpFieldName) 
    	throws Exception{
		this.getHibernateTemplate().save(obj);
		this.getHibernateTemplate().flush();
		this.getHibernateTemplate().refresh(obj, LockMode.UPGRADE);
		
		//动态生成getZP方法
		Method method = obj.getClass().getMethod("get" + zpFieldName, new Class[]{});//得到getZP方法
		SerializableBlob bb = (SerializableBlob)method.invoke(obj, new Object[]{});
		
		
//		SerializableBlob bb = (SerializableBlob) ((XX_ZP)obj).getZP(); // 先从java.sql.Blob转SerializableBlob    
		
        java.sql.Blob blob = bb.getWrappedBlob(); //再从通过getWrappedBlob()这个方法又转为java.sql.Blob，这里实在不理解    
        oracle.sql.BLOB ob = (oracle.sql.BLOB)blob;// 从java.sql.Blob转oracle.sql.BLOB    
        OutputStream out = ob.getBinaryOutputStream();    
        
//		FileInputStream fin = new FileInputStream(zpFile); 
//		byte[] data = new byte[(int)fin.available()]; 
//		fin.read(data); 
        byte[] data = null;
        data = Base64.decodeBase64(zp.getBytes()); //将Base64数据进行解码转换
		
		out.write(data); 
//		fin.close(); 
		out.close(); 
		this.getHibernateTemplate().flush(); 
    	
    	return obj;
	}

}
