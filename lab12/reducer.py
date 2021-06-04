#!/usr/bin/env python
"""reducer.py"""

import sys

dictionar={}
for pereche in sys.stdin:
    pereche = pereche.strip()
    site,url=pereche.split(" , ")
    if(not(site in dictionar.keys())):
        dictionar[site]=[]
    if(not(url in dictionar[site])):
        dictionar[site].append(url)
    lista = list(dictionar.items())
    for site in lista:
        print(site)

