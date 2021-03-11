<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Formular student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Formular student</h3>
		Introduceti datele noi:
		<form action="./update-student" method="post">
			Nume: <input type="text" name="nume" value='<%=request.getParameter("nume")%>'/>
			<br />
			Prenume: <input type="text" name="prenume" value='<%=request.getParameter("prenume")%>' />
			<br />
			Varsta: <input type="number" name="varsta"/>
            <br />
			<br />
			<button type="submit" name="submit">Trimite</button>
		</form>
	</body>
</html>