import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DeleteStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        Integer id=0;
        TypedQuery<StudentEntity> query = em.createQuery("select student from StudentEntity student WHERE student.nume = '"+ nume +"' AND student.prenume = '" + prenume + "'", StudentEntity.class);
        List<StudentEntity> results = query.getResultList();
        for (StudentEntity student : results) {
            id = student.getId();
        }

        transaction.begin();
        em.createQuery("delete from StudentEntity WHERE id=" + id).executeUpdate();
        transaction.commit();

        em.close();
        factory.close();

        response.setContentType("text/html");
        response.getWriter().println("Datele au fost sterse din baza de date." +
                "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
    }
}