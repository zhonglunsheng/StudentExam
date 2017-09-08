package com.lipop.dao;

import com.lipop.model.Exam;
import com.lipop.model.PageBean;
import com.lipop.util.HibernateUtil;
import com.lipop.util.StringUtil;

import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

public class ExamDao {
    /**
     * ���ӿ��Լ�¼
     * @param exam
     * @throws Exception
     */
    public void saveExam(Exam exam)throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(exam);
        session.getTransaction().commit();
    }

    /**
     * ǰ̨ѧ�����˿��Լ�¼
     * @Title: getExams   
     * @Description: TODO(������һ�仰�����������������)   
     * @param: @param exam
     * @param: @return
     * @param: @throws Exception      
     * @return: List<Exam>      
     * @throws
     */
    public List<Exam> getExams(Exam exam)throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StringBuilder hql = new StringBuilder("from Exam e");
        if (exam.getStudent()!=null&&StringUtil.isNotEmpty(exam.getStudent().getId())){
            hql.append(" and e.student.id='").append(exam.getStudent().getId()).append("'");
        }
        hql.append(" order by examDate desc");
        Query query = session.createQuery(hql.toString().replaceFirst("and","where"));
        @SuppressWarnings("unchecked")
		List<Exam> examList = query.list();
        session.getTransaction().commit();
        return examList;
    }
    
    /**
     * ��̨ѧ�����Գɼ��б�
     * @Title: getExamList   
     * @Description: TODO(������һ�仰�����������������)   
     * @param: @param exam
     * @param: @return
     * @param: @throws Exception      
     * @return: List<Exam>      
     * @throws
     */
    public List<Exam> getExamList(Exam exam,PageBean pageBean)throws Exception{
    	 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
         session.beginTransaction();
         StringBuilder sb = new StringBuilder("from Exam e");
         if (exam.getStudent()!=null&&StringUtil.isNotEmpty(exam.getStudent().getId())) {
			sb.append(" and e.student.id like '%"+exam.getStudent().getId()+"%'");
		}
         if (exam.getStudent()!=null&&StringUtil.isNotEmpty(exam.getStudent().getName())) {
			sb.append(" and e.student.name like '%"+exam.getStudent().getName()+"%'");
		}
         Query query = session.createQuery(sb.toString().replaceFirst("and","where"));
         query.setFirstResult(pageBean.getPageStart());
         query.setMaxResults(pageBean.getPageSize());
         @SuppressWarnings("unchecked")
		List<Exam> examList = (List<Exam>)query.list();
         session.getTransaction().commit();
         return examList;
    }
    
    /**
     * ����Ա��ȡȫ��ѧ�����Լ�¼
     * @Title: examTotal   
     * @Description: TODO(������һ�仰�����������������)   
     * @param: @param exam
     * @param: @return
     * @param: @throws Exception      
     * @return: int      
     * @throws
     */
    public int examTotal(Exam exam)throws Exception{
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StringBuilder sb = new StringBuilder("select count(*) from t_exam e,t_student s where e.studentId=s.id");
        if (exam.getStudent()!=null&&StringUtil.isNotEmpty(exam.getStudent().getId())) {
			sb.append(" and s.id like '%"+exam.getStudent().getId()+"%'");
		}
        if (exam.getStudent()!=null&&StringUtil.isNotEmpty(exam.getStudent().getName())) {
			sb.append(" and s.name like '%"+exam.getStudent().getName()+"%'");
		}
        Query query = session.createSQLQuery(sb.toString());
        int total = ((BigInteger)query.uniqueResult()).intValue();
        session.getTransaction().commit();
        return total;
    }
    
    public void examDelete(Exam exam)throws Exception{
    	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(exam);
        session.getTransaction().commit();
    }

}
