import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetIdUpdateStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // se citesc parametrii din cererea de tip POST
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");

        EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        StringBuilder responseText = new StringBuilder();

        Integer id=0;
        TypedQuery<StudentEntity> query = em.createQuery("select student from StudentEntity student WHERE student.nume = '"+ nume +"' AND student.prenume = '" + prenume + "'", StudentEntity.class);
        List<StudentEntity> results = query.getResultList();
        if(results.size()!=0) {
            for (StudentEntity student : results) {
                id = student.getId();
            }
        }
        request.setAttribute("id", id);

        // inchidere EntityManager
        em.close();
        factory.close();

        // trimitere raspuns la client
        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());


        request.getRequestDispatcher("./update.jsp").forward(request, response);
    }
}
