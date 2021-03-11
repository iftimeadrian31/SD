import ejb.CursEntity;
import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateCursServlet extends HttpServlet {
    Integer id=0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nume = request.getParameter("nume");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        StringBuilder responseText = new StringBuilder();

        TypedQuery<CursEntity> query = em.createQuery("select curs from CursEntity curs WHERE curs.nume = '"+ nume +"'", CursEntity.class);
        List<CursEntity> results = query.getResultList();
        for (CursEntity curs : results) {
            id = curs.getId();
        }

        // inchidere EntityManager
        em.close();
        factory.close();

        // trimitere raspuns la client
        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());


        request.getRequestDispatcher("./update-curs.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nume = request.getParameter("nume");
        String profesor = request.getParameter("profesor");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.createQuery("update CursEntity SET nume= \"" + nume + "\" ,profesor= \"" + profesor + "\" WHERE id=" + id).executeUpdate();
        transaction.commit();

        em.close();
        factory.close();

        request.getRequestDispatcher("./update-curs.jsp").forward(request, response);
    }
}