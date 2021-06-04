#!/usr/bin/env python
"""mapper.py"""

import sys
import urllib.request
import re

# input comes from STDIN (standard input)
for url_pagina in sys.stdin:
	url_pagina = url_pagina.strip()
	webUrl= urllib.request.urlopen(url_pagina)
	data = webUrl.read()
	plainText=data.decode('utf8')
	links = re.findall("href=[\"\'](.*?)[\"\']", plainText)
	for link in links:
		print(url_pagina+" , "+link)


