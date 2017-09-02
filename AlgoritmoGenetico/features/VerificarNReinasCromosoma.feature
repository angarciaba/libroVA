# language: es
# encoding: utf-8
# Archivo: VerificarNReinasCromosoma.feature
# Autor: Ángel García Baños
# Email: angarciaba@gmail.com y angel.garcia@correounivalle.edu.co
# Fecha creación: 2014-11-08
# Fecha última modificación: 2015-03-24
# Versión: 0.2
# Licencia: GPL

Característica: Verificar el correcto funcionamiento de los Cromosomas.

  Antecedentes: Crear unos cuantos cromosomas para poder trabajar con ellos
    Dado que los Cromosomas van a ser de 50 genes
    Y tengo un Cromosoma1 con todos los genes distintos
    Y copio el Cromosoma1 al Cromosoma2

  Escenario: El Cromosoma debe de estar bien formado
    Entonces todos los genes del Cromosoma1 deben ser distintos
    
  Escenario: La mutación funciona
    Cuando muto el Cromosoma1 10 veces
    Entonces todos los genes del Cromosoma1 deben ser distintos
    Y el Cromosoma1 debe ser distinto al Cromosoma2
    
  Escenario: La mutación funciona
    Cuando muto el Cromosoma1 1 veces
    Entonces todos los genes del Cromosoma1 deben ser distintos
    Y la diferencia entre el Cromosoma1 y el Cromosoma2 deben ser 2 genes

  Escenario: El cruce funciona
    Cuando copio el Cromosoma1 al Cromosoma3
    Y muto el Cromosoma3 50 veces
    Y cruzo el Cromosoma1 con el Cromosoma3 dando como resultado el Cromosoma4
    Entonces el Cromosoma4 debe tener los genes del Cromosoma1 o el Cromosoma3
    Y el Cromosoma4 debe ser distinto al Cromosoma1
    Y el Cromosoma4 debe ser distinto al Cromosoma3

