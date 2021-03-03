<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Formular student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Informatii student</h3>

        		<!-- populare bean cu informatii din cererea HTTP -->
        		<jsp:useBean id="studentBean" class="beans.StudentBean" scope="request" />
        		<jsp:setProperty name="studentBean" property="nume" value='<%= request.getAttribute("nume") %>'/>
        		<jsp:setProperty name="studentBean" property="prenume" value='<%= request.getAttribute("prenume") %>'/>
        		<jsp:setProperty name="studentBean" property="oras" value='<%= request.getAttribute("oras") %>'/>
        		<jsp:setProperty name="studentBean" property="tara" value='<%= request.getAttribute("tara") %>'/>
        		<jsp:setProperty name="studentBean" property="varsta" value='<%= request.getAttribute("varsta") %>'/>

        		<!-- folosirea bean-ului pentru afisarea informatiilor -->
        		<p>Urmatoarele informatii au fost introduse:</p>
        		<ul type="bullet">
        			<li>Nume: <jsp:getProperty name="studentBean" property="nume" /></li>
        			<li>Prenume: <jsp:getProperty name="studentBean" property="prenume" /></li>
        			<li>Oras: <jsp:getProperty name="studentBean" property="oras" /></li>
        			<li>Tara: <jsp:getProperty name="studentBean" property="tara" /></li>
        			<li>Varsta: <jsp:getProperty name="studentBean" property="varsta" /></li>

        			<!-- anul nasterii nu face parte din bean, il afisam separat (daca exista) -->
        			<li>Anul nasterii: <%
        			    Object anNastere = request.getAttribute("anNastere");
        			    if (anNastere != null) {
        			        out.print(anNastere);
        			    } else {
        			        out.print("necunoscut");
        			    }
        			%></li>
        		</ul>
        <h3>Formular student</h3>
        		Introduceti datele despre student:
        		<form action="./actualizare-student" method="post">
        			Nume: <input type="text" name="nume" />
        			<br />
        			Prenume: <input type="text" name="prenume" />
        			<br />
        			Oras: <input type="text" name="oras" />
                    <br />
                    Tara: <input type="text" name="tara" />
                    <br />
        			Varsta: <input type="number" name="varsta" />
        			<br />
        			<br />
        			<button type="submit" name="submit">Trimite</button>
        		</form>
        <p>
                <a href="./index.jsp">Home</a>
        </p>

	</body>
</html>