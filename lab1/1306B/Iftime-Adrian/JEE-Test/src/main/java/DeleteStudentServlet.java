import beans.StudentBean;
import beans.Students;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.List;

public class DeleteStudentServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException

    {
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
        boolean gasit=false;
        for(int i=0;i<list.size();i++)
        {
            bean=list.get(i);
            if((bean.getNume().equals(nume_cautat))&&(bean.getPrenume().equals(prenume_cautat)))
            {
                gasit=true;
                break;
            }
        }
        if(gasit==true)
        {
            // initializare serializator Jackson
            XmlMapper mapper = new XmlMapper();

            list.remove(bean);
            students.setList(list);

            // serializare bean sub forma de string XML
            mapper.writeValue(new File("/home/adi/1306B/Iftime-Adrian/student.xml"), students);
        }
    }

}