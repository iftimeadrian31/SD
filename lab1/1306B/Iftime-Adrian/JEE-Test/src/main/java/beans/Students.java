package beans;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class Students implements java.io.Serializable {
    private List<StudentBean> list;

    public Students () {
    }

    public List<StudentBean> getList() {
        return list;
    }

    public void setList(List<StudentBean>  list) {
        this.list = list;
    }


}