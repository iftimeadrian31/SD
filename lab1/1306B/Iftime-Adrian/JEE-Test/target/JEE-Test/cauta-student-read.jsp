<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Cautare-student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Cautare-student</h3>
		Introduceti datele despre student:
		<form action="./read-student" method="get">
			Nume: <input type="text" name="nume_cautat" />
			<br />
            Prenume: <input type="text" name="prenume_cautat" />
            <br />
			<br />
            <button type="submit" name="submit">Trimite</button>
		</form>
	</body>
</html>