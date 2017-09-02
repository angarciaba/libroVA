require File.join(File.dirname(__FILE__), '..', '..', 'NReinas.rb')
require 'rspec'






class AlgoritmoGeneticoTest < AlgoritmoGeneticoNReinas

  def initialize(cuantasReinas=1, cuantosCromosomas=1)
    super(cuantasReinas, cuantosCromosomas)
  end

  def evaluar(cromosoma)
    super(cromosoma)
  end
end

