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
import java.util.ArrayList;
import java.util.List;

public class ProcessStudentServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // deserializare student din fisierul XML de pe disc
        File file = new File("/home/adi/1306B/Iftime-Adrian/student.xml");
        XmlMapper xmlMapper = new XmlMapper();
        Students students;
        List<StudentBean> list;
        if (!file.exists()) {
            students = new Students();
             list=new ArrayList<StudentBean>();
        }
        else {
            students = xmlMapper.readValue(file, Students.class);
            list = students.getList();
        }

        // se citesc parametrii din cererea de tip POST
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        int index = Integer.parseInt(request.getParameter("varsta"));
        String oras = request.getParameter("oras");
        String tara = request.getParameter("tara");
        int varsta = Integer.parseInt(request.getParameter("varsta"));

        /*
        procesarea datelor - calcularea anului nasterii
         */
        int anCurent = Year.now().getValue();
        int anNastere = anCurent - varsta;

        // initializare serializator Jackson
        XmlMapper mapper = new XmlMapper();

        // creare bean si populare cu date
        StudentBean bean = new StudentBean();
        bean.setNume(nume);
        bean.setPrenume(prenume);
        bean.setOras(oras);
        bean.setTara(tara);
        bean.setVarsta(varsta);
        list.add(bean);
        students.setList(list);
        // serializare bean sub forma de string XML
        mapper.writeValue(new File("/home/adi/1306B/Iftime-Adrian/student.xml"), students);

        // se trimit datele primite si anul nasterii catre o alta pagina JSP pentru afisare
        request.setAttribute("nume", nume);
        request.setAttribute("prenume", prenume);
        request.setAttribute("varsta", varsta);
        request.setAttribute("oras", oras);
        request.setAttribute("tara", tara);
        request.setAttribute("anNastere", anNastere);
        request.getRequestDispatcher("./info-student.jsp").forward(request, response);
    }

}