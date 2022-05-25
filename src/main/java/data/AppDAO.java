package data;

import config.DBConnector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppDAO<T> implements DAO<T> {

    private Class<T> tClass;

    @Override
    public void insert(T element) {
        Transaction transaction = null;
        try(Session session = DBConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(element);

            transaction.commit();
        }

        catch (Exception e){
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
        }
    }

    @Override
    public boolean update(T element) {
        Transaction transaction = null;
        try(Session session = DBConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(element);

            transaction.commit();

            return true;
        }
        catch (Exception e){
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(T element) {
        Transaction transaction = null;
        try(Session session = DBConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.delete(element);

            transaction.commit();

            return true;
        }
        catch (Exception e){
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T getById(int id) {
        Transaction transaction = null;
        try(Session session = DBConnector.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            T returnElement = session.get(tClass, id);

            transaction.commit();

            return returnElement;
        }
        catch (Exception e){
            if (transaction != null)
                transaction.rollback();

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<T> getAll() {
        try(Session session = DBConnector.getSessionFactory().openSession()){
            return session.createQuery("from " + tClass.getSimpleName(), tClass).list();
        }
    }
}
