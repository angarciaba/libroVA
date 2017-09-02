# encoding: utf-8
# Archivo: VerificarNReinas_steps.rb
# Autor: Ángel García Baños
# Email: angarciaba@gmail.com y angel.garcia@correounivalle.edu.co
# Fecha creación: 2015-05-20
# Fecha última modificación: 2015-05-20
# Versión: 0.1
# Licencia: GPL

Cuando(/^el tablero es$/) do |tabla|
  tablero = tabla.raw
  @cromosoma = Cromosoma.new(0)
  tablero.each_index do |fila|
    tablero[fila].each_index { |columna|  @cromosoma[columna] = fila if tablero[fila][columna] and not tablero[fila][columna].empty? }
  end
end

Entonces(/^al evaluarlo debe indicar (\d+) conflictos?$/) do |numeroConflictos|
  algoritmoGeneticoTest = AlgoritmoGeneticoTest.new
  aptitud = algoritmoGeneticoTest.evaluar(@cromosoma)
  expect(-aptitud).to eq(numeroConflictos.to_i)
end

