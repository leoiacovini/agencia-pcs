package pcs.labsoft.agencia.models;

public class MeioDeTransporte {
    private final int id;
	private String nome;
	private double preco;
	private final String tipo;
	
	public MeioDeTransporte(String tipo, double preco, int id)
	{
		this.tipo = tipo;
		this.preco = preco;
		this.id = id;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public double getPreco()
	{
		return preco;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getTipo()
	{
		return tipo;
	}
}
