import ejb.CursEntity;
import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetIdUpdateCursServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // se citesc parametrii din cererea de tip POST
        String nume = request.getParameter("nume");

        EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        StringBuilder responseText = new StringBuilder();

        Integer id=0;
        TypedQuery<CursEntity> query = em.createQuery("select curs from CursEntity curs WHERE curs.nume = \""+ nume +"\"", CursEntity.class);
        List<CursEntity> results = query.getResultList();
        for (CursEntity curs : results) {
            id = curs.getId();
        }
        id=1;
        request.setAttribute("id", id);

        // inchidere EntityManager
        em.close();
        factory.close();

        // trimitere raspuns la client
        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());


        request.getRequestDispatcher("./update-curs.jsp").forward(request, response);
    }
}
