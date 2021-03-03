import beans.StudentBean;
import beans.Students;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // deserializare student din fisierul XML de pe disc
        File file = new File("/home/adi/1306B/Iftime-Adrian/student.xml");

        if (!file.exists()) {
            response.sendError(404, "Nu a fost gasit niciun student serializat pe disc!");
            return;
        }
        XmlMapper xmlMapper = new XmlMapper();
        Students students = xmlMapper.readValue(file, Students.class);
        List<StudentBean> list=students.getList();

        if(list.size()==0) {
            response.sendError(404, "nu avem studenti");
            return;
        }
        String nume_cautat = request.getParameter("nume_cautat");
        String prenume_cautat = request.getParameter("prenume_cautat");
        StudentBean bean=new StudentBean();
        //List<StudentBean> lista_gasiti = new ArrayList<StudentBean>();
        int gasit=-1;
        int marime_lista=list.size();
        for(int i=0;i<marime_lista;i++)
        {
            bean=list.get(i);
            if((bean.getNume().equals(nume_cautat))&&(bean.getPrenume().equals(prenume_cautat)))
            {
                gasit=2;
                //lista_gasiti.add(bean);
                break;
            }
        }
        if(gasit==2) {// deserializare student din fisierul XML de pe disc

            request.setAttribute("nume", bean.getNume());
            request.setAttribute("prenume", bean.getPrenume());
            request.setAttribute("oras", bean.getOras());
            request.setAttribute("tara", bean.getTara());
            request.setAttribute("varsta", bean.getVarsta());


            //request.setAttribute("lista_gasiti", lista_gasiti);
            // redirectionare date catre pagina de afisare a informatiilor studentului
            request.getRequestDispatcher("./info-student.jsp").forward(request, response);
        }
    }
}
