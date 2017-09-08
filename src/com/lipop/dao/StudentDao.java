package com.lipop.dao;

import com.lipop.model.PageBean;
import com.lipop.model.Student;
import com.lipop.util.HibernateUtil;
import com.lipop.util.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

public class StudentDao {
    /**
     * ѧ���˵�¼
     * @param student
     * @return
     * @throws Exception
     */
    public Student login(Student student)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Student as s where s.id=:id and s.password=:password");
        query.setString("id",student.getId())
                .setString("password",student.getPassword());
        Student resultStu = (Student)query.uniqueResult();
        session.getTransaction().commit();
        return resultStu;
    }

    /**
     * ѧ�����¸�������
     * @param student
     * @throws Exception
     */
    public void updateStudent(Student student)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(student);
        session.getTransaction().commit();
    }

    /**
     * ����Ա��̨ѧ���б�
     * @param student
     * @param pageBean 
     * @return
     * @throws Exception
     */
    public List<Student> getStudents(Student student, PageBean pageBean)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        StringBuilder hql = new StringBuilder("from Student");
        if (StringUtil.isNotEmpty(student.getId())){
            hql.append(" and id like '%"+student.getId()+"%'");
        }
        if (StringUtil.isNotEmpty(student.getName())){
            hql.append(" and name like '%"+student.getName()+"%'");
        }
        Query query = session.createQuery(hql.toString().replaceFirst("and","where"));
        if (pageBean!=null){
            query.setFirstResult(pageBean.getPageStart())
                    .setMaxResults(pageBean.getPageSize());
        }
        @SuppressWarnings("unchecked")
		List<Student> studentList = query.list();
        session.getTransaction().commit();
        return studentList;
    }

    /**
     * ѧ������ͳ��
     * @param student
     * @return
     * @throws Exception
     */
    public int studentTotal(Student student)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        StringBuilder sql = new StringBuilder("select count(*) from t_student");
        if (StringUtil.isNotEmpty(student.getId())){
            sql.append(" and id like '%").append(student.getId()).append("%'");
        }
        if (StringUtil.isNotEmpty(student.getName())){
            sql.append(" and name like '%").append(student.getName()).append("%'");
        }
        Query query = session.createSQLQuery(sql.toString().replaceFirst("and","where"));
        int total = ((BigInteger)query.uniqueResult()).intValue();
        session.getTransaction().commit();
        return total;
    }

    /**
     * ����Ա���ѧ����¼
     * @param student
     * @throws Exception
     */
    public void add(Student student)throws Exception{
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.merge(student);
        session.getTransaction().commit();
    }

    /**
     * ����Աͨ��ѧ��id��ȡ��Ϣ
     * @return
     * @throws Exception
     */
    public Student getStudentById(String id)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "from Student s where s.id=:id";
        Query query =session.createQuery(hql);
        query.setString("id",id);
        Student student = (Student) query.uniqueResult();
        session.getTransaction().commit();
        return student;
    }

    /**
     * ����Աɾ��ѧ����¼
     * @Title: deleteStudent   
     * @Description: TODO(������һ�仰�����������������)   
     * @param: @param student
     * @param: @throws Exception      
     * @return: void      
     * @throws
     */
    public void deleteStudent(Student student)throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(student);
        session.getTransaction().commit();
    }
}
