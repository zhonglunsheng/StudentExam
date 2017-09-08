package com.lipop.dao;

import com.lipop.model.Paper;
import com.lipop.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;


public class PaperDao {
    /**
     * ѧ��ѡ���Ծ�
     * @return
     * @throws Exception
     */
    public List<Paper> list()throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Paper");
        @SuppressWarnings("unchecked")
		List<Paper> paperList = (List<Paper>)query.list();
        session.getTransaction().commit();
        return paperList;
    }

    /**
     * ͨ���Ծ�id��ȡ����
     * @param paperId
     * @return
     * @throws Exception
     */
    public Paper getPaper(String paperId)throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Paper paper = (Paper)session.get(Paper.class,Integer.parseInt(paperId));
        session.getTransaction().commit();
        return paper;
    }

    /**
     * ɾ���Ծ�
     * @param paper
     * @throws Exception
     */
    public void deletePaper(Paper paper)throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(paper);
        session.getTransaction().commit();
    }
    
    /**
     * �Ծ���Ϣ�޸�
     * @Title: updatePaper   
     * @Description: TODO(������һ�仰�����������������)   
     * @param: @param paper
     * @param: @throws Exception      
     * @return: void      
     * @throws
     */
    public void updatePaper(Paper paper)throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    session.merge(paper);
	    session.getTransaction().commit();
    }
}
