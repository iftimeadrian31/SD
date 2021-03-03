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

public class ActualizareStudentServlet extends HttpServlet {
    private String nume_cautat;
    private String prenume_cautat;
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
        nume_cautat = request.getParameter("nume_cautat");
        prenume_cautat = request.getParameter("prenume_cautat");
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
        if(gasit==true) {
            request.setAttribute("nume", bean.getNume());
            request.setAttribute("prenume", bean.getPrenume());
            request.setAttribute("oras", bean.getOras());
            request.setAttribute("tara", bean.getTara());
            request.setAttribute("varsta", bean.getVarsta());

            // redirectionare date catre pagina de afisare a informatiilor studentului
            request.getRequestDispatcher("./actualizare-student.jsp").forward(request, response);
        }

    }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
                // se citesc parametrii din cererea de tip POST
                String nume = request.getParameter("nume");
                String prenume = request.getParameter("prenume");
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
                list.remove(bean);
                bean.setNume(nume);
                bean.setPrenume(prenume);
                bean.setVarsta(varsta);
                bean.setOras(oras);
                bean.setTara(tara);
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
                request.getRequestDispatcher("./actualizare-student.jsp").forward(request, response);
        }
    }
 }