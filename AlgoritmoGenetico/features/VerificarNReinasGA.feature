# language: es
# encoding: utf-8
# Archivo: VerificarNReinasGA.feature
# Autor: Ángel García Baños
# Email: angarciaba@gmail.com y angel.garcia@correounivalle.edu.co
# Fecha creación: 2015-05-20
# Fecha última modificación: 2015-05-20
# Versión: 0.1
# Licencia: GPL

Característica: Verificar que funciona la evaluación de un Cromosoma en el algoritmo genético para la N Reinas. Nota: los tableros aquí indicados no deben tener conflictos en filas ni en columnas, pues eso no se verifica.

  Escenario: Ningún conflicto
    Cuando el tablero es
      |   | X |   |   |
      |   |   |   | X |
      | X |   |   |   |
      |   |   | X |   |
    Entonces al evaluarlo debe indicar 0 conflictos

  Escenario: Un conflicto en diagonal principal
    Cuando el tablero es
      | X |   |   |   |
      |   | X |   |   |
      |   |   |   |   |
      |   |   |   |   |
    Entonces al evaluarlo debe indicar 1 conflicto
    
  Escenario: Un conflicto en diagonal principal
    Cuando el tablero es
      |   | X |   |   |
      |   |   | X |   |
      |   |   |   |   |
      |   |   |   |   |
    Entonces al evaluarlo debe indicar 1 conflicto

  Escenario: Un conflicto en diagonal secundaria
    Cuando el tablero es
      |   |   | X |   |
      |   | X |   |   |
      |   |   |   |   |
      |   |   |   |   |
    Entonces al evaluarlo debe indicar 1 conflicto

  Escenario: Un conflicto en diagonal secundaria
    Cuando el tablero es
      |   | X |   |   |
      | X |   |   |   |
      |   |   |   |   |
      |   |   |   |   |
    Entonces al evaluarlo debe indicar 1 conflicto

  Escenario: Dos conflictos
    Cuando el tablero es
      |   |   |   | X |
      |   |   |   |   |
      |   | X |   |   |
      |   |   | X |   |
    Entonces al evaluarlo debe indicar 2 conflictos

  Escenario: Dos conflictos
    Cuando el tablero es
      | X |   |   |   |
      |   | X |   |   |
      |   |   | X |   |
      |   |   |   |   |
    Entonces al evaluarlo debe indicar 2 conflictos

  Escenario: Tres conflictos
    Cuando el tablero es
      | X |   |   |   |
      |   | X |   |   |
      |   |   | X |   |
      |   |   |   | X |
    Entonces al evaluarlo debe indicar 3 conflictos

  Escenario: Tres conflictos
    Cuando el tablero es
      |   |   |   | X |
      | X |   |   |   |
      |   | X |   |   |
      |   |   | X |   |
    Entonces al evaluarlo debe indicar 3 conflictos

  Escenario: Cuatro conflictos
    Cuando el tablero es
      |   |   | X |   |
      |   |   |   | X |
      | X |   |   |   |
      |   | X |   |   |
    Entonces al evaluarlo debe indicar 4 conflictos





