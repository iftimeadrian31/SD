import ejb.CursEntity;
import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteCursServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nume = request.getParameter("nume");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        Integer id=0;
        TypedQuery<CursEntity> query = em.createQuery("select curs from CursEntity curs WHERE curs.nume = '"+ nume +"'", CursEntity.class);
        List<CursEntity> results = query.getResultList();
        for (CursEntity curs : results) {
            id = curs.getId();
        }

        transaction.begin();
        em.createQuery("delete from CursEntity WHERE id=" + id).executeUpdate();
        transaction.commit();

        em.close();
        factory.close();

        response.setContentType("text/html");
        response.getWriter().println("Datele au fost sterse din baza de date." +
                "<br /><br /><a href='./'>Inapoi la meniul principal</a>");

    }
}