package com.lipop.dao;

import com.lipop.model.PageBean;
import com.lipop.model.Question;
import com.lipop.util.HibernateUtil;
import com.lipop.util.StringUtil;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class QuestionDao {
	/**
	 * ͨ��id��ȡquestion
	 * @Title: getQuestion   
	 * @Description: TODO(������һ�仰�����������������)   
	 * @param: @param questionId
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: Question      
	 * @throws
	 */
    public Question getQuestion(String questionId)throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Question question = (Question) session.get(Question.class,Integer.parseInt(questionId));
        session.getTransaction().commit();
        return question;
    }
    
    /**
     * �ж��Ծ�������Ŀ��
     * @Title: isExistQuestion   
     * @Description: TODO(������һ�仰�����������������)   
     * @param: @param paperId
     * @param: @return
     * @param: @throws Exception      
     * @return: Boolean      
     * @throws
     */
    public Boolean isExistQuestion(String paperId)throws Exception{
    	 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         Query query = session.createQuery("from Question q where q.paper.id=:id");
         query.setInteger("id", Integer.parseInt(paperId));
         @SuppressWarnings("unchecked")
		List<Question> questionList = (List<Question>)query.list();
         session.getTransaction().commit();
         if (questionList.size()>0) {
			return true;
		}else{
			return false;
		}
    }
    /**
     * ��ȡ��Ŀ�б�
     * @Title: getQuestionList   
     * @Description: TODO(������һ�仰�����������������)   
     * @param: @param question
     * @param: @param pageBean
     * @param: @return
     * @param: @throws Exception      
     * @return: List<Question>      
     * @throws
     */
    public List<Question> getQuestionList(Question question,PageBean pageBean)throws Exception{
   	 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StringBuilder sb = new StringBuilder("from Question q");
        if (StringUtil.isNotEmpty(question.getSubject())) {
			sb.append(" where q.subject like '%"+question.getSubject()+"%'");
		}
        Query query = session.createQuery(sb.toString().replaceFirst("and","where"));
        query.setFirstResult(pageBean.getPageStart());
        query.setMaxResults(pageBean.getPageSize());
        @SuppressWarnings("unchecked")
		List<Question> qusetionList = (List<Question>)query.list();
        session.getTransaction().commit();
        return qusetionList;
   }
   
   /**
    * ��ȡȫ��������Ŀ����
    * @Title: examTotal   
    * @Description: TODO(������һ�仰�����������������)   
    * @param: @param exam
    * @param: @return
    * @param: @throws Exception      
    * @return: int      
    * @throws
    */
   public int questionTotal(Question question)throws Exception{
   	   Session session = HibernateUtil.getSessionFactory().getCurrentSession();
       session.beginTransaction();
       StringBuilder sb = new StringBuilder("select count(*) from t_question q");
       if (StringUtil.isNotEmpty(question.getSubject())) {
			sb.append(" where q.subject like '%"+question.getSubject()+"%'");
		}
       Query query = session.createSQLQuery(sb.toString());
       int total = ((BigInteger)query.uniqueResult()).intValue();
       session.getTransaction().commit();
       return total;
   }
   
   /**
    * ��Ӻ��޸���Ŀ
    * @Title: saveQuestion   
    * @Description: TODO(������һ�仰�����������������)   
    * @param: @param question
    * @param: @throws Exception      
    * @return: void      
    * @throws
    */
   public void saveQuestion(Question question)throws Exception{
	   Session session = HibernateUtil.getSessionFactory().getCurrentSession();
       session.beginTransaction();
       session.merge(question);
       session.getTransaction().commit();
   }
   
   public void deleteQuestion(Question question)throws Exception{
	   Session session = HibernateUtil.getSessionFactory().getCurrentSession();
       session.beginTransaction();
       session.delete(question);
       session.getTransaction().commit();
   }
}
