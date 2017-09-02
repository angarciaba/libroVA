#!/usr/bin/env ruby
# encoding: utf-8
# Programa: NReinas.rb
# Autor: Ángel García Baños
# Email: angarciaba@gmail.com y angel.garcia@correounivalle.edu.co
# Fecha creación: 2014-11-07
# Fecha última modificación: 2014-11-08
# Versión: 0.3 
# Tiempo dedicado: 4:30 horas

############################################################
# Utilidad: Enseñar a programar en Ruby. Hacer un algoritmo genético para resolver las NReinas
############################################################
# VERSIONES
# 0.3 rdoc
# 0.2 Se simplificaron las clases a sólo dos (Cromosoma y AlgoritmoGeneticoNReinas), para que fuera un ejemplo sencillo y didáctico.
# 0.1 La primera. Con clases Gen, Cromosoma, AlgoritmoGenetico y NReinas. Demasiado complicado.
############################################################
# Para ayudar a depurar:
def dd(expresion,entorno,mensaje="")
  p "#{expresion}=#{entorno.eval(expresion)}  #{mensaje}"
end
# Ejemplo de uso:
# a="Hola"
# dd("a",binding)
############################################################
# Para que funcione bundler (que traiga las gemas especificadas en Gemfile):
require 'rubygems'
require 'bundler/setup'
############################################################
=begin
require 'ruby-prof'
RubyProf.start
$result = RubyProf.stop
printer = RubyProf::FlatPrinter.new($result)
printer.print(STDOUT)
=end
############################################################

# El Gen es un entero, por lo que no merece la pena hacer una clase para ello

# El Cromosoma es un Array de Genes (enteros)
class Cromosoma < Array
  # El Cromosoma tiene una aptitud que se puede leer y escribir
  attr_accessor :aptitud
  
  # Se define cuantosGenes va a tener el Cromosoma
  # Este constructor depende del problema a resolver, en este caso, las NReinas. Por ello, lo que hace es
  # construir un Cromosoma de genes enteros en un Array, cuya posición indica la columna y cuyos alelos
  # codifican el número de la fila donde se ubica cada reina
  def initialize(cuantosGenes=0)
    super
    cuantosGenes.times { |n| self[n] = n }  # Para asegurar que no haya alelos (valores de genes) repetidos
    sort_by! {rand}  # Se permutan los genes al azar
  end

  # La mutación se hace intercambiando dos genes cualesquiera  
  def mutar!
    cualGen1, cualGen2 = rand(size), rand(size)
    self[cualGen1], self[cualGen2] = self[cualGen2], self[cualGen1]
    @aptitud = nil
    self
  end

  # El cruce se hace uniforme, eligiendo al azar un gen del padre o de la madre, para cada posición
  def cruzar(otroCromosoma)
    hijo = Cromosoma.new
    self.zip(otroCromosoma).each { |genPadre, genMadre| hijo << (rand < 0.5 ? genPadre : genMadre) }
    hijo
  end
  
  # No se puede usar el operador = porque hace una copia superficial (por referencia), y lo que queremos es una copia profunda (por valor). 
  # Ojo: El operador = no se puede redefinir porque es global (se puede usar con cualquier tipo de dato), por lo que no pertenece a ninguna clase específica.
  def clone 
    otroCromosoma = Cromosoma.new
    otroCromosoma.replace(self)
    otroCromosoma.aptitud = self.aptitud
    otroCromosoma
  end
end


# Esta clase implementa un algoritmo genético para resolver el problema de las NReinas. Es un Array de Cromosomas.
class AlgoritmoGeneticoNReinas < Array
  attr_reader :mejorCromosoma, :numeroDeEvaluaciones
  
  # En el constructor hay que especificar cuantas reinas tiene el problema y cuantos Cromosomas se desea tener
  # en la población
  def initialize(cuantasReinas, cuantosCromosomas=100)
    cuantosCromosomas.times { self << Cromosoma.new(cuantasReinas) }
    @mejorCromosoma = Cromosoma.new
    @numeroDeEvaluaciones = 0
  end
  
  # Se ejecuta el número de generaciones que se especifique aquí
  def ejecutar(cuantasGeneraciones=1000)
    cuantasGeneraciones.times do
      cromosoma1 = seleccionarPorTorneo
      cromosoma2 = seleccionarPorTorneo
#      cromosomaHijo = cromosoma1.cruzar(cromosoma2)  # El cruce no se debe usar, porque genera cromosomas inválidos
      cromosomaHijo = cromosoma1
      cromosoma1.mutar!.mutar!
      cromosoma2.mutar!
      reemplazar(cromosoma1)
      reemplazar(cromosoma2)
      reemplazar(cromosomaHijo)
    end
    self
  end
  
  private

  # Evalúa un Cromosoma, retornando su aptitud.
  # La función de evaluación depende del problema concreto a resolver. En este caso, las NReinas, se
  # puntúa negativamente cada colisión entre reinas, en los dos tipos de diagonales
  # En las filas y en las columnas es imposible que haya colisiones debido a la forma de codificar el Cromosoma  
  # Dos reinas A y B están en la misma diagonal si la recta que definen tiene pendiente +1 o -1, o sea:
  # (yA-yB)/(xA-xB) = +-1
  #   ==>  (yA-yB)=+-(xA-xB)  ==>   (yA+-xA) = (yB+-xB)
  def evaluar(cromosoma)
    return cromosoma.aptitud if cromosoma.aptitud
    @numeroDeEvaluaciones += 1

    coordenadas = []
    cromosoma.each_index do |fila| 
      coordenadas[fila] = []
      coordenadas[fila][0] = fila+cromosoma[fila] if cromosoma[fila]
      coordenadas[fila][1] = fila-cromosoma[fila] if cromosoma[fila]
    end
    repetidos = coordenadas.inject([Hash.new(0),Hash.new(0)]) { |hash, item| hash[0][item[0]] += 1; hash[1][item[1]] += 1; hash}

    conflictos = 0
    repetidos.each do |r| 
      conflictos += r.inject(0) { |acumulador, item| acumulador+item[1]-1 }
    end
    
    cromosoma.aptitud = -conflictos
    @mejorCromosoma = cromosoma.clone if not @mejorCromosoma.aptitud or @mejorCromosoma.aptitud < cromosoma.aptitud # Copia profunda
    cromosoma.aptitud
  end
  
  # La selección por torneo elige dos Cromosomas al azar y retorna el que tenga mejor aptitud
  def seleccionarPorTorneo
    cromosoma1 = sample
    cromosoma2 = sample
    if evaluar(cromosoma1) > evaluar(cromosoma2) then
      cromosoma1
    else
      cromosoma2
    end
  end
  
  # El reemplazo recibe un nuevo Cromosoma y lo inserta en un lugar al azar en la población, eliminando al 
  # cromosoma que estuviera allí
  def reemplazar(cromosoma)  # Eliminando otro al azar
    self[rand(size)] = cromosoma.clone  # Copia profunda
  end
end


if $0 == __FILE__
  ######################################
  # INPUTS
  numeroCromosomas=200
  numeroGeneraciones=100000
  numeroReinas=12
  ######################################

  espacioBusqueda=1
  1.upto(numeroReinas) { |i| espacioBusqueda *= i }
  nreinas = AlgoritmoGeneticoNReinas.new(numeroReinas,numeroCromosomas)
  nreinas.ejecutar(numeroGeneraciones)
  p "====== SOLUCION PERFECTA ======" if nreinas.mejorCromosoma.aptitud == 0
  p "El mejor cromosoma es #{nreinas.mejorCromosoma} que tiene #{-nreinas.mejorCromosoma.aptitud} conflictos."
  p "El espacio de búsqueda es #{espacioBusqueda} y se hicieron #{nreinas.numeroDeEvaluaciones} evaluaciones (#{(100.0*nreinas.numeroDeEvaluaciones)/espacioBusqueda} %)"
end





