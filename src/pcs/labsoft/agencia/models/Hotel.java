package pcs.labsoft.agencia.models;

public class Hotel {
	private final int id;
	private String nome;
	private double preco;
	
	public Hotel(String nome, double preco, int id)
	{
		this.nome = nome;
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
}
