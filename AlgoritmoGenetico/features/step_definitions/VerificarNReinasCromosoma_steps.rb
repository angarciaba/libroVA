# encoding: utf-8
# Archivo: VerificarNReinas_steps.rb
# Autor: Ángel García Baños
# Email: angarciaba@gmail.com y angel.garcia@correounivalle.edu.co
# Fecha creación: 2014-11-08
# Fecha última modificación: 2015-05-19
# Versión: 0.1
# Licencia: GPL

Dado /^que los Cromosomas van a ser de (\d+) genes$/  do |numeroDeGenes|
  @numeroDeGenes = numeroDeGenes.to_i
  @cromosomas = Hash.new(Cromosoma.new(@numeroDeGenes))
end


Y /^tengo un Cromosoma(.+?) con todos los genes distintos$/ do |nombreDeUnCromosoma|
  @cromosomas[nombreDeUnCromosoma] = Cromosoma.new(@numeroDeGenes)
end


Y /^copio el Cromosoma(.+?) al Cromosoma(.+?)$/ do |nombreDeUnCromosoma, nombreDelOtroCromosoma|
  @cromosomas[nombreDelOtroCromosoma] = @cromosomas[nombreDeUnCromosoma].clone
end


Cuando /^muto el Cromosoma(.+?) (\d+) veces$/ do |nombreDeUnCromosoma, numeroDeVeces|
  numeroDeVeces.to_i.times { @cromosomas[nombreDeUnCromosoma].mutar! }
end


Entonces /^todos los genes del Cromosoma(.+?) deben ser distintos$/ do |nombreDeUnCromosoma|
  vecesQueEstaCadaGen = Hash.new(0)
  @cromosomas[nombreDeUnCromosoma].each { |gen| vecesQueEstaCadaGen[gen] += 1 }
  vecesQueEstaCadaGen.each_value { |veces| expect(veces).to eq(1) }
end

Y /^el Cromosoma(.+?) debe ser distinto al Cromosoma(.+?)$/ do |nombreDeUnCromosoma, nombreDelOtroCromosoma|
  iguales = @cromosomas[nombreDeUnCromosoma].zip(@cromosomas[nombreDelOtroCromosoma]).reduce(true) { |acumulado, genes| genes[0] == genes[1] ? acumulado : false }
  expect(iguales).not_to eq(true)
end

Entonces(/^la diferencia entre el Cromosoma(.+?) y el Cromosoma(.+?) deben ser (.+?) genes$/) do |nombreDeUnCromosoma, nombreDelOtroCromosoma, numeroDeGenesDistintos|
  diferencia = @cromosomas[nombreDeUnCromosoma].zip(@cromosomas[nombreDelOtroCromosoma]).reduce(0) { |acumulado, genes| genes[0] == genes[1] ? acumulado : acumulado+1 }
  expect(diferencia).to eq(numeroDeGenesDistintos.to_i)
end

Cuando(/^cruzo el Cromosoma(.+?) con el Cromosoma(.+?) dando como resultado el Cromosoma(\d+)$/) do |nombreDeUnCromosoma, nombreDelOtroCromosoma, nombreDelCromosomaHijo|
  @cromosomas[nombreDelCromosomaHijo] = @cromosomas[nombreDeUnCromosoma].cruzar(@cromosomas[nombreDelOtroCromosoma])
end

Entonces(/^el Cromosoma(.+?) debe tener los genes del Cromosoma(.+?) o el Cromosoma(.+?)$/) do |nombreDelCromosomaHijo, nombreDeUnCromosoma, nombreDelOtroCromosoma|
  correcto =  @cromosomas[nombreDeUnCromosoma].zip(@cromosomas[nombreDelOtroCromosoma]).zip(@cromosomas[nombreDelCromosomaHijo]).reduce(true) { |acumulado, genes| genes[1] == genes[0][0] or genes[1] == genes[0][1] ? acumulado : false }
  expect(correcto).to eq(true) 
end



